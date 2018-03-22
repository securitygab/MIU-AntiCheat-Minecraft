package io.github.astramgg-miuanticheat.detection.checks.movement.antivelocity;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class AntiVelocity extends Check {

	public AntiVelocity(Profile profile) {
		super(profile, CheckType.ANTIVELOCITY);

		versions.add(new AntiVelocityA(this));
	}

}
