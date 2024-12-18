package me.cworldstar.piratefinds.impl.ae.items;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.items.*;
import me.cworldstar.piratefinds.impl.ae.items.items.blocks.TestBlockItem;
import me.cworldstar.piratefinds.impl.ae.items.items.boxes.ConfigLootBox;
import me.cworldstar.piratefinds.impl.ae.items.items.masks.SantaMask;

public class PFItemClass {
	
	private static HashMap<String, AbstractPFItem> items = new HashMap<String, AbstractPFItem>();
	
	public static final NamespacedKey PF_ITEM_KEY = new NamespacedKey(PirateFinds.getThisPlugin(), "PF_ITEM_KEY");
	
	public static HashMap<String, AbstractPFItem> getItems() {
		return items;
	}
	
	public static AbstractPFItem getItem(String item) {
		return items.get(item);
	}
	
	@NotNull
	public static boolean isPFItemSimilar(ItemStack one, ItemStack two) {
		if(one == null || two == null) return false;
		if(one.isSimilar(two)) return true;
		ItemMeta one_meta = one.getItemMeta();
		ItemMeta two_meta = two.getItemMeta();
		if(one_meta == null || two_meta == null) return false; // not a PF item
		PersistentDataContainer one_container = one_meta.getPersistentDataContainer();
		PersistentDataContainer two_container = two_meta.getPersistentDataContainer();	
		if(one_container.has(PF_ITEM_KEY, PersistentDataType.STRING) && two_container.has(PF_ITEM_KEY, PersistentDataType.STRING)) {
			if(one_container.get(PF_ITEM_KEY, PersistentDataType.STRING) == null) return false;
			if(two_container.get(PF_ITEM_KEY, PersistentDataType.STRING) == null) return false;			
			return one_container.get(PF_ITEM_KEY, PersistentDataType.STRING).contentEquals(two_container.get(PF_ITEM_KEY, PersistentDataType.STRING));
		}
		//not a pf item return false
		return false;
	}
	
	@NotNull
	public static AbstractPFItem nullItem() {
		return items.get("NullItem");
	}
	
	@NotNull
	public static AbstractPFItem getItem(@Nonnull ItemStack itemOnCursor) {
		for(Entry<String, AbstractPFItem> sets : items.entrySet()) {
			AbstractPFItem item = sets.getValue();
			if(isPFItemSimilar(item.getPFItem(), itemOnCursor)) {
				return item;
			}
		}
		return items.get("NullItem");
	}
	
	
	@NotNull
	public static boolean compare(@NotNull AbstractPFItem nullItem, @NotNull AbstractPFItem item) {
		
		String id1 = nullItem.getPFItemID();
		String id2 = item.getPFItemID();
		
		return id1 == id2;
	}
	
	
	public PFItemClass() {
		items.put("NullItem", new NullItem());
		items.put("LockScroll", new LockScroll("LOCK_SCROLL"));
		items.put("UnlockScroll", new UnlockScroll("UNLOCK_SCROLL"));
		items.put("UnsealScroll", new UnsealScroll("UNSEAL_SCROLL"));
		items.put("TitanLootbox", new TitanLootBox("TITAN_LOOTBOX"));
		items.put("MobLootbox", new MobLootBox());
		items.put("WardenBox", new WardenBox("WARDEN_LOOTBOX"));
		items.put("EnderDragonLootbox", new EnderDragonLootBox());
		items.put("DragonScale", new DragonScale("DRAGON_SCALE"));
		items.put("EvolveScroll", new EvolveScroll("EVOLVE_SCROLL"));
		items.put("ItemMagnet", new Magnet("MAGNET"));
		items.put("Reinforcement", new Reinforcement("REINFORCEMENT"));
		items.put("IronGolemBox", new IronGolemBox());
		items.put("Hammer", new Hammer("HAMMER"));
		items.put("ChristmasLootbox", new ChristmasBox());
		items.put("CandyCane", new CandyCane());
		items.put("Sharpener", new Sharpener("SHARPENER"));
		
		// masks
		items.put("SantaMask", new SantaMask());
		
		// gkit vouchers
		
		items.put("JollyGkitVoucher", new GKitVoucher("jolly"));
		items.put("TamerGkitVoucher", new GKitVoucher("tamer"));
		items.put("BarbarianGkitVoucher", new GKitVoucher("barbarian"));
		items.put("MinerGkitVoucher", new GKitVoucher("miner"));
		items.put("ExplorerGkitVoucher", new GKitVoucher("treasurehunter"));
		
		
		// blocks
		items.put("TestBlock", new TestBlockItem());
		
		//i mliike realy drunk so maybe this suks?? idk
		for(String key : PirateFinds.getThisPlugin().getBoxConfig().getKeys(false)) {
			AbstractLootBox.buildFromConfig(PirateFinds.getThisPlugin().getBoxConfig().getConfigurationSection(key));
		}
		
		
	}
	
	public static <T extends AbstractPFItem> void registerAnyItem(T item) {
		if(!(item instanceof AbstractPFItem)) return;
		try {
			items.put(item.getPFItemID(), item);
		} finally {
			
		}		
	}
	
	public static void registerItem(ConfigLootBox item) {
		PirateFinds.log(item.toString());
		items.put(item.getPFItemID(), item);
	}
	
	public static void registerItem(String itemId, Class<AbstractPFItem> clazz) {
		
		AbstractPFItem item = null;
		try {
			item = clazz.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		if(item == null) {
			PirateFinds.log("ERROR: Hotloaded item " + itemId + " was not loaded. Look up in the file.");
			return;
		}
		
		items.put(itemId, item);
	}




}
