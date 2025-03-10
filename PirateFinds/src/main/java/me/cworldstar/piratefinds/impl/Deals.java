package me.cworldstar.piratefinds.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Deals {

	public Map<ItemStack, Integer> deals = new HashMap<ItemStack, Integer>();
	private Player owner;
	
	public Deals(Player player) {
		this.owner = player;
	}
	
	public Player getOwner() {
		return this.owner;
	}

	public Set<Entry<ItemStack, Integer>> toEntrySet() {
		return deals.entrySet();
	}

	public void addItem(ItemStack itemStack, int price) {
		deals.put(itemStack, price);
	}

	public void close(Entry<ItemStack, Integer> deal) {
		deals.remove(deal.getKey());
	}

}
