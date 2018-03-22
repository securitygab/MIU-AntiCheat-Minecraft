package io.github.astramgg.miuanticheat.detection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import io.github.astramgg.miuanticheat.miu;
import io.github.astramgg.miuanticheat.detection.checks.Check;
import io.github.astramgg.miuanticheat.info.Profile;

public class Detection {

	private static final int ALERT_INTERVAL = profile.getLastAlertTime() < miu.getInstance().getConfig()
			.getInt("alertInterval");

	private final Profile profile;
	private final Check check;
	private final String checkVersion;
	private final double certainty;
	private final long time;

	/**
	 * @param profile
	 *            The player's profile instance.
	 * @param type
	 *            The type of cheat the player has used to set of the detection
	 */
	public Detection(Profile profile, Check check, String checkVersion, double certainty) {
		this.profile = profile;
		this.check = check;
		// The time that the detection was triggered.
		this.time = System.currentTimeMillis();
		this.checkVersion = checkVersion;
		this.certainty = certainty;
	}

	public void alert() {
		if (System.currentTimeMillis() - profile.getLastAlertTime() < ALERT_INTERVAL) {
			return;
		}

		final String message = ChatColor.RED + profile.getPlayer().getName() + " failed the "
				+ check.getType().getName().toLowerCase() + checkVersion + " check. (certainty: " + check.getCertainty()
				+ "%, ping: " + profile.getPing() + ")";

		// Yay! We get to use Java 8 streams! :D
		Bukkit.getOnlinePlayers().stream().filter(player -> player.isOp())
				.forEach(player -> player.sendMessage(message));

		profile.setLastAlertTime(System.currentTimeMillis());
	}

	public Check getCheck() {
		return check;
	}

	public String getCheckVersion() {
		return checkVersion;
	}

	/**
	 * @return The certainty calculated from this detection alone - ignores
	 *         other detections.
	 */
	public double getCertainty() {
		return certainty;
	}

	/**
	 * @return The time that the detection happened in Unix time * 1000 (time in
	 *         seconds since January 1st 1970)
	 */
	public long getTime() {
		return time;
	}

}
