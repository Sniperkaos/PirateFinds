package me.cworldstar.piratefinds;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.cworldstar.piratefinds.events.TickerTickEvent;

public class Ticker {
	
	private BukkitTask TickerTask;
	private ArrayList<Consumer<Integer>> handlers = new ArrayList<Consumer<Integer>>();
	
	
	public Ticker() {
		TickerTask = new BukkitRunnable() {
			@Override
			public void run() {
				Ticker.this.handlers.forEach((Consumer<Integer> handler) -> {
					handler.accept(this.getTaskId());

				});
				Bukkit.getPluginManager().callEvent(new TickerTickEvent());
			}
			
		}.runTaskTimer(PirateFinds.getThisPlugin(), 0, 20L);
	}
	
	public BukkitTask getTickerTask() {
		return this.TickerTask;
	}

	public void registerTask(Consumer<Integer> task) {
		this.handlers.add(task);
	}
	
	
}
