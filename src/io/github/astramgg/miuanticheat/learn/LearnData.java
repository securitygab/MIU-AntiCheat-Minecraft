package io.github.astramgg.miuanticheat.learn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.astramgg.miuanticheat.miu;
import io.github.astramgg.miuanticheat.detection.CheckType;

public class LearnData {

	private static ArrayList<LearnData> learnData = new ArrayList<LearnData>();

	public static LearnData getLearnData(CheckType type) {
		if (!type.canLearn()) {
			return null;
		}

		LearnData data = null;

		for (LearnData check : learnData) {
			if (check.getType() == type) {
				data = check;
			}
		}

		if (data == null) {
			data = new LearnData(type);
			learnData.add(data);
		}

		return data;
	}

	public static final File DIRECTORY = new File(
			miu.getInstance().getDataFolder().getPath() + File.separator + "LearnStore");

	// Init.
	{
		DIRECTORY.mkdirs();
	}

	private final CheckType type;

	public LearnData(CheckType type) {
		this.type = type;
	}

	public void storeNewData(Learn learn, KnownCheating knownCheating) {
		final FileConfiguration fc = getConfig();

		// Generate a UUID for this check (its identifier).
		UUID uuid = UUID.randomUUID();

		while (fc.isConfigurationSection(uuid.toString())) {
			uuid = UUID.randomUUID();
		}

		// The path to put the data in the file.
		String cheatPath = getCheatPath(knownCheating);

		// Get general values.
		final double currentMean = fc.getDouble(cheatPath + "CurrentMean");
		final double currentLowRange = fc.getDouble(cheatPath + "CurrentLowRange");
		final double currentHighRange = fc.getDouble(cheatPath + "CurrentHighRange");
		final long totalSamples = fc.getLong(cheatPath + "TotalSamples");

		if (currentLowRange == 0.0 || learn.getValue() < currentLowRange) {
			fc.set(cheatPath + "CurrentLowRange", learn.getValue());
		} else if (learn.getValue() > currentHighRange) {
			fc.set(cheatPath + "CurrentHighRange", learn.getValue());
		}

		// Calculate the new average.
		double updateMean = (currentMean + learn.getValue()) / 2.0;

		fc.set(cheatPath + "CurrentMean", updateMean);
		fc.set(cheatPath + "TotalSamples", totalSamples + 1);

		// Save the file.
		save(fc);
	}

	/**
	 * @param knownCheating
	 *            The player's cheating status.
	 * @return The path string needed for the corresponding cheating boolean
	 *         value.
	 */
	private String getCheatPath(KnownCheating knownCheating) {
		return knownCheating == KnownCheating.YES ? "cheating" : "legitimate";
	}

	private YamlConfiguration getConfig() {
		return YamlConfiguration.loadConfiguration(getRecordFile());
	}

	/**
	 * @param type
	 *            The CheckType that one wants to get the file for.
	 * @return The File object associated with this CheckType.
	 */
	private File getRecordFile() {
		final File storeTo = new File(DIRECTORY.getPath() + File.separator + type.getName().toLowerCase() + ".yml");
		createIfNotExists(storeTo);
		return storeTo;
	}

	private void save(FileConfiguration fc) {
		try {
			fc.save(getRecordFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a file if it does not already exist.
	 * 
	 * @param file
	 *            The file one wishes to perform the action on.
	 * @return Whether the file has been created or not.
	 */
	private boolean createIfNotExists(File file) {
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public double getMeanAverage(KnownCheating knownCheating) {
		return getConfig().getDouble(getCheatPath(knownCheating) + "CurrentMean");
	}

	public double getCurrentLowRange(KnownCheating knownCheating) {
		return getConfig().getDouble(getCheatPath(knownCheating) + "CurrentLowRange");
	}

	public double getCurrentHighRange(KnownCheating knownCheating) {
		return getConfig().getDouble(getCheatPath(knownCheating) + "CurrentHighRange");
	}

	public int getTotalSamples() {
		return getConfig().getInt("TotalSamples");
	}

	public CheckType getType() {
		return type;
	}

}
