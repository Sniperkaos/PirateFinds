package me.cworldstar.piratefinds.impl.ae.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.api.EnchantApplyEvent;
import net.advancedplugins.ae.api.ItemApplyEvent;
import net.advancedplugins.ae.handlers.anvil.AnvilEvent;
import net.md_5.bungee.api.ChatColor;

public class Locked implements Listener {

	public static final String LOCKED_LORE_LINE = ChatColor.translateAlternateColorCodes('&', PirateFinds.getThisPlugin().getConfig().getString("ae.locked.locked_lore_line"));
	public static final String LOCKED_MESSAGE = ChatColor.translateAlternateColorCodes('&', PirateFinds.getThisPlugin().getConfig().getString("ae.locked.locked_message"));
	 
	public static void lock(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if(meta == null) {
			return;
		}
		
		List<String> lore = meta.getLore();
		if(lore == null) {
			lore = new ArrayList<String>();
		}
		
		lore.add(LOCKED_LORE_LINE);
		
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	public static boolean isItemLocked(ItemStack i) {
		
		ItemMeta meta = i.getItemMeta();
		if(meta == null) {
			return false;
		}
		
		List<String> lore = meta.getLore();
		if(lore == null) {
			return false;
		}
		for(String lore_line : lore.toArray(new String[0])) {
			if(lore_line.contains(LOCKED_LORE_LINE)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public static void unlock(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if(meta == null) {
			return;
		}
		
		int index = 0;
		List<String> lore = meta.getLore();
		if(lore == null) return;
		for(String lore_line : lore.toArray(new String[0])) {
			if(lore_line.contains(LOCKED_LORE_LINE)) {
				lore.remove(index);
				break;
			}
			index++;
		}
		
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	public Locked() {
		PirateFinds.getThisPlugin().getServer().getPluginManager().registerEvents(this, PirateFinds.getThisPlugin());
	}


	@EventHandler
	public void onAnvilUse(InventoryClickEvent e) {
		if(e.getClickedInventory() == null) return;
		if(!(e.getClickedInventory().getType() == InventoryType.ANVIL)) return;
		if(e.getSlotType() == SlotType.CRAFTING) {
			if(Locked.isItemLocked(e.getCurrentItem())) {
				e.getWhoClicked().sendMessage(ChatUtils.createBroadcast(LOCKED_MESSAGE));
				e.setCancelled(true);
			}
		}
		
	}
	
	
	@EventHandler
	public void onEnchantApply(EnchantApplyEvent e) {
		
		if(e.isCancelled()) return;
		
		ItemStack item = e.getItemStack();
		ItemMeta meta = item.getItemMeta();
		if(meta == null) {
			return;
		}
		
		List<String> lore = meta.getLore();
		if(lore == null) {
			return;
		}
		for(String lore_line : lore.toArray(new String[0])) {
			if(lore_line.contains(LOCKED_LORE_LINE)) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(LOCKED_MESSAGE);
				break;
			}
		}
	}
	
	@EventHandler
	public void onBlackScroll(ItemApplyEvent e) {
		if(isItemLocked(e.getItemStack())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(LOCKED_MESSAGE);
		}
	}
	
}
