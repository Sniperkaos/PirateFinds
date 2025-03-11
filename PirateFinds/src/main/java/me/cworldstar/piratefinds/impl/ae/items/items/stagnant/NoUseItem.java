package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;

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
