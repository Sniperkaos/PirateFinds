package me.cworldstar.piratefinds.impl.ae.items.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class NullItem extends AbstractPFItem {

	private static ItemStack nullItem = new ItemStack(Material.BARRIER);
	static {
		ItemMeta meta = nullItem.getItemMeta();
		meta.setItemName(ChatUtils.apply("&5&lNULL ITEM"));
		nullItem.setItemMeta(meta);
	}
	
	public NullItem() {
		super("NULL");
	}

	@Override
	public ItemStack build() {
		return nullItem.clone();
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return false;
	}

	@Override
	public PFItemType getType() {
		return PFItemType.INVALID;
	}

}
