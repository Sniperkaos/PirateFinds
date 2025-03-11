package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class GoldenSingularity extends NoUseItem implements MaterialSingularity {

	private static ItemStack GOLD_SINGULARITY_STACK = new ItemStack(Material.GOLD_INGOT);
	static {
		ItemMeta meta = GOLD_SINGULARITY_STACK.getItemMeta();
		meta.setItemName(ChatUtils.apply("&x&F&F&A&A&0&0&lCompressed Gold Ingot"));
		meta.setDisplayName(ChatUtils.apply("&x&F&F&A&A&0&0&lCompressed Gold Ingot"));
		meta.setEnchantmentGlintOverride(true);
		
		meta.setLore(ChatUtils.apply(Arrays.asList(new String[] {
				"",
				"&x&F&F&A&A&0&0&oThis gold ingot, while fundamentally the same,",
				"&x&F&F&A&A&0&0&ohas some striking differences. Notably, it's",
				"&x&F&F&A&A&0&0&oabout nine times stronger.",
				"",
				"&7( &6This item can only be used for crafting. &7)"
		})));
		GOLD_SINGULARITY_STACK.setItemMeta(meta);
	}
	
	public GoldenSingularity() {
		super("GOLD_SINGULARITY");
		this.registerBlock(GOLD_SINGULARITY_STACK, pf_item_id);
	}

	@Override
	public ItemStack getPFItem() {
		return GOLD_SINGULARITY_STACK;
	}

	@Override
	public ItemStack build() {
		return GOLD_SINGULARITY_STACK.clone();
	}

}
