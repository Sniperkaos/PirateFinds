package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.InternalEnchantment;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.api.AEAPI;
import net.advancedplugins.ae.impl.utils.ColorUtils;

public class DragonScale extends AbstractPFItem {
	private static ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
	private static PFItemType type = PFItemType.DRAG_AND_DROP;
	
	public static boolean enchantDragonEnchantment(Player p, ItemStack i) {
		ArrayList<InternalEnchantment> enchants = PirateFinds.getAEExpansion().getRandomEnchantments(p, i.getType(), 0, Arrays.asList(new String[] {"DRAGON"}));
		if(enchants.get(0) == null) return false;
		for(InternalEnchantment enchant : enchants.toArray(new InternalEnchantment[0])) {
			if(AEAPI.hasCustomEnchant(enchant.getEnchantment(), i)) {
				if(AEAPI.getEnchantLevel(enchant.getEnchantment(), i) >= AEAPI.getHighestEnchantmentLevel(enchant.getEnchantment())) {
					return false;
				} else {
					int level = AEAPI.getEnchantLevel(enchant.getEnchantment(), i)+1;
					p.sendMessage(ChatUtils.apply("&dA dragon enchantment has been upgraded!"));
					AEAPI.applyEnchant(enchant.getEnchantment(),level,i);
				};
			} else {
				p.sendMessage(ChatUtils.apply("&dA dragon enchantment has been applied!"));
				AEAPI.applyEnchant(enchants.get(0).getEnchantment(), 1, i);
			}
		}
		return true;
	}
	
	public DragonScale(String id) {
		super(id);
	}
	
	@Override
	public PFItemType getType() {
		return type;	
	}
	
	public ItemStack getPFItem() {
		return item;
	}
	
	static {
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ColorUtils.format("&5&lDragon Scale"));
		meta.setLore(List.of(new String[] {
				"",
				ColorUtils.format("&7[ &d&lDRAGON SCALE&r &7]"),
				ColorUtils.format("&dDragging and dropping this onto an item"),
				ColorUtils.format("&dwill either enchant the item with a random Dragon enchantment,"),
				ColorUtils.format("&dor increase the level of a dragon enchantment by 1."),
		}));
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
	}
	

	public final String pf_item_id = "DRAGON_SCALE";
	
	@Override
	public ItemStack build() {
		ItemStack citem = item.clone();
		this.make(citem);
		return citem;
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return enchantDragonEnchantment(p, on);
	}
}
