package de.hglabor.kitapi.kit.util;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public final class EventUtils {
    private EventUtils() {
    }

    public static Player getPlayer(Event event) {
        if (event instanceof EntityDamageByEntityEvent damageEvent) {
            return getAttacker(damageEvent);
        } else if (event instanceof ProjectileHitEvent hitEvent) {
            return getShooter(hitEvent);
        } else if (event instanceof ProjectileLaunchEvent launchEvent) {
            return getLauncher(launchEvent);
        } else if (event instanceof PlayerEvent playerEvent) {
            return playerEvent.getPlayer();
        } else if (event instanceof PlayerDeathEvent deathEvent) {
            return getKiller(deathEvent);
        } else if (event instanceof EntityDeathEvent deathEvent) {
            return getKiller(deathEvent);
        } else if (event instanceof InventoryInteractEvent interactEvent) {
            return (Player) interactEvent.getWhoClicked();
        } else if (event instanceof BlockPlaceEvent blockPlaceEvent) {
            return blockPlaceEvent.getPlayer();
        } else if (event instanceof BlockBreakEvent blockBreakEvent) {
            return blockBreakEvent.getPlayer();
        }
        return null;
    }

    public static Player getAttacker(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) return player;
        return null;
    }

    public static Player getTarget(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) return player;
        return null;
    }

    public static Player getShooter(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) return player;
        return null;
    }

    public static Player getLauncher(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) return player;
        return null;
    }

    public static Player getKiller(PlayerDeathEvent event) {
        return event.getPlayer().getKiller();
    }

    public static Player getKiller(EntityDeathEvent event) {
        return event.getEntity().getKiller();
    }

    public static boolean isRightClick(PlayerInteractEvent event) {
        return event.getAction().isRightClick();
    }
}
