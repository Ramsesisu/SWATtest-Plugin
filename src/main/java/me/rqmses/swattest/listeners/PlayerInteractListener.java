package me.rqmses.swattest.listeners;

import me.rqmses.swattest.SWATtest;
import me.rqmses.swattest.global.SoundUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;

public class PlayerInteractListener implements Listener {
  public static final HashMap<UUID, Long> cooldowns = new HashMap<>();
  
  public static final HashMap<UUID, Integer> cooldowntimes = new HashMap<>();
  
  public static final HashMap<String, Boolean> rpgcooldown = new HashMap<>();
  
  public static final HashMap<String, Integer> tazerstatus = new HashMap<>();
  
  public static final HashMap<String, String[]> tazerreloadmsg = new HashMap<>();
  
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
        shootM4(player);
        return true;
      } 
      if (player.getInventory().getItemInMainHand().getType() == Material.STONE_HOE) {
        shootSniper(player);
        return true;
      } 
      if (player.getInventory().getItemInMainHand().getType() == Material.GOLD_BARDING) {
        shootMP5(player);
        return true;
      } 
      if (player.getInventory().getItemInMainHand().getType() == Material.GOLD_HOE) {
        shootJagdflinte(player);
        return true;
      } 
      if (player.getInventory().getItemInMainHand().getType() == Material.GOLD_AXE) {
        if (rpgcooldown.get(player.getName())) {
          if (cooldowns.containsKey(event.getPlayer().getUniqueId())) {
            long secondsLeft = cooldowns.get(player.getUniqueId()) + cooldowntimes.get(player.getUniqueId()) - System.currentTimeMillis();
            if (secondsLeft > 0L) {
              player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
              return true;
            } 
          } 
          if (PlayerDeathListener.spawnprotection.get(player.getName())) {
            PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.FALSE);
            player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
          } 
          cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
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
            ArrayList<String> lore = new ArrayList<>();
            String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
            lore.add(templore);
            meta.setLore(lore);
            gun.setItemMeta(meta);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
            Vector playerDirection = player.getLocation().getDirection();
            Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
            bullet.setCustomName("rpg-"+player.getName());
            bullet.setVelocity(bullet.getVelocity().multiply(7));
            bullet.setGravity(false);
            bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
            cooldowntimes.put(player.getUniqueId(), 0);
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
          tazerstatus.put(player.getName(), 10);
        if (tazerstatus.get(player.getName()) >= 10) {
          if (PlayerDeathListener.spawnprotection.get(player.getName())) {
            PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.FALSE);
            player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
          } 
          tazerstatus.put(player.getName(), 1);
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
          BlockIterator bItr = new BlockIterator(player, 5);
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
                if (e.getHealth() >= 1.0D)
                  e.damage(1.0D);
                e.setGlowing(true);
                Bukkit.getScheduler().runTaskLater(SWATtest.plugin, () -> {
                  e.setGlowing(false);
                  e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 7*10, 1));
                  e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 16*20, 3));
                  e.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 16*20, 200));
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
        if (PlayerDeathListener.spawnprotection.get(player.getName())) {
          PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.FALSE);
          player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
        } 
        flammilast.putIfAbsent(player.getName(), 1L);
        flammiloaded.putIfAbsent(player.getName(), Boolean.FALSE);
        flammiloading.putIfAbsent(player.getName(), Boolean.FALSE);
        flammiloadingcounter.putIfAbsent(player.getName(), 100L);
        flammireleasetask.putIfAbsent(player.getName(), null);
        flammifinaltask.putIfAbsent(player.getName(), null);
        flammireloadmsg.putIfAbsent(player.getName(), ChatColor.translateAlternateColorCodes('&', "&8&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"));
        if (flammilast.get(player.getName()) > 0L) {
          if (System.currentTimeMillis() - flammiloadingcounter.get(player.getName()) >= 1050L)
            if (flammiloading.get(player.getName())) {
              flammireloadmsg.put(player.getName(), flammireloadmsg.get(player.getName()).replaceFirst("8", "a"));
              player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(flammireloadmsg.get(player.getName())));
              flammiloadingcounter.put(player.getName(), flammilast.get(player.getName()));
            } else if (flammiloaded.get(player.getName())) {
              ArrayList<String> lore = new ArrayList<>();
              String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + 500);
              lore.add(templore);
              meta.setLore(lore);
              flammi.setItemMeta(meta);
              flammiloadingcounter.put(player.getName(), flammilast.get(player.getName()));
            }  
          if (flammifinaltask.get(player.getName()) == null)
            flammifinaltask.put(player.getName(), Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(SWATtest.plugin, () -> {
                    if (flammiloading.get(player.getName()) && !(Boolean) flammiloaded.get(player.getName())) {
                      flammiloaded.put(player.getName(), Boolean.TRUE);
                      flammiloading.put(player.getName(), Boolean.FALSE);
                      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛")));
                    } 
                    flammifinaltask.put(player.getName(), null);
                  }, 200L));
          if (System.currentTimeMillis() - flammilast.get(player.getName()) <= 250L) {
            if (flammireleasetask.get(player.getName()) != null && Bukkit.getServer().getScheduler().isQueued(flammireleasetask.get(player.getName()).getTaskId()))
              Bukkit.getServer().getScheduler().cancelTask(flammireleasetask.get(player.getName()).getTaskId());
            flammireleasetask.put(player.getName(), Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(SWATtest.plugin, () -> {
                    flammireloadmsg.put(player.getName(), ChatColor.translateAlternateColorCodes('&', "&8&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"));
                    flammiloading.put(player.getName(), Boolean.FALSE);
                    flammiloaded.put(player.getName(), Boolean.FALSE);
                    if (flammifinaltask.get(player.getName()) != null && Bukkit.getServer().getScheduler().isQueued(flammifinaltask.get(player.getName()).getTaskId())) {
                      Bukkit.getServer().getScheduler().cancelTask(flammifinaltask.get(player.getName()).getTaskId());
                      flammifinaltask.put(player.getName(), null);
                    } 
                  }, 40L));
          } else {
            flammiloading.put(player.getName(), Boolean.TRUE);
          } 
        } 
        flammilast.put(player.getName(), System.currentTimeMillis());
        if (flammiloaded.get(player.getName())) {
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
          BlockIterator bItr = new BlockIterator(player, 5);
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
                e.damage(1.0D);
                e.setFireTicks(100);
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
        tazerstatus.put(player.getName(), 10);
      tazerreloadmsg.put(player.getName(), new String[]{(ChatColor.translateAlternateColorCodes('&', "&eRecharge... &7» &8&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"))});
      if (tazerstatus.get(player.getName()) < 10) {
        final int[] temptazerstatus = {tazerstatus.get(player.getName())};
        for (int i = 0; i < 10 && 
          i < temptazerstatus[0] - 1; i++) {
          tazerreloadmsg.put(player.getName(), new String[] { ((String[])tazerreloadmsg.get(player.getName()))[0].replaceFirst("8", "a") });
        } 
        BukkitRunnable reload = new BukkitRunnable() {
            public void run() {
              temptazerstatus[0] = temptazerstatus[0] + 1;
              PlayerInteractListener.tazerreloadmsg.put(player.getName(), new String[] { ((String[])PlayerInteractListener.tazerreloadmsg.get(player.getName()))[0].replaceFirst("8", "a") });
              player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(((String[])PlayerInteractListener.tazerreloadmsg.get(player.getName()))[0]));
              PlayerInteractListener.tazerstatus.put(player.getName(), temptazerstatus[0]);
              if (temptazerstatus[0] >= 10) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&eRecharge... &7&aComplete")));
                cancel();
              } 
            }
          };
        tazertask.put(player.getName(), reload.runTaskTimer(SWATtest.plugin, 0L, 100L));
      } 
    } 
    if (stack2 != null && 
      player.getInventory().getItem(event.getPreviousSlot()).getType() == Material.WOOD_HOE && 
      tazertask.get(player.getName()) != null) {
      tazertask.get(player.getName()).cancel();
      if (tazerstatus.get(player.getName()) < 10)
        tazerstatus.put(player.getName(), tazerstatus.get(player.getName()) - 1);
    } 
  }
  
  public static void reloadGun(Player player, int cooldown, ItemStack gun, int maxammo) {
    cooldowntimes.put(player.getUniqueId(), cooldown);
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

  public static void shootM4(Player player) {
    if (cooldowns.containsKey(player.getUniqueId())) {
      long secondsLeft = cooldowns.get(player.getUniqueId()) + cooldowntimes.get(player.getUniqueId()) - System.currentTimeMillis();
      if (secondsLeft > 0L) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
        return;
      }
    }
    if (!player.hasMetadata("NPC")) {
      if (PlayerDeathListener.spawnprotection.get(player.getName())) {
        PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.FALSE);
        player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
      }
    } else {
      if (!(player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_BARDING)) {return;}
    }
    cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    ItemStack gun = player.getInventory().getItemInMainHand();
    ItemMeta meta = gun.getItemMeta();
    String strlore = String.valueOf(meta.getLore());
    String[] ammos = strlore.split("/");
    ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
    ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
    int ammo = Integer.parseInt(ammos[0]);
    int allammo = Integer.parseInt(ammos[1]);
    if (ammo > 0) {
      ArrayList<String> lore = new ArrayList<>();
      String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
      lore.add(templore);
      meta.setLore(lore);
      gun.setItemMeta(meta);
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
      Vector playerDirection = player.getLocation().getDirection();
      Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
      bullet.setCustomName("m4-"+player.getName());
      bullet.setVelocity(bullet.getVelocity().multiply(4.5D));
      bullet.setGravity(false);
      bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
      int range = 100;
      for (Entity target : player.getWorld().getNearbyEntities(player.getLocation(), range, range, range)) {
        if (target instanceof Player) new SoundUtils(SWATtest.plugin, (Player) target, target.getLocation(), range, 0.55F);
      }
      cooldowntimes.put(player.getUniqueId(), 400);
      Bukkit.getScheduler().runTaskLater(SWATtest.plugin, bullet::remove, 60L);
    } else {
      reloadGun(player, 4000, gun, 21);
    }
  }

  public static void shootSniper(Player player) {
    if (cooldowns.containsKey(player.getUniqueId())) {
      long secondsLeft = cooldowns.get(player.getUniqueId()) + cooldowntimes.get(player.getUniqueId()) - System.currentTimeMillis();
      if (secondsLeft > 0L) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
        return;
      }
    }
    if (!player.hasMetadata("NPC")) {
      if (PlayerDeathListener.spawnprotection.get(player.getName())) {
        PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.FALSE);
        player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
      }
    }
    cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    ItemStack gun = player.getInventory().getItemInMainHand();
    ItemMeta meta = gun.getItemMeta();
    String strlore = String.valueOf(meta.getLore());
    String[] ammos = strlore.split("/");
    ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
    ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
    int ammo = Integer.parseInt(ammos[0]);
    int allammo = Integer.parseInt(ammos[1]);
    if (ammo > 0) {
      ArrayList<String> lore = new ArrayList<>();
      String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
      lore.add(templore);
      meta.setLore(lore);
      gun.setItemMeta(meta);
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
      Vector playerDirection = player.getLocation().getDirection();
      Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
      bullet.setCustomName("sniper-"+player.getName());
      bullet.setVelocity(bullet.getVelocity().multiply(6));
      bullet.setGravity(false);
      bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
      int range = 100;
      for (Entity target : player.getWorld().getNearbyEntities(player.getLocation(), range, range, range)) {
        if (target instanceof Player) new SoundUtils(SWATtest.plugin, (Player) target, target.getLocation(), range, 0.0F);
      }
      cooldowntimes.put(player.getUniqueId(), 5000);
      Bukkit.getScheduler().runTaskLater(SWATtest.plugin, bullet::remove, 60L);
    } else {
      reloadGun(player, 10000, gun, 5);
    }
  }

  public static void shootMP5(Player player) {

    if (cooldowns.containsKey(player.getUniqueId())) {
      long secondsLeft = cooldowns.get(player.getUniqueId()) + cooldowntimes.get(player.getUniqueId()) - System.currentTimeMillis();
      if (secondsLeft > 0L) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
        return;
      }
    }
    if (!player.hasMetadata("NPC")) {
      if (PlayerDeathListener.spawnprotection.get(player.getName())) {
        PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.FALSE);
        player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
      }
    }
    cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    ItemStack gun = player.getInventory().getItemInMainHand();
    ItemMeta meta = gun.getItemMeta();
    String strlore = String.valueOf(meta.getLore());
    String[] ammos = strlore.split("/");
    ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
    ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
    int ammo = Integer.parseInt(ammos[0]);
    int allammo = Integer.parseInt(ammos[1]);
    if (ammo > 0) {
      ArrayList<String> lore = new ArrayList<>();
      String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
      lore.add(templore);
      meta.setLore(lore);
      gun.setItemMeta(meta);
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
      Vector playerDirection = player.getLocation().getDirection();
      Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
      bullet.setCustomName("mp5-"+player.getName());
      bullet.setVelocity(bullet.getVelocity().multiply(5.25));
      bullet.setGravity(false);
      bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
      int range = 100;
      for (Entity target : player.getWorld().getNearbyEntities(player.getLocation(), range, range, range)) {
        if (target instanceof Player) new SoundUtils(SWATtest.plugin, (Player) target, target.getLocation(), range, 1.0F);
      }
      cooldowntimes.put(player.getUniqueId(), 300);
      Bukkit.getScheduler().runTaskLater(SWATtest.plugin, bullet::remove, 60L);
    } else {
      reloadGun(player, 3000, gun, 25);
    }
  }

  public static void shootJagdflinte(Player player) {

    if (cooldowns.containsKey(player.getUniqueId())) {
      long secondsLeft = cooldowns.get(player.getUniqueId()) + cooldowntimes.get(player.getUniqueId()) - System.currentTimeMillis();
      if (secondsLeft > 0L) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
        return;
      }
    }
    if (!player.hasMetadata("NPC")) {
      if (PlayerDeathListener.spawnprotection.get(player.getName())) {
        PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.FALSE);
        player.sendMessage(ChatColor.GREEN + "Dein Spawnschutz ist nun vorbei.");
      }
    }
    cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    ItemStack gun = player.getInventory().getItemInMainHand();
    ItemMeta meta = gun.getItemMeta();
    String strlore = String.valueOf(meta.getLore());
    String[] ammos = strlore.split("/");
    ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
    ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
    int ammo = Integer.parseInt(ammos[0]);
    int allammo = Integer.parseInt(ammos[1]);
    if (ammo > 0) {
      ArrayList<String> lore = new ArrayList<>();
      String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
      lore.add(templore);
      meta.setLore(lore);
      gun.setItemMeta(meta);
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));
      Vector playerDirection = player.getLocation().getDirection();
      Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
      bullet.setCustomName("jagdflinte-"+player.getName());
      bullet.setVelocity(bullet.getVelocity().multiply(4));
      bullet.setGravity(false);
      bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
      int range = 100;
      for (Entity target : player.getWorld().getNearbyEntities(player.getLocation(), range, range, range)) {
        if (target instanceof Player) new SoundUtils(SWATtest.plugin, (Player) target, target.getLocation(), range, -0.5F);
      }
      cooldowntimes.put(player.getUniqueId(), 3000);
      Bukkit.getScheduler().runTaskLater(SWATtest.plugin, bullet::remove, 60L);
    } else {
      reloadGun(player, 6000, gun, 5);
    }
  }
}
