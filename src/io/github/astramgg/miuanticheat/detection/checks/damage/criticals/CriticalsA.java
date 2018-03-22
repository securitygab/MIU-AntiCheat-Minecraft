package io.github.astramgg-miuanticheat.detection.checks.damage.criticals;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import io.github.astramgg-miuanticheat.behaviour.Behaviour;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class CriticalsA extends CheckVersion {

	public CriticalsA(Check check) {
		super(check, "A", "Checks if a player is distributing critical hits without the needed conditions to do so.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			callback(isCritical() && !isValidCritical());
		}
	}

	/**
	 * @return Whether the hit is a critical hit or not.
	 */
	private boolean isCritical() {
		final Player player = profile.getPlayer();
		final Behaviour behaviour = profile.getBehaviour();
		return player.getFallDistance() > 0.0 && !behaviour.isOnLadder() && !behaviour.isOnVine()
				&& !behaviour.isInWater() && !player.hasPotionEffect(PotionEffectType.BLINDNESS)
				&& !player.isInsideVehicle() && !player.isSprinting() && !((Entity) player).isOnGround();
	}

	/**
	 * @return Re-check whether the hit is valid using our values instead of
	 *         Bukkit's (which can be faked by some clients, such as fall
	 *         distance and whether the player is on ground).
	 */
	private boolean isValidCritical() {
		final Behaviour behaviour = profile.getBehaviour();
		return behaviour.getMotion().getFallDistance() > 0.0;
	}

}
