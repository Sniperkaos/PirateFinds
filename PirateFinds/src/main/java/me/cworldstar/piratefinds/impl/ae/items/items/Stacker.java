package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.jeff_media.morepersistentdatatypes.DataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class Stacker extends AbstractPFItem {

	private static NamespacedKey key = new NamespacedKey(PirateFinds.getThisPlugin(), "STACKER_AMOUNT");
	
	public ItemStack item = new ItemStack(Material.CHEST);
	public ItemStack holding;
	private Material material;
	
	public Stacker(String string, Material material) {
		super(string);
		this.material = material;
	}
	
	public void updateItem(ItemStack stack, String replace, int amount) {
		ItemMeta meta = stack.getItemMeta();
		if(meta == null) return;
		
		List<String> lore = meta.getLore();
		if(lore == null) lore = new ArrayList<String>();
		
		lore.replaceAll(loreLine -> loreLine.replace(replace, Integer.toString(amount)));
		meta.setLore(lore);
		
		stack.setItemMeta(meta);
		
	}
	
	public ItemStack getPFItem() {
		return item;
	}
	
	public void updateItem(ItemStack stack, String replace, boolean status) {
		ItemMeta meta = stack.getItemMeta();
		if(meta == null) return;
		
		List<String> lore = meta.getLore();
		if(lore == null) lore = new ArrayList<String>();
		
		lore.replaceAll(loreLine -> loreLine.replace(replace, Boolean.toString(status)));
		meta.setLore(lore);
		
		stack.setItemMeta(meta);
		
	}
	
	public int getAmount(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		if(meta == null) return 0;
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		int amount = pdc.get(key, PersistentDataType.INTEGER);
		return amount;
	}
	
	public void setAmount(ItemStack stack, int amount) {
		ItemMeta meta = stack.getItemMeta();
		if(meta == null) return;
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(key, PersistentDataType.INTEGER, amount);		
		updateItem(stack, "%amount%", amount);
	}
	
	public boolean getStatus (ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		if(meta == null) return false;
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		boolean amount = pdc.get(key, PersistentDataType.BOOLEAN);
		return amount;
	}
	
	public void toggleStatus(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		if(meta == null) return;
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		boolean amount = pdc.get(key, PersistentDataType.BOOLEAN);
		pdc.set(key, PersistentDataType.BOOLEAN, !amount);
		
		updateItem(stack, "%status%", !amount);
	}
	
	public ItemStack getHoldingItem (ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		if(meta == null) return null;
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		ItemStack amount = pdc.get(key, DataType.ITEM_STACK);
		return amount;
	}
	
	@Override
	public ItemStack build() {
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ChatUtils.apply("&6&lStacker: &7" + this.holding.getType().toString()));
		List<String> lore = meta.getLore();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(key, PersistentDataType.INTEGER, 0);
		pdc.set(key, DataType.ITEM_STACK, this.holding);
		pdc.set(key, PersistentDataType.BOOLEAN, false);
		lore.add(ChatUtils.apply("&7Holding: &a%amount%"));
		lore.add(ChatUtils.apply("&aRight-click&7 to gain a stack."));
		lore.add(ChatUtils.apply("&7Drop to toggle."));
		lore.add(ChatUtils.apply("&7Currently: %status%."));
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		ItemStack clone = item.clone();
		updateItem(clone, "%status%", true);
		updateItem(clone, "%amount%", 0);
		
		return item.clone();
	}
	
	@Override
	public PFItemType getType() {
		// TODO Auto-generated method stub
		return PFItemType.RIGHT_CLICK;
	}
	
	@Override
	public List<PFItemType> getTypes() {
		return Arrays.asList(new PFItemType[] {
				PFItemType.RIGHT_CLICK,
				PFItemType.TICK,
				PFItemType.DROP_ITEM
		});
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return false;
	}

	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		switch(type) {
			case RIGHT_CLICK:
				onItemRightClick(p, on);
				break;
			case DROP_ITEM:
				onItemDropItem(on);
				break;
			case TICK:
				tick(p, on);
			default:
				break;
		}
	}
	
	public void tick(Player p, ItemStack self) {
		Inventory inventory = p.getInventory();
		ItemStack[] items = inventory.getContents();
		for(ItemStack item : items) {
			if(item.isSimilar(new ItemStack(this.material))) {
				setAmount(self, getAmount(self));
				inventory.clear(inventory.first(item));
			}
		}
	}

	private void onItemDropItem(ItemStack on) {
		toggleStatus(on);
	}

	private void onItemRightClick(Player p, ItemStack on) {
		
		int amount = getAmount(on);
		ItemStack holdingItem = getHoldingItem(on).clone();
		if(amount >= 64) {
			holdingItem.setAmount(holdingItem.getMaxStackSize());
		} else {
			holdingItem.setAmount(amount);
		}
		
		p.getInventory().addItem(holdingItem);
	}
}
