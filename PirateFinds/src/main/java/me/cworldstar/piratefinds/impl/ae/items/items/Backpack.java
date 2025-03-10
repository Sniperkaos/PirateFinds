package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.jeff_media.morepersistentdatatypes.DataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject.InventorySize;
import me.cworldstar.piratefinds.impl.ui.items.BackpackUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class Backpack extends AbstractPFItem {

	public static final NamespacedKey NSKEY_INVENTORY = PirateFinds.createKey("inventory");
	
	private ItemStack item = new ItemStack(Material.PLAYER_HEAD);
	private String id;
	private PFItemType type = PFItemType.RIGHT_CLICK;
	private InventorySize size = InventorySize.SMALL;
	
	public ItemStack getPFItem() {
		return this.item;
	}
	
	public InventorySize getSize() {
		return size;
	}
	
	public Backpack(String id, ItemStack i, String size, String item_key) {
		super(id);
		
		this.id = item_key;
		this.item = i;
		this.size = InventorySize.valueOf(size);
	}

	public static void buildFromConfig(ConfigurationSection backpack) {
		
		List<String> lore = backpack.getStringList("lore");
		String name = ChatUtils.apply(backpack.getString("name"));
		String base64 = backpack.getString("headId");
		ItemStack head = SkullCreator.itemFromBase64(base64);
		ItemMeta meta = head.getItemMeta();
		
		//-- to set the name
		meta.setItemName(name);
		meta.setDisplayName(name);
		
		String s = "pack_" + backpack.getName();
		
		meta.setLore(ChatUtils.apply(lore));
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, s);
		
		head.setItemMeta(meta);
		
		String size = backpack.getString("size");
		
		Backpack pack = new Backpack(s, head, size, s);
		PFItemClass.registerAnyItem(pack);
		
	}

	@Override
	public ItemStack build() {
		
		ItemStack clone = this.item.clone();
		ItemMeta meta = clone.getItemMeta();
		
		List<String> lore = meta.getLore();
		lore.replaceAll(line->line.replace("%items%", Integer.toString(0)));
		meta.setLore(lore);
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(NSKEY_INVENTORY, DataType.ITEM_STACK_ARRAY, new ItemStack[0]);
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, id);
		clone.setItemMeta(meta);
		
		return clone;
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		open(on, p);
	}
	
	public static void open(ItemStack backpack, Player p) {
		ItemMeta meta = backpack.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		
		ItemStack[] items = container.get(NSKEY_INVENTORY, DataType.ITEM_STACK_ARRAY);
		String backpack_item_key = container.get(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING);
		Backpack bck = (Backpack) PFItemClass.getItem(backpack_item_key);

		BackpackUI ui = new BackpackUI(p, items, bck, backpack, bck.getSize());
		ui.open();
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return false;
	}

	@Override
	public PFItemType getType() {
		return type;
	}

	public static void updateInventory(List<ItemStack> items, ItemStack backpack) {
		ItemMeta meta = backpack.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		PirateFinds.log("amount of items: " + items);
		container.set(NSKEY_INVENTORY, DataType.ITEM_STACK_ARRAY, items.toArray(new ItemStack[0]));
		
		List<String> lore = meta.getLore();
		lore.replaceAll(line->line.replace("%items%", Integer.toString(items.size())));
		meta.setLore(ChatUtils.apply(lore));
		
		backpack.setItemMeta(meta);
	}
}
