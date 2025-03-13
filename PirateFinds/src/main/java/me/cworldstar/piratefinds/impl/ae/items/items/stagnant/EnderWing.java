package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class EnderWing extends NoUseItem implements NoEat {

	private static ItemStack ENDER_WING_STACK = new ItemStack(Material.PHANTOM_MEMBRANE);
	static {
		ItemMeta meta = ENDER_WING_STACK.getItemMeta();
		meta.setItemName(ChatUtils.apply("&5&lEnder Dragon's Wing"));
		meta.setDisplayName(ChatUtils.apply("&5&lEnder Dragon's Wing"));
		meta.setEnchantmentGlintOverride(true);
		
		meta.setLore(ChatUtils.apply(Arrays.asList(new String[] {
				"",
				"&d&oThis strange material is covered in scales, and",
				"&d&ohas a leathery feel to it. It's also very",
				"&d&odurable.",
				"",
				"&7 * &dDrops from the Ender Dragon.",
				"",
				"&7( &5This item can only be used for crafting. &7)"
		})));
		ENDER_WING_STACK.setItemMeta(meta);
	}
	
	public EnderWing() {
		super("ENDER_WING");
	}

	@Override
	public ItemStack getPFItem() {
		return ENDER_WING_STACK;
	}

	@Override
	public ItemStack build() {
		return ENDER_WING_STACK.clone();
	}

}
