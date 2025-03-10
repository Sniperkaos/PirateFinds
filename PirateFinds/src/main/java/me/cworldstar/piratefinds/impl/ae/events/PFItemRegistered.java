package me.cworldstar.piratefinds.impl.ae.events;

import javax.annotation.Nonnull;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;

public class PFItemRegistered extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	private AbstractPFItem what;
	private String id;
	private boolean cancelled;
	
	/**
	 * 
	 * PFItemRegistered event.
	 * Is called when a {@link AbstractPFItem} is registered in the {@link PFItemClass} class.
	 * Can be cancelled to prevent the registration of an item.
	 * 
	 * @author cworldstar
	 * 
	 * @param item
	 * @param id
	 */
	
	public PFItemRegistered(AbstractPFItem item, String id) {
		super();
		this.what = item;
		this.id = id;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * 
	 * Returns the {@link AbstractPFItem} associated with this event.
	 * 
	 * @return {@link AbstractPFItem}
	 */
	public AbstractPFItem getItem() {
		return this.what;
	}
	
	/**
	 * 
	 * Returns the {@link String} id associated with this event.
	 * This may not match the {@link AbstractPFItem} getId() method.
	 * 
	 * @return {@link String}
	 */
	public String getId() {
		return this.id;
	}
	
	
	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
	
	public void cancel() {
		this.cancelled = true;
	}

	@Override
	public @Nonnull HandlerList getHandlers() {
		return getHandlerList();
	}

	public static @Nonnull HandlerList getHandlerList() {
		return handlers;
	}

}
