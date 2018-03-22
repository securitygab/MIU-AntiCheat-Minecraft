package io.github.miuanticheat.behaviour;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.miuanticheat.info.Profile;

public class Behaviour {

	private final Profile profile;

	private final Motion motion;

	/**
	 * @param profile
	 *            The profile of the player whose behaviour is being analysed.
	 */
	public Behaviour(Profile profile) {
		this.profile = profile;
		this.motion = new Motion(this);
	}

	public final Motion getMotion() {
		return motion;
	}

	/**
	 * @return If the player is in water or not.
	 */
	public final boolean isInWater() {
		Material in = getBlockPlayerIsIn().getType();
		return in == Material.WATER || in == Material.STATIONARY_WATER;
	}

	/**
	 * @return If the player is in a cobweb or not.
	 */
	public final boolean isInWeb() {
		return getBlockPlayerIsIn().getType() == Material.WEB;
	}

	/**
	 * @return If the player is on a ladder or not.
	 */
	public final boolean isOnLadder() {
		return getBlockPlayerIsIn().getType() == Material.LADDER;
	}

	/**
	 * @return If the player is on a vine or not.
	 */
	public final boolean isOnVine() {
		return getBlockPlayerIsIn().getType() == Material.VINE;
	}

	/**
	 * @return If the player is standing on a liquid block or not.
	 */
	public final boolean isOnLiquidBlock() {
		return getBlockUnderPlayer().isLiquid();
	}

	/**
	 * @return If the player is in a liquid block or not.
	 */
	public final boolean isInLiquid() {
		return getBlockPlayerIsIn().isLiquid();
	}

	/**
	 * @return If the player is on the ground or not.
	 */
	public final boolean isOnGround() {
		final double y = getPlayer().getLocation().getY();
		return y % 1.0 == 0.0 || y % 0.5 == 0.0 || getBlockUnderPlayer().getType().isSolid()
				|| motion.getFallDistance() <= 0.5;
	}

	/**
	 * @return The block that a player is in.
	 */
	public final Block getBlockPlayerIsIn() {
		return getPlayer().getLocation().getBlock();
	}

	/**
	 * @return The block above the player.
	 */
	public final Block getBlockAbovePlayer() {
		return getBlockOnFace(BlockFace.UP);
	}

	/**
	 * @return The block under the player.
	 */
	public final Block getBlockUnderPlayer() {
		return getBlockOnFace(BlockFace.DOWN);
	}

	/**
	 * @param face
	 *            Which BlockFace to check.
	 * @return The block on this BlockFace.
	 */
	public final Block getBlockOnFace(BlockFace face) {
		return getPlayer().getLocation().getBlock().getRelative(face);
	}

	/**
	 * @param type
	 *            Get the level of a specific PotionEffect on a player.
	 * @return
	 */
	public final int getPotionEffectLevel(PotionEffectType type) {
		for (PotionEffect effect : getPlayer().getActivePotionEffects()) {
			if (effect.getType().equals(type)) {
				return effect.getAmplifier();
			}
		}

		return 0;
	}

	/**
	 * @param level
	 *            The level of the enchantment.
	 * @param typeModifier
	 *            The type modifier of the enchantment.
	 * @return The calculated EPF.
	 */
	public final double getEPF(int level, double typeModifier) {
		return Math.floor((6 + level * level) * typeModifier / 3);
	}

	/**
	 * @return The height of the space that the player is in.
	 */
	public final int getHeightOfSpace() {
		final int max = getPlayer().getWorld().getMaxHeight();
		for (int y = 0; y <= max; y++) {
			final Location added = getPlayer().getLocation().clone().add(0, y, 0);
			if (added.getBlock().getType().isSolid()) {
				return y;
			}
		}
		return max;
	}

	/**
	 * @return The amount of blocks below a player until a solid block is
	 *         reached.
	 */
	public final int getBlocksBelowGround() {
		final int playerY = getPlayer().getLocation().getBlockY();
		for (int y = playerY; y >= 0; y--) {
			final Location added = getPlayer().getLocation().clone().subtract(0, playerY - y, 0);
			if (added.getBlock().getType().isSolid()) {
				return playerY - y;
			}
		}
		return 0;
	}

	public final boolean isInCreativeOrSpectator() {
		final GameMode mode = getPlayer().getGameMode();
		return mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR;
	}

	/**
	 * @return Get the Player.
	 */
	protected final Player getPlayer() {
		return profile.getPlayer();
	}

}
