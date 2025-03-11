package me.cworldstar.piratefinds.impl.ae.items.items.stagnant;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class IronSingularity extends NoUseItem implements MaterialSingularity {

	private static ItemStack IRON_SINGULARITY_STACK = new ItemStack(Material.IRON_INGOT);
	static {
		ItemMeta meta = IRON_SINGULARITY_STACK.getItemMeta();
		meta.setItemName(ChatUtils.apply("&f&lCompressed Iron Ingot"));
		meta.setDisplayName(ChatUtils.apply("&f&lCompressed Iron Ingot"));
		meta.setEnchantmentGlintOverride(true);
		
		meta.setLore(ChatUtils.apply(Arrays.asList(new String[] {
				"",
				"&f&oThis iron ingot, while fundamentally the same,",
				"&f&ohas some striking differences. Notably, it's",
				"&f&oabout nine times stronger.",
				"",
				"&7( &8This item can only be used for crafting. &7)"
		})));
		IRON_SINGULARITY_STACK.setItemMeta(meta);
	}
	
	public IronSingularity() {
		super("IRON_SINGULARITY");
		this.registerBlock(IRON_SINGULARITY_STACK, pf_item_id);
	}

	@Override
	public ItemStack getPFItem() {
		return IRON_SINGULARITY_STACK;
	}

	@Override
	public ItemStack build() {
		return IRON_SINGULARITY_STACK.clone();
	}

}
