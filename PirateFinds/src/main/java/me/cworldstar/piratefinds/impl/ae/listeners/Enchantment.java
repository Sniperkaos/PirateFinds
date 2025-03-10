package me.cworldstar.piratefinds.impl.ae.listeners;

import org.bukkit.block.EnchantingTable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import me.cworldstar.piratefinds.PirateFinds;
//import me.cworldstar.piratefinds.impl.rpg.RPGFrontend;

public class Enchantment implements Listener {
	public Enchantment() {
		PirateFinds.registerListener(this);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEnchantment(EnchantItemEvent e) {
		if(e.getEnchantBlock() instanceof EnchantingTable) {
			//RPGFrontend.createRPGItem(e.getItem());
		}
	}
}
