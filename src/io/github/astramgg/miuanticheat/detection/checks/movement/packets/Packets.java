package io.github.astramgg-miuanticheat.detection.checks.movement.packets;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class Packets extends Check {

	public Packets(Profile profile) {
		super(profile, CheckType.PACKETS);

		versions.add(new PacketsA(this));
	}

}
