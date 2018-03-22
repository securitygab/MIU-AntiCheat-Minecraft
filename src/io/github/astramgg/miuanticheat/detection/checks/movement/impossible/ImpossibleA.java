package io.github.astramgg-miuanticheatdetection.checks.movement.impossible;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.astramgg-miuanticheatdetection.checks.Check;
import io.github.astramgg-miuanticheatdetection.checks.CheckVersion;

public class ImpossibleA extends CheckVersion {

	public ImpossibleA(Check check) {
		super(check, "A", "Checks whether the player is completing impossible actions.");
	}

	@Override
	public void call(Event event) {
		final Player player = profile.getPlayer();

		if (event instanceof PlayerMoveEvent) {
			if ((player.isSprinting() || player.isBlocking() || player.isConversing()) && player.isSneaking()) {
				callback(true);
				return;
			}
			callback(false);
		} else if (event instanceof AsyncPlayerChatEvent) {
			if ((player.isBlocking() || player.isSprinting() || player.isSneaking())) {
				callback(true);
				return;
			}
			callback(false);
		}
	}

}
