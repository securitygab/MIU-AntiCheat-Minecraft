package io.github.astramgg-miuanticheat.detection.checks.movement.waterwalk;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.astramgg-miuanticheat.behaviour.Behaviour;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;
import io.github.astramgg-miuanticheat.util.Helper;

public class WaterWalkA extends CheckVersion {

	public WaterWalkA(Check check) {
		super(check, "A", "Checks whether the player is walking on water");
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			final PlayerMoveEvent pme = (PlayerMoveEvent) event;

			final Behaviour behaviour = profile.getBehaviour();

			if (!behaviour.getMotion().isDescending() && !behaviour.isInCreativeOrSpectator()
					&& !profile.getPlayer().isInsideVehicle() && behaviour.isOnLiquidBlock()
					&& !behaviour.isInWater()) {

				final Material from = getMaterialDown(pme.getFrom());
				final Material to = getMaterialDown(pme.getTo());

				// Avoid false positives.
				if (from == Material.WATER_LILY || to == Material.WATER_LILY || Helper.isSlab(from)
						|| Helper.isSlab(to)) {
					return;
				}

				final double fromY = pme.getFrom().getY(), toY = pme.getTo().getY();

				// If the player is walking on a block.
				if ((fromY % 1.0 == 0.0 || fromY % 0.5 == 0.0) && (toY % 1.0 == 0.0 || toY % 0.5 == 0.0)) {
					callback(true);
				}
			}

			callback(false);
		}
	}

	/**
	 * @return The percentage of a minute that the player has stood on water
	 *         for.
	 */

	private Material getMaterialDown(Location location) {
		return location.getBlock().getRelative(BlockFace.DOWN).getType();
	}

	private boolean isWater(Material material) {
		return material == Material.WATER || material == Material.STATIONARY_WATER;
	}
}
