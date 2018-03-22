package io.github.astramgg-miuanticheat.detection.checks.damage.killaura;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.astramgg-miuanticheat.miu;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;
import io.github.astramgg-miuanticheat.util.Helper;

public class KillauraC extends CheckVersion {

	private static final int NEEDED_SAMPLES = miu.getInstance().getConfig().getInt("killaura.c.neededSamples");
	private static final double DISALLOWED_ANGLE = miu.getInstance().getConfig()
			.getDouble("killaura.c.disallowedAngle");

	private HashMap<Double, Long> previousAngles;

	public KillauraC(Check check) {
		super(check, "C", "Checks the angle between the player and the target.");
		this.previousAngles = new HashMap<Double, Long>();
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			final EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;

			if (edbe.getEntity() instanceof LivingEntity) {
				final LivingEntity le = (LivingEntity) edbe.getEntity();

				final Location eye = profile.getPlayer().getEyeLocation();

				final double yawBetween = Helper.getYawBetween(eye, le.getLocation());
				final double playerYaw = profile.getPlayer().getEyeLocation().getYaw();

				final double angleBetween = Math.abs(180 - Math.abs(Math.abs(yawBetween - playerYaw) - 180));

				callback(angleBetween >= DISALLOWED_ANGLE && previousAngles.size() >= NEEDED_SAMPLES
						&& getAverageAngle() >= DISALLOWED_ANGLE);

				previousAngles.put(angleBetween, System.currentTimeMillis());
			}
		}
	}

	private double getAverageAngle() {
		int processed = 0;
		double average = 0.0;

		for (double angle : previousAngles.keySet()) {

			if (processed == 0) {
				average = angle;
			} else {
				average += angle;
				average /= 2;
			}

			processed++;
		}

		return average;
	}

}
