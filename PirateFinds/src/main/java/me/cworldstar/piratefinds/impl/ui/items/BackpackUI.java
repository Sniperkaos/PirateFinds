package me.cworldstar.piratefinds.impl.ui.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.items.Backpack;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;

public class BackpackUI extends BaseUIObject {
	
	private List<ItemStack> items;
	private Backpack backpack;
	private ItemStack item;
	
	public BackpackUI(Player player, ItemStack[] items, Backpack backpack, ItemStack item, InventorySize size) {
		super(player, size);
		this.items = Arrays.asList(items);
		this.backpack = backpack;
		this.item = item;
		this.tryDecorate();
	}

	@Override
	public void decorate(Inventory inv) {
		
	}
	
	public void tryDecorate() {
		
		
		this.addMenuCloseHandler(new MenuHandler<InventoryCloseEvent>(
				(InventoryCloseEvent e) -> {
					Backpack.updateInventory(items, item);
					PirateFinds.logDebug(this.items.toString());
					PirateFinds.logDebug("is this closing");
				}
			)
		);
		
		this.addGlobalMenuClickHandler(new MenuHandler<InventoryClickEvent>(
				(InventoryClickEvent e) -> {
					
					PirateFinds.logDebug("clicked");
					
					boolean isMyInventory = e.getClickedInventory().equals(this.getInventory());
					
					PirateFinds.logDebug(Boolean.toString(isMyInventory));
					
					// normal clicking on backpack UI without an empty slot, will remove it
					if(
							e.getCurrentItem() != null && 
							!e.isShiftClick() &&
							isMyInventory
							
					) {
						PirateFinds.logDebug("removing slot");
						ItemStack removed = e.getCurrentItem();
						this.items.remove(removed);
					} 
					// normal clicking on backpack UI with an item in cursor, will add it
					if(
							e.getCursor() != null &&
							!e.isShiftClick() &&
							isMyInventory
					) {
						PirateFinds.logDebug("adding slot");
						ItemStack added = e.getCursor();
						this.items.set(e.getSlot(), added);
					}
					
					// shift clicking on backpack UI, removes it
					if(
						e.isShiftClick() &&
						isMyInventory
					) {
						ItemStack removed = e.getCurrentItem();
						this.items.remove(removed);
						PirateFinds.logDebug("removing slot");
					}
					
					// shift clicking on your inventory, will add it
					if(
						e.isShiftClick() &&
						!isMyInventory
					) {
						ItemStack added = e.getCurrentItem();
						this.items.set(e.getSlot(), added);
						PirateFinds.logDebug("adding slot");
					}

				}
			)
		);
		
		int current_pos = 0;
		for(ItemStack i : items) {
			if (i==null) continue;
			this.getInventory().setItem(current_pos, i);
			current_pos+=1;
		}
	}

}

