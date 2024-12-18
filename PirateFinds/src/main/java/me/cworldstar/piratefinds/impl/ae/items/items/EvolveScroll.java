package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.impl.utils.ColorUtils;

//todo: too lazy make this l8r
// how it works: You can "evolve" an item, changing it's name and attributes.

public class EvolveScroll extends AbstractPFItem {

	public EvolveScroll(String id) {
		super(id);
	}

	private static ItemStack item = new ItemStack(Material.PAPER);
	private static PFItemType type = PFItemType.DRAG_AND_DROP;
	public ItemStack getPFItem() {
		return item;
	}

	@Override
	public PFItemType getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	static {
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ColorUtils.format("&7&l(&c&l!&7&l) &a&lEv&6&lol&4&lve &f&lScroll"));
		meta.setLore(List.of(new String[] {
				ColorUtils.format("&c&oDragging and dropping this onto an item"),
				ColorUtils.format("&c&owill evolve the item."),
		}));
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
	}
	
	public final String pf_item_id = "EVOLVE_SCROLL";
	@Override
	public ItemStack build() {
		ItemStack citem = item.clone();
		this.make(citem);
		return citem;
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {

	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {

		p.sendMessage(ChatUtils.createBroadcast("&7This is not implemented yet."));
		
		return false;
	}
	
}
