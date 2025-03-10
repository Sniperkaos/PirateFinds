package me.cworldstar.piratefinds.impl.papi;

import javax.annotation.Nonnull;

import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.cworldstar.piratefinds.impl.armorer.ArmorSets;
import me.cworldstar.piratefinds.impl.armorer.ArmorSets.Sets;

public class ArmorerExpansion extends PlaceholderExpansion {

	@Override
	public @Nonnull String getIdentifier() {
		return "armorer";
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
		switch(params) {
			case "armor_set":
				String set = ArmorSets.randomSet(player);
				ArmorSets.updateLastSet(player, set);
				return set;
			case "armor_piece":
				String piece = ArmorSets.randomPiece(player);
				ArmorSets.updateLastPiece(player, piece);
				return piece;
			case "armor_set_last":
				return ArmorSets.getLastSet(player);
			case "armor_piece_last":
				return ArmorSets.getLastPiece(player);
			case "armor_set_name":
				String armor_set_id = ArmorSets.getLastSet(player);
				Sets setNameInstance = Sets.getSetFromName(armor_set_id);
				return setNameInstance.getSetDisplayName();
			default:
				return "null";
		}
		
	}
	
}
