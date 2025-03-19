package me.cworldstar.piratefinds.impl.papi;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.OfflinePlayer;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.util.player.UserManager;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class MCMMOExpansion extends PlaceholderExpansion {

	@Override
	public @Nonnull String getIdentifier() {
		return "PFMCMMO";
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
	public boolean persist() {
		return true;
	}
	
	@Override
	public String onRequest(OfflinePlayer player, @Nonnull String params) {
		if(params.contains("mcmmo_overall_leaderboard_name_")) {
			String to_parse = params.replace("mcmmo_overall_leaderboard_name_", "");
			int place = Integer.valueOf(to_parse);
			return mcMMO.getDatabaseManager().readLeaderboard(null, 1, place).get(0).name;
		}
		
		if(params.contains("mcmmo_overall_leaderboard_value_")) {
			String to_parse = params.replace("mcmmo_overall_leaderboard_value_", "");
			int place = Integer.valueOf(to_parse);
			return String.valueOf(mcMMO.getDatabaseManager().readLeaderboard(null, 1, place).get(0).statVal);
		}
		return "An error occured.";
	}
	
}
