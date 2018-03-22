package io.github.astramgg-miuanticheat.detection.checks.interact.fastbow;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class Fastbow extends Check {

	public Fastbow(Profile profile) {
		super(profile, CheckType.FASTBOW);

		versions.add(new FastbowA(this));
	}

}
