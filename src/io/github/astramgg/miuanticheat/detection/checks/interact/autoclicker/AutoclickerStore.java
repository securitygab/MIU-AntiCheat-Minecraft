package io.github.astramgg-miuanticheat.detection.checks.interact.autoclicker;

import java.util.ArrayList;

public class AutoclickerStore {

	private int totalClicks, currentClicks;
	private double currentClickRate;
	private long totalStartTime, currentStartTime;

	private ArrayList<ClickRate> clickRates;

	public AutoclickerStore() {
		this.totalClicks = currentClicks = 0;
		this.currentClickRate = 0.0;
		this.totalStartTime = System.currentTimeMillis();
		this.currentStartTime = -1;

		this.clickRates = new ArrayList<ClickRate>();
	}

	/**
	 * Called when we want to log a click.
	 */
	public void call() {
		currentClicks++;

		final long current = System.currentTimeMillis();

		if (currentStartTime == -1) {
			currentStartTime = current;
		} else {
			long difference = current - currentStartTime;
			if (difference >= 1000) {

				// Check every second.

				currentClickRate = (double) currentClicks / (difference / 1000.0);

				clickRates.add(new ClickRate(currentClickRate));

				currentClicks = 0;
				currentStartTime = System.currentTimeMillis();
			}
		}

		totalClicks++;
	}

	public int getTotalClicks() {
		return totalClicks;
	}

	public double getCurrentClickRate() {
		return currentClickRate;
	}

	public double getTotalClickRate() {
		return totalClicks / (System.currentTimeMillis() - totalStartTime);
	}

	/**
	 * @param time
	 *            The amount of time to check for in milliseconds.
	 * @return The clicks that have happened in last specified amount of time.
	 */
	public ArrayList<Double> getCPSInTime(long time) {
		final long current = System.currentTimeMillis();

		ArrayList<Double> clicks = new ArrayList<Double>();

		for (ClickRate rate : clickRates) {
			if (current - rate.getTime() <= time) {
				clicks.add(rate.getCPS());
			}
		}

		return clicks;
	}

}
