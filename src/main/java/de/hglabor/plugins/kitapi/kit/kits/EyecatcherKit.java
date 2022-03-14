package de.hglabor.plugins.kitapi.kit.kits;

import de.hglabor.plugins.kitapi.KitApi;
import de.hglabor.plugins.kitapi.kit.AbstractKit;
import de.hglabor.plugins.kitapi.kit.events.KitEvent;
import de.hglabor.plugins.kitapi.kit.settings.DoubleArg;
import de.hglabor.plugins.kitapi.kit.settings.FloatArg;
import de.hglabor.plugins.kitapi.kit.settings.IntArg;
import de.hglabor.plugins.kitapi.player.KitPlayer;
import de.hglabor.plugins.kitapi.util.RotationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class EyecatcherKit extends AbstractKit implements Listener {

	public static final EyecatcherKit INSTANCE = new EyecatcherKit();

	@DoubleArg
	private final double radius;

	@IntArg
	private final int duration;

	@FloatArg
	private final float cooldown;

	private EyecatcherKit() {
		super("Eyecatcher", Material.ENDER_EYE);
		setMainKitItem(getDisplayMaterial());
		radius = 15.0;
		duration = 5;
		cooldown = 25.0F;
	}

	@KitEvent
	@Override
	public void onPlayerRightClickKitItem(PlayerInteractEvent event, KitPlayer kitPlayer) {
		Player player = event.getPlayer();
		AtomicInteger tick = new AtomicInteger(0);
		Bukkit.getScheduler().runTaskTimer(KitApi.getInstance().getPlugin(), (task) -> {
			tick.getAndIncrement();
			if(tick.get() == duration*20) {
				task.cancel();
				return;
			}
			if(!kitPlayer.isValid()) {
				task.cancel();
				return;
			}
			if(kitPlayer.areKitsDisabled()) {
				task.cancel();
				return;
			}
			player.getNearbyEntities(radius, radius, radius).forEach(it -> {
				RotationUtils.Rotation rotation = RotationUtils.getNeededRotations(player, it);
				if(it instanceof Player) {
					Location location = it.getLocation().clone();
					location.setYaw(rotation.getYaw());
					location.setPitch(rotation.getPitch());
				} else {
					it.setRotation(rotation.getYaw(), rotation.getPitch());
				}
			});
		}, 0, 1L);
		kitPlayer.activateKitCooldown(INSTANCE);
	}

	@Override
	public float getCooldown() {
		return cooldown;
	}
}
