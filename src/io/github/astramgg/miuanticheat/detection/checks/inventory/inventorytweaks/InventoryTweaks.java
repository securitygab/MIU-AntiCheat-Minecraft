package io.github.astramgg-miuanticheat.detection.checks.inventory.inventorytweaks;

import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.info.Profile;

public class InventoryTweaks extends Check {

	public InventoryTweaks(Profile profile) {
		super(profile, CheckType.INVENTORYTWEAKS);

		versions.add(new InventoryTweaksA(this));
		versions.add(new InventoryTweaksB(this));
	}

}
