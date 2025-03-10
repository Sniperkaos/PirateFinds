package me.cworldstar.piratefinds.impl.utils;

import java.util.List;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cworldstar.piratefinds.PirateFinds;
import net.md_5.bungee.api.ChatColor;

public class ChatUtils {

	public static String createBroadcast(String string) {
		return ChatColor.translateAlternateColorCodes('&', "&7[ &c&lPirateFinds Arena&r &7]: " + string);
	}
	
	public static String apply(String string) {
		return ChatColor.translateAlternateColorCodes('&',string);
	}
	
	public static List<String> apply(List<String> string) {
		string.replaceAll(s->apply(s));
		return string;
	}
	
	
	public static void broadcast(String string) {
		PirateFinds.getServerStatic().getOnlinePlayers().forEach((Player p) -> {
			p.sendMessage(apply(PlaceholderAPI.setPlaceholders(p, string)));
		});
	}

}
