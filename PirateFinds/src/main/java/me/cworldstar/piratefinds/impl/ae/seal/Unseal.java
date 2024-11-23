package me.cworldstar.piratefinds.impl.ae.seal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.AEExpansion;
import me.cworldstar.piratefinds.impl.ae.InternalEnchantment;
import net.md_5.bungee.api.ChatColor;

public class Unseal {
	
	public static final String UNSEALED_LORE_LINE = ChatColor.translateAlternateColorCodes('&', "&f&l*** SEALED ***");
	
	public Unseal() {
		throw new UnsupportedOperationException("Static class");
	}
	
	public static void unsealItem(Player p, ItemStack i) {
		PirateFinds.log("UnsealItem reached beginning");
		ItemMeta meta = i.getItemMeta();
		if(meta == null) return;
		PirateFinds.log("UnsealItem meta exists");
		List<String> lore = meta.getLore();
		int index = 0;
		PirateFinds.log("UnsealItem parsing lore");
		for(String loreLine : lore.toArray(new String[0])) {
			if(loreLine.contains(UNSEALED_LORE_LINE)) {
				PirateFinds.log("UnsealItem lore parsed");
				lore.remove(index);
				meta.setLore(lore);
				i.setItemMeta(meta);
				AEExpansion expansion = PirateFinds.getAEExpansion();
				PirateFinds.log("UnsealItem getting enchants");
				ArrayList<InternalEnchantment> enchants = expansion.getRandomEnchantments(p, i.getType(), new Random().nextInt(10), List.of(new String[] {
						"LEGENDARY",
						"ULTIMATE",
						"ELITE",
						"UNIQUE",
						"SIMPLE"
				}));
				
				PirateFinds.log("UnsealItem adding enchants");
				enchants.forEach((InternalEnchantment enchant) -> {
					expansion.enchantItem(i, enchant);
				});
				
				break;
			}
			index++;
		}
		
	}

	public static void sealItem(Player player, ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		lore.add(UNSEALED_LORE_LINE);
		meta.setLore(lore);
		i.setItemMeta(meta);
	}
}
