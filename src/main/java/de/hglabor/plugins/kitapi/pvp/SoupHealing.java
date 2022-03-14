package de.hglabor.plugins.kitapi.pvp;

import de.hglabor.plugins.kitapi.kit.events.event.PlayerAteSoupEvent;
import de.hglabor.plugins.kitapi.kit.kits.SpitKit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class SoupHealing implements Listener {
	public static final List<Material> SOUP_MATERIAL = Arrays.asList(Material.MUSHROOM_STEW, Material.SUSPICIOUS_STEW);

	@EventHandler(priority = EventPriority.MONITOR)
	public void onRightClickSoup(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR) {
			return;
		}
		ItemStack itemStack = event.getItem();
		if (itemStack == null) {
			return;
		}
		if (event.hasItem() && SOUP_MATERIAL.contains(event.getMaterial())) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
				return;
			}
			int amountToHeal = 7;
			if (itemStack.hasItemMeta() && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(SpitKit.INSTANCE.getSpitProjectileKey())) {
				amountToHeal = SpitKit.INSTANCE.getSpitSoupHealing();
			}
			boolean hasPresouped = false;
			ItemStack soup = player.getInventory().getItemInMainHand().clone();
			if (player.getFoodLevel() < 20) {
				player.setFoodLevel(player.getFoodLevel() + 6);
				player.setSaturation(player.getSaturation() + 7);
				player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL));
			}
			if (player.getHealth() < player.getMaxHealth()) {
				if (player.getHealth() + amountToHeal > player.getMaxHealth()) {
					hasPresouped = true;
				}
				player.setHealth(Math.min(player.getHealth() + amountToHeal, player.getMaxHealth()));
				player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL));
			} else {
				hasPresouped = true;
			}
			Bukkit.getPluginManager().callEvent(new PlayerAteSoupEvent(player, hasPresouped, soup));
		}
	}
}

