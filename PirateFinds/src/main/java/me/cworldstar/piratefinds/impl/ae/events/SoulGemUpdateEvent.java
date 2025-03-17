package me.cworldstar.piratefinds.impl.ae.events;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

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
	private ItemStack stack;
	
	public SoulGemUpdateEvent(Player who, AbstractPFItem gem, ItemStack stack, int souls) {
		super(who);
		
		this.stack = stack;
		this.gem = gem;
		this.souls = souls;
	}
	
	public ItemStack getStack() {
		return stack;
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
