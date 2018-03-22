package io.github.miuanticheat.detection.checks.blocks.liquids;

import io.github.miuanticheat.detection.CheckType;
import io.github.miuanticheat.detection.checks.Check;
import io.github.miuanticheat.info.Profile;

public class Liquids extends Check {

	public Liquids(Profile profile) {
		super(profile, CheckType.LIQUIDS);

		versions.add(new LiquidsA(this));
	}

}
