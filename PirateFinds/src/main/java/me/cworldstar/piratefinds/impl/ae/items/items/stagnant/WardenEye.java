package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class WardenEye extends NoUseItem implements NoEat {

	private static ItemStack WARDEN_EYE_STACK = new ItemStack(Material.SPIDER_EYE);
	static {
		ItemMeta meta = WARDEN_EYE_STACK.getItemMeta();
		meta.setItemName(ChatUtils.apply("&3&lWarden's Eye"));
		meta.setDisplayName(ChatUtils.apply("&3&lWarden's Eye"));
		meta.setEnchantmentGlintOverride(true);
		
		meta.setLore(ChatUtils.apply(Arrays.asList(new String[] {
				"",
				"&b&oDropped from a warden, this strange material",
				"&b&oseems to still work... is it looking at me?",
				"",
				"&7( &3This item can only be used for crafting. &7)"
		})));
		WARDEN_EYE_STACK.setItemMeta(meta);
	}
	
	public WardenEye() {
		super("WARDEN_EYE");
	}

	@Override
	public ItemStack getPFItem() {
		return WARDEN_EYE_STACK;
	}

	@Override
	public ItemStack build() {
		return WARDEN_EYE_STACK.clone();
	}

}
