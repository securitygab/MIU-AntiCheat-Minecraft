package io.github.astramgg-miuanticheat.detection.checks.movement.packets;

import org.bukkit.event.Event;

import com.comphenix.protocol.PacketType;

import io.github.astramgg-miuanticheat.miu;
import io.github.astramgg-miuanticheat.detection.checks.Check;
import io.github.astramgg-miuanticheat.detection.checks.CheckVersion;
import io.github.astramgg-miuanticheat.util.PlayerPacketEvent;

public class PacketsA extends CheckVersion {

	private static final double MAX_PACKETS_PER_TICK = miu.getInstance().getConfig()
			.getDouble("packets.a.maxPacketsPerTick");

	private long lastTime;
	private int movements;

	public PacketsA(Check check) {
		super(check, "A", "Checks the packets that the player is sending against their latency.");
		this.lastTime = -1;
		this.movements = 0;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerPacketEvent) {

			final PlayerPacketEvent ppe = (PlayerPacketEvent) event;

			if (ppe.getPacket().getType() != PacketType.Play.Client.POSITION) {
				return;
			}

			if (lastTime != -1) {
				final long difference = System.currentTimeMillis() - lastTime;

				if (difference >= 1000) {
					// Check every second.

					/*
					 * The amount of movement packets sent every tick.
					 * 
					 * I have tested the value this gives and the normal client
					 * sends about 1 packet per tick.
					 */
					final double movementsPerTick = ((double) movements / ((double) difference / 1000.0)) / 20.0;

					if (movementsPerTick > MAX_PACKETS_PER_TICK) {
						// The player is sending more packets than allowed.

						final double perecentageDifference = ((movementsPerTick - MAX_PACKETS_PER_TICK)
								/ MAX_PACKETS_PER_TICK) * 100.0;

						/*
						 * If the percentage difference is over 10% of what is
						 * allowed, the player is likely to be cheating.
						 */
						if (perecentageDifference > 10.0) {
							callback(true);
						}

						callback(true);
					}

					// Reset everything.
					lastTime = System.currentTimeMillis();
					movements = 0;
				}
			} else {
				lastTime = System.currentTimeMillis();
			}

			movements++;
		}
	}

}
