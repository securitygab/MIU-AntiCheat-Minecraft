package io.github.astramgg-miuanticheat.detection.checks.damage.antidamage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import io.github.astramgg-miuanticheat.Crescent;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class AntiDamageA extends CheckVersion {

	public AntiDamageA(Check check) {
		super(check, "A", "Checks if a player takes the required damage when they ar eon fire or next to a cactus.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageEvent) {
			final EntityDamageEvent ede = (EntityDamageEvent) event;
			final DamageCause cause = ede.getCause();

			if (cause == DamageCause.FIRE || cause == DamageCause.CONTACT) {
				final Player player = profile.getPlayer();

				if (cause == DamageCause.FIRE && player.getFoodLevel() < 20) {
					// The AntiFire cheat only works when the hunger bar is
					// full.
					return;
				}

				final double previousHealth = player.getHealth();

				// Check a little later.
				Bukkit.getScheduler().runTaskLater(Crescent.getInstance(), () -> {
					if (player.getHealth() > previousHealth - ede.getDamage()) {
						callback(true);
					}
				}, 2L);
			}
		}
	}

}
