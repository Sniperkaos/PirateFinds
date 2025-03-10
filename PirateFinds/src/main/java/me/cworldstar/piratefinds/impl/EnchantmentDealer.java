package me.cworldstar.piratefinds.impl;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;

public class EnchantmentDealer {

	private static Map<Player, Deals> deals = new HashMap<Player, Deals>();
	
	public void addDealsForPlayer(Player player) {
		// 1 AE, 1 iron, 3 t1, 2 t2
		
		Deals deal = new Deals(player);
		deal.addItem(PFItemClass.getItem("ae_shard").build(), 100);
		deal.addItem(PFItemClass.getItem("t1_shard").build(), 15);
		deal.addItem(PFItemClass.getItem("t1_shard").build(), 15);
		deal.addItem(PFItemClass.getItem("t1_shard").build(), 15);
		deal.addItem(PFItemClass.getItem("t2_shard").build(), 25);
		deal.addItem(PFItemClass.getItem("t2_shard").build(), 25);
		deal.addItem(PFItemClass.getItem("t3_shard").build(), 40);

		deals.putIfAbsent(player, deal);
	}
	
	public void refreshDealsForPlayer(Player player) {
		// remove the old deal impl
		deals.remove(player);
		
		//create a new one
		Deals deal = new Deals(player);
		deal.addItem(PFItemClass.getItem("ae_shard").build(), 100);
		deal.addItem(PFItemClass.getItem("t1_shard").build(), 15);
		deal.addItem(PFItemClass.getItem("t1_shard").build(), 15);
		deal.addItem(PFItemClass.getItem("t1_shard").build(), 15);
		deal.addItem(PFItemClass.getItem("t2_shard").build(), 25);
		deal.addItem(PFItemClass.getItem("t2_shard").build(), 25);
		deal.addItem(PFItemClass.getItem("t3_shard").build(), 40);

		// place it
		deals.put(player, deal);
	}
	
	public static Deals getDeals(Player owner) {
		return deals.get(owner);
	}

	public void time(Player player) {
		TimeImpl.at(12, 0, 0, new Runnable() {
			@Override
			public void run() {
				refreshDealsForPlayer(player);
			}
		});
	}
}
