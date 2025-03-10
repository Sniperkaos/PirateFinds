package me.cworldstar.piratefinds.impl.player;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.HealthImpl;
import me.cworldstar.piratefinds.impl.profile.PlayerProfile;
import me.cworldstar.piratefinds.impl.profile.Profile;

public class JoinListener implements Listener {
	public JoinListener() {
		PirateFinds plugin = PirateFinds.getThisPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		Optional<Profile> player_profile = PlayerProfile.getPlayerProfile(player);
		if(player_profile.isEmpty()) {
			PirateFinds.logger().warning("Player does not have a player profile. This should be impossible.");
			return;
		}
		Profile profile = player_profile.get();
		HealthImpl.startForEntity(player, profile.getStat("max_health"));
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		PlayerProfile.savePlayerProfile(player);
	}
}
