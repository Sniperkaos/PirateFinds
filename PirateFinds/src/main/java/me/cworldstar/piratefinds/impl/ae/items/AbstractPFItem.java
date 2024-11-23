package me.cworldstar.piratefinds.impl.ae.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractPFItem {
	
	public static enum PFItemType {
		DRAG_AND_DROP,
		RIGHT_CLICK,
		INVALID
	}
	
	private static ItemStack item;
	private PFItemType type;
	
	public abstract ItemStack build();
	public abstract boolean checkExpend(Player p, ItemStack on);
	public abstract void onItemUse(Player p, ItemStack on);

	public abstract PFItemType getType();
	
}
