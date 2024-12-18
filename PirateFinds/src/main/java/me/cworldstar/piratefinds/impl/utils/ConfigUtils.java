package me.cworldstar.piratefinds.impl.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.YamlConfiguration;

import me.cworldstar.piratefinds.PirateFinds;

public class ConfigUtils {
	public ConfigUtils() {
		throw new IllegalStateException("Static class");
	}
	
	public static void saveDefault(File toSave, String resource) {
		if(!toSave.exists()) {
			try {
				toSave.createNewFile();
				InputStreamReader reader = new InputStreamReader(PirateFinds.getThisPlugin().getResource(resource));
				YamlConfiguration normal = YamlConfiguration.loadConfiguration(reader);
				normal.save(toSave);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
