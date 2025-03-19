package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LavaGolemCore extends AnyNoUseItem {

	private static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		return item;
	}
	
	public LavaGolemCore() {
		

		
		super("LavaGolemCore", getItem());
	}

}
