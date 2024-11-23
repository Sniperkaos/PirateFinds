package me.cworldstar.piratefinds.impl;

import org.bukkit.event.Listener;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.health.HealthListener;
import me.cworldstar.piratefinds.impl.welcome.NewPlayerWelcome;

public class ListenerClass {
	
	private void register(Listener listener) {
		PirateFinds finds = PirateFinds.getThisPlugin();
		finds.getServer().getPluginManager().registerEvents(listener, finds);
	}
	
	
	public ListenerClass() {
		
		
		HealthListener HL = new HealthListener();
		register(HL);
		
		new NewPlayerWelcome();
		
		
	}
}
