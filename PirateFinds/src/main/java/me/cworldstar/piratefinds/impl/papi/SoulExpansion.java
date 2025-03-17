package me.cworldstar.piratefinds.impl.papi;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.cworldstar.piratefinds.impl.ae.souls.SoulAPI;

public class SoulExpansion extends PlaceholderExpansion {

	@Override
	public @Nonnull String getIdentifier() {
		return "SoulExpansion";
	}

	@Override
	public @Nonnull String getAuthor() {
		return "cworldstar";
	}

	@Override
	public @Nonnull String getVersion() {
		return "1.0.0";
	}
	
	@Override
	public String onRequest(OfflinePlayer player, @Nonnull String params) {
		if(params.contains("_souls")) {
			return Integer.toString(SoulAPI.getSouls(Bukkit.getPlayer(params.replace("_souls", ""))).querySouls());
		}
		return Integer.toString(0);
	}
	
}
