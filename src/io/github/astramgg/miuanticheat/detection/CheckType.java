package io.github.astramgg.miuanticheat.detection;

import org.bukkit.configuration.file.FileConfiguration;

import io.github.astramgg.miuanticheat.miu;

public enum CheckType {
	WATERWALK("WaterWalk", false, 10), NOFALL("NoFall", false, 10), ANTIVELOCITY("AntiVelocity", false, 10), SNEAK(
			"Sneak", false, 10), FLY("Fly", false, 10), FASTBOW("Fastbow", false, 10), SPEED("Speed", false,
					10), KILLAURA("Killaura", true, 10), FASTHEAL("FastHeal", false, 10), CRITICALS("Criticals", false,
							10), REACH("Reach", false, 10), INVENTORYTWEAKS("InventoryTweaks", false, 10), PACKETS(
									"Packets", false, 10), LIQUIDS("Liquids", false, 10), ANTIDAMAGE("AntiDamage",
											false, 10), AUTOCLICKER("Autoclicker", false, 10), NOSWING("NoSwing", false,
													10), ANTIGRAVITY("AntiGravity", false, 10);

	private final String name;
	private final boolean canLearn;
	private final int normalCheatConsider;
	private final int cheatConsider;
	private final boolean prevent;

	private CheckType(String name, boolean canLearn, int normalCheatConsider) {
		this.name = name;
		this.canLearn = canLearn;
		this.normalCheatConsider = normalCheatConsider;

		final FileConfiguration fc = miu.getInstance().getConfig();

		/*
		 * The name of the check used to signify this check in the file (in
		 * lower case).
		 */
		final String systemName = name.toLowerCase();

		this.cheatConsider = fc.getInt(systemName + ".cheatConsider");
		this.prevent = fc.getBoolean(systemName + ".prevent");
	}

	/**
	 * @return The user-friendly name of the check.
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return Whether the plugin can actually learn from this CheckType.
	 */
	public final boolean canLearn() {
		return canLearn;
	}

	/**
	 * @return The suggested, default, value for cheatConsider;
	 */
	public final int getNormalCheatConsider() {
		return normalCheatConsider;
	}

	/**
	 * @return The number of checks that need to be set off for the player to
	 *         fully be considered cheating.
	 */
	public final int getCheatConsider() {
		return cheatConsider;
	}

	/**
	 * @return Whether the player should be prevented from performing an action
	 *         (e.g. teleported back).
	 */
	public final boolean shouldPrevent() {
		return prevent;
	}
}
