package me.cworldstar.piratefinds.impl.ae;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.advancedplugins.ae.features.sets.instances.ArmorSet;
import net.advancedplugins.ae.items.ItemLoader;
import net.advancedplugins.ae.features.sets.enums.SetPiece;
import net.advancedplugins.ae.utils.AManager;
import net.advancedplugins.ae.features.gkits.GKitBuilder;
import net.advancedplugins.ae.features.sets.SetsManager;
import net.advancedplugins.ae.Core;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.effects.*;
import me.cworldstar.piratefinds.impl.ae.listeners.Locked;
import me.cworldstar.piratefinds.impl.ui.test.GKitPreviewUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.api.AEAPI;

/**
 * 
 * AE Expansion for Pirate Finds
 * @author cworldstar
 *
 */

public class AEExpansion {

	private boolean loaded = false;
	
	
	/**
	 * 
	 * Gives a player a gkit that's locked.
	 * 
	 * @param {@link Player} player
	 * @param {@link String} setId
	 */
	
	public void givePlayerLockedSet(Player player, String setId) {
		SetsManager manager = Core.getSetsManager();
		ArmorSet set = manager.getSet(setId);
		for(SetPiece piece : SetPiece.values()) {
			ItemStack item = set.getItem(piece);
			if(item != null) {
				Locked.lock(item);				
				AManager.giveItem(player, item);
			}
		}
	}
	
	public SetsManager getSetManager() {
		return Core.getSetsManager();
	}
	
	public ItemLoader getItemLoader() {
		return Core.getItemLoader();
	};
	
	
	public void givePlayerLockedItemWithEnchantments(Player player, Material item, String[] enchantments) {
				
		ItemStack itemStack = new ItemStack(item);
		for(String enchantment : enchantments) {
			String[] split = enchantment.split(":");
			InternalEnchantment IEnchantment = new InternalEnchantment(split[0], Integer.parseInt(split[1]));
			enchantItem(itemStack, IEnchantment);
		}
		
		Locked.lock(itemStack);
		AManager.giveItem(player, itemStack);
	}
	
	public static Set<String> getSets() {
		SetsManager manager = Core.getSetsManager();
		return manager.getSets();
	}
	
	public void registerAllEffects() {
		
		PirateFinds plugin = PirateFinds.getThisPlugin();
		
		AEAPI.registerEffect(plugin, new IncreaseDamage(plugin));
		AEAPI.registerEffect(plugin, new RemoveDamage(plugin));
		AEAPI.registerEffect(plugin, new PercentMaxHealth(plugin));
		AEAPI.registerEffect(plugin, new AutoSell(plugin));
		AEAPI.registerEffect(plugin, new OldSoulAddition());
		AEAPI.registerEffect(plugin, new OldSoulRemove());
		AEAPI.registerEffect(plugin, new CurrentHealth(plugin));
	}
	
	public InternalEnchantment getRandomEnchantment(OfflinePlayer player, Material held_item, List<String> enchants) {
		
		ArrayList<String> all_enchants = new ArrayList<String>(enchants);
		
		int max_random = all_enchants.size();
		
		Random random = new Random();
		random.setSeed(player.getUniqueId().getMostSignificantBits() * System.currentTimeMillis());
		
		String enchantment = all_enchants.get(random.nextInt(max_random));
		boolean applies_on = AEAPI.getEnchantmentInstance(enchantment).canBeApplied(held_item);
		if(!applies_on) {
			int apply_timeout = 0;
			while(!applies_on) {
				if(apply_timeout > 1000) {
					break;
				}
				enchantment = all_enchants.get(random.nextInt(max_random));
				applies_on = AEAPI.getEnchantmentInstance(enchantment).canBeApplied(held_item);
				apply_timeout++;
			}
		}
		
		int highest_level = AEAPI.getHighestEnchantmentLevel(enchantment);
		int level = random.nextInt(highest_level);
		if(level == 0) {
			level=1;
		}
		
		return new InternalEnchantment(enchantment, level);
		
	}
	
	public ArrayList<InternalEnchantment> getRandomEnchantments(OfflinePlayer player, Material held_item, int amount_of_enchants, List<String> groups) {
		ArrayList<InternalEnchantment> enchants = new ArrayList<InternalEnchantment>();
		ArrayList<String> all_enchants = new ArrayList<String>();
		
		groups.forEach((String group) -> {
			all_enchants.addAll(AEAPI.getEnchantmentsByGroup(group));
		});
				
		ArrayList<String> used_before = new ArrayList<String>();
		
		for(int i=0; i<=amount_of_enchants; i++) {

			InternalEnchantment enchantment = getRandomEnchantment(player, held_item, all_enchants);
			
			if(used_before.contains(enchantment.getEnchantment())) {
				int timeout = 0;
				while (used_before.contains(enchantment.getEnchantment())) {
					if(timeout > 1000) {
						PirateFinds.log("getRandomEnchantments timed out, are there not enough enchants?");
						break;
					}
					enchantment = getRandomEnchantment(player, held_item, all_enchants);
					timeout++;
				}
			}
			
			used_before.add(enchantment.getEnchantment());
			enchants.add(enchantment);
		}
		
		
		return enchants;
	}
	
	public void givePlayerGkitItem(Material item, Player player, String gkit) {
		ItemStack items[] = getGkitItems(player, gkit);
		for(ItemStack iStack : items) {
			if(iStack.getType() == item) {
				AManager.giveItem(player, iStack);
			}
		}
	}
	
	public void enchantItem(ItemStack item, InternalEnchantment enchantment) {
		AEAPI.applyEnchant(enchantment.getEnchantment(), enchantment.getLevel(), item);
	}
	
	public void lockItem(ItemStack item) {
		Locked.lock(item);
	}
	
	public AEExpansion() {
		if(Bukkit.getPluginManager().isPluginEnabled("AdvancedEnchantments")) {
			this.loaded = true;
			new Locked();
			registerAllEffects();
		}
	}

	public boolean isLoaded() {
		return this.loaded;
	}

	private static ItemStack[] getGkitItems(Player player, String string) {
		return GKitBuilder.build(string, player);
	}
	
	public void givePlayerLockedGkit(Player player, String string) {
		ItemStack[] items = GKitBuilder.build(string, player);
		for(ItemStack item : items) {
			Locked.lock(item);
			AManager.giveItem(player, item);
		}
	}


	public static GKitPreviewUI previewGKit(Player player, String string) {
		
		ItemStack[] gkitItems = getGkitItems(player, string);
		GKitPreviewUI ui = new GKitPreviewUI(player);
		for(ItemStack i : gkitItems) {
			if(i.getMaxStackSize() > 2) {
				ItemMeta meta = i.getItemMeta();
				if(meta == null) continue;
				meta.setItemName(ChatUtils.apply("&f&l" + meta.getItemName() + " &7(&cx" + i.getAmount() + "&7)"));
				meta.setEnchantmentGlintOverride(true);
				i.setItemMeta(meta);
				i.setAmount(1);
			}
			ui.addUnclickableItem(ui.getFirstClearSlot(), i);
		}
		
		return ui;
	}


}
