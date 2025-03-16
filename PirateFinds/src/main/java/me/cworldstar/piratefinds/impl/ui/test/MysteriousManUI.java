package me.cworldstar.piratefinds.impl.ui.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.auctioneer.Auctioneer;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;

/**
 * Implementation of the Auctioneer NPC.
 * It should have 7 slots for items, 3 rows.
 * It should be randomly generated from config.
 * 
 * @author cworldstar
 *
 */
public class MysteriousManUI extends BaseUIObject {

	private static ItemStack ui_barrier = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);

	static {
		ItemMeta meta = ui_barrier.getItemMeta();
		meta.setItemName(" ");
		ui_barrier.setItemMeta(meta);
	}
	
	public MysteriousManUI(Player player) {
		super(player, InventorySize.MEDIUM);
	}
	
	private static ArrayList<Integer> barrier_slots = new ArrayList<Integer>(); 
	
	static {
		int[] ints = new int[] {0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26};
		List<Integer> slots = Arrays.stream(ints).boxed().toList();
		barrier_slots.addAll(slots);
	}	

	@Override
	protected void decorate(Inventory i) {
		barrier_slots.forEach((Integer slot) -> {
			this.addUnclickableItem(slot, ui_barrier);
		});
		Auctioneer.display(this);
	}

}
