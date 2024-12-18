package me.cworldstar.piratefinds.impl.ae.events;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.Inventory;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem.PFItemType;

public class PFItemUsed extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	private Player who;
	private AbstractPFItem what;
	private Inventory where;
	private PFItemType type;
	private boolean cancelled;
	private boolean expended;
	
	public PFItemUsed(Player who, Inventory where, AbstractPFItem what, boolean expended) {
		super(who);
		
		this.who = who;
		this.what = what;
		this.where = where;
		this.expended = expended;
		this.type = what.getType();
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}
	
	public void expend() {
		this.expended = true;
	}
	
	public void doNotExpend() {
		this.expended = false;
	}
	
	/**
	 * 
	 * Returns whether or not this PF item was
	 * expended.
	 * 
	 * @return {@link Boolean}
	 */
	public boolean expended() {
		return this.expended;
	}
	
	/**
	 * 
	 * Returns the {@link PFItemType} associated with
	 * this item.
	 * 
	 * @return {@link PFItemType}
	 */
	
	public PFItemType type() {
		return this.type;
	}
	
	/**
	 * 
	 * Returns the {@link Inventory} associated with this event.
	 * 
	 * @return {@link Inventory}
	 */
	@Nullable
	public Inventory where() {
		return this.where;
	}
	/**
	 * 
	 * Returns the {@link AbstractPFItem} associated with this event.
	 * 
	 * @return {@link AbstractPFItem}
	 */
	public AbstractPFItem what() {
		return this.what;
	}
	/**
	 * 
	 * Returns the {@link Player} associated with this event.
	 * 
	 * @return {@link Player}
	 */
	public Player who() {
		return this.who;
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
