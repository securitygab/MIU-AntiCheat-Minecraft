package io.github.astramgg-miuanticheat.detection.checks.movement.speed;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class SpeedA extends CheckVersion {

	public SpeedA(Check check) {
		super(check, "A", "Checks if a player is moving too quickly.");
	}

	@Override
	public void call(Event event) {
		// Causes false positives.
		if (profile.getBehaviour().getMotion().isDescending()) {
			return;
		}

		if (event instanceof PlayerMoveEvent) {
			final Player player = profile.getPlayer();

			if (player.getGameMode() == GameMode.SPECTATOR || player.isInsideVehicle()) {
				/*
				 * If the player is in spectator mode, return out of the method
				 * as the speed function in this GameMode could cause false
				 * positives.
				 */
				return;
			}

			final PlayerMoveEvent pme = (PlayerMoveEvent) event;

			final int speedLevel = profile.getBehaviour().getPotionEffectLevel(PotionEffectType.SPEED);

			// Ignore if the player's speed is higher than two.
			if (speedLevel > 2) {
				return;
			}

			/*
			 * Ignore y for this check. We only want to check speed on the x and
			 * z axes.
			 */
			final Vector from = pme.getFrom().toVector().clone().setY(0.0),
					to = pme.getTo().toVector().clone().setY(0.0);

			double distance = to.distanceSquared(from);

			if (speedLevel > 0) {
				// Take into account speed potions.
				distance -= (distance / 100.0) * (speedLevel * 20.0);
			}

			/*
			 * Walking and flying (but not sprinting): 0.40, Sprinting and
			 * flying: 1.25
			 */
			final long current = System.currentTimeMillis();
			if ((player.isFlying() && player.isSprinting()) || (current
					- profile.getBehaviour().getMotion().getLastFly() <= 500
					&& current - profile.getBehaviour().getMotion()
							.getLastSprint() <= 500) /*
														 * Player is flying and
														 * sprinting and flying
														 * or has been in the
														 * last half a second.
														 */) {
				callback(distance > 1.25);
			} else {
				callback(distance > 0.40);
			}
		}
	}

}
