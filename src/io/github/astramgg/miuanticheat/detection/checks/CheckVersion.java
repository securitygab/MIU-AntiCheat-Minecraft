package io.github.astramgg.miuanticheat.detection.checks;

import java.util.ArrayList;

import io.github.astramgg.miuanticheat.detection.CheckType;
import io.github.astramgg.miuanticheat.detection.Detection;
import io.github.astramgg.miuanticheat.info.Profile;

public abstract class CheckVersion implements CheckApproach {

	/**
	 * The category of check that
	 */
	protected final Check check;

	/**
	 * The profile of the player.
	 */
	protected final Profile profile;
	/**
	 * The CheckType of this check version (the overall bracket of what this
	 * check covers).
	 */
	protected final CheckType type;
	/**
	 * The version (the name given to) this specific type of check.
	 */
	protected final String checkVersion;

	/**
	 * A description of what this particular check does.
	 */
	protected final String description;

	/**
	 * Previous suspicious activity triggered by the player for this particular
	 * check.
	 */
	protected ArrayList<Detection> previous;

	/**
	 * The total number of times that this particular CheckVersion has been
	 * called.
	 */
	protected int totalCalls;

	/**
	 * The total number of times that this CheckVersion has returned with belief
	 * that the player could be cheating.
	 */
	protected int improperCalls;

	public CheckVersion(Check check, String checkVersion, String description) {
		this.check = check;
		this.profile = check.getProfile();
		this.type = check.getType();
		this.checkVersion = checkVersion;
		this.description = description;
		this.previous = new ArrayList<Detection>();

		this.totalCalls = 0;
		this.improperCalls = 0;
	}

	/**
	 * This will be called when a CheckVersion finishes executing the check
	 * method. The callback method (this method) will handle this.
	 * 
	 * @param suspicious
	 *            Whether the player showed suspicious behaviour or not.
	 * @param certainty
	 *            The calculated percentage change of the player cheating.
	 * @param info
	 *            Any additional information to be sent to users.
	 */
	public void callback(boolean suspicious) {
		totalCalls++;

		if (suspicious) {
			final Detection detection = new Detection(profile, check, checkVersion, calculateCertainty());

			// Add the detection to the list for this CheckType.
			previous.add(detection);

			improperCalls++;

			detection.alert();
		}
	}

	/**
	 * @return The overall certainty of a player cheating (taking into account
	 *         previous checks).
	 */
	public final double calculateCertainty() {
		return (double) (improperCalls / totalCalls) * 100.0;
	}

	public final CheckType getCheckType() {
		return type;
	}

	public final String getCheckVersion() {
		return checkVersion;
	}

	public final String getCheckDescription() {
		return description;
	}

}
