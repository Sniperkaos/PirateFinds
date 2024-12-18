package me.cworldstar.piratefinds.events;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TickerTickEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	
	@Override
	public @Nonnull HandlerList getHandlers() {
		return getHandlerList();
	}

	public static @Nonnull HandlerList getHandlerList() {
		return handlers;
	}

}
