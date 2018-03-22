package io.github.astramgg.miuanticheat.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Helper {

	public static double getDistanceSquaredXZ(Location from, Location to) {
		final double diffX = Math.abs(from.getX() - to.getX()), diffY = Math.abs(from.getY() - to.getY());
		return diffX * diffX + diffY * diffY;
	}

	public static double getYawBetween(Location from, Location to) {
		final Location change = from.clone();

		final Vector start = change.toVector();
		final Vector target = to.toVector();

		// Get the difference between the two locations and set this as the
		// direction (the direct line from location to location).
		change.setDirection(target.subtract(start));

		return change.getYaw();
	}

	public static boolean isSlab(Material material) {
		return material.toString().toLowerCase().contains("slab");
	}

}
