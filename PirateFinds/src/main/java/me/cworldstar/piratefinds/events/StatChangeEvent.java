package me.cworldstar.piratefinds.events;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StatChangeEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Player owner;
	private String stat;
	private long oldValue;
	private long newValue;
	
	public StatChangeEvent(@Nonnull Player p, @Nonnull String stat, @Nonnull long oldValue, @Nonnull long newValue) {
		this.owner = p;
		this.stat = stat;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public String getStat() {
		return this.stat;	
	}
	
	public long getOldValue() {
		return this.oldValue;
	}
	
	public long getNewValue() {
		return this.newValue;
	}
	
	public void setNewValue(long value) {
		this.newValue = value;
	}
	
	@Override
	public @Nonnull HandlerList getHandlers() {
		return getHandlerList();
	}

	public static @Nonnull HandlerList getHandlerList() {
		return handlers;
	}

}
