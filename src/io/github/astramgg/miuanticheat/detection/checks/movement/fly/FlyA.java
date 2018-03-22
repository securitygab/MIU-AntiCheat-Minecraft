package io.github.astramgg-miuanticheat.detection.checks.movement.fly;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class FlyA extends CheckVersion {

	private long startTime;

	public FlyA(Check check) {
		super(check, "A", "Checks if the player has been ascending for a large amount of time.");
		this.startTime = -1;
	}

	@Override
	public void call(Event event) {
		if (profile.getBehaviour().getPotionEffectLevel(PotionEffectType.SPEED) > 2) {
			/*
			 * Potion effects above this level cause false positives. They're
			 * not available without the /effect command anyway.
			 */
			return;
		}

		if (event instanceof PlayerMoveEvent) {

			final Player player = profile.getPlayer();

			if (!profile.getBehaviour().isInCreativeOrSpectator() || !player.getAllowFlight()) {
				final PlayerMoveEvent pme = (PlayerMoveEvent) event;

				final double distanceSquared = pme.getTo().toVector().distanceSquared(pme.getFrom().toVector());

				/*
				 * If the player isn't on the ground (they could be walking),
				 * check if the block above the player is air and the player has
				 * not fallen.
				 * 
				 * If so, they are using fly cheats.
				 */
				if (!profile.getBehaviour().isOnGround()
						&& profile.getBehaviour().getBlockAbovePlayer().getType() == Material.AIR
						&& !profile.getBehaviour().getMotion().isDescending()) {

					if (startTime == -1) {
						startTime = System.currentTimeMillis();
						return;
					}

					/*
					 * Rough values for what this should be. Normal: 350, Jump
					 * Boost I: 250, Jump Boost II: Same as Normal.
					 */
					final long timeInAir = System.currentTimeMillis() - startTime;

					int allowed = 0;

					final int jumpBoostLevel = profile.getBehaviour().getPotionEffectLevel(PotionEffectType.JUMP);

					if (jumpBoostLevel == 0 || jumpBoostLevel == 2) {
						allowed = 350;
					} else if (jumpBoostLevel == 1) {
						allowed = 250;
					} else {
						return;
					}

					/*
					 * I roughly measured jumping as 0.25 as a vector squared
					 * between the to and from points.
					 * 
					 * Here, 0.30 is being used to avoid false positives.
					 */
					if (distanceSquared > 0.30 && timeInAir > allowed) {
						callback(true);
					}
				} else {
					startTime = System.currentTimeMillis();
				}

			}
		}
	}

}
