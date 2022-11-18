package me.rqmses.swattest.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import me.rqmses.swattest.SWATtest;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class PlayerInteractListener implements Listener {
  public static final HashMap<String, Long> cooldowns = new HashMap<>();
  
  public static final HashMap<String, Integer> cooldowntimes = new HashMap<>();
  
  public static final HashMap<String, Boolean> rpgcooldown = new HashMap<>();
  
  public static final HashMap<String, Integer> tazerstatus = new HashMap<>();
  
  public static final HashMap<String, String[]> tazerreloadmsg = (HashMap)new HashMap<>();
  
  public static final HashMap<String, BukkitTask> tazertask = new HashMap<>();
  
  public static final HashMap<String, Long> flammilast = new HashMap<>();
  
  public static final HashMap<String, BukkitTask> flammireleasetask = new HashMap<>();
  
  public static final HashMap<String, BukkitTask> flammifinaltask = new HashMap<>();
  
  public static final HashMap<String, Boolean> flammiloading = new HashMap<>();
  
  public static final HashMap<String, Boolean> flammiloaded = new HashMap<>();
  
  public static final HashMap<String, Long> flammiloadingcounter = new HashMap<>();
  
  public static final HashMap<String, String> flammireloadmsg = new HashMap<>();
  
  @EventHandler
  public static boolean onPlayerUse(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if (player.getGameMode() == GameMode.SPECTATOR)
      return false; 
    if (player.isFlying())
      return false; 
    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_BARDING) {
        if (cooldowns.containsKey(event.getPlayer().getName())) {
          long secondsLeft = (Long) cooldowns.get(event.getPlayer().getName()) + (Integer) cooldowntimes.get(event.getPlayer().getName()) - System.currentTimeMillis();
          if (secondsLeft > 0L) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
            return true;
          } 
        } 
        if (((Boolean)PlayerDeathListener.spawnprotection.get(player.getName())).booleanValue()) {
          PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.valueOf(false));
          player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
        } 
        cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
        event.setCancelled(true);
        ItemStack gun = player.getInventory().getItemInMainHand();
        ItemMeta meta = gun.getItemMeta();
        String strlore = String.valueOf(meta.getLore());
        String[] ammos = strlore.split("/");
        ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
        ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
        int ammo = Integer.parseInt(ammos[0]);
        int allammo = Integer.parseInt(ammos[1]);
        if (ammo > 0) {
          PlayerDamageListener shooter = new PlayerDamageListener();
          shooter.setShooter(player.getName());
          ArrayList<String> lore = new ArrayList<>();
          String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
          lore.add(templore);
          meta.setLore(lore);
          gun.setItemMeta(meta);
          player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
          Vector playerDirection = player.getLocation().getDirection();
          Arrow bullet = (Arrow)player.launchProjectile(Arrow.class, playerDirection);
          bullet.setCustomName("m4");
          bullet.setVelocity(bullet.getVelocity().multiply(3.75D));
          bullet.setGravity(false);
          bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
          Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers().forEach(nearPlayer -> {
                if (nearPlayer.getLocation().distance(player.getLocation()) <= 50.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 100.0F, 0.55F);
                  nearPlayer.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 0.1F, 0.0F);
                } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 100.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 50.0F, 0.55F);
                } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 150.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 25.0F, 0.55F);
                } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 200.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5.0F, 0.55F);
                } 
              });
          cooldowntimes.put(player.getName(), Integer.valueOf(400));
        } else {
          reloadGun(player, 4000, event.getItem(), 21);
        } 
        return true;
      } 
      if (player.getInventory().getItemInMainHand().getType() == Material.STONE_HOE) {
        if (cooldowns.containsKey(event.getPlayer().getName())) {
          long secondsLeft = ((Long)cooldowns.get(player.getName())).longValue() + ((Integer)cooldowntimes.get(player.getName())).intValue() - System.currentTimeMillis();
          if (secondsLeft > 0L) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
            return true;
          } 
        } 
        if (((Boolean)PlayerDeathListener.spawnprotection.get(player.getName())).booleanValue()) {
          PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.valueOf(false));
          player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
        } 
        cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
        event.setCancelled(true);
        ItemStack gun = player.getInventory().getItemInMainHand();
        ItemMeta meta = gun.getItemMeta();
        String strlore = String.valueOf(meta.getLore());
        String[] ammos = strlore.split("/");
        ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
        ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
        int ammo = Integer.parseInt(ammos[0]);
        int allammo = Integer.parseInt(ammos[1]);
        if (ammo > 0) {
          PlayerDamageListener shooter = new PlayerDamageListener();
          shooter.setShooter(player.getName());
          ArrayList<String> lore = new ArrayList<>();
          String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
          lore.add(templore);
          meta.setLore(lore);
          gun.setItemMeta(meta);
          player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
          Vector playerDirection = player.getLocation().getDirection();
          Arrow bullet = (Arrow)player.launchProjectile(Arrow.class, playerDirection);
          bullet.setCustomName("sniper");
          bullet.setVelocity(bullet.getVelocity().multiply(6));
          bullet.setGravity(false);
          bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
          Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers().forEach(nearPlayer -> {
                if (nearPlayer.getLocation().distance(player.getLocation()) <= 50.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 100.0F, 0.0F);
                  nearPlayer.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 0.1F, 0.0F);
                } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 100.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 50.0F, 0.0F);
                } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 150.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 25.0F, 0.0F);
                } 
                if (nearPlayer.getLocation().distance(player.getLocation()) <= 200.0D)
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5.0F, 0.0F); 
              });
          cooldowntimes.put(player.getName(), Integer.valueOf(5000));
        } else {
          reloadGun(player, 10000, event.getItem(), 5);
        } 
        return true;
      } 
      if (player.getInventory().getItemInMainHand().getType() == Material.GOLD_BARDING) {
        if (cooldowns.containsKey(event.getPlayer().getName())) {
          long secondsLeft = ((Long)cooldowns.get(player.getName())).longValue() + ((Integer)cooldowntimes.get(player.getName())).intValue() - System.currentTimeMillis();
          if (secondsLeft > 0L) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
            return true;
          } 
        } 
        if (((Boolean)PlayerDeathListener.spawnprotection.get(player.getName())).booleanValue()) {
          PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.valueOf(false));
          player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
        } 
        cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
        event.setCancelled(true);
        ItemStack gun = player.getInventory().getItemInMainHand();
        ItemMeta meta = gun.getItemMeta();
        String strlore = String.valueOf(meta.getLore());
        String[] ammos = strlore.split("/");          
        ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
        ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
        int ammo = Integer.parseInt(ammos[0]);
        int allammo = Integer.parseInt(ammos[1]);
        if (ammo > 0) {
          PlayerDamageListener shooter = new PlayerDamageListener();
          shooter.setShooter(player.getName());
          ArrayList<String> lore = new ArrayList<>();
          String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
          lore.add(templore);
          meta.setLore(lore);
          gun.setItemMeta(meta);
          player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
          Vector playerDirection = player.getLocation().getDirection();
          Arrow bullet = (Arrow)player.launchProjectile(Arrow.class, playerDirection);
          bullet.setCustomName("mp5");
          bullet.setVelocity(bullet.getVelocity().multiply(5));
          bullet.setGravity(false);
          bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
          Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers().forEach(nearPlayer -> {
                if (nearPlayer.getLocation().distance(player.getLocation()) <= 50.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 100.0F, 1.0F);
                  nearPlayer.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 0.1F, 0.0F);
                } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 100.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 50.0F, 1.0F);
                } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 150.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 25.0F, 1.0F);
                } 
                if (nearPlayer.getLocation().distance(player.getLocation()) <= 200.0D)
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5.0F, 1.0F); 
              });
          cooldowntimes.put(player.getName(), Integer.valueOf(300));
        } else {
          reloadGun(player, 3000, event.getItem(), 25);
        } 
        return true;
      } 
      if (player.getInventory().getItemInMainHand().getType() == Material.GOLD_HOE) {
        if (cooldowns.containsKey(event.getPlayer().getName())) {
          long secondsLeft = ((Long)cooldowns.get(player.getName())).longValue() + ((Integer)cooldowntimes.get(player.getName())).intValue() - System.currentTimeMillis();
          if (secondsLeft > 0L) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
            return true;
          } 
        } 
        if (((Boolean)PlayerDeathListener.spawnprotection.get(player.getName())).booleanValue()) {
          PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.valueOf(false));
          player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
        } 
        cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
        event.setCancelled(true);
        ItemStack gun = player.getInventory().getItemInMainHand();
        ItemMeta meta = gun.getItemMeta();
        String strlore = String.valueOf(meta.getLore());
        String[] ammos = strlore.split("/");
        ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
        ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
        int ammo = Integer.parseInt(ammos[0]);
        int allammo = Integer.parseInt(ammos[1]);
        if (ammo > 0) {
          PlayerDamageListener shooter = new PlayerDamageListener();
          shooter.setShooter(player.getName());
          ArrayList<String> lore = new ArrayList<>();
          String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
          lore.add(templore);
          meta.setLore(lore);
          gun.setItemMeta(meta);
          player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
          Vector playerDirection = player.getLocation().getDirection();
          Arrow bullet = (Arrow)player.launchProjectile(Arrow.class, playerDirection);
          bullet.setCustomName("jagdflinte");
          bullet.setVelocity(bullet.getVelocity().multiply(3));
          bullet.setGravity(false);
          bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
          Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers().forEach(nearPlayer -> {
                if (nearPlayer.getLocation().distance(player.getLocation()) <= 50.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 100.0F, -0.5F);
                  nearPlayer.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 0.1F, 0.0F);
                } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 100.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 50.0F, -0.5F);
                } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 150.0D) {
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 25.0F, -0.5F);
                } 
                if (nearPlayer.getLocation().distance(player.getLocation()) <= 200.0D)
                  nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5.0F, -0.5F); 
              });
          cooldowntimes.put(player.getName(), Integer.valueOf(3000));
        } else {
          reloadGun(player, 6000, event.getItem(), 5);
        } 
        return true;
      } 
      if (player.getInventory().getItemInMainHand().getType() == Material.GOLD_AXE) {
        if (((Boolean)rpgcooldown.get(player.getName())).booleanValue()) {
          if (cooldowns.containsKey(event.getPlayer().getName())) {
            long secondsLeft = ((Long)cooldowns.get(player.getName())).longValue() + ((Integer)cooldowntimes.get(player.getName())).intValue() - System.currentTimeMillis();
            if (secondsLeft > 0L) {
              player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
              return true;
            } 
          } 
          if (((Boolean)PlayerDeathListener.spawnprotection.get(player.getName())).booleanValue()) {
            PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.valueOf(false));
            player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
          } 
          cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
          event.setCancelled(true);
          ItemStack gun = player.getInventory().getItemInMainHand();
          ItemMeta meta = gun.getItemMeta();
          String strlore = String.valueOf(meta.getLore());
          String[] ammos = strlore.split("/");
          ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
          ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
          int ammo = Integer.parseInt(ammos[0]);
          int allammo = Integer.parseInt(ammos[1]);
          if (ammo > 0) {
            PlayerDamageListener shooter = new PlayerDamageListener();
            shooter.setShooter(player.getName());
            ArrayList<String> lore = new ArrayList<>();
            String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
            lore.add(templore);
            meta.setLore(lore);
            gun.setItemMeta(meta);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
            Vector playerDirection = player.getLocation().getDirection();
            Arrow bullet = (Arrow)player.launchProjectile(Arrow.class, playerDirection);
            bullet.setCustomName("rpg");
            bullet.setVelocity(bullet.getVelocity().multiply(7));
            bullet.setGravity(false);
            bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
            cooldowntimes.put(player.getName(), Integer.valueOf(0));
          } else {
            reloadGun(player, 80000, event.getItem(), 1);
          } 
        } else {
          player.sendMessage(ChatColor.GRAY + "Du kannst deine RPG noch nicht benutzen.");
        } 
        return true;
      } 
      if (player.getInventory().getItemInMainHand().getType() == Material.WOOD_HOE) {
        if (!tazerstatus.containsKey(player.getName()))
          tazerstatus.put(player.getName(), Integer.valueOf(10)); 
        if (((Integer)tazerstatus.get(player.getName())).intValue() >= 10) {
          if (((Boolean)PlayerDeathListener.spawnprotection.get(player.getName())).booleanValue()) {
            PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.valueOf(false));
            player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
          } 
          tazerstatus.put(player.getName(), Integer.valueOf(1));
          Location origin = player.getEyeLocation();
          Vector direction = origin.getDirection();
          direction.normalize();
          for (int i = 0; i < 20.0D; i++) {
            Location loc = origin.add(direction);
            loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.subtract(direction.clone().multiply(0.75D)), 1, 0.05D, 0.05D, 0.05D, 0.0D);
          } 
          List<Entity> nearbyE = player.getNearbyEntities(5.0D, 5.0D, 5.0D);
          ArrayList<LivingEntity> livingE = new ArrayList<>();
          for (Entity e : nearbyE) {
            if (e instanceof LivingEntity)
              livingE.add((LivingEntity)e); 
          } 
          BlockIterator bItr = new BlockIterator((LivingEntity)player, 5);
          while (bItr.hasNext()) {
            Block block = bItr.next();
            int bx = block.getX();
            int by = block.getY();
            int bz = block.getZ();
            for (LivingEntity e : livingE) {
              Location loc = e.getLocation();
              double ex = loc.getX();
              double ey = loc.getY();
              double ez = loc.getZ();
              if (bx - 0.75D <= ex && ex <= bx + 1.75D && bz - 0.75D <= ez && ez <= bz + 1.75D && (by - 1) <= ey && ey <= by + 2.5D) {
                LivingEntity target = e;
                if (target.getHealth() >= 1.0D)
                  target.damage(1.0D); 
                target.setGlowing(true);
                LivingEntity finalTarget = target;
                BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater((Plugin) SWATtest.plugin, () -> {
                  finalTarget.setGlowing(false);
                  finalTarget.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 7, 1));
                  finalTarget.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 16, 3));
                  finalTarget.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 16, 255));
                }, 10L);
                return true;
              } 
            } 
          } 
        } 
        return true;
      } 
      if (player.getInventory().getItemInMainHand().getType() == Material.BLAZE_POWDER && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
        ItemStack flammi = player.getInventory().getItemInMainHand();
        ItemMeta meta = flammi.getItemMeta();
        String strlore = String.valueOf(meta.getLore());
        String[] ammos = strlore.split("/");
        ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
        int ammo = Integer.parseInt(ammos[0]);
        if (ammo == 0) {
          event.setCancelled(true);
          return true;
        } 
        if (((Boolean)PlayerDeathListener.spawnprotection.get(player.getName())).booleanValue()) {
          PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.valueOf(false));
          player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
        } 
        flammilast.putIfAbsent(player.getName(), Long.valueOf(1L));
        flammiloaded.putIfAbsent(player.getName(), Boolean.valueOf(false));
        flammiloading.putIfAbsent(player.getName(), Boolean.valueOf(false));
        flammiloadingcounter.putIfAbsent(player.getName(), Long.valueOf(100L));
        flammireleasetask.putIfAbsent(player.getName(), null);
        flammifinaltask.putIfAbsent(player.getName(), null);
        flammireloadmsg.putIfAbsent(player.getName(), ChatColor.translateAlternateColorCodes('&', "&8&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"));
        if (((Long)flammilast.get(player.getName())).longValue() > 0L) {
          if (System.currentTimeMillis() - ((Long)flammiloadingcounter.get(player.getName())).longValue() >= 1050L)
            if (((Boolean)flammiloading.get(player.getName())).booleanValue()) {
              flammireloadmsg.put(player.getName(), ((String)flammireloadmsg.get(player.getName())).replaceFirst("8", "a"));
              player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(flammireloadmsg.get(player.getName())));
              flammiloadingcounter.put(player.getName(), flammilast.get(player.getName()));
            } else if (((Boolean)flammiloaded.get(player.getName())).booleanValue()) {
              ArrayList<String> lore = new ArrayList<>();
              String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + 500);
              lore.add(templore);
              meta.setLore(lore);
              flammi.setItemMeta(meta);
              flammiloadingcounter.put(player.getName(), flammilast.get(player.getName()));
            }  
          if (flammifinaltask.get(player.getName()) == null)
            flammifinaltask.put(player.getName(), Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin)SWATtest.plugin, () -> {
                    if (((Boolean)flammiloading.get(player.getName())).booleanValue() && !((Boolean)flammiloaded.get(player.getName())).booleanValue()) {
                      flammiloaded.put(player.getName(), Boolean.valueOf(true));
                      flammiloading.put(player.getName(), Boolean.valueOf(false));
                      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛")));
                    } 
                    flammifinaltask.put(player.getName(), null);
                  }, 200L));
          if (System.currentTimeMillis() - ((Long)flammilast.get(player.getName())).longValue() <= 250L) {
            if (flammireleasetask.get(player.getName()) != null && Bukkit.getServer().getScheduler().isQueued(((BukkitTask)flammireleasetask.get(player.getName())).getTaskId()))
              Bukkit.getServer().getScheduler().cancelTask(((BukkitTask)flammireleasetask.get(player.getName())).getTaskId()); 
            flammireleasetask.put(player.getName(), Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin)SWATtest.plugin, () -> {
                    flammireloadmsg.put(player.getName(), ChatColor.translateAlternateColorCodes('&', "&8&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"));
                    flammiloading.put(player.getName(), Boolean.valueOf(false));
                    flammiloaded.put(player.getName(), Boolean.valueOf(false));
                    if (flammifinaltask.get(player.getName()) != null && Bukkit.getServer().getScheduler().isQueued(((BukkitTask)flammifinaltask.get(player.getName())).getTaskId())) {
                      Bukkit.getServer().getScheduler().cancelTask(((BukkitTask)flammifinaltask.get(player.getName())).getTaskId());
                      flammifinaltask.put(player.getName(), null);
                    } 
                  }, 40L));
          } else {
            flammiloading.put(player.getName(), Boolean.valueOf(true));
          } 
        } 
        flammilast.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
        if (((Boolean)flammiloaded.get(player.getName())).booleanValue()) {
          Location origin = player.getEyeLocation().subtract(0.0D, 0.25D, 0.0D);
          Vector direction = origin.getDirection();
          direction.normalize();
          for (int i = 0; i < 20.0D; i++) {
            Location loc = origin.add(direction);
            loc.getWorld().spawnParticle(Particle.FLAME, loc.subtract(direction.clone().multiply(0.75D)), 1, 0.05D, 0.05D, 0.05D, 0.0D);
          } 
          List<Entity> nearbyE = player.getNearbyEntities(5.0D, 5.0D, 5.0D);
          ArrayList<LivingEntity> livingE = new ArrayList<>();
          for (Entity e : nearbyE) {
            if (e instanceof LivingEntity)
              livingE.add((LivingEntity)e); 
          } 
          BlockIterator bItr = new BlockIterator((LivingEntity)player, 5);
          while (bItr.hasNext()) {
            Block block = bItr.next();
            int bx = block.getX();
            int by = block.getY();
            int bz = block.getZ();
            for (LivingEntity e : livingE) {
              Location loc = e.getLocation();
              double ex = loc.getX();
              double ey = loc.getY();
              double ez = loc.getZ();
              if (bx - 0.75D <= ex && ex <= bx + 1.75D && bz - 0.75D <= ez && ez <= bz + 1.75D && (by - 1) <= ey && ey <= by + 2.5D) {
                LivingEntity target = e;
                target.damage(1.0D);
                target.setFireTicks(100);
              } 
            } 
          } 
        } 
      } 
    } 
    if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
      if (player.getInventory().getItemInMainHand().getType() == Material.STONE_HOE && !Objects.equals(player.getCustomName(), "brokenleg") && player.isSneaking())
        if (Objects.equals(player.getCustomName(), "zoom")) {
          player.removePotionEffect(PotionEffectType.SLOW);
          player.removePotionEffect(PotionEffectType.JUMP);
          player.setCustomName("");
        } else {
          player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2147483647, 255));
          player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, 200));
          player.setCustomName("zoom");
        }   
    return false;
  }
  
  @EventHandler
  public void onItemDrop(PlayerDropItemEvent event) {
    Player player = event.getPlayer();
    if (player.getGameMode() == GameMode.SPECTATOR)
      return; 
    if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_BARDING) {
      event.setCancelled(true);
      reloadGun(player, 4000, event.getItemDrop().getItemStack(), 21);
    } 
    if (event.getItemDrop().getItemStack().getType() == Material.STONE_HOE) {
      event.setCancelled(true);
      reloadGun(player, 10000, event.getItemDrop().getItemStack(), 5);
    } 
    if (event.getItemDrop().getItemStack().getType() == Material.GOLD_BARDING) {
      event.setCancelled(true);
      reloadGun(player, 3000, event.getItemDrop().getItemStack(), 25);
    } 
    if (event.getItemDrop().getItemStack().getType() == Material.GOLD_HOE) {
      event.setCancelled(true);
      reloadGun(player, 6000, event.getItemDrop().getItemStack(), 5);
    } 
    if (event.getItemDrop().getItemStack().getType() == Material.GOLD_AXE) {
      event.setCancelled(true);
      reloadGun(player, 80000, event.getItemDrop().getItemStack(), 1);
    } 
  }
  
  @EventHandler
  public void onItemSwitch(PlayerItemHeldEvent event) {
    final Player player = event.getPlayer();
    ItemStack stack1 = event.getPlayer().getInventory().getItem(event.getNewSlot());
    ItemStack stack2 = event.getPlayer().getInventory().getItem(event.getPreviousSlot());
    tazertask.putIfAbsent(player.getName(), null);
    if (stack1 != null && 
      player.getInventory().getItem(event.getNewSlot()).getType() == Material.WOOD_HOE) {
      if (!tazerstatus.containsKey(player.getName()))
        tazerstatus.put(player.getName(), Integer.valueOf(10)); 
      tazerreloadmsg.put(player.getName(), new String[]{(ChatColor.translateAlternateColorCodes('&', "&eRecharge... &7» &8&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"))});
      if (((Integer)tazerstatus.get(player.getName())).intValue() < 10) {
        final int[] temptazerstatus = { ((Integer)tazerstatus.get(player.getName())).intValue() };
        for (int i = 0; i < 10 && 
          i < temptazerstatus[0] - 1; i++) {
          tazerreloadmsg.put(player.getName(), new String[] { ((String[])tazerreloadmsg.get(player.getName()))[0].replaceFirst("8", "a") });
        } 
        BukkitRunnable reload = new BukkitRunnable() {
            public void run() {
              temptazerstatus[0] = temptazerstatus[0] + 1;
              PlayerInteractListener.tazerreloadmsg.put(player.getName(), new String[] { ((String[])PlayerInteractListener.tazerreloadmsg.get(player.getName()))[0].replaceFirst("8", "a") });
              player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(((String[])PlayerInteractListener.tazerreloadmsg.get(player.getName()))[0]));
              PlayerInteractListener.tazerstatus.put(player.getName(), Integer.valueOf(temptazerstatus[0]));
              if (temptazerstatus[0] >= 10) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&eRecharge... &7&aComplete")));
                cancel();
              } 
            }
          };
        tazertask.put(player.getName(), reload.runTaskTimer((Plugin)SWATtest.plugin, 0L, 100L));
      } 
    } 
    if (stack2 != null && 
      player.getInventory().getItem(event.getPreviousSlot()).getType() == Material.WOOD_HOE && 
      tazertask.get(player.getName()) != null) {
      ((BukkitTask)tazertask.get(player.getName())).cancel();
      if (((Integer)tazerstatus.get(player.getName())).intValue() < 10)
        tazerstatus.put(player.getName(), Integer.valueOf(((Integer)tazerstatus.get(player.getName())).intValue() - 1)); 
    } 
  }
  
  public static void reloadGun(Player player, int cooldown, ItemStack gun, int maxammo) {
    cooldowntimes.put(player.getName(), Integer.valueOf(cooldown));
    ItemMeta meta = gun.getItemMeta();
    String strlore = String.valueOf(meta.getLore());
    String[] ammos = strlore.split("/");
    ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
    ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
    int ammo = Integer.parseInt(ammos[0]);
    int allammo = Integer.parseInt(ammos[1]);
    if (ammo != maxammo) {
      if (ammo + allammo >= maxammo) {
        allammo -= maxammo - ammo;
        ammo += maxammo - ammo;
      } else {
        ammo += allammo;
        allammo = 0;
      } 
      ArrayList<String> lore = new ArrayList<>();
      String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
      lore.add(templore);
      meta.setLore(lore);
      gun.setItemMeta(meta);
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
      player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100.0F, 0.0F);
    } 
  }
}
