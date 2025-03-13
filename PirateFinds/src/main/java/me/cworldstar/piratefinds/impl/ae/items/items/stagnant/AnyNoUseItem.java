package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;

/**
 * 
 * This {@link Class} is a more advanced implementation of {@link NoUseItem}.
 * It implements specific {@link Interface}s which cancels the usage of an item.
 * 
 * @author cworldstar
 *
 */
public class AnyNoUseItem extends NoUseItem implements NoPlacement, NoEat {

	private ItemStack item;
	
	public AnyNoUseItem(String id, ItemStack item) {
		
		super(id);
		
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, this.pf_item_id);
		item.setItemMeta(meta);
		
		this.item = item;
	}

	@Override
	public ItemStack getPFItem() {
		return item;
	}

	@Override
	public ItemStack build() {
		
		ItemStack clone = item.clone();
		
		ItemMeta meta = clone.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, this.pf_item_id);
		clone.setItemMeta(meta);
		
		return clone;
	}

}
