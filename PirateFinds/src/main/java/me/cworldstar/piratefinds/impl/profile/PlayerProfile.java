package me.cworldstar.piratefinds.impl.profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.cworldstar.piratefinds.PirateFinds;


public class PlayerProfile {
	
	protected static HashMap<UUID, Profile> LoadedProfiles = new HashMap<UUID, Profile>();
	
	public static Profile loadPlayerProfile(Player player) {
		
		PirateFinds plugin = PirateFinds.getThisPlugin();
		
		UUID playerUUID = player.getUniqueId();
		
		File file = new File("plugins/" + plugin.getName().replace(" ", "_") + "/profiles", playerUUID.toString() + ".json");
		Reader reader;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			plugin.getLogger().log(Level.WARNING, "Player profile for player " + player.getName() + " does not exist! Is this a first join?");
			e.printStackTrace();
			
			return Profile.NULL_PROFILE;
		}
		
		JsonElement profileJson = JsonParser.parseReader(reader);
		Profile profile = Profile.fromJSON(profileJson);
		
		return profile;
		
	}
	
	public static boolean savePlayerProfile(Player player) {
		PirateFinds plugin = PirateFinds.getThisPlugin();
		
		UUID playerUUID = player.getUniqueId();
		
		File file = new File("plugins/" + plugin.getName().replace(" ", "_") + "/profiles", playerUUID.toString() + ".json");
		FileWriter writer;
		try {
			file.createNewFile();
			writer = new FileWriter(file, false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			writer.write(getPlayerProfile(player).serialize());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		

		
		
		return true;
	}
	
	public static Profile getPlayerProfile(Player player) {
		
		Profile player_profile = LoadedProfiles.get(player.getUniqueId());
		
		if(player_profile != null) {
			return player_profile;
		}
		
		return loadPlayerProfile(player);
	}

}
