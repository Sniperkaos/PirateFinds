package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class EmeraldSingularity extends NoUseItem implements MaterialSingularity {

	private static ItemStack EMERALD_SINGULARITY_STACK = new ItemStack(Material.EMERALD);
	static {
		ItemMeta meta = EMERALD_SINGULARITY_STACK.getItemMeta();
		meta.setItemName(ChatUtils.apply("&a&lCompressed Emerald"));
		meta.setDisplayName(ChatUtils.apply("&a&lCompressed Emerald"));
		meta.setEnchantmentGlintOverride(true);
		
		meta.setLore(ChatUtils.apply(Arrays.asList(new String[] {
				"",
				"&a&oThis emerald, while fundamentally the same,",
				"&a&ohas some striking differences. Notably, it's",
				"&a&oabout nine times stronger.",
				"",
				"&7( &2This item can only be used for crafting. &7)"
		})));
		EMERALD_SINGULARITY_STACK.setItemMeta(meta);
	}
	
	public EmeraldSingularity() {
		super("EMERALD_SINGULARITY");
		this.registerBlock(EMERALD_SINGULARITY_STACK, pf_item_id);
	}

	@Override
	public ItemStack getPFItem() {
		return EMERALD_SINGULARITY_STACK;
	}

	@Override
	public ItemStack build() {
		return EMERALD_SINGULARITY_STACK.clone();
	}

}
