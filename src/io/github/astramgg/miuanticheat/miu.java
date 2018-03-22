package io.github.astramgg.miuanticheat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import io.github.astramgg.miuanticheat.commands.CommandCrescent;
import io.github.astramgg.miuanticheat.listeners.BehaviourListeners;
import io.github.astramgg.miuanticheat.listeners.DetectionListener;

public final class miu extends JavaPlugin {

	private static Crescent instance;

	/**
	 * @return The instance of the plugin.
	 */
	public static miu getInstance() {
		return instance;
	}

	private ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		instance = this;

		this.protocolManager = ProtocolLibrary.getProtocolManager();

		// Configuration.
		loadConfig();

		registerListeners();
		registerCommands();
	}

	private void registerCommands() {
		getCommand("miuanticheat").setExecutor(new CommandCrescent());
	}

	private void registerListeners() {
		final PluginManager pm = Bukkit.getPluginManager();

		final BehaviourListeners behaviourListeners = new BehaviourListeners();
		behaviourListeners.registerPacketListeners();

		pm.registerEvents(behaviourListeners, this);
		pm.registerEvents(new DetectionListener(), this);
	}

	/**
	 * Set the configuration defaults.
	 */
	private void loadConfig() {
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}

	public ProtocolManager getProtocolManager() {
		return protocolManager;
	}

}
