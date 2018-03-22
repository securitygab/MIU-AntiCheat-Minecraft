package io.github.astramgg-miuanticheat.detection.checks.interact.autoclicker;

public class ClickRate {

	private final double cps;
	private final long time;

	public ClickRate(double cps) {
		this.cps = cps;
		this.time = System.currentTimeMillis();
	}

	public double getCPS() {
		return cps;
	}

	public long getTime() {
		return time;
	}

}
