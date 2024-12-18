package me.cworldstar.piratefinds.impl.ae.seal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mozilla.javascript.regexp.NativeRegExp;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.AEExpansion;
import me.cworldstar.piratefinds.impl.ae.InternalEnchantment;
import me.cworldstar.piratefinds.impl.ae.items.items.UnsealScroll.UnsealedItemRarity;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.md_5.bungee.api.ChatColor;

public class Unseal {
	
	public static final String UNSEALED_LORE_LINE = ChatColor.translateAlternateColorCodes('&', "&f&l*** SEALED ***");
	
	public Unseal() {
		throw new UnsupportedOperationException("Static class");
	}
	
	public static void unsealItem(Player p, ItemStack i, UnsealedItemRarity unsealedItemRarity) {
		ItemMeta meta = i.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) {
			return;
		}
		int index = 0;
		for(String loreLine : lore.toArray(new String[0])) {
			if(loreLine.contains(UNSEALED_LORE_LINE)) {
				lore.remove(index);
				meta.setLore(lore);
				i.setItemMeta(meta);
				AEExpansion expansion = PirateFinds.getAEExpansion();
				ArrayList<InternalEnchantment> enchants = expansion.getRandomEnchantments(p, i.getType(), new Random().nextInt(10), unsealedItemRarity.getRarities());
				enchants.forEach((InternalEnchantment enchant) -> {
					expansion.enchantItem(i, enchant);
				});
				break;
			}
			index++;
		}
		
		meta = i.getItemMeta();
		String stuff;
		if(meta.hasDisplayName()) {
			stuff = "&f%player_name%'s " + unsealedItemRarity.getColor() + unsealedItemRarity.toString() + "&f " + meta.getDisplayName();
		}
		stuff = "&f%player_name%'s " + unsealedItemRarity.getColor() + unsealedItemRarity.toString() + "&f " + i.getType();
		String replace = unsealedItemRarity.toString().toLowerCase();
		char first_digit = replace.charAt(0);
		replace.replaceFirst(Character.toString(first_digit), Character.toString(first_digit).toUpperCase());
		stuff.replace(unsealedItemRarity.toString(), replace);
		
		meta.setItemName(ChatUtils.apply(PlaceholderAPI.setPlaceholders(p, stuff)));
		i.setItemMeta(meta);
	}

	public static void sealItem(Player player, ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		if(meta == null) return;
		List<String> lore = meta.getLore();
		if(lore == null) {
			lore = new ArrayList<String>();
		}
		lore.add(UNSEALED_LORE_LINE);
		meta.setLore(lore);
		i.setItemMeta(meta);
	}
}
