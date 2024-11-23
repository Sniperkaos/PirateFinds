package me.cworldstar.piratefinds.impl.ae.items;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.impl.ae.items.items.*;

public class PFItemClass {
	
	private static HashMap<String, AbstractPFItem> items = new HashMap<String, AbstractPFItem>();
	
	public static HashMap<String, AbstractPFItem> getItems() {
		return items;
	}
	
	public static AbstractPFItem getItem(String item) {
		return items.get(item);
	}
	
	@Nullable
	public static AbstractPFItem getItem(@Nonnull ItemStack itemOnCursor) {
		for(Entry<String, AbstractPFItem> sets : items.entrySet()) {
			AbstractPFItem item = sets.getValue();
			
			if(item.build().isSimilar(itemOnCursor)) {
				return item;
			}
		}
		return null;
	}
	
	public PFItemClass() {
		items.put("LockScroll", new LockScroll());
		items.put("UnlockScroll", new UnlockScroll());
		items.put("UnsealScroll", new UnsealScroll());
		items.put("TitanLootbox", new TitanLootBox());
	}


}
