package me.cworldstar.piratefinds.impl.ae.items.items.masks;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem.PFItemType;

public abstract class AbstractMask extends AbstractPFItem {

	protected static PFItemType type = PFItemType.DRAG_AND_DROP;
	
	public AbstractMask(String mask_id) {
		super("MASK"+mask_id);
	}
	/**
	 * 
	 * @return The ItemStack of the mask to return.
	 * 
	 */
	abstract ItemStack makeMask();
	
	@Override
	public List<PFItemType> getTypes() {
		return Arrays.asList(new PFItemType[] {
				PFItemType.DRAG_AND_DROP,
		});
	}

	@Override
	public ItemStack build() {
		ItemStack citem = this.makeMask();
		return citem;
	}
	
	@Override
	public abstract ItemStack getPFItem();
	
	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return true;
	}

	@Override
	public PFItemType getType() {
		return type;
	}

}
