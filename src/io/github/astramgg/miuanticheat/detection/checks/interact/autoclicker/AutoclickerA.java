package io.github.astramgg-miuanticheat.detection.checks.interact.autoclicker;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.astramgg-miuanticheat.miu;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class AutoclickerA extends CheckVersion {

	private static final double SUSPICIOUS_CPS = miu.getInstance().getConfig()
			.getDouble("autoclicker.a.suspiciousCPS");
	private static final int REVIEW_TIME = miu.getInstance().getConfig().getInt("autoclicker.a.reviewTime");
	private static final int NEEDED_SAMPLES = miu.getInstance().getConfig().getInt("autoclicker.a.neededSamples");
	private static final double ALLOWED_INTERVAL = miu.getInstance().getConfig()
			.getDouble("autoclicker.a.allowedInterval");

	private AutoclickerStore store;
	private int aboveSuspicious;

	public AutoclickerA(Check check) {
		super(check, "A", "Calculates CPS from data collected from the player swinging their arm.");
		this.store = new AutoclickerStore();
		this.aboveSuspicious = 0;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerInteractEvent) {
			final PlayerInteractEvent pie = (PlayerInteractEvent) event;
			if (pie.getAction() != Action.LEFT_CLICK_AIR && pie.getAction() != Action.LEFT_CLICK_BLOCK
					&& !pie.isCancelled()) {
				return;
			}
		} else if (!(event instanceof EntityDamageByEntityEvent)) {
			return;
		}

		store.call();

		if (store.getCurrentClickRate() > SUSPICIOUS_CPS) {
			aboveSuspicious++;
			final double certainty = (aboveSuspicious / store.getTotalClicks()) * 100.0;
			if (certainty > 70.0) {
				/*
				 * If more than 70% of the clicks are deemed to be suspicious,
				 * the player is probably cheating.
				 */
				callback(true);
			}
		}

		final ArrayList<Double> clicks = store.getCPSInTime(REVIEW_TIME);

		if (clicks.size() < NEEDED_SAMPLES) {
			return;
		}
		// Check for oscillating autoclickers.

		// Every time we identify something suspicious, add to this.
		int chance = 0;

		ArrayList<Double> differences = new ArrayList<Double>();
		for (int i = 0; i < clicks.size(); i++) {
			if (i != 0) {
				final double current = clicks.get(i), previous = clicks.get(i - 1);

				if (previous < current) {
					chance++;
				}

				if (i % 1 == 0) {
					// Use absolute so we can identify if the
					// autoclicker
					// slows down too.
					final double difference = Math.abs(current - previous);

					if (differences.size() != 0) {
						// Compare this difference to previous
						// differences.
						for (int p = 0; p < differences.size(); p++) {
							final double differenceBetweenDifferences = Math.abs(difference - differences.get(p));
							if (differenceBetweenDifferences == 0.0
									|| differenceBetweenDifferences < ALLOWED_INTERVAL) {
								chance++;
							}
						}
					}

					differences.add(difference);
				}
			}
		}

		final Double[] sorted = clicks.toArray(new Double[clicks.size()]);

		final double smallest = sorted[0], largest = sorted[sorted.length - 1];

		if (largest - smallest == 0.0) {
			chance += sorted.length;
		}

		Bukkit.broadcastMessage("autoclicker chance: " + chance);
	}

	public AutoclickerStore getStore() {
		return store;
	}

}
