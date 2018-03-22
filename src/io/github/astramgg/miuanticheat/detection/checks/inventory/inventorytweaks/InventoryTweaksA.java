package io.github.astramgg-miuanticheat.detection.checks.inventory.inventorytweaks;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class InventoryTweaksA extends CheckVersion {

	public InventoryTweaksA(Check check) {
		super(check, "A", "Check if the player is performing impossible actions whilst moving.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof InventoryClickEvent) {
			// The player has clicked.

			final Player player = profile.getPlayer();

			if (player.isSprinting() || player.isSneaking() || player.isBlocking() || player.isSleeping()
					|| player.isConversing()) {
				// The player has clicked in their inventory impossibly.
				callback(true);
			}
		}
	}

}
