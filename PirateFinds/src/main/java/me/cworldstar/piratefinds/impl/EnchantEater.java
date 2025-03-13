package me.cworldstar.piratefinds.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.ae.listeners.Locked;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ExperienceUtils;
import net.advancedplugins.ae.api.AEAPI;

public class EnchantEater {
	public static void eatEnchantments(Player p, ItemStack i, int price) {
		ItemMeta meta = i.getItemMeta();
		
		if(meta == null) {
			p.sendMessage(ChatUtils.apply("&7[ &d&lEnchant Eater &7]: I don't like this item."));
			return;
		}
		
		if(Locked.isItemLocked(i)) {
			p.sendMessage(ChatUtils.apply("&7[ &d&lEnchant Eater &7]: I don't want locked items."));
			return;
		}
		
		ExperienceUtils.changePlayerExp(p, -ExperienceUtils.getExpAtLevel(price));

		Map<Enchantment, Integer> enchants = meta.getEnchants();
		
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		for(Entry<Enchantment, Integer> set : enchants.entrySet()) {
			ItemStack enchantment_book = new ItemStack(Material.ENCHANTED_BOOK);
			EnchantmentStorageMeta e_meta = (EnchantmentStorageMeta) enchantment_book.getItemMeta();
			e_meta.addStoredEnchant(set.getKey(), set.getValue(), true);
			enchantment_book.setItemMeta(e_meta);
			items.add(enchantment_book);
		}
		
		for(Entry<String, Integer> aset : AEAPI.getEnchantmentsOnItem(i).entrySet()) {
			Random random = new Random();
			ItemStack enchantment_book = AEAPI.createEnchantmentBook(aset.getKey(), aset.getValue(), random.nextInt(50, 100), random.nextInt(1, 49), p);
			items.add(enchantment_book);
		}
		
		ArrayList<ItemStack> contents = new ArrayList<ItemStack>(Arrays.asList(p.getInventory().getStorageContents()));
		contents.removeIf(item -> item==null);
		
		if((contents.size() + items.size()) > p.getInventory().getSize()) {
			p.sendMessage(ChatUtils.apply("&7[ &d&lEnchant Eater &7]: You'll need to clear your inventory before I do this."));
			p.playSound(p, Sound.ENTITY_VILLAGER_NO, 0.8F, 0.4F);
			return;
		}
		
		for(Entry<String, Integer> aset : AEAPI.getEnchantmentsOnItem(i).entrySet()) {
			AEAPI.removeEnchantment(i, aset.getKey());
		}
		
		i.removeEnchantments();
		
		p.sendMessage(ChatUtils.apply("&7[ &d&lEnchant Eater &7]: A pleasure."));
		p.getInventory().addItem(items.toArray(new ItemStack[0]));
		p.playSound(p, Sound.ENTITY_VILLAGER_TRADE, 0.8F, 0.4F);

	}
}
