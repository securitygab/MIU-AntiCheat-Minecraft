package io.github.astramgg-miuanticheat.detection.checks.health.fastheal;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class FastHeal extends Check {

	public FastHeal(Profile profile) {
		super(profile, CheckType.FASTHEAL);
		versions.add(new FastHealA(this));
	}

}
