package me.cworldstar.piratefinds.impl.ae;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class SoulImpl {

	private static Map<Player, SoulImpl> Impl = new HashMap<Player, SoulImpl>();
	
	public static SoulImpl getImpl(Player p) {
		return Impl.get(p);
	}
	
	private Player p;
	private boolean soulsActive = false;
	
	
	public SoulImpl(Player p) {
		this.p = p;
		this.soulsActive = false;
		
		Impl.put(p, this);
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public boolean areSoulsActive() {
		return soulsActive;
	}
	
	public void setSoulActive(boolean b) {
		this.soulsActive = b;
	}
}
