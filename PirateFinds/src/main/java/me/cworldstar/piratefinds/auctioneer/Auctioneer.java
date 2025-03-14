package me.cworldstar.piratefinds.auctioneer;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.Deals;

public class Auctioneer {
	
	private static Map<Player, Deals> deals = new HashMap<Player, Deals>();
	
	public static void onPlayerJoin(Player p) {
		if(!deals.containsKey(p)) {
			Deals deal = new Deals(p);
			
			
			/*
			 * players:
			 *   sniperkaos_uuid:
			 *     '1':
			 *        item: itemstack
			 *        price: 100000
			 * 
			 */
			
			ConfigurationSection has_prior_deals_information = PirateFinds.getAuctioneerConfig().getConfigurationSection("players").getConfigurationSection(p.getUniqueId().toString());
			if(has_prior_deals_information != null) {
				for(String key : has_prior_deals_information.getKeys(false)) {
					ConfigurationSection the_deal = has_prior_deals_information.getConfigurationSection(key);
					ItemStack item = (ItemStack) the_deal.getItemStack("item");
					int price = (Integer) the_deal.getInt("price");
					deal.addItem(item, price);
				}
			} else {
				ConfigurationSection section = PirateFinds.getAuctioneerConfig().getConfigurationSection("players").createSection(p.getUniqueId().toString());	
				Map<ItemStack, Integer> to_add = createDeals(7);
				to_add.forEach((ItemStack item, Integer price) -> {
					section.createSection(Integer.toString(section.getKeys(false).size()));
					section.set("item", item);
					section.set("price", price);
					deal.addItem(item, price);
				});
			}
			
			deals.put(p, deal);
		}
	}
	
	private static Map<ItemStack, Integer> createDeals(int i) {
		return null;
	}

	public static void onPlayerLeave(Player p) {

	}
	
	public static Deals getDeals(Player p) {
		return null;
		
	}
}
