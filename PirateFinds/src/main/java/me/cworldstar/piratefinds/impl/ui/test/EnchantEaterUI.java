package me.cworldstar.piratefinds.impl.ui.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.EnchantEater;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.api.AEAPI;
import net.advancedplugins.ae.impl.utils.EntityHead;
import net.advancedplugins.ae.impl.utils.SkullCreator;
import net.md_5.bungee.api.ChatColor;

public class EnchantEaterUI extends BaseUIObject {
	
	private static ItemStack ui_barrier = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
	
	private static ItemStack HEAD_ALLOW = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNiNzJkNWY3MGI1ZDJhMjJiNDljMDIxZTc5MjgyMTJiYmE0MDI0MTAzZmY2OTk1MjcyOGI0YzIwMTJhM2M0OSJ9fX0=");
	
	static {
		ItemMeta meta = ui_barrier.getItemMeta();
		meta.setItemName(" ");
		
		ui_barrier.setItemMeta(meta);
		
		ItemMeta HeadMeta = HEAD_ALLOW.getItemMeta();
		HeadMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lEAT ENCHANTS"));
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatUtils.apply("&cPrice: &a&l%price%&r"));
		HeadMeta.setLore(lore);
		
		HEAD_ALLOW.setItemMeta(HeadMeta);
		
	}
	
	public EnchantEaterUI(Player p) {
		super(p, InventorySize.MEDIUM, "Enchant Eater");
	}
	
	private final int PER_ENCHANTMENT_LEVEL = 3;
	private final int PER_CUSTOM_ENCHANTMENT_LEVEL = 6;
	
	private void makePrice(ItemStack i) {
		int price = 0;
		HashMap<String, Integer> enchants = AEAPI.getEnchantmentsOnItem(i);
		for(Entry<String, Integer> enchant : enchants.entrySet()) {
			price += (PER_CUSTOM_ENCHANTMENT_LEVEL * enchant.getValue());
		}
		
		ItemMeta meta = i.getItemMeta();
		if(meta == null) {
			this.price = price;
			return;
		}
		Map<Enchantment, Integer> normal_enchants = meta.getEnchants();
		for(Entry<Enchantment, Integer> n_enchant : normal_enchants.entrySet()) {
			price += (PER_ENCHANTMENT_LEVEL * n_enchant.getValue());
		}
		
		this.price = price;
	}
	
	private ItemStack updateHeadItem(ItemStack head, int price) {
		ItemStack newhead = head.clone();
		ItemMeta HeadMeta = newhead.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>(HeadMeta.getLore());
		lore.replaceAll(line -> line.replace("%price%", Integer.toString(price)));
		HeadMeta.setLore(lore);
		newhead.setItemMeta(HeadMeta);
		
		return newhead;
	}

	private static ArrayList<Integer> barrier_slots = new ArrayList<Integer>(); 
	private int price;
	
	static {
		int[] ints = new int[] {0,1,2,3,4,5,6,7,8,9,10,11,15,16,17,18,19,20,21,23,24,25,26};
		List<Integer> slots = Arrays.stream(ints).boxed().toList();
		barrier_slots.addAll(slots);
	}

	@Override
	protected void decorate(Inventory i) {
		barrier_slots.forEach((Integer slot) -> {
			this.addUnclickableItem(slot, ui_barrier);
		});
		
		this.addUnclickableItem(12, new ItemStack(Material.PINK_STAINED_GLASS_PANE));
		this.addUnclickableItem(14, new ItemStack(Material.PINK_STAINED_GLASS_PANE));
		
		this.setItem(22, HEAD_ALLOW);
		this.addInsertableSlot(13);
		
		this.addMenuClickHandler(22, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			Player p = (Player) e.getWhoClicked();
			if(e.getClickedInventory().getItem(13) == null) {
					p.sendMessage(ChatUtils.apply("&7[ &d&lEnchant Eater &7]: You didn't give me an item."));
					p.playSound(p, Sound.ENTITY_VILLAGER_NO, 0.8F, 0.4F);
				return;
			}
			
			if(price == 0) {
				p.sendMessage("EnchantEater Error: Price of item is 0, this is impossible! Try re-inserting the item without shift-clicking.");
				return;
			}
			
			if(p.getLevel() < price) {
				p.sendMessage(ChatUtils.apply("&7[ &d&lEnchant Eater &7]: You do not have enough experience levels."));
				p.playSound(p, Sound.ENTITY_VILLAGER_NO, 0.8F, 0.4F);
				return;
			}
			
			if(e.getClickedInventory().getItem(13).getType() == Material.ENCHANTED_BOOK) {
				p.sendMessage(ChatUtils.apply("&7[ &d&lEnchant Eater &7]: I am not able to disenchant this."));
				p.playSound(p, Sound.ENTITY_VILLAGER_NO, 0.8F, 0.4F);
				return;
			}
			
			p.setLevel(p.getLevel() - this.price);
			EnchantEater.eatEnchantments((Player) e.getWhoClicked(), e.getClickedInventory().getItem(13));
			this.close();
		}));
		
		this.addMenuCloseHandler(new MenuHandler<InventoryCloseEvent>((InventoryCloseEvent e) -> {
			if(e.getInventory().getItem(13) == null) return;
			e.getPlayer().getInventory().addItem(this.getInventory().getItem(13));
		}));
		
		EnchantEaterUI self = this;
		
		this.addInsertHandler(13, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			makePrice(e.getCursor());
			self.setItem(22, updateHeadItem(HEAD_ALLOW, price));
		}));
		
	}

	
}
