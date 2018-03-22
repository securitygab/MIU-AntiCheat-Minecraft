package io.github.astramgg-miuanticheat.detection.checks.interact.fastbow;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;

import io.github.astramgg-miuanticheat.miu;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class FastbowA extends CheckVersion {

	private static final int ALLOWED_MINIMUM_DIFFERENCE = miu.getInstance().getConfig()
			.getInt("fastbow.a.allowedMinimumDifference");

	/**
	 * The time that a player previously shot a bow.
	 */
	private long lastTime;

	public FastbowA(Check check) {
		super(check, "A", "Measures the time between shooting arrows and checks if this is too small.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityShootBowEvent) {
			if (lastTime != -1) {
				if (System.currentTimeMillis() - lastTime < ALLOWED_MINIMUM_DIFFERENCE) {
					callback(true);
				}
			}

			lastTime = System.currentTimeMillis();
		}
	}

}
