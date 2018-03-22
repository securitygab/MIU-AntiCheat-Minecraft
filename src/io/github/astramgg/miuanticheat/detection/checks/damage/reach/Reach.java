package io.github.astramgg-miuanticheat.detection.checks.damage.reach;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class Reach extends Check {

	public Reach(Profile profile) {
		super(profile, CheckType.REACH);

		versions.add(new ReachA(this));
	}

}
