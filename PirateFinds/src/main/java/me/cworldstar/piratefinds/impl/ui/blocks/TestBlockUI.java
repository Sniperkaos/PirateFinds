package me.cworldstar.piratefinds.impl.ui.blocks;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import me.cworldstar.piratefinds.impl.blocks.PFBlockData;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;

public class TestBlockUI extends BaseUIObject {

	public PFBlockData pfBlockData;
	
	public TestBlockUI(Player player, PFBlockData data) {
		super(player, InventorySize.SMALL);
		pfBlockData = data;
		this.tryDecorate();
	}

	@Override
	protected void decorate(Inventory inv) {
	}
	
	public void tryDecorate() {
		this.getInventory().setContents(pfBlockData.getInventory("inventory").getContents());
		
		for(int i=0; i<InventorySize.LARGE.toInt(); i++) {
			this.addInsertableSlot(i);
			this.addInsertHandler(i, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
				pfBlockData.getInventory("inventory").setContents(this.getInventory().getContents());
			}));
		}
		
		this.addMenuCloseHandler(new MenuHandler<InventoryCloseEvent>((InventoryCloseEvent e) -> {
			pfBlockData.getInventory("inventory").setContents(this.getInventory().getContents());
		}));
	}

}
