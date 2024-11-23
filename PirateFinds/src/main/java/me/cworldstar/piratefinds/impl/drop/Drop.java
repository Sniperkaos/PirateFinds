package me.cworldstar.piratefinds.impl.drop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.cworldstar.piratefinds.PirateFinds;

public class Drop implements Listener {
	
	private HashMap<UUID, DropImpl> drops = new HashMap<UUID, DropImpl>();
	private ArrayList<String> worlds = new ArrayList<String>(PirateFinds.getThisPlugin().getConfig().getStringList("drop-confirm.worlds"));
	
	
	public DropImpl getDropImpl(Player player) {
		return drops.get(player.getUniqueId());
	}
	
	public Drop() {
		PirateFinds finds = PirateFinds.getThisPlugin();
		finds.getServer().getPluginManager().registerEvents(this, finds);
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerItemDrop(PlayerDropItemEvent e) {
		
		if(!PirateFinds.getThisPlugin().getConfig().getBoolean("drop-confirm.enabled")) return;
		
		if(!worlds.contains(e.getPlayer().getWorld().getName())) return;
		DropImpl playerDropImpl = getDropImpl(e.getPlayer());
		e.setCancelled(playerDropImpl.drop());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		PirateFinds.log(e.getPlayer().getName() + " has joined! Creating a DropImpl.");
		drops.put(e.getPlayer().getUniqueId(), new DropImpl(e.getPlayer()));
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		PirateFinds.log(e.getPlayer().getName() + " has left! Removing DropImpl.");
		drops.remove(e.getPlayer().getUniqueId());
	}


}
