package io.github.astramgg-miuanticheat.detection.checks.movement.antivelocity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.astramgg-miuanticheat.miu;
import io.github.astramgg-miuanticheat.behaviour.Behaviour;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class AntiVelocityA extends CheckVersion {

	public AntiVelocityA(Check check) {
		super(check, "A", "Check if the player has taken knockback when hit.");
	}

	@Override
	public void call(Event event) {
		final Behaviour behaviour = profile.getBehaviour();
		/*
		 * Thanks Eyremba for identifying these sources of false positives :D
		 * (https://github.com/Eyremba).
		 */
		if (behaviour.isOnLadder() || behaviour.isInWeb() || behaviour.getHeightOfSpace() <= 2) {
			return;
		}
		if (event instanceof PlayerVelocityEvent) {
			final PlayerVelocityEvent pve = (PlayerVelocityEvent) event;

			final Player player = pve.getPlayer();

			final double originalY = player.getLocation().getY();

			final double ticksToMove = (Math.pow(profile.getPing(), 2.0) + 50.0) / 20.0;

			final double expectedYVel = pve.getVelocity().getY();

			// If the player is supposed to move a bit, check if they actually
			// do so!
			if (expectedYVel > 0.1) {
				// Check a little later.
				new BukkitRunnable() {
					// Number of ticks that have been counted so far.
					int time = 0;

					@Override
					public void run() {
						time++;

						final double current = player.getLocation().getY() - originalY;
						if (current > expectedYVel || expectedYVel - current < 0.20) {
							callback(false);
							cancel();
							return;
						}

						if (time > ticksToMove) {
							callback(true);
							cancel();
							return;
						}
					}
				}.runTaskTimer(miu.getInstance(), 0L,
						1L /* Check every tick */);

			}
		}

	}

}
