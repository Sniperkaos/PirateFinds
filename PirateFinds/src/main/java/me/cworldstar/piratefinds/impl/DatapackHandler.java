package me.cworldstar.piratefinds.impl;

import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.packs.DataPack;
import org.bukkit.packs.DataPackManager;

import me.cworldstar.piratefinds.PirateFinds;

public class DatapackHandler {
	
	private static DataPackManager packManager = PirateFinds.getServerStatic().getDataPackManager();
	/**
	 * 
	 * This method returns a {@link List} of {@link DataPack}.
	 * 
	 * @return {@link List}
	 */
	public static List<DataPack> getPacks() {
		return packManager.getDataPacks().stream().toList();
	}
	
	
	/**
	 * This method returns a {@link List} of {@link NamespacedKey},
	 * streamed from the loaded {@link DataPack}.
	 * 
	 * @return {@link List}
	 */
	public static List<NamespacedKey> getKeys() {
		return packManager.getDataPacks().stream().map(DataPack::getKey).toList();
	}
	
	/**
	 * 
	 * This is how the method {@link EnchantmentTotem#enchant} interfaces
	 * with datapack enchantments.
	 * 
	 * @param {@link String} ench_name
	 * @return {@link Enchantment}
	 */
	public static Enchantment getEnchantment(String ench_name) {
		List<DataPack> loadedPacks = getPacks();
		Enchantment found = null;
		
		for(DataPack pack : loadedPacks.toArray(new DataPack[0])) {
			NamespacedKey key = pack.getKey();
			NamespacedKey enchantKey = new NamespacedKey(key.getNamespace(), ench_name);
			found = Registry.ENCHANTMENT.get(enchantKey);
		}
		
		return found;
	}
}
