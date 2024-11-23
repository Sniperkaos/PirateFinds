package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem.PFItemType;
import me.cworldstar.piratefinds.impl.ae.listeners.Locked;
import me.cworldstar.piratefinds.impl.ae.seal.Unseal;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.impl.utils.ColorUtils;
import net.advancedplugins.ae.items.AEItem;

//todo: too lazy make this l8r

public class UnsealScroll extends AbstractPFItem {

	private static PFItemType type = PFItemType.DRAG_AND_DROP;
	

	@Override
	public PFItemType getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	public static enum UnsealedItemRarity {
		LEGENDARY("&6", new String[] {
				
		}),
		ULTIMATE("&e", new String[] {
				
		}),
		ELITE("&b", new String[] {
				
		}),
		UNIQUE("&a", new String[] {
				
		}),
		SIMPLE("&7", new String[] {
				
		});

		private ArrayList<String> rarities = new ArrayList<String>();
		private String colorCode;
		
		private UnsealedItemRarity(String colorCode, String[] arrayList) {
			this.colorCode = colorCode;
			this.rarities.addAll(List.of(arrayList));
		}
	}
	
	private static ItemStack item = new ItemStack(Material.PAPER);
	static {
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ColorUtils.format("&7&l(&c&l!&7&l) &f&lUnseal Scroll: {rarity}"));
		meta.setLore(List.of(new String[] {
				ColorUtils.format("&c&oDragging and dropping this onto an item"),
				ColorUtils.format("&c&owill unseal the item, giving you a"),
				ColorUtils.format("&c&o{rarity} tier item."),
		}));
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
	}

	@Override
	public ItemStack build() {
		
		ItemStack cItem = item.clone();
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		String new_name = meta.getDisplayName().replace("{rarity}", ColorUtils.format("&bELITE&c&o"));
		meta.setDisplayName(new_name);
		lore.replaceAll(loreLine -> loreLine.replace("{rarity}", ColorUtils.format("&bELITE&c&o")));
		meta.setLore(lore);
		cItem.setItemMeta(meta);
		
		return cItem;
	}

	
	@Override
	public void onItemUse(Player p, ItemStack on) {
		Unseal.unsealItem(p, on);
	}
	
	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		ItemMeta meta = on.getItemMeta();
		
		if(meta == null) return false;
		
		List<String> lore = meta.getLore();

		if(!lore.contains(Unseal.UNSEALED_LORE_LINE)) {
			p.sendMessage(ChatUtils.createBroadcast("&7This item is not sealed. Your scroll will not be expended."));
			return false;
		}
		
		return true;
	}

}
