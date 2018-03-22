package io.github.astramgg-miuanticheatt.detection.checks.movement.fly;

import io.github.astramgg-miuanticheatt.detection.CheckType;
import io.github.astramgg-miuanticheatt.detection.checks.Check;
import io.github.astramgg-miuanticheatt.info.Profile;

public class Fly extends Check {

	public Fly(Profile profile) {
		super(profile, CheckType.FLY);

		versions.add(new FlyA(this));
	}

}
