package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;

/**
 * 
 * Extending {@link NoUseItem} means that the {@link AbstractPFItem} has no use.
 * You can also use {@link AnyNoUseItem}. The difference lies where
 * {@link AnyNoUseItem} implements event-cancelling interfaces whilst this one
 * does not. 
 * 
 * @author cworldstar
 *
 */
public abstract class NoUseItem extends AbstractPFItem {
	
	public NoUseItem(String id) {
		super(id);
	}
	
	@Override
	public abstract ItemStack getPFItem();
	
	@Override
	public abstract ItemStack build();
	
	@Override
	public PFItemType getType() {
		return PFItemType.NULL;
	}
	
	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return false;
	};
	
	
	
	
	
}
