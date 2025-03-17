package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class SoulVoucher extends AbstractPFItem {

	private static ItemStack SOUL_VOUCHER_ITEMSTACK = new ItemStack(Material.PAPER);
	
	static {
		ItemMeta meta = SOUL_VOUCHER_ITEMSTACK.getItemMeta();
		meta.setDisplayName(ChatUtils.apply("&7[ &cSoul Voucher &7]"));
		meta.setItemName(ChatUtils.apply("&7[ &cSoul Voucher &7]"));
		
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("&4&oA haunting piece of paper, filled");
		lore.add("&4&owith anguish and strife.");
		lore.add("");
		lore.add("&7 * &fVoucher Souls: &c%souls%");
		lore.add("&7( &7Drag and drop onto a &cSoul Gem");
		lore.add("&7to add souls to it.&7)");
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "SoulVoucher");
		
		meta.setLore(ChatUtils.apply(lore));
		
		SOUL_VOUCHER_ITEMSTACK.setItemMeta(meta);
	}
	
	public SoulVoucher() {
		super("SoulVoucher");
	}

	@Override
	public ItemStack getPFItem() {
		return SOUL_VOUCHER_ITEMSTACK;
	}

	@Override
	public ItemStack build() {
		
		ItemStack clone = SOUL_VOUCHER_ITEMSTACK.clone();
		ItemMeta meta = clone.getItemMeta();
		
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "SoulVoucher");
		
		clone.setItemMeta(meta);
		
		return clone;
	}
	
	public ItemStack create(int amount) {
		ItemStack built = build();
		ItemMeta meta = built.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(SoulGem.SOUL_KEY, PersistentDataType.INTEGER, amount);
		List<String> newLore = meta.getLore();
		newLore.replaceAll(lore-> lore.replace("%souls%", Integer.toString(amount)));
		meta.setLore(newLore);
		built.setItemMeta(meta);
		
		return built;
	}
	
	@Override
	public void onItemUse(Player clicked, ItemStack slotItem, PFItemType type, ItemStack cursor) {
		if(PFItemClass.compare(PFItemClass.getItem(slotItem), PFItemClass.getItem("SoulGem"))) {
			ItemMeta meta = cursor.getItemMeta();
			PersistentDataContainer pdc = meta.getPersistentDataContainer();
			int souls = pdc.get(SoulGem.SOUL_KEY, PersistentDataType.INTEGER);
			SoulGem.updateSouls(slotItem, souls);
		}
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		if(PFItemClass.compare(PFItemClass.getItem(on), PFItemClass.getItem("SoulGem"))) {
			return true;
		}
		return false;
	}

	@Override
	public PFItemType getType() {
		return PFItemType.DRAG_AND_DROP;
	}

}
