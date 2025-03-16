package me.cworldstar.piratefinds.auctioneer;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.impl.Deals;

/**
 * This class is the same a
 * @author rainb
 *
 */
public class AuctioneerDeal extends Deals {

	private Map<String, Object> data;
	
	public AuctioneerDeal(Player player) {
		super(player);
		
		data = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getData() {
		return data;
	}
	
	public void setData(String id, Object any) {
		data.putIfAbsent(id, any);
	}

	@Override
	public void addItem(ItemStack itemStack, int price) {
		deals.put(itemStack, price);
	}
	
}
