package me.cworldstar.piratefinds.impl.ae.items;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.events.PFItemRegistered;
import me.cworldstar.piratefinds.impl.ae.items.items.*;
import me.cworldstar.piratefinds.impl.ae.items.items.blocks.TestBlockItem;
import me.cworldstar.piratefinds.impl.ae.items.items.boxes.ConfigLootBox;
import me.cworldstar.piratefinds.impl.ae.items.items.weapons.*;
import me.cworldstar.piratefinds.impl.ui.ErrorsIf;
import me.cworldstar.piratefinds.impl.ae.items.items.masks.SantaMask;
import me.cworldstar.piratefinds.impl.ae.items.items.stagnant.*;
import net.advancedplugins.ae.api.AEAPI;

public class PFItemClass {
	
	private static HashMap<String, AbstractPFItem> items = new HashMap<String, AbstractPFItem>();
	private static List<String> totems = new ArrayList<String>();
	
	public static final NamespacedKey PF_ITEM_KEY = new NamespacedKey(PirateFinds.getThisPlugin(), "PF_ITEM_KEY");
	
	/**
	 * Returns a copy of the {@link AbstractPFItem} database.
	 */
	public static Map<String, AbstractPFItem> getItems() {
		return Map.copyOf(items);
	}
	
	/**
	 * 
	 * Gets an {@link AbstractPFItem} from its ID. Returns {@link #nullItem()}
	 * if none is found.
	 * 
	 * @param {@link String} item
	 * @return {@link AbstractPFItem}
	 */
	@Nonnull
	@ErrorsIf(Reason = "NullItem is removed")
	public static AbstractPFItem getItem(String item) {
		AbstractPFItem pfItem = items.get(item);
		return pfItem == null ? nullItem() : pfItem;
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param one A {@link ItemStack}.
	 * @param two A {@link ItemStack}.
	 * @return {@link Boolean} Whether or not the two items are similar.
	 */
	@Nonnull
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
	
	/**
	 * 
	 * @return {@link AbstractPFItem} The {@link #nullItem()}.
	 */
	@Nonnull
	public static AbstractPFItem nullItem() {
		return items.get("NullItem");
	}
	
	
	/**
	 * This {@link AbstractPFItem}
	 * @return The {@link AbstractPFItem} associated with this {@link ItemStack}, or the {@link #nullItem()}.
	 */
	@Nonnull
	public static AbstractPFItem getItem(@Nonnull ItemStack itemOnCursor) {
		for(Entry<String, AbstractPFItem> sets : items.entrySet()) {
			AbstractPFItem item = sets.getValue();
			if(isPFItemSimilar(item.getPFItem(), itemOnCursor)) {
				return item;
			}
		}
		return items.get("NullItem");
	}
	
	
	/**
	 * 
	 * @param nullItem An {@link AbstractPFItem}.
	 * @param item An {@link AbstractPFItem}.
	 * @return {@link Boolean} Whether or not the two {@link AbstractPFItem}s are the same.
	 */
	@Nonnull
	public static boolean compare(@Nonnull AbstractPFItem nullItem, @Nonnull AbstractPFItem item) {
		
		String id1 = nullItem.getPFItemID();
		String id2 = item.getPFItemID();
		
		return id1.contains(id2);
	}
	
	
	private static void internalRegisterItem(String id, AbstractPFItem item) {
		PFItemRegistered event = new PFItemRegistered(item, id);
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()) {
			items.put(id, item);
		}
	}
	
