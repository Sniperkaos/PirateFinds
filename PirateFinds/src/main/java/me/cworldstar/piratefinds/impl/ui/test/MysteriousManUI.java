package me.cworldstar.piratefinds.impl.ui.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.impl.ui.BaseUIObject;

public class MysteriousManUI extends BaseUIObject {

	private static ItemStack ui_barrier = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);

	
	public MysteriousManUI(Player player) {
		super(player, InventorySize.LARGE);
	}
	
	private static ArrayList<Integer> barrier_slots = new ArrayList<Integer>(); 
	
	static {
		int[] ints = new int[] {0,1,2,3,4,5,6,7,8,9,10,11,15,16,17,18,19,20,21,23,24,25,26};
		List<Integer> slots = Arrays.stream(ints).boxed().toList();
		barrier_slots.addAll(slots);
	}

	@Override
	protected void decorate(Inventory i) {
		barrier_slots.forEach((Integer slot) -> {
			this.addUnclickableItem(slot, ui_barrier);
		});
	}

}
