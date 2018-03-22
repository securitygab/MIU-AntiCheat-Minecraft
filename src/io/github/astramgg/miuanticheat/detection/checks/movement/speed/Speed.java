package io.github.astramgg-miuanticheat.detection.checks.movement.speed;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class Speed extends Check {

	public Speed(Profile profile) {
		super(profile, CheckType.SPEED);

		versions.add(new SpeedA(this));
	}

}
