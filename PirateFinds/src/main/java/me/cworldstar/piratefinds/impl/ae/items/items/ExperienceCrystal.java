package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class ExperienceCrystal extends AbstractPFItem {

	public static ItemStack EXPERIENCE_CRYSTAL = new ItemStack(Material.LIME_CANDLE);
	public static final NamespacedKey EXPERIENCE_CRYSTAL_KEY = PirateFinds.createKey("experienceStored");
	private static final String EXPERIENCE_CRYSTAL_NAME = "&x&3&9&F&B&1&1&lE&x&5&3&F&B&2&C&lx&x&6&E&F&B&4&7&lp&x&8&8&F&B&6&2&le&x&A&2&F&B&7&D&lr&x&8&8&F&B&6&2&li&x&6&E&F&B&4&7&le&x&5&3&F&B&2&C&ln&x&3&9&F&B&1&1&lc&x&5&C&F&B&3&9&le &x&7&E&F&B&6&2&lC&x&A&1&F&B&8&A&lr&x&C&3&F&B&B&2&ly&x&A&1&F&B&8&A&ls&x&7&E&F&B&6&2&lt&x&5&C&F&B&3&9&la&x&3&9&F&B&1&1&ll";
	
	static {
		ItemMeta meta = EXPERIENCE_CRYSTAL.getItemMeta();
		meta.setEnchantmentGlintOverride(true);
		meta.setItemName(ChatUtils.apply(EXPERIENCE_CRYSTAL_NAME));
		meta.setDisplayName(ChatUtils.apply(EXPERIENCE_CRYSTAL_NAME));
		meta.setLore(ChatUtils.apply(Arrays.asList(new String[] {
			"",
			"&a&oThe glimmer of this strange crystal",
			"&a&ois unnaturally pleasing to the eye.",
			"",
			"&7&o( &a&oTurn this in at spawn to the Shard Merchant! &7&o)"
		})));
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "experience_crystal");
		EXPERIENCE_CRYSTAL.setItemMeta(meta);
	}
	
	public ExperienceCrystal() {
		super("experience_crystal");
	}

	@Override
	public ItemStack build() {
		
		ItemStack clone = EXPERIENCE_CRYSTAL.clone();
		ItemMeta meta = clone.getItemMeta();
		meta.setItemName(ChatUtils.apply(EXPERIENCE_CRYSTAL_NAME));
		meta.setDisplayName(ChatUtils.apply(EXPERIENCE_CRYSTAL_NAME));
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "experience_crystal");
		
		return clone;
	}
	
	public ItemStack getPFItem() {
		return EXPERIENCE_CRYSTAL;
	}

	
	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return true;
	}

	@Override
	public PFItemType getType() {
		return PFItemType.RIGHT_CLICK;
	}

}
