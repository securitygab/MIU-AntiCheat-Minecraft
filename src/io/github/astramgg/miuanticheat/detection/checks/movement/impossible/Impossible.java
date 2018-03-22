package io.github.astramgg-miuanticheat.detection.checks.movement.impossible;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class Impossible extends Check {

	public Impossible(Profile profile) {
		super(profile, CheckType.SNEAK);

		versions.add(new ImpossibleA(this));
	}

}
