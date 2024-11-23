package me.cworldstar.piratefinds.impl.events;

import javax.annotation.Nonnull;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This {@link PlayerEvent} is called when the {@link Ticker} is called
 * from the main plugin. DO NOT LEAVE THESE REGISTERED UNLESS THE UI IS STATIC!
 * YOU WILL CAUSE A MEMORY LEAK!
 * 
 * @author cworldstar
 */

public class UIUpdateEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	
	public UIUpdateEvent() {
		super();
	}

	@Override
	public @Nonnull HandlerList getHandlers() {
		return getHandlerList();
	}

	public static @Nonnull HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;		
	}

}
