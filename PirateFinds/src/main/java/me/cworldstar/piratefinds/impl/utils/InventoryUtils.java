package me.cworldstar.piratefinds.impl.utils;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;

public class InventoryUtils {
	/**
	 * 
	 * {@link InventoryUtils#locateMutableStack(Inventory, ItemStack)} takes an Inventory and an ItemStack.
	 * If it does not find an itemstack matching the given item, it will return -1. This method cannot be null.
	 * 
	 * @param {@link Inventory}
	 * @param {@link AbstractPFItem}
	 * @return {@link Integer}
	 */
	@Nonnull
	public static Integer locateMutableStack(Inventory toLook, @Nonnull final ItemStack toCompare) {
		for(int i=0; i<toLook.getSize(); i++) {
			ItemStack at = toLook.getItem(i);
			if(at == null) continue;
			if(toCompare.isSimilar(at)) {
				return i;
			}
		}
		return -1;
	}

	
	/**
	 * 
	 * {@link InventoryUtils#locateMutableStack(Inventory, AbstractPFItem)} takes an Inventory and an AbstractPFItem.
	 * If it does not find an itemstack matching the given item, it will return -1. This method cannot be null.
	 * 
	 * @param {@link Inventory}
	 * @param {@link AbstractPFItem}
	 * @return {@link Integer}
	 */
	@Nonnull
	public static Integer locateMutableStack(Inventory toLook, @Nonnull AbstractPFItem item) {
		for(int i=0; i<toLook.getSize(); i++) {
			ItemStack at = toLook.getItem(i);
			if(at == null) continue;
			
			AbstractPFItem at_item = PFItemClass.getItem(at);
			if(PFItemClass.compare(at_item, PFItemClass.nullItem()) == true) continue;
			
			if(PFItemClass.compare(at_item, item)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void addOrDropItem(Inventory i, ItemStack ...stacks) {
		Map<Integer, ItemStack> noAdd = i.addItem(stacks);
		if(noAdd.size() > 0) {
			for(Entry<Integer, ItemStack> items : noAdd.entrySet()) {
				InventoryHolder holder = i.getHolder();
				if(holder instanceof LivingEntity) {
					LivingEntity entity = ((LivingEntity) holder);
					World w = entity.getWorld();
					w.dropItem(entity.getLocation(), items.getValue());
				}
			}
		}
	}
	
}


