package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class ConfigStagnantItem {

	public static void fromConfig(ConfigurationSection section) {
		
		/*
		 * 
		 * item_name:
		 *   material: 
		 *   skull_id: 
		 *   name:
		 *   lore:
		 *   -
		 *   -
		 *   glowing: false
		 *   data: 0
		 */
		
		String id = section.getName().toUpperCase();
		ItemStack item;
		Material mat = Material.valueOf(section.getString("material"));
		if(mat.equals(Material.PLAYER_HEAD)) {
			item = SkullCreator.itemFromBase64(section.getString("skull_id"));
		} else {
			item = new ItemStack(mat);
		}
		
		String name = ChatUtils.apply(section.getString("name"));
		List<String> lore = ChatUtils.apply(section.getStringList("lore"));
		boolean glowing = section.getBoolean("glowing");
		int cm_data = section.getInt("data");
		
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(name);
		meta.setDisplayName(name);
		
		meta.setLore(lore);
		meta.setEnchantmentGlintOverride(glowing);
		meta.setCustomModelData(cm_data);
		
		item.setItemMeta(meta);
		
		PFItemClass.registerAnyItem(new AnyNoUseItem(id, item));
		
	}
	
}
