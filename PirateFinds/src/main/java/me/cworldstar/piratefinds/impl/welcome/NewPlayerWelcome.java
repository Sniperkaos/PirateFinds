package me.cworldstar.piratefinds.impl.welcome;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cworldstar.piratefinds.PirateFinds;
import net.md_5.bungee.api.ChatColor;

public class NewPlayerWelcome implements Listener {
	public NewPlayerWelcome() {
		PirateFinds.registerListener(this);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(e.getPlayer().getLastPlayed() == 0) {
			
			List<String> message = PirateFinds.getThisPlugin().getConfig().getStringList("first-join.first-join-message");
			List<String> commands = PirateFinds.getThisPlugin().getConfig().getStringList("first-join.first-join-commands");
			
			commands = PlaceholderAPI.setPlaceholders(e.getPlayer(), commands);
			commands.replaceAll(command -> ChatColor.translateAlternateColorCodes('&', command));
			
			message = PlaceholderAPI.setPlaceholders(e.getPlayer(), message);
			message.replaceAll(loreline -> ChatColor.translateAlternateColorCodes('&', loreline));
			
			for(Player p: PirateFinds.getServerStatic().getOnlinePlayers().toArray(new Player[0])) {
				for(String lore : message.toArray(new String[0])) {
					p.sendMessage(lore);
				}
			}
			
			for(String s : commands.toArray(new String[0])) {
				PirateFinds.getServerStatic().dispatchCommand(Bukkit.getServer().getConsoleSender(),  s);
			}
		}
	}
}
