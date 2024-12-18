package me.cworldstar.piratefinds.impl.ae.items.items.blocks;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;

public abstract class AbstractPFBlockItem extends AbstractPFItem {

	public AbstractPFBlockItem(String id) {
		super(id);
	}
	
	public abstract void blockPlaced(BlockPlaceEvent e);
	public abstract ItemStack getItem();
	
	@Override
	public ItemStack build() {
		return getItem();
	}

	public ItemStack getPFItem() {
		return getItem();
	}
	
	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return true;
	}

	@Override
	public PFItemType getType() {
		return PFItemType.BLOCK_PLACE;
	}
	
	@Override
	public List<PFItemType> getTypes() {
		return Arrays.asList(new PFItemType[] {
			PFItemType.BLOCK_PLACE	
		});
	}

	@Override
	public void onItemUse(Player player, ItemStack itemActual, PFItemType dropItem, BlockPlaceEvent e) {
		e.setCancelled(false);		
		blockPlaced(e);
		
	}
	
}
