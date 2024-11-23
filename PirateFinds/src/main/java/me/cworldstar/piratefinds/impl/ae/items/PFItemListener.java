package me.cworldstar.piratefinds.impl.ae.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.events.PFItemUsed;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem.PFItemType;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

/**
 * 
 * 
 * This is the Listener for all {@link AbstractPFItem}s.
 * You can cancel usage of items by listening to
 * the {@link PFItemUsed} event, and using the cancel()
 * method.
 * 
 * @author cw
 *
 */

public class PFItemListener implements Listener {
	public static PFItemClass itemClass = new PFItemClass();
	
	public PFItemListener() {
		PirateFinds.registerListener(this);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onInventoryClick(InventoryClickEvent e) {
		ItemStack itemOnCursor = e.getCursor();
		if(itemOnCursor == null) return;
		AbstractPFItem item = PFItemClass.getItem(itemOnCursor);
		if(item != null && item.getType() == PFItemType.DRAG_AND_DROP) {
			
			if(e.getCurrentItem() == null) {
				return;
			}
			
			if(e.getCurrentItem().getItemMeta() == null) {
				return;
			}
			
			if(e.getCurrentItem() != null && PFItemClass.getItem(e.getCurrentItem()) == null) {
				e.setCancelled(true);
			}
			
			boolean expend = item.checkExpend((Player) e.getWhoClicked(), e.getCurrentItem());
			PFItemUsed event = new PFItemUsed((Player) e.getWhoClicked(), e.getClickedInventory(), item, expend);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled()) {
				return;
			}
			
			item.onItemUse((Player) e.getWhoClicked(), e.getCurrentItem());
			
			Player player = (Player) e.getWhoClicked();
			if(expend) {
				player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 1.0F, 1.2F, 0);
				if(itemOnCursor.getAmount() == 1) {
					e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
				} else {
					itemOnCursor.setAmount(itemOnCursor.getAmount() - 1);
				}
			}
			else {
				player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0f, 0.6f);
			}
			

			
		}
	}
	
	@EventHandler
	public void onRightClickItem(PlayerInteractEvent e) {
		ItemStack itemInHand = e.getItem();
		if(itemInHand == null) return;
		AbstractPFItem item = PFItemClass.getItem(itemInHand);
		if(item != null && item.getType() == PFItemType.RIGHT_CLICK) {
			PirateFinds.log("RightClickItem got item " + item.build().getItemMeta().getItemName());
			e.setCancelled(true);
			boolean expend = item.checkExpend(e.getPlayer(), itemInHand);
			PFItemUsed event = new PFItemUsed(e.getPlayer(), e.getPlayer().getInventory(), item, expend);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled()) {
				return;
			}
			item.onItemUse(e.getPlayer(), itemInHand);
		}
	}
}
	