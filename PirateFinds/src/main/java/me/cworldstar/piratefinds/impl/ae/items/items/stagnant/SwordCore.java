package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class SwordCore extends NoUseItem implements NoPlacement {

	private static ItemStack SWORD_CORE_STACK = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzMyOGFkZWI3MDI3NTU5ZjNlYzVlZjY2ZDQ1MmQ4ODQwYzJiMDkzZTM5N2YwZjkzZTU5MDI5ZjI5MzFjYzA5MyJ9fX0=");
	static {
		ItemMeta meta = SWORD_CORE_STACK.getItemMeta();
		meta.setItemName(ChatUtils.apply("&e&k|||&r &f&lSword Core"));
		meta.setDisplayName(ChatUtils.apply("&e&k|||&r &f&lSword Core"));
		meta.setEnchantmentGlintOverride(true);
		
		meta.setLore(ChatUtils.apply(Arrays.asList(new String[] {
				"",
				"&f&oA sentient core, for a strong blade.",
				"",
				"&7( &3This item can only be used for crafting. &7)"
		})));
		SWORD_CORE_STACK.setItemMeta(meta);
	}
	
	public SwordCore() {
		super("SWORD_CORE");
	}

	@Override
	public ItemStack getPFItem() {
		return SWORD_CORE_STACK;
	}

	@Override
	public ItemStack build() {
		return SWORD_CORE_STACK.clone();
	}

}
