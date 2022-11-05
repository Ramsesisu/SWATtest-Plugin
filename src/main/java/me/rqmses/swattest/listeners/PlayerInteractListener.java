package me.rqmses.swattest.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
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

import static me.rqmses.swattest.SWATtest.plugin;

public class PlayerInteractListener implements Listener {

    public static HashMap<String, Long> cooldowns = new HashMap<String, Long>();
    public static HashMap<String, Integer> cooldowntimes = new HashMap<String, Integer>();

    public static HashMap<String, Integer> tazerstatus = new HashMap<String, Integer>();
    public static HashMap<String, String[]> tazerreloadmsg = new HashMap<String, String[]>();
    public static HashMap<String, BukkitTask> tazertask = new HashMap<String, BukkitTask>();

    public static HashMap<String, Long> flammilast = new HashMap<String, Long>();
    public static HashMap<String, BukkitTask> flammireleasetask = new HashMap<String, BukkitTask>();
    public static HashMap<String, BukkitTask> flammifinaltask = new HashMap<String, BukkitTask>();
    public static HashMap<String, Boolean> flammiloading = new HashMap<String, Boolean>();
    public static HashMap<String, Boolean> flammiloaded = new HashMap<String, Boolean>();
    public static HashMap<String, Long> flammiloadingcounter = new HashMap<String, Long>();
    public static HashMap<String, String> flammireloadmsg = new HashMap<String, String>();

    @EventHandler
    public static boolean onPlayerUse(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.SPECTATOR) {
            return false;
        }

        if (player.isFlying()) {
            return false;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            // M4

            if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_BARDING) {
                if (cooldowns.containsKey(event.getPlayer().getName())) {
                    long secondsLeft = ((cooldowns.get(event.getPlayer().getName()))) + cooldowntimes.get(event.getPlayer().getName()) - (System.currentTimeMillis());
                    if (secondsLeft > 0) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
                        return true;
                    }
                }
                cooldowns.put(player.getName(), System.currentTimeMillis());
                event.setCancelled(true);
                int ammo;
                int allammo;

                ItemStack gun = player.getInventory().getItemInMainHand();
                ItemMeta meta = gun.getItemMeta();

                String strlore = String.valueOf(meta.getLore());
                String[] ammos = strlore.split("/");
                ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
                ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
                ammo = Integer.valueOf(ammos[0]);
                allammo = Integer.valueOf(ammos[1]);

