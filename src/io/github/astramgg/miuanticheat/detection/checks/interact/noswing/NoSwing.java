package io.github.astramgg-miuanticheat.detection.checks.interact.noswing;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class NoSwing extends Check {

	public NoSwing(Profile profile) {
		super(profile, CheckType.NOSWING);

		versions.add(new NoSwingA(this));
	}

}
