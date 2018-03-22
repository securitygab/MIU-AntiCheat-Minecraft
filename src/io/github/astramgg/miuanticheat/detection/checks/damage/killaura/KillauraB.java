package io.github.astramgg-miuanticheat.detection.checks.damage.killaura;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.BlockIterator;

import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class KillauraB extends CheckVersion {

	public KillauraB(Check check) {
		super(check, "B", "Check if the attacked entity is in the player's line of sight.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;

			if (edbe.getCause() != DamageCause.ENTITY_ATTACK) {
				return;
			}

			callback(!isInLineOfSight(edbe.getEntity(), 2.0));
		}
	}

	/**
	 * @param check
	 *            The entity to check whether
	 * @param distance
	 *            The difference in distance to allow for.
	 * @return
	 */
	private boolean isInLineOfSight(Entity check, double distance) {
		final Location entityLocation = check.getLocation();
		final BlockIterator iterator = new BlockIterator(profile.getPlayer().getEyeLocation(), 0.0, 7);

		while (iterator.hasNext()) {
			final Location current = iterator.next().getLocation();

			if (getLocationDifference(current, entityLocation, "X") < distance
					&& getLocationDifference(current, entityLocation, "Y") < distance
					&& getLocationDifference(current, entityLocation, "Z") < distance) {
				return true;
			}
		}

		// The entity has not been found in the player's line of sight.
		return false;
	}

	private double getLocationDifference(Location first, Location second, String axis) {
		double difference = 0.0;

		switch (axis) {
		case "X":
			difference = first.getX() - second.getX();
			break;
		case "Y":
			difference = first.getY() - second.getY();
			break;
		case "Z":
			difference = first.getZ() - second.getZ();
			break;
		}

		return Math.abs(difference);
	}

}