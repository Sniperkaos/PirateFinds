package me.cworldstar.piratefinds.impl.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import me.cworldstar.piratefinds.PirateFinds;

public class Config {
	
	private YamlConfiguration config;
	private PirateFinds plugin;
	private boolean exists = false;
	
	public Config(File file) {
		this.plugin = PirateFinds.getThisPlugin();
		if(file.exists()) {
			this.config = new YamlConfiguration();
			try {
				config.load(file);
				this.exists = true;
			} catch(IOException | InvalidConfigurationException e) {
				e.printStackTrace();
				config = YamlConfiguration.loadConfiguration(file);
			}
		} else {
			file.getParentFile().mkdirs();
            plugin.saveResource(file.getName(), true);
		}
	}
	
	public YamlConfiguration getConfig() {
		return this.config;
	}

	public static YamlConfiguration load(File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config;
	}
	
	public static Config saveDefault(File file, InputStream stream) {
		FileWriter writer;
		InputStreamReader reader = new InputStreamReader(stream);
		try {
			writer = new FileWriter(file);
			YamlConfiguration config = YamlConfiguration.loadConfiguration(reader);
			writer.write(config.saveToString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return new Config(file);
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return exists;
	}
}
