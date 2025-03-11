package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.Crafting;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;

public interface MaterialSingularity {

	public default void registerBlock(ItemStack materialSingularity, String id) {
		
		String stringified_material = materialSingularity.getType().toString();
		boolean ingot_exists = stringified_material.contains("INGOT");
		Material material;
		if(ingot_exists) {
			material = Material.valueOf(stringified_material.replace("INGOT", "BLOCK"));
		} else {
			material = Material.valueOf(stringified_material + "_BLOCK");
		}
		
		
		if(material == null) {
			PirateFinds.log("WARNING: MaterialSingularity " + id + " was fed an invalid item. There is no block.");
			return;
		}
		
		ItemStack block = new ItemStack(material);
		ItemMeta blockMeta = block.getItemMeta();
		ItemMeta meta = materialSingularity.getItemMeta();
		String name = meta.getItemName();
		List<String> lore = meta.getLore();
		
		name = name.replace("ingot", "block");
		name = name.replace("Ingot", "Block");
		
		lore.replaceAll(line->line.replace("ingot", "block"));
		lore.replaceAll(line->line.replace("Ingot", "Block"));
				
		blockMeta.setItemName(name);
		blockMeta.setDisplayName(name);
		blockMeta.setEnchantmentGlintOverride(true);
		blockMeta.setLore(lore);
		
		block.setItemMeta(blockMeta);
		
		NoUseItem no_use_item = new AnyNoUseItem(id + "_BLOCK", block);
		
		PFItemClass.registerAnyItem(no_use_item);
		// create the crafting recipe
		Map<Character, Object> key = new HashMap<Character, Object>();
		key.put('a', Crafting.createExactChoice(materialSingularity));
		CraftingRecipe from_ingot = Crafting.createShapedRecipe(id+"_BLOCK_FROM_INGOT", block, new String[] {
				"aaa",
				"aaa",
				"aaa"
		},key);
		Crafting.add(from_ingot);
		
		ItemStack nine = materialSingularity.clone();
		nine.setAmount(9);
		
		CraftingRecipe to_ingot = Crafting.createShapelessRecipe(id+"_BLOCK_TO_INGOT", nine, new Object[] {
				Crafting.createExactChoice(block)
		});
		Crafting.add(to_ingot);
		
	}
}
