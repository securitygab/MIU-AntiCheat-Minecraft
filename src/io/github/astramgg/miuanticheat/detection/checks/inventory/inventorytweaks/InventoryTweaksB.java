package io.github.astramgg-miuanticheat.detection.checks.inventory.inventorytweaks;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.astramgg-miuanticheat.miu;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class InventoryTweaksB extends CheckVersion {

	private static final int BOWLS_PER_SINGLE_SLOT = miu.getInstance().getConfig()
			.getInt("inventorytweaks.b.bowlsPerSingleSlot");

	// The slots that the bowl has been placed in in the past.
	private HashMap<Integer, Integer> slotsChosen;

	public InventoryTweaksB(Check check) {
		super(check, "B", "Check if bowls are stacking up in the player's inventory.");

		this.slotsChosen = new HashMap<Integer, Integer>();
	}

	@Override
	public void call(Event event) {
		if (event instanceof InventoryClickEvent) {
			final InventoryClickEvent ice = (InventoryClickEvent) event;

			if (ice.getCurrentItem().getType() == Material.BOWL) {

				final int slot = ice.getSlot();

				if (!slotsChosen.containsKey(slot)) {
					slotsChosen.put(slot, 1);
				} else {
					slotsChosen.put(slot, slotsChosen.get(slot) + 1);
				}

				if (getStackChance() >= 100.0) {
					callback(true);
				}
			}
		}
	}

	/**
	 * @return The chance of a player using autosoup on the premise that they
	 *         are stacking bowls in their inventory. Take into account: the
	 *         amount of slots the bowls have been placed to and the amount of
	 *         bowls in that particular slot.
	 */
	private double getStackChance() {
		final int total = getTotalBowls();

		/*
		 * If there is no information yet or fewer than 20 bowls have been
		 * placed, ignore.
		 */
		if (slotsChosen.size() == 0 || total < 20) {
			return 0.0;
		}

		final double averageBowlsPerSlot = total / slotsChosen.size();

		/*
		 * The amount of slots that have higher than the average or have failed
		 * the basic check.
		 */
		int failed = 0;

		for (int slot : slotsChosen.keySet()) {
			final int amount = slotsChosen.get(slot);

			if (slotsChosen.size() != 1) {
				// If more than one slot has been used.
				if (amount > averageBowlsPerSlot) {
					failed++;
				}
			} else {
				// Only one slot has been used.

				if (amount >= BOWLS_PER_SINGLE_SLOT) {
					failed++;
				}
			}
		}

		return (failed / slotsChosen.size()) * 100.0;
	}

	/**
	 * @return Get the total amount of bowls moved.
	 */
	private int getTotalBowls() {
		int total = 0;

		for (int slot : slotsChosen.keySet()) {
			total += slotsChosen.get(slot);
		}

		return total;
	}
}