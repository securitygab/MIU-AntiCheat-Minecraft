package io.github.astramgg.miuanticheat.detection.checks;

import org.bukkit.event.Event;

public interface CheckApproach {
	/**
	 * Called to check for cheating (through a Bukkit listener).
	 * 
	 * @param event
	 *            The event the check will need to analyse.
	 */
	void call(Event event);
}
