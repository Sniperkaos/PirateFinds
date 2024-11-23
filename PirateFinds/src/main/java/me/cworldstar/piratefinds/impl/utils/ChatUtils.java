package me.cworldstar.piratefinds.impl.utils;

import net.md_5.bungee.api.ChatColor;

public class ChatUtils {

	public static String createBroadcast(String string) {
		return ChatColor.translateAlternateColorCodes('&', "&7[ &c&lPirateFinds Arena&r &7]: " + string);
	}
	
	public static String apply(String string) {
		return ChatColor.translateAlternateColorCodes('&',string);
	}

}
