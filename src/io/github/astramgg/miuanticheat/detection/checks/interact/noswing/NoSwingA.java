package io.github.astramgg-miuanticheat.detection.checks.interact.noswing;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class NoSwingA extends CheckVersion {

	private long lastInteract;

	public NoSwingA(Check check) {
		super(check, "A", "Check if the player swings");
		this.lastInteract = -1;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerInteractEvent) {
			final PlayerInteractEvent pie = (PlayerInteractEvent) event;

			// Types of interacting where the arm must swing.
			if (pie.getAction() == Action.LEFT_CLICK_AIR || pie.getAction() == Action.LEFT_CLICK_BLOCK) {
				lastInteract = System.currentTimeMillis();
			}
		} else if (event instanceof PlayerAnimationEvent) {
			final PlayerAnimationEvent pae = (PlayerAnimationEvent) event;

			if (pae.getAnimationType() == PlayerAnimationType.ARM_SWING) {
				final long difference = System.currentTimeMillis() - lastInteract;

				Bukkit.broadcastMessage("swing difference: " + difference);
			}
		}
	}

}
