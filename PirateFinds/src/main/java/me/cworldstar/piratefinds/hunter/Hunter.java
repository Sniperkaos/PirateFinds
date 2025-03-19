package me.cworldstar.piratefinds.hunter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.impl.entities.AbstractPFEntity;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;

public class Hunter {

	private static Map<Player, List<HunterDeal>> playerDeals = new HashMap<Player, List<HunterDeal>>();
	
	public static void display(BaseUIObject object) {
		
	}
	
	
	
	
	
	/**
	 * 
	 * 
	 * Creates a hunter deal and adds it to the player's deals
	 * 
	 * @param p
	 * @param where
	 * @param toSpawn
	 * @param coordinateBounds
	 * @return
	 */
	public static HunterDeal createHunterDeal(Player p, World where, AbstractPFEntity toSpawn, int coordinateBounds) {
		
		HunterDeal deal = new HunterDeal(p, where, toSpawn, coordinateBounds);
		
		List<HunterDeal> deals = playerDeals.get(p);
		deals.add(deal);
		
		return deal;
	}
	
	public static Map<Player, List<HunterDeal>> getDeals() {
		return playerDeals;
	}

	public static Location generateCoordinate(World w, int bound) {
		
		double x = Math.floor(Math.random() * bound + 1);
		double z = Math.floor(Math.random() * bound + 1);
		
		return new Location(w, x, w.getHighestBlockYAt((int) x, (int) z), z);
	}


	public static void onPlayerJoin(Player player) {
		playerDeals.putIfAbsent(player, new ArrayList<HunterDeal>());
	}
	
}
