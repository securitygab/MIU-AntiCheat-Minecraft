package io.github.miuanticheat.detection.checks.blocks.liquids;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.miuanticheat.detection.checks.Check;
import io.github.miuanticheat.detection.checks.CheckVersion;

public class LiquidsA extends CheckVersion {

	public LiquidsA(Check check) {
		super(check, "A", "Checks if player has broken a liquid block.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof BlockBreakEvent) {
			final BlockBreakEvent bbe = (BlockBreakEvent) event;

			if (bbe.getBlock().isLiquid()) {
				callback(true);
			}
		}
	}

}
