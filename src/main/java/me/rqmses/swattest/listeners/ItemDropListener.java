package me.rqmses.swattest.listeners;

import me.rqmses.swattest.SWATtest;
import org.bukkit.*;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

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
    if (event.getItemDrop().getItemStack().getType() == Material.SLIME_BALL) {
      dropFlash(event.getPlayer());
      event.setCancelled(true);
    }
    if (event.getItemDrop().getItemStack().getType() == Material.WOOD_HOE)
      event.setCancelled(true); 
    if (event.getItemDrop().getItemStack().getType() == Material.BLAZE_POWDER)
      event.setCancelled(true); 
    if (event.getItemDrop().getItemStack().getType() == Material.GOLD_AXE)
      event.setCancelled(true); 
  }
  
  @EventHandler
  public void onFlashRightClick(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      if (player.getInventory().getItemInMainHand().getType() == Material.SLIME_BALL) {
        event.setCancelled(true);
        dropFlash(player);
      }
    }
  }

  public void dropFlash(Player player) {
      PlayerDeathListener.spawnprotection.put(player.getName(), Boolean.FALSE);
      if (cooldowns.containsKey(player.getName())) {
        long secondsLeft = cooldowns.get(player.getName()) + cooldowntimes.get(player.getName()) - System.currentTimeMillis();
        if (secondsLeft > 0L) {
          return;
        }
      }
      cooldowns.put(player.getName(), System.currentTimeMillis());
      ItemStack flash = new ItemStack(Material.SLIME_BALL);
      ItemMeta flashmeta = flash.getItemMeta();
      flashmeta.setDisplayName(ChatColor.GRAY + "Blendgranate");
      flash.setItemMeta(flashmeta);
      Item thrownFlash = player.getWorld().dropItem(player.getLocation(), flash);
      Vector flashvel = player.getLocation().getDirection();
      flashvel.multiply(1.225D);
      thrownFlash.setVelocity(flashvel);
      player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
      cooldowntimes.put(player.getName(), 3000);
  }
  
  @EventHandler
  public void onItemSpawn(ItemSpawnEvent event) {
    if (event.getEntity().getItemStack().getType() == Material.SLIME_BALL)
      Bukkit.getScheduler().runTaskLater(SWATtest.plugin, () -> {
            event.getEntity().remove();
            this.nearPlayers.clear();
            this.nearPlayers = new ArrayList<>(getEntitiesAroundPoint(event.getEntity().getLocation(), 5.0D));
            for (Entity nearPlayer : this.nearPlayers) {
              if (nearPlayer instanceof Player) {
                this.nextplayer = (Player)nearPlayer;
                Random rand = new Random();
                if (PlayerDeathListener.spawnprotection.get(this.nextplayer.getName()) != null && !PlayerDeathListener.spawnprotection.get(this.nextplayer.getName())) {
                  this.nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rand.nextInt(140) + 60, 0));
                  this.nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rand.nextInt(200) + 200, 0));
                } else if (nearPlayer.hasMetadata("NPC")) {
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
                if (PlayerDeathListener.spawnprotection.get(this.nextplayer.getName()) != null && !PlayerDeathListener.spawnprotection.get(this.nextplayer.getName())) {
                  this.nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rand.nextInt(100), 0));
                  this.nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rand.nextInt(200), 0));
                } else if (nearPlayer.hasMetadata("NPC")) {
                    this.nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rand.nextInt(140) + 60, 0));
                    this.nextplayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rand.nextInt(200) + 200, 0));
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
