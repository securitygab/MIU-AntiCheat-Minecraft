package io.github.astramgg.miuanticheat.info;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.github.astramgg.miuanticheat.behaviour.Behaviour;
import io.github.astramgg.miuanticheat.detection.CheckType;
import io.github.astramgg.miuanticheat.detection.checks.Check;
import io.github.astramgg.miuanticheat.detection.checks.blocks.liquids.Liquids;
import io.github.astramgg.miuanticheat.detection.checks.damage.antidamage.AntiDamage;
import io.github.astramgg.miuanticheat.detection.checks.damage.criticals.Criticals;
import io.github.astramgg.miuanticheat.detection.checks.damage.killaura.Killaura;
import io.github.astramgg.miuanticheat.detection.checks.damage.nofall.NoFall;
import io.github.astramgg.miuanticheat.detection.checks.damage.reach.Reach;
import io.github.astramgg.miuanticheat.detection.checks.health.fastheal.FastHeal;
import io.github.astramgg.miuanticheat.detection.checks.interact.autoclicker.Autoclicker;
import io.github.astramgg.miuanticheat.detection.checks.interact.fastbow.Fastbow;
import io.github.astramgg.miuanticheat.detection.checks.interact.noswing.NoSwing;
import io.github.astramgg.miuanticheat.detection.checks.inventory.inventorytweaks.InventoryTweaks;
import io.github.astramgg.miuanticheat.detection.checks.movement.antigravity.AntiGravity;
import io.github.astramgg.miuanticheat.detection.checks.movement.antivelocity.AntiVelocity;
import io.github.astramgg.miuanticheat.detection.checks.movement.fly.Fly;
import io.github.astramgg.miuanticheat.detection.checks.movement.impossible.Impossible;
import io.github.astramgg.miuanticheat.detection.checks.movement.packets.Packets;
import io.github.astramgg.miuanticheat.detection.checks.movement.speed.Speed;
import io.github.astramgg.miuanticheat.detection.checks.movement.waterwalk.WaterWalk;
import io.github.astramgg.miuanticheat.learn.KnownCheating;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class Profile {

	private static ArrayList<Profile> profiles = new ArrayList<Profile>();

	public static Profile getProfile(UUID uuid) {
		for (Profile profile : profiles) {
			if (profile.getUUID() == uuid) {
				return profile;
			}
		}

		// The player does not have a profile, create a new one for them.

		Profile profile = new Profile(uuid);
		profiles.add(profile);

		return profile;

	}

	private final UUID uuid;
	private final Behaviour behaviour;
	// The time the player joined the server on their current session.
	private final long joinTime;
	private ArrayList<Check> checks;
	private long lastAlertTime;

	// Whether a moderator has flagged this player as cheating so we can use the
	// learning feature.
	private KnownCheating knownCheating;

	public Profile(UUID uuid) {
		this.uuid = uuid;
		this.behaviour = new Behaviour(this);
		this.joinTime = System.currentTimeMillis();
		// Create an ArrayList containing all the checks. This should only be
		// able to hold the maximum size of checks.
		this.checks = new ArrayList<Check>(CheckType.values().length);

		this.knownCheating = KnownCheating.UNDEFINED;
		this.lastAlertTime = 0;
		
		addChecks();
	}

	/**
	 * Add the cheat checks so that they can be utilised.
	 */
	private void addChecks() {
		checks.add(new AntiVelocity(this));
		checks.add(new NoFall(this));
		checks.add(new Fastbow(this));
		checks.add(new Fly(this));
		checks.add(new Impossible(this));
		checks.add(new Speed(this));
		checks.add(new WaterWalk(this));
		checks.add(new Killaura(this));
		checks.add(new FastHeal(this));
		checks.add(new Criticals(this));
		checks.add(new Reach(this));
		checks.add(new Packets(this));
		checks.add(new Liquids(this));
		checks.add(new AntiDamage(this));
		checks.add(new Autoclicker(this));
		checks.add(new AntiGravity(this));
		checks.add(new NoSwing(this));
		checks.add(new InventoryTweaks(this));
	}

	/**
	 * @return The UUID of the user.
	 */
	public final UUID getUUID() {
		return uuid;
	}

	/**
	 * @return The Behaviour object of the user.
	 */
	public final Behaviour getBehaviour() {
		return behaviour;
	}

	/**
	 * @param type
	 *            The CheckType that one would like to get the Check object of.
	 * @return The Check object associated with this particular CheckType. If
	 *         this cannot be found, then null will be returned.
	 */
	public Check getCheck(CheckType type) {
		for (Check check : checks) {
			if (check.getType() == type) {
				return check;
			}
		}

		return null;
	}

	/**
	 * @return Whether the player is online (on the server) or not.
	 */
	public final boolean isOnline() {
		return getPlayer() != null;
	}

	/**
	 * @return The Player instance of the user.
	 */
	public final Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	/**
	 * @return The latency of the player to the server (in milliseconds).
	 */
	public final int getPing() {
		final CraftPlayer cp = (CraftPlayer) getPlayer();
		final EntityPlayer ep = (EntityPlayer) cp.getHandle();
		return ep.ping;
	}

	/**
	 * @return The time that the player has been online for (in milliseconds).
	 */
	public final long getOnlineTime() {
		return System.currentTimeMillis() - joinTime;
	}

	public final KnownCheating getKnownCheating() {
		return knownCheating;
	}

	public void setKnownCheating(KnownCheating knownCheating) {
		this.knownCheating = knownCheating;
	}
	
	public final long getLastAlertTime() {
		return lastAlertTime;
	}
	
	public void setLastAlertTime(long lastAlertTime) {
		this.lastAlertTime = lastAlertTime;F
	}

}
