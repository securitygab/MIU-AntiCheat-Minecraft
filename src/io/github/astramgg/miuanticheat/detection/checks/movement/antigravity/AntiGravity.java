package io.github.astramgg-miuanticheat.detection.checks.movement.antigravity;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class AntiGravity extends Check {

	public AntiGravity(Profile profile) {
		super(profile, CheckType.ANTIGRAVITY);

		versions.add(new AntiGravityA(this));
	}

}
