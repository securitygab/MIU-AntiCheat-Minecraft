package io.github.astramgg-miuanticheat.detection.checks.damage.killaura;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.astramgg-miuanticheat.miu;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;

public class KillauraA extends CheckVersion {

	private static final int ALLOWED_HITS_PER_SECOND = miu.getInstance().getConfig()
			.getInt("killaura.a.allowedHitsPerSecond");
	private static final int ATTACK_TIME_COUNT_INTERVAL = miu.getInstance().getConfig()
			.getInt("killaura.a.attackTimeCountInterval");

	/*
	 * The amount of entities the player hit in an amount of time (stored in
	 * milliseconds).
	 */
	private TreeMap<Long, Integer> hits;
	// Entities the player has hit in the allowed time.
	private TreeMap<Long, ArrayList<Integer>> hasHit;

	public KillauraA(Check check) {
		super(check, "A", "Checks the amount of entities that a player attacks in a certain amount of time.");
		this.hits = new TreeMap<Long, Integer>();
		this.hasHit = new TreeMap<Long, ArrayList<Integer>>();
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			final EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;

			final Entry<Long, Integer> last = hits.lastEntry();

			final long current = System.currentTimeMillis();

			final int attackedID = edbe.getEntity().getEntityId();

			ArrayList<Integer> ids;

			if (last == null || current - last.getKey() >= ATTACK_TIME_COUNT_INTERVAL) {
				/*
				 * We need to create a new entry. This is because either: there
				 * are currently no entries, or one has to be created because
				 * the old one is outdated.
				 */
				hits.put(current, 1);

				ids = new ArrayList<Integer>();

				if (!ids.contains(attackedID)) {
					ids.add(attackedID);
				}

				hasHit.put(current, ids);
			} else {
				/*
				 * We do not need to create a new entry. We will simply add to
				 * the old one.
				 */
				final int currentHits = last.getValue();
				hits.put(last.getKey(), currentHits + 1);

				ids = hasHit.lastEntry().getValue();

				if (!ids.contains(attackedID)) {
					ids.add(attackedID);
				}

				// Check the hits per second of the player.

				final double hitsPerSecond = last.getValue() / ((current - last.getKey()) / 1000.0);

				// The amount of extra hits per second the player is allowed to
				// deal.
				final double allowedExtraHits = ids.size() != 1
						? ids.size()/*
									 * The amount of entities that the player
									 * has hit. This can increase the hits per
									 * second value by about 0.75 per entity.
									 */ * 0.75 : 0.0;

				callback(hitsPerSecond > ALLOWED_HITS_PER_SECOND + allowedExtraHits);
			}
		}
	}

}
