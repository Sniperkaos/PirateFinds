package me.cworldstar.piratefinds.impl.utils;

import javax.annotation.Nullable;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;

public class InventoryUtils {
	@Nullable
	public static Integer locateMutableStack(Inventory toLook, ItemStack toCompare) {
		for(int i=0; i<toLook.getSize(); i++) {
			ItemStack at = toLook.getItem(i);
			if(at == null) continue;
			if(toCompare.isSimilar(at)) {
				return i;
			}
		}
		return null;
	}

	public static Integer locateMutableStack(Inventory toLook, @NotNull AbstractPFItem item) {
		for(int i=0; i<toLook.getSize(); i++) {
			ItemStack at = toLook.getItem(i);
			if(at == null) continue;
			
			AbstractPFItem at_item = PFItemClass.getItem(at);
			if(PFItemClass.compare(at_item, PFItemClass.nullItem()) == true) continue;
			
			if(PFItemClass.compare(at_item, item)) {
				return i;
			}
		}
		return null;
	}
}
