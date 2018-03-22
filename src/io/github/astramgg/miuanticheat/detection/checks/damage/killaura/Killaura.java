package io.github.astramgg-miuanticheat.detection.checks.damage.killaura;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class Killaura extends Check {

	public Killaura(Profile profile) {
		super(profile, CheckType.KILLAURA);

		versions.add(new KillauraA(this));
		versions.add(new KillauraB(this));
		versions.add(new KillauraC(this));
		versions.add(new KillauraLearn(this));
	}

}
