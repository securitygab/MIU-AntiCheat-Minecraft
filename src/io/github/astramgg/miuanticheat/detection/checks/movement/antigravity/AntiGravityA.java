package io.github.astramgg-miuanticheat.detection.checks.movement.antigravity;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.astramgg-miuanticheat.miu;
import io.github.astramgg-miuanticheat.behaviour.Behaviour;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class AntiGravityA extends CheckVersion {

	private static final double ALLOWED_MAX_GRAVITY_DIFFERENCE = miu.getInstance().getConfig()
			.getDouble("antigravity.a.allowedMaxGravityDifference");

	public AntiGravityA(Check check) {
		super(check, "A", "Checks if the player is following gravity properly or not.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			final PlayerMoveEvent pme = (PlayerMoveEvent) event;

			final Behaviour behaviour = profile.getBehaviour();

			if (!behaviour.isOnGround() && !behaviour.isInLiquid() && !behaviour.isInWeb()) {
				if (behaviour.getMotion().isDescending()) {
					final double difference = Math.abs((behaviour.getMotion()
							.calculateGravityEffect() /*
														 * Expected y difference
														 */)
							- (pme.getTo().getY() - pme.getFrom()
									.getY()) /* Actual y difference */);
					if (difference > ALLOWED_MAX_GRAVITY_DIFFERENCE && behaviour
							.getBlocksBelowGround() > 2 /*
														 * The player being too
														 * close to the ground
														 * causes false
														 * positives.
														 */) {
						callback(true);
					}
				}
			}
		}
	}

}
