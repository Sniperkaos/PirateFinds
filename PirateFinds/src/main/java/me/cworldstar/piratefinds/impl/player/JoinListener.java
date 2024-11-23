package me.cworldstar.piratefinds.impl.player;

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
		Profile player_profile = PlayerProfile.getPlayerProfile(player);
		HealthImpl.startForEntity(player, player_profile.getStat("max_health"));
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		PlayerProfile.savePlayerProfile(player);
	}
}
