package io.github.astramgg-miuanticheatdetection.checks.damage.antidamage;

import io.github.astramgg-miuanticheatdetection.CheckType;
import io.github.astramgg-miuanticheatdetection.checks.Check;
import io.github.astramgg-miuanticheatinfo.Profile;

public class AntiDamage extends Check {

	public AntiDamage(Profile profile) {
		super(profile, CheckType.ANTIDAMAGE);

		versions.add(new AntiDamageA(this));
	}

}
