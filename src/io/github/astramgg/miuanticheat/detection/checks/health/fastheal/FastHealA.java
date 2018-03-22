package io.github.astramgg-miuanticheat.detection.checks.health.fastheal;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class FastHealA extends CheckVersion {

	/**
	 * The time that the player last regenerated health.
	 */
	private long lastTime;

	public FastHealA(Check check) {
		super(check, "A", "Check if the delay between the player regenerating health is acceptable.");
		this.lastTime = -1;
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityRegainHealthEvent) {
			EntityRegainHealthEvent erhe = (EntityRegainHealthEvent) event;

			if (erhe.getRegainReason() == RegainReason.SATIATED) {
				/*
				 * Player is regenerating health because their hunger is full.
				 */

				final double health = profile.getPlayer().getHealth();

				if (!isAcceptable(health, erhe.getAmount())) {
					callback(true);
				}

				lastTime = System.currentTimeMillis();
			}
		}
	}

	/**
	 * @param health
	 *            The health of the player.
	 * @param amount
	 *            The amount of health that the player has regenerated.
	 * @return If regenerating this amount of health is acceptable in the given
	 *         amount of time.
	 */
	private boolean isAcceptable(double health, double amount) {
		final long current = System.currentTimeMillis();

		// The amount of time that it takes to regenerate 1 health.
		int regenRate = 0;

		if (health == profile.getPlayer().getMaxHealth()) {
			/*
			 * Player has full health. Regeneration rate is 1 health every
			 * second.
			 */
			regenRate = 1000;
		} else if (health > 18.0) {
			/*
			 * Regeneration level at this level of health is 1 health every 4
			 * seconds.
			 */
			regenRate = 4000;
		}

		if (regenRate == 0) {
			return true;
		}

		return !(amount == 1.0 && current - lastTime < regenRate);
	}

}
