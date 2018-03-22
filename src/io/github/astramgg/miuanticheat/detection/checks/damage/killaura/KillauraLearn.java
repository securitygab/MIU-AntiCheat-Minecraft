package io.github.astramgg-miuanticheat.detection.checks.damage.killaura;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.astramgg-miuanticheat.miu;
import io.github.astramgg-miuanticheat.detection.CheckType;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;
import io.github.astramgg-miuanticheat.detection.checks.damage.reach.ReachA;
import io.github.astramgg-miuanticheat.detection.checks.interact.autoclicker.AutoclickerA;
import io.github.astramgg-miuanticheat.learn.Learn;
import io.github.astramgg-miuanticheat.util.Helper;

public class KillauraLearn extends CheckVersion {

	private static final boolean DISABLED = miu.getInstance().getConfig().getBoolean("killaura.learn.disabled");

	public KillauraLearn(Check check) {
		super(check, "Learn", "Uses data to calculate a value which is then stored for future retrieval.");
	}

	@Override
	public void call(Event event) {
		if (!DISABLED) {
			if (event instanceof EntityDamageByEntityEvent) {
				final EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;

				final Player player = profile.getPlayer();

				final double reachSquared = Helper.getDistanceSquaredXZ(player.getLocation(),
						edbe.getEntity().getLocation());

				// Get the ReachA CheckVersion.
				final ReachA reach = (ReachA) profile.getCheck(CheckType.REACH).getCheckVersion("A");

				final double averageReach = reach.getAverageSquaredReach();

				final AutoclickerA autoclicker = (AutoclickerA) profile.getCheck(CheckType.AUTOCLICKER)
						.getCheckVersion("A");

				final double currentDamageCPS = autoclicker.getStore().getCurrentClickRate();

				final int ping = profile.getPing();

				// Add one, as this can't be zero as it will mess up our
				// calculations if it is.
				final double angle = Helper.getYawBetween(profile.getPlayer().getEyeLocation(),
						edbe.getEntity().getLocation()) + 1;

				final double value = reachSquared * averageReach * angle * ((ping + 100) / 100) * currentDamageCPS;

				final Learn learn = new Learn(type, value);

				learn.storeData(profile.getKnownCheating());

				final double comparison = learn.compare();

				Bukkit.broadcastMessage("learn comparison: " + comparison);

				if (comparison > 100.0) {
					callback(true);
				} else {
					callback(false);
				}
			}
		}
	}

}
