package me.cworldstar.piratefinds.impl.profile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;


public class PlayerProfile {
	
	private static HashMap<UUID, Profile> LoadedProfiles = new HashMap<UUID, Profile>();
	
	public static void memorySaveProfile(@Nonnull Profile profile) {
		LoadedProfiles.put(profile.getOwner().getUniqueId(), profile);
	}
	
	public static HashMap<UUID, Profile> getLoadedProfiles() {
		return LoadedProfiles;
	}
	
	@Nullable
	public static Profile loadPlayerProfile(@Nonnull Player player) {
		
		PirateFinds plugin = PirateFinds.getThisPlugin();
		UUID playerUUID = player.getUniqueId();
		
		File folder = plugin.getDataFolder();
		File pfolder = new File(folder + "/profile");
		
		if(!pfolder.exists()) {
			pfolder.mkdirs();
		}
		
		File file = new File(pfolder, playerUUID.toString() + ".yml");
		
		if(file.exists()) {
			YamlConfiguration profileConfig = YamlConfiguration.loadConfiguration(file);
			Profile profile = Profile.fromConfiguration(profileConfig);
			profile.setOwner(player);
			return profile;
		} else {
			Profile profile = Profile.NULL_PROFILE.clone();
			profile.setOwner(player);
			return profile;
		}
	}
	
	@Nonnull
	public static boolean savePlayerProfile(@Nonnull Player player) {
		PirateFinds plugin = PirateFinds.getThisPlugin();
		UUID playerUUID = player.getUniqueId();
		
		Profile profile = LoadedProfiles.get(playerUUID);
		profile.saveToConfig();
		
		File folder = plugin.getDataFolder();
		File pfolder = new File(folder + "/profile");
		
		if(!pfolder.exists()) {
			pfolder.mkdirs();
		}
		
		File file = new File(pfolder, playerUUID.toString() + ".yml");
		
		try {
			profile.getConfig().save(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		LoadedProfiles.remove(playerUUID);
		
		return true;
	}
	
	public static void saveAll() {
		for(Entry<UUID, Profile> profileEntry : LoadedProfiles.entrySet()) {
			boolean success = savePlayerProfile(profileEntry.getValue().getOwner());
			if(!success) {
				PirateFinds.logger().warning("Player " + profileEntry.getValue().getOwner().getName() + "'s profile has encountered an error in saving.");
			}
		}
	}
	
	@Nonnull
	public static Optional<Profile> getPlayerProfile(@Nonnull Player player) {
		
		Profile player_profile = LoadedProfiles.get(player.getUniqueId());
		if(player_profile == null) {
			player_profile = Profile.NULL_PROFILE.clone();
			player_profile.setOwner(player);
		}
		
		return Optional.of(player_profile);

	}

}
