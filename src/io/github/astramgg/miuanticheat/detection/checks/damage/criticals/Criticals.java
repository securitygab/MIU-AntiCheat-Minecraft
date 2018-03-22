package io.github.astramgg-miuanticheat.detection.checks.damage.criticals;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class Criticals extends Check {

	public Criticals(Profile profile) {
		super(profile, CheckType.CRITICALS);

		versions.add(new CriticalsA(this));
	}

}
