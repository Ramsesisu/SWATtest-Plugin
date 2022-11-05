package me.rqmses.swattest.listeners;

import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class PlayerDamageListener implements Listener {

    public static String shooter;
    public void setShooter(String name) {
        shooter = name;
    }

    List<Entity> entitylist;

    @EventHandler
    public void onBulletHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager().getType() == EntityType.ARROW) {
                EntityDamageByEntityEvent entityEvent = event;
                Player player = (Player) entityEvent.getEntity();

                PlayerDeathListener killer = new PlayerDeathListener();
                killer.setKiller(shooter);

                String weapontype = event.getDamager().getCustomName();

                if (weapontype == "m4") {
                    if (player.getInventory().getChestplate() == null) {
                        event.setDamage(7);
                    } else {
                        if (player.isBlocking()) {
                            if (((Player) event.getEntity()).getInventory().getChestplate().getDurability() < 79) {
                                ((Player) event.getEntity()).getInventory().getChestplate().setDurability((short) (((Player) event.getEntity()).getInventory().getChestplate().getDurability() + 1));
                            } else {
                                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                                ((Player) event.getEntity()).getInventory().setChestplate(null);
                            }
                        } else {
                            if (((Player) event.getEntity()).getInventory().getChestplate().getDurability() < 76) {
                                ((Player) event.getEntity()).getInventory().getChestplate().setDurability((short) (((Player) event.getEntity()).getInventory().getChestplate().getDurability() + 4));
                                event.setDamage(1);
                            } else {
                                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                                ((Player) event.getEntity()).getInventory().setChestplate(null);
                                event.setDamage(1);
                            }
                        }
                    }
                }
                if (weapontype == "sniper") {
                    if (player.getInventory().getChestplate() == null) {
                        event.setDamage(14.5);
                    } else {
                        if (player.isBlocking()) {
                            if (((Player) event.getEntity()).getInventory().getChestplate().getDurability() < 79) {
                                ((Player) event.getEntity()).getInventory().getChestplate().setDurability((short) (((Player) event.getEntity()).getInventory().getChestplate().getDurability() + 1));
                            } else {
                                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                                ((Player) event.getEntity()).getInventory().setChestplate(null);
                            }
                        } else {
                            if (((Player) event.getEntity()).getInventory().getChestplate().getDurability() < 76) {
                                ((Player) event.getEntity()).getInventory().getChestplate().setDurability((short) (((Player) event.getEntity()).getInventory().getChestplate().getDurability() + 4));
                                event.setDamage(1);
                            } else {
                                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                                ((Player) event.getEntity()).getInventory().setChestplate(null);
                                event.setDamage(1);
                            }
                        }
                    }
                }
                if (weapontype == "mp5") {
                    if (player.getInventory().getChestplate() == null) {
                        event.setDamage(5);
                    } else {
                        if (player.isBlocking()) {
                            if (((Player) event.getEntity()).getInventory().getChestplate().getDurability() < 79) {
                                ((Player) event.getEntity()).getInventory().getChestplate().setDurability((short) (((Player) event.getEntity()).getInventory().getChestplate().getDurability() + 1));
                            } else {
                                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                                ((Player) event.getEntity()).getInventory().setChestplate(null);
                            }
                        } else {
                            if (((Player) event.getEntity()).getInventory().getChestplate().getDurability() < 76) {
                                ((Player) event.getEntity()).getInventory().getChestplate().setDurability((short) (((Player) event.getEntity()).getInventory().getChestplate().getDurability() + 4));
                                event.setDamage(1);
                            } else {
                                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                                ((Player) event.getEntity()).getInventory().setChestplate(null);
                                event.setDamage(1);
                            }
                        }
                    }
                }
                if (weapontype == "jagdflinte") {
                    if (player.getInventory().getChestplate() == null) {
                        event.setDamage(11.5);
                    } else {
                        if (player.isBlocking()) {
                            if (((Player) event.getEntity()).getInventory().getChestplate().getDurability() < 79) {
                                ((Player) event.getEntity()).getInventory().getChestplate().setDurability((short) (((Player) event.getEntity()).getInventory().getChestplate().getDurability() + 1));
                            } else {
                                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                                ((Player) event.getEntity()).getInventory().setChestplate(null);
                            }
                        } else {
                            if (((Player) event.getEntity()).getInventory().getChestplate().getDurability() < 76) {
                                ((Player) event.getEntity()).getInventory().getChestplate().setDurability((short) (((Player) event.getEntity()).getInventory().getChestplate().getDurability() + 4));
                                event.setDamage(1);
                            } else {
                                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                                ((Player) event.getEntity()).getInventory().setChestplate(null);
                                event.setDamage(1);
                            }
                        }
                    }
                }
                if (weapontype == "rpg") {
                    event.setDamage(1000);

                    Location loc = player.getLocation();
                    loc.getWorld().createExplosion(loc, 10, true);
                    loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);

                    Player nearplayer;
                    entitylist = event.getEntity().getNearbyEntities(2, 2, 2);
                    for (Entity entity : entitylist) {
                        nearplayer = (Player) entity;
                        nearplayer.damage(100);
                        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*10,1));
                        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3,1));
                    }
                    entitylist.clear();
                    entitylist = event.getEntity().getNearbyEntities(6, 6, 6);
                    for (Entity entity : entitylist) {
                        nearplayer = (Player) entity;
                        nearplayer.damage(35);
                        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*10,1));
                        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3,1));
                    }
                    entitylist.clear();
                    entitylist = event.getEntity().getNearbyEntities(10, 10, 10);
                    for (Entity entity : entitylist) {
                        nearplayer = (Player) entity;
                        nearplayer.damage(20);
                        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*5,1));
                        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3,1));
                    }
                    entitylist.clear();
                    entitylist = event.getEntity().getNearbyEntities(15, 15, 15);
                    for (Entity entity : entitylist) {
                        nearplayer = (Player) entity;
                        nearplayer.damage(8);
                        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*2,1));
                        nearplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*2,1));
                    }
                    entitylist.clear();
                }
            }
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (event.getDamage() >= 30) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3), true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1), true);
                    player.setCustomName("brokenleg");
                    player.sendMessage(ChatColor.GRAY + "Du hast dir dein Bein gebrochen!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerSprint(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();

        if (player.getCustomName() == "brokenleg" && !(player.isFlying())) {
            player.damage(1);
            event.setCancelled(true);
        }
    }

    private final Set<UUID> prevPlayersOnGround = Sets.newHashSet();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getVelocity().getY() > 0) {
            double jumpVelocity = 0.42F;
            if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                jumpVelocity += (float) (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F;
            }
            if (event.getPlayer().getLocation().getBlock().getType() != Material.LADDER && prevPlayersOnGround.contains(player.getUniqueId())) {
                if (!player.isOnGround() && Double.compare(player.getVelocity().getY(), jumpVelocity) == 0) {

                    if (player.getCustomName() == "brokenleg" && !(player.isFlying())) {
                        player.damage(1);
                    }
                }
            }
        }
        if (player.isOnGround()) {
            prevPlayersOnGround.add(player.getUniqueId());
        } else {
            prevPlayersOnGround.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
