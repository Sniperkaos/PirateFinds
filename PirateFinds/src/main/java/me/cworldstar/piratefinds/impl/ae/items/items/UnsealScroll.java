package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem.PFItemType;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ae.listeners.Locked;
import me.cworldstar.piratefinds.impl.ae.seal.Unseal;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ExpandedRandom;
import net.advancedplugins.ae.impl.utils.ColorUtils;
import net.advancedplugins.ae.items.AEItem;

//todo: too lazy make this l8r

public class UnsealScroll extends AbstractPFItem {

	public UnsealScroll(String id) {
		super(id);
	}

	private static PFItemType type = PFItemType.DRAG_AND_DROP;
	private static NamespacedKey UNSEAL_SCROLL_PDC_TAG = new NamespacedKey(PirateFinds.getThisPlugin(), "UNSEALSCROLLRARITY");
	public final String pf_item_id = "UNSEAL_SCROLL";
	
	
	@Override
	public PFItemType getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	public static enum UnsealedItemRarity {
		MASTERY("&4", new String[] {
				"MASTERY",
				"SOUL",
				"FABLED",
				"LEGENDARY",
				"ULTIMATE",
				"ELITE",
				"UNIQUE",
				"SIMPLE"
		}),
		SOUL("&c", new String[] {
				"SOUL",
				"FABLED",
				"LEGENDARY",
				"ULTIMATE",
				"ELITE",
				"UNIQUE",
				"SIMPLE"
		}),
		FABLED("&d", new String[] {
				"FABLED",
				"LEGENDARY",
				"ULTIMATE",
				"ELITE",
				"UNIQUE",
				"SIMPLE"
		}),
		LEGENDARY("&6", new String[] {
				"LEGENDARY",
				"ULTIMATE",
				"ELITE",
				"UNIQUE",
				"SIMPLE"
		}),
		ULTIMATE("&e", new String[] {
				"ULTIMATE",
				"ELITE",
				"UNIQUE",
				"SIMPLE"
		}),
		ELITE("&b", new String[] {
				"ELITE",
				"UNIQUE",
				"SIMPLE"
		}),
		UNIQUE("&a", new String[] {
				"UNIQUE",
				"SIMPLE"
		}),
		SIMPLE("&7", new String[] {
				"SIMPLE"	
		});

		private ArrayList<String> rarities = new ArrayList<String>();
		private String colorCode;
		
		private UnsealedItemRarity(String colorCode, String[] arrayList) {
			this.colorCode = colorCode;
			this.rarities.addAll(List.of(arrayList));
		}
		
		public ArrayList<String> getRarities() {
			return this.rarities;
		}
		
		public String getColor() {
			return this.colorCode;
		}
	}
	
	public static ItemStack item = new ItemStack(Material.PAPER);
	static {
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "UNSEAL_SCROLL");
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
		ExpandedRandom<UnsealedItemRarity> random_rarity = new ExpandedRandom<UnsealedItemRarity>();


		random_rarity.add(UnsealedItemRarity.LEGENDARY, 20);
		random_rarity.add(UnsealedItemRarity.ULTIMATE, 40);
		random_rarity.add(UnsealedItemRarity.ELITE, 60);
		random_rarity.add(UnsealedItemRarity.UNIQUE, 80);
		random_rarity.add(UnsealedItemRarity.SIMPLE, 100);

		UnsealedItemRarity rarity = random_rarity.resolve();
		String new_name = meta.getItemName().replace("{rarity}", ColorUtils.format(rarity.getColor() + "&l" + rarity.toString() + "&r&c&o"));
		meta.setDisplayName(new_name);
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(UNSEAL_SCROLL_PDC_TAG, PersistentDataType.STRING, rarity.toString());
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "UNSEAL_SCROLL");
		int index = 0;
		for(String loreLine : lore.toArray(new String[0])) {
			lore.set(index, loreLine.replace("{rarity}", ColorUtils.format(rarity.getColor() + "&l&o" + rarity.toString() + "&r&c&o")));
			index++;
		}
		meta.setLore(lore);
		cItem.setItemMeta(meta);
		
		return cItem;
	}
	
	public ItemStack getPFItem() {
		return item;
	}
	
	public String getRarity(ItemStack i) {
		if(i.getItemMeta() == null) return null;
		PirateFinds.log("GetRarity turned out " + i.getItemMeta().getPersistentDataContainer().get(UNSEAL_SCROLL_PDC_TAG, PersistentDataType.STRING));
		return i.getItemMeta().getPersistentDataContainer().get(UNSEAL_SCROLL_PDC_TAG, PersistentDataType.STRING);
	}

	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type, ItemStack thisItem) {
		ItemMeta meta = on.getItemMeta();
		if(meta == null) return;

		List<String> lore = meta.getLore();
		if(lore == null) lore = new ArrayList<String>();
		if(!lore.contains(Unseal.UNSEALED_LORE_LINE)) return;
		String rarity = getRarity(thisItem);
		if(rarity == null) return;
		
		Unseal.unsealItem(p, on, UnsealedItemRarity.valueOf(rarity));
	}
	
	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		ItemMeta meta = on.getItemMeta();
		
		if(meta == null) return false;
		
		List<String> lore = meta.getLore();
		if(lore == null) lore = new ArrayList<String>();
		if(!lore.contains(Unseal.UNSEALED_LORE_LINE)) {
			p.sendMessage(ChatUtils.createBroadcast("&7This item is not sealed. Your scroll will not be expended."));
			return false;
		}
		
		return true;
	}

}
