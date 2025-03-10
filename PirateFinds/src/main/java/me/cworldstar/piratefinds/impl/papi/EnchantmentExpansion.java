package me.cworldstar.piratefinds.impl.papi;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.OfflinePlayer;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class EnchantmentExpansion extends PlaceholderExpansion {

	private Random random = new Random();
	
	@Override
	public @Nonnull String getIdentifier() {
		// TODO Auto-generated method stub
		return "enchantment_expansion";
	}

	@Override
	public @Nonnull String getAuthor() {
		// TODO Auto-generated method stub
		return "cworldstar";
	}

	@Override
	public @Nonnull String getVersion() {
		// TODO Auto-generated method stub
		return "1.0.0";
	}

	@Override
	public boolean persist() {
		return true;
	}
	
	@Override
	public String onRequest(OfflinePlayer player, @Nonnull String params) {
		switch(params) {
			case "random_enchantment":
				List<Enchantment> all_enchantments = Registry.ENCHANTMENT.stream().toList();
				return all_enchantments.get(random.nextInt(all_enchantments.size())).getKey().getKey();
			default:
				return "null";
		}
		
	}
	
}
