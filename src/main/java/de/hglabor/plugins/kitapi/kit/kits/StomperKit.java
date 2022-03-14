package de.hglabor.plugins.kitapi.kit.kits;

import de.hglabor.plugins.kitapi.KitApi;
import de.hglabor.plugins.kitapi.kit.AbstractKit;
import de.hglabor.plugins.kitapi.kit.events.KitEvent;
import de.hglabor.plugins.kitapi.kit.kits.grappler.GrapplerKit;
import de.hglabor.plugins.kitapi.kit.settings.DoubleArg;
import de.hglabor.plugins.kitapi.player.KitPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class StomperKit extends AbstractKit {
	public static final StomperKit INSTANCE = new StomperKit();
	@DoubleArg
	private final double radius;

	protected StomperKit() {
		super("Stomper", Material.DIAMOND_BOOTS);
		radius = 3D;
	}

	@Override
	public void onEnable(KitPlayer kitPlayer) {
		if(kitPlayer.hasKit(GravityKit.INSTANCE) || kitPlayer.hasKit(GrapplerKit.INSTANCE) || kitPlayer.hasKit(GhostKit.INSTANCE) || kitPlayer.hasKit(ElytraKit.INSTANCE)) {
			kitPlayer.disableKits(true);
			kitPlayer.getBukkitPlayer().get().sendMessage(ChatColor.RED + "Unallowed kit combo, your kits got disabled!");
		}
	}

	@KitEvent
	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
			Player stomper = (Player) event.getEntity();
			final double STOMPER_DAMAGE = event.getDamage();
			stomper.playSound(stomper.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
			if (STOMPER_DAMAGE > 4) {
				event.setDamage(4);
			}
			for (LivingEntity livingEntity : stomper.getWorld().getNearbyEntitiesByType(LivingEntity.class, stomper.getLocation(), radius)) {
				if (livingEntity == stomper) {
					continue;
				}
				if (livingEntity instanceof Player) {
					Player playerEntity = (Player) livingEntity;
					KitPlayer kitPlayer = KitApi.getInstance().getPlayer(playerEntity);
					if (kitPlayer.isValid()) {
						if (!playerEntity.isSneaking()) {
							livingEntity.damage(STOMPER_DAMAGE, stomper);
						}
						kitPlayer.getLastHitInformation().setLastDamager(stomper);
						kitPlayer.getLastHitInformation().setLastDamagerTimestamp(System.currentTimeMillis());
						playerEntity.playSound(playerEntity.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.05f, 1);
					}
				} else {
					livingEntity.damage(STOMPER_DAMAGE, stomper);
				}
			}
		}
	}
}
