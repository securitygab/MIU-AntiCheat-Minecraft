package io.github.astramgg-miuanticheat.detection.checks.damage.nofall;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class NoFall extends Check {

	public NoFall(Profile profile) {
		super(profile, CheckType.NOFALL);

		versions.add(new NoFallA(this));
	}

}