                if (ammo > 0) {
                    PlayerDamageListener shooter = new PlayerDamageListener();
                    shooter.setShooter(player.getName());

                    ArrayList<String> lore = new ArrayList<String>();
                    String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
                    lore.add(templore);
                    meta.setLore(lore);
                    gun.setItemMeta(meta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                    Vector playerDirection = player.getLocation().getDirection();
                    Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
                    bullet.setCustomName("m4");
                    bullet.setVelocity(bullet.getVelocity().multiply(3.75));
                    bullet.setGravity(false);
                    bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);

                    Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers().forEach((Player nearPlayer) -> {
                        if (nearPlayer.getLocation().distance(player.getLocation()) <= 50) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 100, 0.54F);
                            nearPlayer.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 0.1F, 0);
                        } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 100) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 50, 0.54F);
                        } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 150) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 25, 0.54F);
                        } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 200) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 0.54F);
                        }
                    });

                    PlayerDamageListener weapontype = new PlayerDamageListener();

                    cooldowntimes.put(player.getName(), 400);
                } else {
                    player.playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100, 0);

                    int maxammo = 21;

                    if (ammo + allammo >= maxammo) {
                        allammo = allammo - (maxammo - ammo);
                        ammo = ammo + (maxammo - ammo);
                    } else {
                        ammo = ammo + allammo;
                        allammo = 0;
                    }

                    ArrayList<String> lore = new ArrayList<>();
                    String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
                    lore.add(templore);
                    meta.setLore(lore);
                    gun.setItemMeta(meta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                    cooldowntimes.put(player.getName(), 4000);
                }
                return true;
            }

            // Sniper

            if (player.getInventory().getItemInMainHand().getType() == Material.STONE_HOE) {
                if (cooldowns.containsKey(event.getPlayer().getName())) {
                    long secondsLeft = ((cooldowns.get(player.getName()))) + cooldowntimes.get(player.getName()) - (System.currentTimeMillis());
                    if (secondsLeft > 0) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
                        return true;
                    }
                }
                cooldowns.put(player.getName(), System.currentTimeMillis());
                event.setCancelled(true);
                int ammo;
                int allammo;

                ItemStack gun = player.getInventory().getItemInMainHand();
                ItemMeta meta = gun.getItemMeta();

                String strlore = String.valueOf(meta.getLore());
                String[] ammos = strlore.split("/");
                ammos[0] = ammos[0].substring(3, ammos[1].length() - 1).replace("§", "");
                ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
                ammo = Integer.valueOf(ammos[0]);
                allammo = Integer.valueOf(ammos[1]);

                if (ammo > 0) {
                    PlayerDamageListener shooter = new PlayerDamageListener();
                    shooter.setShooter(player.getName());

                    ArrayList<String> lore = new ArrayList<String>();
                    String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
                    lore.add(templore);
                    meta.setLore(lore);
                    gun.setItemMeta(meta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                    Vector playerDirection = player.getLocation().getDirection();
                    Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
                    bullet.setCustomName("sniper");
                    bullet.setVelocity(bullet.getVelocity().multiply(6));
                    bullet.setGravity(false);
                    bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);

                    Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers().forEach((Player nearPlayer) -> {
                        if (nearPlayer.getLocation().distance(player.getLocation()) <= 50) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 100, 0F);
                            nearPlayer.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 0.1F, 0);
                        } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 100) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 50, 0F);
                        } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 150) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 25, 0F);
                        }
                        if (nearPlayer.getLocation().distance(player.getLocation()) <= 200) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 0F);
                        }
                    });

                    cooldowntimes.put(player.getName(), 5000);
                } else {
                    player.playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100, 0);

                    int maxammo = 5;

                    if (ammo + allammo >= maxammo) {
                        allammo = allammo - (maxammo - ammo);
                        ammo = ammo + (maxammo - ammo);
                    } else {
                        ammo = ammo + allammo;
                        allammo = 0;
                    }

                    ArrayList<String> lore = new ArrayList<>();
                    String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
                    lore.add(templore);
                    meta.setLore(lore);
                    gun.setItemMeta(meta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                    cooldowntimes.put(player.getName(), 10000);
                }
                return true;
            }

            // Mp5

            if (player.getInventory().getItemInMainHand().getType() == Material.GOLD_BARDING) {
                if (cooldowns.containsKey(event.getPlayer().getName())) {
                    long secondsLeft = ((cooldowns.get(player.getName()))) + cooldowntimes.get(player.getName()) - (System.currentTimeMillis());
                    if (secondsLeft > 0) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
                        return true;
                    }
                }
                cooldowns.put(player.getName(), System.currentTimeMillis());
                event.setCancelled(true);
                int ammo;
                int allammo;

                ItemStack gun = player.getInventory().getItemInMainHand();
                ItemMeta meta = gun.getItemMeta();

                String strlore = String.valueOf(meta.getLore());
                String[] ammos = strlore.split("/");
                ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
                ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
                ammo = Integer.valueOf(ammos[0]);
                allammo = Integer.valueOf(ammos[1]);

                if (ammo > 0) {
                    PlayerDamageListener shooter = new PlayerDamageListener();
                    shooter.setShooter(player.getName());

                    ArrayList<String> lore = new ArrayList<String>();
                    String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
                    lore.add(templore);
                    meta.setLore(lore);
                    gun.setItemMeta(meta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                    Vector playerDirection = player.getLocation().getDirection();
                    Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
                    bullet.setCustomName("mp5");
                    bullet.setVelocity(bullet.getVelocity().multiply(5));
                    bullet.setGravity(false);
                    bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);

                    Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers().forEach((Player nearPlayer) -> {
                        if (nearPlayer.getLocation().distance(player.getLocation()) <= 50) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 100, 1F);
                            nearPlayer.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 0.1F, 0);
                        } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 100) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 50, 1F);
                        } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 150) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 25, 1F);
                        }
                        if (nearPlayer.getLocation().distance(player.getLocation()) <= 200) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 1F);
                        }
                    });

                    cooldowntimes.put(player.getName(), 300);
                } else {
                    player.playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100, 0);

                    int maxammo = 25;

                    if (ammo + allammo >= maxammo) {
                        allammo = allammo - (maxammo - ammo);
                        ammo = ammo + (maxammo - ammo);
                    } else {
                        ammo = ammo + allammo;
                        allammo = 0;
                    }

                    ArrayList<String> lore = new ArrayList<>();
                    String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
                    lore.add(templore);
                    meta.setLore(lore);
                    gun.setItemMeta(meta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                    cooldowntimes.put(player.getName(), 3000);
                }
                return true;
            }

            // Jagdflinte

            if (player.getInventory().getItemInMainHand().getType() == Material.GOLD_HOE) {
                if (cooldowns.containsKey(event.getPlayer().getName())) {
                    long secondsLeft = ((cooldowns.get(player.getName()))) + cooldowntimes.get(player.getName()) - (System.currentTimeMillis());
                    if (secondsLeft > 0) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
                        return true;
                    }
                }
                cooldowns.put(player.getName(), System.currentTimeMillis());
                event.setCancelled(true);
                int ammo;
                int allammo;

                ItemStack gun = player.getInventory().getItemInMainHand();
                ItemMeta meta = gun.getItemMeta();

                String strlore = String.valueOf(meta.getLore());
                String[] ammos = strlore.split("/");
                ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
                ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
                ammo = Integer.valueOf(ammos[0]);
                allammo = Integer.valueOf(ammos[1]);

                if (ammo > 0) {
                    PlayerDamageListener shooter = new PlayerDamageListener();
                    shooter.setShooter(player.getName());

                    ArrayList<String> lore = new ArrayList<String>();
                    String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
                    lore.add(templore);
                    meta.setLore(lore);
                    gun.setItemMeta(meta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                    Vector playerDirection = player.getLocation().getDirection();
                    Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
                    bullet.setCustomName("jagdflinte");
                    bullet.setVelocity(bullet.getVelocity().multiply(3));
                    bullet.setGravity(false);
                    bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);

                    Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers().forEach((Player nearPlayer) -> {
                        if (nearPlayer.getLocation().distance(player.getLocation()) <= 50) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 100, -0.5F);
                            nearPlayer.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 0.1F, 0);
                        } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 100) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 50, -0.5F);
                        } else if (nearPlayer.getLocation().distance(player.getLocation()) <= 150) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 25, -0.5F);
                        }
                        if (nearPlayer.getLocation().distance(player.getLocation()) <= 200) {
                            nearPlayer.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, -0.5F);
                        }
                    });

                    cooldowntimes.put(player.getName(), 3000);
                } else {
                    player.playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100, 0);

                    int maxammo = 5;

                    if (ammo + allammo >= maxammo) {
                        allammo = allammo - (maxammo - ammo);
                        ammo = ammo + (maxammo - ammo);
                    } else {
                        ammo = ammo + allammo;
                        allammo = 0;
                    }

                    ArrayList<String> lore = new ArrayList<>();
                    String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
                    lore.add(templore);
                    meta.setLore(lore);
                    gun.setItemMeta(meta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                    cooldowntimes.put(player.getName(), 5000);
                }
                return true;
            }

            // RPG

            if (player.getInventory().getItemInMainHand().getType() == Material.GOLD_AXE) {
                if (cooldowns.containsKey(event.getPlayer().getName())) {
                    long secondsLeft = ((cooldowns.get(player.getName()))) + cooldowntimes.get(player.getName()) - (System.currentTimeMillis());
                    if (secondsLeft > 0) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Du kannst diese Waffe gerade nicht benutzen..."));
                        return true;
                    }
                }
                cooldowns.put(player.getName(), System.currentTimeMillis());
                event.setCancelled(true);
                int ammo;
                int allammo;

                ItemStack gun = player.getInventory().getItemInMainHand();
                ItemMeta meta = gun.getItemMeta();

                String strlore = String.valueOf(meta.getLore());
                String[] ammos = strlore.split("/");
                ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
                ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
                ammo = Integer.valueOf(ammos[0]);
                allammo = Integer.valueOf(ammos[1]);

                if (ammo > 0) {
                    PlayerDamageListener shooter = new PlayerDamageListener();
                    shooter.setShooter(player.getName());

                    ArrayList<String> lore = new ArrayList<String>();
                    String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + allammo);
                    lore.add(templore);
                    meta.setLore(lore);
                    gun.setItemMeta(meta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                    Vector playerDirection = player.getLocation().getDirection();
                    Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
                    bullet.setCustomName("rpg");
                    bullet.setVelocity(bullet.getVelocity().multiply(7));
                    bullet.setGravity(false);
                    bullet.setPickupStatus(Arrow.PickupStatus.DISALLOWED);

                    cooldowntimes.put(player.getName(), 0);
                } else {
                    player.playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100, 0);

                    int maxammo = 1;

                    if (ammo + allammo >= maxammo) {
                        allammo = allammo - (maxammo - ammo);
                        ammo = ammo + (maxammo - ammo);
                    } else {
                        ammo = ammo + allammo;
                        allammo = 0;
                    }

                    ArrayList<String> lore = new ArrayList<>();
                    String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
                    lore.add(templore);
                    meta.setLore(lore);
                    gun.setItemMeta(meta);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                    cooldowntimes.put(player.getName(), 80000);
                }
                return true;
            }

            // Tazer

            if (player.getInventory().getItemInMainHand().getType() == Material.WOOD_HOE) {
                if (!tazerstatus.containsKey(player.getName())) {
                    tazerstatus.put(player.getName(), 10);
                }
                if (tazerstatus.get(player.getName()) >= 10) {
                    tazerstatus.put(player.getName(), 1);

                    Location origin = player.getEyeLocation();
                    Vector direction = origin.getDirection();
                    Location destination = origin.clone().add(direction);

                    direction.normalize();
                    for (int i = 0; i < 20.0; i++) {
                        Location loc = origin.add(direction);
                        loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.subtract(direction.clone().multiply(0.75)), 1, 0.05, 0.05, 0.05, 0);
                    }

                    List<Entity> nearbyE = player.getNearbyEntities(5, 5, 5);
                    ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();

                    for (Entity e : nearbyE) {
                        if (e instanceof LivingEntity) {
                            livingE.add((LivingEntity) e);
                        }
                    }

                    LivingEntity target = null;
                    BlockIterator bItr = new BlockIterator(player, 5);
                    Block block;
                    Location loc;
                    int bx, by, bz;
                    double ex, ey, ez;
                    while (bItr.hasNext()) {
                        block = bItr.next();
                        bx = block.getX();
                        by = block.getY();
                        bz = block.getZ();
                        for (LivingEntity e : livingE) {
                            loc = e.getLocation();
                            ex = loc.getX();
                            ey = loc.getY();
                            ez = loc.getZ();
                            if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75) && (by - 1 <= ey && ey <= by + 2.5)) {
                                target = e;
                                if (target.getHealth() >= 1) {
                                    target.damage(1);
                                }
                                target.setGlowing(true);
                                LivingEntity finalTarget = target;
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
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

            // Flammi

            if (player.getInventory().getItemInMainHand().getType() == Material.BLAZE_POWDER && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                int ammo;

                ItemStack messer = player.getInventory().getItemInMainHand();
                ItemMeta meta = messer.getItemMeta();

                String strlore = String.valueOf(meta.getLore());
                String[] ammos = strlore.split("/");
                ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
                ammo = Integer.valueOf(ammos[0]);

                if (ammo == 0) {
                    event.setCancelled(true);
                    return true;
                }

                flammilast.putIfAbsent(player.getName(), 1L);
                flammiloaded.putIfAbsent(player.getName(), false);
                flammiloading.putIfAbsent(player.getName(), false);
                flammiloadingcounter.putIfAbsent(player.getName(), 100L);
                flammireleasetask.putIfAbsent(player.getName(), null);
                flammifinaltask.putIfAbsent(player.getName(), null);
                flammireloadmsg.putIfAbsent(player.getName(), ChatColor.translateAlternateColorCodes('&', "&8&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"));

                if (flammilast.get(player.getName()) > 0L) {
                    if (System.currentTimeMillis() - flammiloadingcounter.get(player.getName()) >= 1050L) {
                        if (flammiloading.get(player.getName())) {
                            flammireloadmsg.put(player.getName(), flammireloadmsg.get(player.getName()).replaceFirst("8", "a"));
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(flammireloadmsg.get(player.getName())));
                            flammiloadingcounter.put(player.getName(), flammilast.get(player.getName()));
                        } else  if (flammiloaded.get(player.getName())) {
                            ArrayList<String> lore = new ArrayList<String>();
                            String templore = ChatColor.translateAlternateColorCodes('&', "&6" + (ammo - 1) + "&8/&6" + 500);
                            lore.add(templore);
                            meta.setLore(lore);
                            messer.setItemMeta(meta);
                            flammiloadingcounter.put(player.getName(), flammilast.get(player.getName()));
                        }
                    }

                    if (flammifinaltask.get(player.getName()) == null) {
                        flammifinaltask.put(player.getName(), Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
                            @Override
                            public void run() {
                                if (flammiloading.get(player.getName()) && !flammiloaded.get(player.getName())) {
                                    flammiloaded.put(player.getName(), true);
                                    flammiloading.put(player.getName(), false);
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛&a⬛")));
                                }
                                flammifinaltask.put(player.getName(), null);
                            }
                        }, 200L));
                    }

                    if ((System.currentTimeMillis() - flammilast.get(player.getName())) <= 250L) {
                        if (flammireleasetask.get(player.getName()) != null && Bukkit.getServer().getScheduler().isQueued(flammireleasetask.get(player.getName()).getTaskId())) {
                            Bukkit.getServer().getScheduler().cancelTask(flammireleasetask.get(player.getName()).getTaskId());
                        }
                        flammireleasetask.put(player.getName(), Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
                            @Override
                            public void run() {
                                flammireloadmsg.put(player.getName(), ChatColor.translateAlternateColorCodes('&', "&8&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"));
                                flammiloading.put(player.getName(), false);
                                flammiloaded.put(player.getName(), false);
                                if (flammifinaltask.get(player.getName()) != null && Bukkit.getServer().getScheduler().isQueued(flammifinaltask.get(player.getName()).getTaskId())) {
                                    Bukkit.getServer().getScheduler().cancelTask(flammifinaltask.get(player.getName()).getTaskId());
                                    flammifinaltask.put(player.getName(), null);
                                }
                            }
                        }, 40L));
                    } else {
                        flammiloading.put(player.getName(), true);
                    }
                }
                flammilast.put(player.getName(), System.currentTimeMillis());

                if (flammiloaded.get(player.getName())) {
                    Location origin = player.getEyeLocation().subtract(0,0.25,0);
                    Vector direction = origin.getDirection();
                    Location destination = origin.clone().add(direction);

                    direction.normalize();
                    for (int i = 0; i < 20.0; i++) {
                        Location loc = origin.add(direction);
                        loc.getWorld().spawnParticle(Particle.FLAME, loc.subtract(direction.clone().multiply(0.75)), 1, 0.05, 0.05, 0.05, 0);
                    }

                    List<Entity> nearbyE = player.getNearbyEntities(5, 5, 5);
                    ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();

                    for (Entity e : nearbyE) {
                        if (e instanceof LivingEntity) {
                            livingE.add((LivingEntity) e);
                        }
                    }

                    LivingEntity target = null;
                    BlockIterator bItr = new BlockIterator(player, 5);
                    Block block;
                    Location loc;
                    int bx, by, bz;
                    double ex, ey, ez;
                    while (bItr.hasNext()) {
                        block = bItr.next();
                        bx = block.getX();
                        by = block.getY();
                        bz = block.getZ();
                        for (LivingEntity e : livingE) {
                            loc = e.getLocation();
                            ex = loc.getX();
                            ey = loc.getY();
                            ez = loc.getZ();
                            if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75) && (by - 1 <= ey && ey <= by + 2.5)) {
                                target = e;
                                target.damage(1);
                                target.setFireTicks(5*20);
                            }
                        }
                    }
                }
            }
        }


        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

            // Sniper

            if (player.getInventory().getItemInMainHand().getType() == Material.STONE_HOE && !(player.getCustomName() == "brokenleg") && player.isSneaking()) {
                if (player.getCustomName() == "zoom") {
                    player.removePotionEffect(PotionEffectType.SLOW);
                    player.removePotionEffect(PotionEffectType.JUMP);
                    player.setCustomName("");
                } else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE,255));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 200));
                    player.setCustomName("zoom");
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        // M4

        if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_BARDING) {
            event.setCancelled(true);

            cooldowntimes.put(player.getName(), 4000);

            int ammo;
            int allammo;
            int maxammo = 21;

            ItemStack gun = event.getItemDrop().getItemStack();
            ItemMeta meta = gun.getItemMeta();

            String strlore = String.valueOf(meta.getLore());
            String[] ammos = strlore.split("/");
            ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
            ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
            ammo = Integer.valueOf(ammos[0]);
            allammo = Integer.valueOf(ammos[1]);

            if (!(ammo == 21)) {

                if (ammo + allammo >= maxammo) {
                    allammo = allammo - (maxammo - ammo);
                    ammo = ammo + (maxammo - ammo);
                } else {
                    ammo = ammo + allammo;
                    allammo = 0;
                }

                ArrayList<String> lore = new ArrayList<>();
                String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
                lore.add(templore);
                meta.setLore(lore);
                gun.setItemMeta(meta);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                player.playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100, 0);
            }
        }

        // Sniper

        if (event.getItemDrop().getItemStack().getType() == Material.STONE_HOE) {
            event.setCancelled(true);

            cooldowntimes.put(player.getName(), 10000);

            int ammo;
            int allammo;
            int maxammo = 5;

            ItemStack gun = event.getItemDrop().getItemStack();
            ItemMeta meta = gun.getItemMeta();

            String strlore = String.valueOf(meta.getLore());
            String[] ammos = strlore.split("/");
            ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
            ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
            ammo = Integer.valueOf(ammos[0]);
            allammo = Integer.valueOf(ammos[1]);

            if (!(ammo == 5)) {

                if (ammo + allammo >= maxammo) {
                    allammo = allammo - (maxammo - ammo);
                    ammo = ammo + (maxammo - ammo);
                } else {
                    ammo = ammo + allammo;
                    allammo = 0;
                }

                ArrayList<String> lore = new ArrayList<>();
                String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
                lore.add(templore);
                meta.setLore(lore);
                gun.setItemMeta(meta);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                player.playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100, 0);
            }
        }

        // Mp5

        if (event.getItemDrop().getItemStack().getType() == Material.GOLD_BARDING) {
            event.setCancelled(true);

            cooldowntimes.put(player.getName(), 3000);

            int ammo;
            int allammo;
            int maxammo = 25;

            ItemStack gun = event.getItemDrop().getItemStack();
            ItemMeta meta = gun.getItemMeta();

            String strlore = String.valueOf(meta.getLore());
            String[] ammos = strlore.split("/");
            ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
            ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
            ammo = Integer.valueOf(ammos[0]);
            allammo = Integer.valueOf(ammos[1]);

            if (!(ammo == 25)) {

                if (ammo + allammo >= maxammo) {
                    allammo = allammo - (maxammo - ammo);
                    ammo = ammo + (maxammo - ammo);
                } else {
                    ammo = ammo + allammo;
                    allammo = 0;
                }

                ArrayList<String> lore = new ArrayList<>();
                String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
                lore.add(templore);
                meta.setLore(lore);
                gun.setItemMeta(meta);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                player.playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100, 0);
            }
        }

        // Jagdflinte

        if (event.getItemDrop().getItemStack().getType() == Material.GOLD_HOE) {
            event.setCancelled(true);

            cooldowntimes.put(player.getName(), 5000);

            int ammo;
            int allammo;
            int maxammo = 5;

            ItemStack gun = event.getItemDrop().getItemStack();
            ItemMeta meta = gun.getItemMeta();

            String strlore = String.valueOf(meta.getLore());
            String[] ammos = strlore.split("/");
            ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
            ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
            ammo = Integer.valueOf(ammos[0]);
            allammo = Integer.valueOf(ammos[1]);

            if (!(ammo == 25)) {

                if (ammo + allammo >= maxammo) {
                    allammo = allammo - (maxammo - ammo);
                    ammo = ammo + (maxammo - ammo);
                } else {
                    ammo = ammo + allammo;
                    allammo = 0;
                }

                ArrayList<String> lore = new ArrayList<>();
                String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
                lore.add(templore);
                meta.setLore(lore);
                gun.setItemMeta(meta);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                player.playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100, 0);
            }
        }

        // RPG

        if (event.getItemDrop().getItemStack().getType() == Material.GOLD_AXE) {
            event.setCancelled(true);

            cooldowntimes.put(player.getName(), 120000);

            int ammo;
            int allammo;
            int maxammo = 1;

            ItemStack gun = event.getItemDrop().getItemStack();
            ItemMeta meta = gun.getItemMeta();

            String strlore = String.valueOf(meta.getLore());
            String[] ammos = strlore.split("/");
            ammos[0] = ammos[0].substring(3, ammos[0].length() - 2).replace("§", "");
            ammos[1] = ammos[1].substring(2, ammos[1].length() - 1).replace("§", "");
            ammo = Integer.valueOf(ammos[0]);
            allammo = Integer.valueOf(ammos[1]);

            if (!(ammo == 1)) {

                if (ammo + allammo >= maxammo) {
                    allammo = allammo - (maxammo - ammo);
                    ammo = ammo + (maxammo - ammo);
                } else {
                    ammo = ammo + allammo;
                    allammo = 0;
                }

                ArrayList<String> lore = new ArrayList<>();
                String templore = ChatColor.translateAlternateColorCodes('&', "&6" + ammo + "&8/&6" + allammo);
                lore.add(templore);
                meta.setLore(lore);
                gun.setItemMeta(meta);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(templore));

                player.playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 100, 0);
            }
        }
    }

    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        ItemStack stack1 = event.getPlayer().getInventory().getItem(event.getNewSlot());
        ItemStack stack2 = event.getPlayer().getInventory().getItem(event.getPreviousSlot());

        tazertask.putIfAbsent(player.getName(), null);

        if (stack1 != null) {
            if (player.getInventory().getItem(event.getNewSlot()).getType() == Material.WOOD_HOE) {

                if (!tazerstatus.containsKey(player.getName())) {
                    tazerstatus.put(player.getName(), 10);
                }

                tazerreloadmsg.put(player.getName(), new String[]{(ChatColor.translateAlternateColorCodes('&', "&eRecharge... &7» &8&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛&8⬛"))});

                if (tazerstatus.get(player.getName()) < 10) {

                    final int[] temptazerstatus = {tazerstatus.get(player.getName())};

                    for (int i = 0; i < 10; i++) {
                        if (i >= temptazerstatus[0] - 1) {break;}
                        tazerreloadmsg.put(player.getName(), new String[]{(tazerreloadmsg.get(player.getName())[0].replaceFirst("8", "a"))});
                    }

                    BukkitRunnable reload = new BukkitRunnable() {
                        @Override
                        public void run() {
                            temptazerstatus[0] = temptazerstatus[0] + 1;
                            tazerreloadmsg.put(player.getName(), new String[]{(tazerreloadmsg.get(player.getName())[0].replaceFirst("8", "a"))});
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(tazerreloadmsg.get(player.getName())[0]));
                            tazerstatus.put(player.getName(), temptazerstatus[0]);
                            if (temptazerstatus[0] >= 10) {
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&eRecharge... &7» &aComplete")));
                                cancel();
                            }
                        }
                    };

                    tazertask.put(player.getName(), reload.runTaskTimer(plugin, 0, 100L));
                }
            }
        }

        if (stack2 != null) {
            if (player.getInventory().getItem(event.getPreviousSlot()).getType() == Material.WOOD_HOE) {
                if (tazertask.get(player.getName()) != null) {
                    tazertask.get(player.getName()).cancel();
                    if (tazerstatus.get(player.getName()) < 10) {
                        tazerstatus.put(player.getName(), tazerstatus.get(player.getName()) - 1);
                    }
                }
            }
        }
    }
}
