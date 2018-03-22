package io.github.astramgg-miuanticheat.detection.checks.movement.waterwalk;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class WaterWalk extends Check {

	public WaterWalk(Profile profile) {
		super(profile, CheckType.WATERWALK);

		versions.add(new WaterWalkA(this));
	}

}
