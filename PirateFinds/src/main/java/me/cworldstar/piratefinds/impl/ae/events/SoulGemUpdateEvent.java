package me.cworldstar.piratefinds.impl.ae.events;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
/**
 * 
 * This {@link Event} is called when a {@link AbstractPFItem} is updated
 * to reflect a new volume of souls. It cannot be canceled, only read.
 * 
 * @author cworldstar
 * 
 */
public class SoulGemUpdateEvent extends PlayerEvent { 
	
	private static final HandlerList handlers = new HandlerList();
	private int souls;
	private AbstractPFItem gem;
	
	public SoulGemUpdateEvent(Player who, AbstractPFItem gem, int souls) {
		super(who);
		
		this.gem = gem;
		this.souls = souls;
	}
	
	public int getSouls() {
		return this.souls;
	}
	
	public AbstractPFItem getGem() {
		return this.gem;
	}

	@Override
	public @Nonnull HandlerList getHandlers() {
		return getHandlerList();
	}

	public static @Nonnull HandlerList getHandlerList() {
		return handlers;
	}
}
