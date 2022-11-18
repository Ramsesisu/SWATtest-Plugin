package me.rqmses.swattest.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import me.rqmses.swattest.SWATtest;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class ItemDropListener implements Listener {
  public static final HashMap<String, Long> cooldowns = new HashMap<>();
  
  public static final HashMap<String, Integer> cooldowntimes = new HashMap<>();
  
  List<Entity> nearPlayers = new ArrayList<>();
  
  Player nextplayer;
  
  @EventHandler
  public void onItemDrop(PlayerDropItemEvent event) {
    ItemStack flash = new ItemStack(Material.SLIME_BALL);
    ItemMeta flashmeta = flash.getItemMeta();
    flashmeta.setDisplayName(ChatColor.GRAY + "Blendgranate");
    flash.setItemMeta(flashmeta);
    if (event.getItemDrop().getItemStack().getType() == Material.FEATHER)
      event.setCancelled(true); 
    if (event.getItemDrop().getItemStack().getType() == Material.LEATHER_CHESTPLATE)
      event.setCancelled(true); 
    if (event.getItemDrop().getItemStack().getType() == Material.ELYTRA)
      event.setCancelled(true); 
    if (event.getItemDrop().getItemStack().getType() == Material.SHIELD)
      event.setCancelled(true); 
    if (event.getItemDrop().getItemStack().getType() == Material.SLIME_BALL)
      event.setCancelled(true); 
    if (event.getItemDrop().getItemStack().getType() == Material.WOOD_HOE)
      event.setCancelled(true); 
    if (event.getItemDrop().getItemStack().getType() == Material.BLAZE_POWDER)
      event.setCancelled(true); 
    if (event.getItemDrop().getItemStack().getType() == Material.GOLD_AXE)
      event.setCancelled(true); 
  }
  
  @EventHandler
  public boolean onFlashDrop(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
      if (player.getInventory().getItemInMainHand().getType() == Material.SLIME_BALL) {
        PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.valueOf(false));
        if (cooldowns.containsKey(event.getPlayer().getName())) {
          long secondsLeft = ((Long)cooldowns.get(player.getName())).longValue() + ((Integer)cooldowntimes.get(player.getName())).intValue() - System.currentTimeMillis();
          if (secondsLeft > 0L) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
            return true;
          } 
        } 
        cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
        event.setCancelled(true);
        ItemStack flash = new ItemStack(Material.SLIME_BALL);
        ItemMeta flashmeta = flash.getItemMeta();
        flashmeta.setDisplayName(ChatColor.GRAY + "Blendgranate");
        flash.setItemMeta(flashmeta);
        Item thrownFlash = player.getWorld().dropItem(player.getLocation(), flash);
        Vector flashvel = player.getLocation().getDirection();
        flashvel.multiply(1.225D);
        thrownFlash.setVelocity(flashvel);
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        cooldowntimes.put(player.getName(), Integer.valueOf(3000));
      }  
    return true;
  }
  
  @EventHandler
  public void onItemSpawn(ItemSpawnEvent event) {
    if (event.getEntity().getItemStack().getType() == Material.SLIME_BALL)
      Bukkit.getScheduler().runTaskLater((Plugin)SWATtest.plugin, () -> {
            event.getEntity().remove();
            this.nearPlayers.clear();
            this.nearPlayers = new ArrayList<>(getEntitiesAroundPoint(event.getEntity().getLocation(), 5.0D));
            for (Entity nearPlayer : this.nearPlayers) {
              if (nearPlayer instanceof Player) {
                this.nextplayer = (Player)nearPlayer;
                Random rand = new Random();
                if (PlayerDeathListener.spawnprotection.get(this.nextplayer.getName()) != null && !((Boolean)PlayerDeathListener.spawnprotection.get(this.nextplayer.getName())).booleanValue()) {
                  this.nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rand.nextInt(140) + 60, 0));
                  this.nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rand.nextInt(200) + 200, 0));
                } 
              } 
            } 
            this.nearPlayers.clear();
            this.nearPlayers = new ArrayList<>(getEntitiesAroundPoint(event.getEntity().getLocation(), 10.0D));
            for (Entity nearPlayer : this.nearPlayers) {
              if (nearPlayer instanceof Player) {
                this.nextplayer = (Player)nearPlayer;
                Random rand = new Random();
                if (PlayerDeathListener.spawnprotection.get(this.nextplayer.getName()) != null && !((Boolean)PlayerDeathListener.spawnprotection.get(this.nextplayer.getName())).booleanValue()) {
                  this.nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rand.nextInt(100), 0));
                  this.nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rand.nextInt(200), 0));
                } 
              } 
            } 
          },60L);
  }
  
  @EventHandler
  public void onItemPickUp(EntityPickupItemEvent event) {
    if (event.getItem().getItemStack().getType() == Material.SLIME_BALL)
      event.setCancelled(true); 
  }
  
  public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
    List<Entity> entities = new ArrayList<>();
    entities.clear();
    World world = location.getWorld();
    int smallX = (int)Math.floor((location.getX() - radius) / 16.0D);
    int bigX = (int)Math.floor((location.getX() + radius) / 16.0D);
    int smallZ = (int)Math.floor((location.getZ() - radius) / 16.0D);
    int bigZ = (int)Math.floor((location.getZ() + radius) / 16.0D);
    for (int x = smallX; x <= bigX; x++) {
      for (int z = smallZ; z <= bigZ; z++) {
        if (world.isChunkLoaded(x, z))
          entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities())); 
      } 
    } 
    entities.removeIf(entity -> (entity.getLocation().distanceSquared(location) > radius * radius));
    return entities;
  }
}