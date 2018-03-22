package io.github.astramgg-miuanticheatt.detection.checks.interact.autoclicker;

import io.github.astramgg-miuanticheatt.detection.CheckType;
import io.github.astramgg-miuanticheatt.detection.checks.Check;
import io.github.astramgg-miuanticheatt.info.Profile;

public class Autoclicker extends Check {

	public Autoclicker(Profile profile) {
		super(profile, CheckType.AUTOCLICKER);

		versions.add(new AutoclickerA(this));
	}
	
}
