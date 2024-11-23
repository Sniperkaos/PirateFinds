package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.listeners.Locked;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.impl.utils.ColorUtils;

//todo: too lazy make this l8r

public class LockScroll extends AbstractPFItem {

	private static ItemStack item = new ItemStack(Material.PAPER);
	private static PFItemType type = PFItemType.DRAG_AND_DROP;
	

	@Override
	public PFItemType getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	static {
		
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ColorUtils.format("&7&l(&c&l!&7&l) &cLock Scroll"));
		meta.setLore(List.of(new String[] {
				ColorUtils.format("&c&oDragging and dropping this onto an item"),
				ColorUtils.format("&c&owill permanently lock the item."),
				ColorUtils.format("&c&oYou can only unlock it using an unlock scroll.")
		}));
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
	}

	

	
	@Override
	public ItemStack build() {
		return item.clone();
	}
	
	
	
	@Override
	public void onItemUse(Player p, ItemStack on) {
		ItemMeta meta = on.getItemMeta();
		List<String> lore = meta.getLore();
		lore.add(Locked.LOCKED_LORE_LINE);
		meta.setLore(lore);
		on.setItemMeta(meta);
	}



	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		ItemMeta meta = on.getItemMeta();
		if(meta == null) return false;
		List<String> lore = meta.getLore();

		if(lore.contains(Locked.LOCKED_LORE_LINE)) {
			p.sendMessage(ChatUtils.createBroadcast("&7This item is already locked. Your scroll will not be expended."));
			return false;
		}
		return true;
	}




}
