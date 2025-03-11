package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class DiamondSingularity extends NoUseItem implements MaterialSingularity {

	private static ItemStack DIAMOND_SINGULARITY_STACK = new ItemStack(Material.DIAMOND);
	static {
		ItemMeta meta = DIAMOND_SINGULARITY_STACK.getItemMeta();
		meta.setItemName(ChatUtils.apply("&b&lCompressed Diamond"));
		meta.setDisplayName(ChatUtils.apply("&b&lCompressed Diamond"));
		meta.setEnchantmentGlintOverride(true);
		
		meta.setLore(ChatUtils.apply(Arrays.asList(new String[] {
				"",
				"&b&oThis diamond, while fundamentally the same,",
				"&b&ohas some striking differences. Notably, it's",
				"&b&oabout nine times stronger.",
				"",
				"&7( &3This item can only be used for crafting. &7)"
		})));
		DIAMOND_SINGULARITY_STACK.setItemMeta(meta);
	}
	
	public DiamondSingularity() {
		super("DIAMOND_SINGULARITY");
		this.registerBlock(DIAMOND_SINGULARITY_STACK, pf_item_id);
	}

	@Override
	public ItemStack getPFItem() {
		return DIAMOND_SINGULARITY_STACK;
	}

	@Override
	public ItemStack build() {
		return DIAMOND_SINGULARITY_STACK.clone();
	}

}
