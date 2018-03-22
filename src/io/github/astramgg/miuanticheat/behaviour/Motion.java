package io.github.miuanticheat.behaviour;

public class Motion {

	private final Behaviour behaviour;

	private double lastYDiff;

	/**
	 * The y coordinate the player was last at before they started falling.
	 */
	private double lastY;

	private double lastSprint;
	private double lastFly;

	public Motion(Behaviour behaviour) {
		this.behaviour = behaviour;
		this.lastYDiff = -1.0;

		this.lastY = -1.0;
		this.lastSprint = -1.0;
		this.lastFly = -1.0;
	}

	public final Behaviour getBehaviour() {
		return behaviour;
	}

	public void setLastYDiff(double lastYDiff) {
		this.lastYDiff = lastYDiff;
	}

	/**
	 * @return The amount the player's y value should go down by.
	 */
	public double calculateGravityEffect() {
		if (lastYDiff != -1.0) {
			double yDiff = (lastYDiff - 0.08) * 0.98;
			this.lastYDiff = yDiff;

			if (yDiff > 3.92) {
				// 3.92 is the terminal velocity for a player.
				yDiff = 3.92;
			}

			return yDiff;
		}
		return 0.0;
	}

	/**
	 * @return The last y coordinate that the player was at the ground at.
	 */
	public final double getLastY() {
		return lastY;
	}

	/**
	 * @param lastY
	 *            The value you want to update the lastY variable to.
	 */
	public final void setLastY(double lastY) {
		this.lastY = lastY;
	}

	public double getLastSprint() {
		return lastSprint;
	}

	public void setLastSprint(double lastSprint) {
		this.lastSprint = lastSprint;
	}

	public double getLastFly() {
		return lastFly;
	}

	public void setLastFly(double lastFly) {
		this.lastFly = lastFly;
	}

	/**
	 * @return The distance that the player has fallen.
	 */
	public final double getFallDistance() {
		// The player cannot have a negative fall distance.
		return Math.max(lastY - behaviour.getPlayer().getLocation().getY(), 0.0);
	}

	/**
	 * @return If the player is ascending or not.
	 */
	public final boolean isDescending() {
		return behaviour.getPlayer().getVelocity().getY() < 0.0;
	}

	/**
	 * @return If the player is ascending or not.
	 */
	public final boolean isAscending() {
		return behaviour.getPlayer().getVelocity().getY() > 0.0;
	}

}