	/**
	 * When this constructor is called, it registers all our {@link AbstractPFItem}s.
	 */
	public PFItemClass() {
		internalRegisterItem("NullItem", new NullItem());
		internalRegisterItem("LockScroll", new LockScroll("LOCK_SCROLL"));
		internalRegisterItem("UnlockScroll", new UnlockScroll("UNLOCK_SCROLL"));
		internalRegisterItem("UnsealScroll", new UnsealScroll("UNSEAL_SCROLL"));
		internalRegisterItem("TitanLootbox", new TitanLootBox("TITAN_LOOTBOX"));
		internalRegisterItem("MobLootbox", new MobLootBox());
		internalRegisterItem("WardenBox", new WardenBox("WARDEN_LOOTBOX"));
		internalRegisterItem("EnderDragonLootbox", new EnderDragonLootBox());
		internalRegisterItem("DragonScale", new DragonScale("DRAGON_SCALE"));
		internalRegisterItem("EvolveScroll", new EvolveScroll("EVOLVE_SCROLL"));
		internalRegisterItem("ItemMagnet", new Magnet("MAGNET"));
		internalRegisterItem("Reinforcement", new Reinforcement("REINFORCEMENT"));
		internalRegisterItem("IronGolemBox", new IronGolemBox());
		internalRegisterItem("Hammer", new Hammer("HAMMER"));
		internalRegisterItem("ChristmasLootbox", new ChristmasBox());
		internalRegisterItem("CandyCane", new CandyCane());
		internalRegisterItem("Sharpener", new Sharpener("SHARPENER"));
		
		// masks
		internalRegisterItem("SantaMask", new SantaMask());
		
		// gkit vouchers
		
		
		for(String gkit : AEAPI.getGKits()) {
			internalRegisterItem(gkit+"GkitVoucher", new GKitVoucher(gkit.toLowerCase()));
		}
		
		internalRegisterItem("experience_crystal", new ExperienceCrystal());
		internalRegisterItem("health_crystal", new HealthCrystal());
		internalRegisterItem(new FencingSabre());
		internalRegisterItem(new SoulVoucher());
		// misc
		internalRegisterItem("infinite_bucket", new InfiniteBucket());
		
		//no use items
		internalRegisterItem(new DiamondSingularity());
		internalRegisterItem(new IronSingularity());
		internalRegisterItem(new GoldenSingularity());
		internalRegisterItem(new EmeraldSingularity());
		internalRegisterItem(new WardenEye());
		internalRegisterItem(new EnderWing());
		internalRegisterItem(new SwordCore());



		// blocks
		internalRegisterItem("TestBlock", new TestBlockItem());
		
		//i mliike realy drunk so maybe this suks?? idk

		registerConfigLootboxes();
		registerConfigTotems();
		registerConfigBackpacks();
		
		SoulGem.buildFromConfig(PirateFinds.getThisPlugin().getSoulConfig().getConfigurationSection("soul-gem"));
	}

	private static void internalRegisterItem(AbstractPFItem item) {
		internalRegisterItem(item.getPFItemID(), item);
	}

	public static void registerConfigBackpacks() {
		for(String key : PirateFinds.getThisPlugin().getBackpackConfig().getKeys(false)) {
			Backpack.buildFromConfig(PirateFinds.getThisPlugin().getBackpackConfig().getConfigurationSection(key));
		}
	}
	
	public static void registerConfigLootboxes() {
		for(String key : PirateFinds.getThisPlugin().getBoxConfig().getKeys(false)) {
			AbstractLootBox.buildFromConfig(PirateFinds.getThisPlugin().getBoxConfig().getConfigurationSection(key));
		}
	}
	
	public static void registerConfigTotems() {
		for(String key : PirateFinds.getThisPlugin().getTotemConfig().getConfigurationSection("totems").getKeys(false)) {
			EnchantmentTotem.buildFromConfig(PirateFinds.getThisPlugin().getTotemConfig().getConfigurationSection("messages"), PirateFinds.getThisPlugin().getTotemConfig().getConfigurationSection("totems").getConfigurationSection(key));
			totems.add(key);
		}
	}
	
	/**
	 * 
	 * @param <T> 
	 * @param item Any instanced class extending {@link AbstractPFItem}.
	 */
	public static <T extends AbstractPFItem> void registerAnyItem(T item) {
		if(!(item instanceof AbstractPFItem)) { 
			PirateFinds.logger().warning("Attempted to register item".concat(" ").concat(item.getPFItemID()).concat(". It was unsuccessful."));
			return;
		}
		try {
			PFItemRegistered event = new PFItemRegistered(item, item.getPFItemID());
			Bukkit.getPluginManager().callEvent(event);
			if(!event.isCancelled()) {
				items.put(item.getPFItemID(), item);
			}
		} finally {
			
		}		
	}
	
	/**
	 * 
	 * @return {@link List} A list of the registered totems.
	 */
	public static List<String> getRegisteredTotems() {
		return totems;
	}
	
	public static void registerItem(ConfigLootBox item) {
		PirateFinds.log(item.toString());
		
		PFItemRegistered event = new PFItemRegistered(item, item.getPFItemID());
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()) {
			items.put(item.getPFItemID(), item);
		}
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
		
		PFItemRegistered event = new PFItemRegistered(item, itemId);
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()) {
			items.put(itemId, item);
		}
	}

	/**
	 *
	 * This method will unregister an item from the PFItemClass.
	 * Internally used to clear old versions of box items so
	 * there are no overlaps.
	 * 
	 *
	 * @author cworldstar
	 */
	
	public static void unregister(@Nonnull String key) {
		if(key.contentEquals("NullItem")) {
			PirateFinds.log("You cannot unregister the null item.");
			return;
		}
		PirateFinds.log("Unregistered item " + key);
		items.remove(key);
	}
	
	public static void unregister(@Nonnull AbstractPFItem item) {
		if(item.getPFItemID().contentEquals("NullItem")) {
			PirateFinds.log("You cannot unregister the null item.");
			return;
		}
		PirateFinds.log("Unregistered item " + item.getPFItemID());
		items.remove(item.getPFItemID());
	}




}
