package io.github.astramgg.miuanticheat.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

import io.github.astramgg.miuanticheat.detection.CheckType;
import io.github.astramgg.miuanticheat.detection.checks.CheckVersion;
import io.github.astramgg.miuanticheat.info.Profile;
import io.github.astramgg.miuanticheat.util.PlayerPacketEvent;

public class DetectionListener implements Listener {

	@EventHandler
	public void onPlayerPacket(PlayerPacketEvent event) {
		final Player player = event.getPlayer();

		getCheckVersion(player, CheckType.PACKETS, "A").call(event);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();

		final Location from = event.getFrom(), to = event.getTo();

		if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY()
				&& from.getBlockZ() == to.getBlockZ()) {
			// If the player has only moved their head.

			getCheckVersion(player, CheckType.SPEED, "A").call(event);

			getCheckVersion(player, CheckType.FLY, "A").call(event);

			getCheckVersion(player, CheckType.ANTIGRAVITY, "A").call(event);

			getCheckVersion(player, CheckType.WATERWALK, "A").call(event);

			getCheckVersion(player, CheckType.NOFALL, "A").call(event);

			getCheckVersion(player, CheckType.SNEAK, "A").call(event);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			final Player player = (Player) event.getDamager();

			getCheckVersion(player, CheckType.REACH, "A").call(event);

			getCheckVersion(player, CheckType.CRITICALS, "A").call(event);

			getCheckVersion(player, CheckType.AUTOCLICKER, "A").call(event);

			getCheckVersion(player, CheckType.KILLAURA, "A").call(event);

			getCheckVersion(player, CheckType.KILLAURA, "B").call(event);

			getCheckVersion(player, CheckType.KILLAURA, "C").call(event);

			getCheckVersion(player, CheckType.KILLAURA, "Learn").call(event);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		final Player player = (Player) event.getEntity();

		getCheckVersion(player, CheckType.ANTIDAMAGE, "A").call(event);
	}

	@EventHandler
	public void onPlayerInteract(EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		final Player player = (Player) event.getEntity();

		getCheckVersion(player, CheckType.FASTBOW, "A").call(event);
	}

	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		final Player player = (Player) event.getEntity();

		getCheckVersion(player, CheckType.FASTHEAL, "A").call(event);
	}

	@EventHandler
	public void onPlayerVelocity(PlayerVelocityEvent event) {
		final Player player = event.getPlayer();

		getCheckVersion(player, CheckType.ANTIVELOCITY, "A").call(event);
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();

		getCheckVersion(player, CheckType.SNEAK, "A").call(event);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		final Player player = event.getPlayer();

		getCheckVersion(player, CheckType.LIQUIDS, "A").call(event);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		final Player player = event.getPlayer();

		getCheckVersion(player, CheckType.AUTOCLICKER, "A").call(event);

		getCheckVersion(player, CheckType.NOSWING, "A").call(event);
	}

	@EventHandler
	public void onPlayerAnimation(PlayerAnimationEvent event) {
		final Player player = event.getPlayer();

		getCheckVersion(player, CheckType.AUTOCLICKER, "A").call(event);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}

		final Player player = (Player) event.getWhoClicked();

		getCheckVersion(player, CheckType.INVENTORYTWEAKS, "A").call(event);

		getCheckVersion(player, CheckType.INVENTORYTWEAKS, "B").call(event);
	}

	private Profile getProfile(Player player) {
		return Profile.getProfile(player.getUniqueId());
	}

	private CheckVersion getCheckVersion(Player player, CheckType type, String version) {
		return getProfile(player).getCheck(type).getCheckVersion(version);
	}

}
