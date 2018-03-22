package io.github.astramgg.miuanticheat.util;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.comphenix.protocol.events.PacketContainer;

public class PlayerPacketEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final Player player;
	private final PacketContainer packet;

	public PlayerPacketEvent(Player player, PacketContainer packet) {
		this.player = player;
		this.packet = packet;
	}

	public final Player getPlayer() {
		return player;
	}

	public final PacketContainer getPacket() {
		return packet;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
