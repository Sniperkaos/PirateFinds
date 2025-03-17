package me.cworldstar.piratefinds.impl.ae.souls;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class SoulAPI {

	private static Map<Player, Souls> souls = new HashMap<Player, Souls>();
	
	public static Souls getSouls(Player query) {
		return souls.get(query);
	}
	
	public static void createSouls(Player toInit) {
		souls.putIfAbsent(toInit, Souls.create(toInit));
	}

}
