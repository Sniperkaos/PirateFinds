package me.cworldstar.piratefinds.impl.ae.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.events.TickerTickEvent;
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
	
	public boolean isPFItemSimilar() {
		return true;
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
			if(expend == false) {
				expend = item.checkExpendWithItem((Player) e.getWhoClicked(), e.getCurrentItem(), itemOnCursor);
			}
			PFItemUsed event = new PFItemUsed((Player) e.getWhoClicked(), e.getClickedInventory(), item, expend);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled()) {
				return;
			}
			
			item.onItemUse((Player) e.getWhoClicked(), e.getCurrentItem(), PFItemType.DRAG_AND_DROP, itemOnCursor); // they are not the same so it's OK to use this
			item.onItemUse((Player) e.getWhoClicked(), e.getCurrentItem(), PFItemType.DRAG_AND_DROP);
			
			
			Player player = (Player) e.getWhoClicked();
			if(expend) {
				player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 1.0F, 1.2F, 0);
				if(itemOnCursor.getAmount() == 1) {
					e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
				} else {
					itemOnCursor.setAmount(itemOnCursor.getAmount() - 1);
				}
				e.setCancelled(true);
			}
			else {
				e.setCancelled(true);
				player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0f, 0.6f);
			}			
		}
	}
	
	@EventHandler
	public void onTickerTick(TickerTickEvent e) {
		PirateFinds.getServerStatic().getOnlinePlayers().forEach((Player p) -> {
			Inventory inventory = p.getInventory();
			ItemStack[] contents = inventory.getContents();
			ArrayList<ItemStack> sanitized_contents = new ArrayList<ItemStack>(Arrays.asList(contents));
			sanitized_contents.removeIf(item -> item==null);
			sanitized_contents.forEach((ItemStack item) -> {
				AbstractPFItem pfitem = PFItemClass.getItem(item);
				if(pfitem != null && (pfitem.getType() == PFItemType.TICK || pfitem.getTypes().contains(PFItemType.TICK))) {
					pfitem.onItemUse(p, item, PFItemType.TICK);
				}
			});
		});
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		Item droppedItem = e.getItemDrop();
		ItemStack itemActual = droppedItem.getItemStack();
		AbstractPFItem item = PFItemClass.getItem(itemActual);
		if(item != null && item.getTypes().contains(PFItemType.DROP_ITEM)) {
			e.setCancelled(true);
			boolean expend = item.checkExpend(e.getPlayer(), itemActual);
			PFItemUsed event = new PFItemUsed(e.getPlayer(), e.getPlayer().getInventory(), item, expend);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled()) {
				return;
			}
			item.onItemUse(e.getPlayer(), itemActual, PFItemType.DROP_ITEM, e);
		}
	}
	
	@EventHandler
	public void onBucketUseEvent(PlayerBucketEmptyEvent e) {
		ItemStack maybeBucket = e.getPlayer().getInventory().getItem(e.getHand());
		AbstractPFItem item = PFItemClass.getItem(maybeBucket);
		if(item != null && 
				!(PFItemClass.compare(PFItemClass.nullItem(), item)) &&
				item.getType().equals(PFItemType.BUCKET_USED)
		) {
			boolean expend = item.checkExpend(e.getPlayer(), maybeBucket);
			PFItemUsed event = new PFItemUsed(e.getPlayer(), e.getPlayer().getInventory(), item, expend);
			Bukkit.getPluginManager().callEvent(event);
			
			if(event.isCancelled()) {
				return;
			}
			
			item.onItemUse(e.getPlayer(), maybeBucket, PFItemType.BUCKET_USED, e);
		}
	}
	
	@EventHandler
	public void onTryBlockPlace(BlockPlaceEvent e) {
		AbstractPFItem item = PFItemClass.getItem(e.getItemInHand());
		if(item != null && !(PFItemClass.compare(PFItemClass.nullItem(), item))) {
			e.setCancelled(true);
			if(item.getTypes().contains(PFItemType.BLOCK_PLACE)) {
				boolean expend = item.checkExpend(e.getPlayer(), e.getItemInHand());
				PFItemUsed event = new PFItemUsed(e.getPlayer(), e.getPlayer().getInventory(), item, expend);
				Bukkit.getPluginManager().callEvent(event);
				if(event.isCancelled()) {
					return;
				}
				item.onItemUse(e.getPlayer(), e.getItemInHand(), PFItemType.BLOCK_PLACE, e);
			}
		}
	}
	
	@EventHandler
	public void onRightClickItem(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR) {
			ItemStack itemInHand = e.getItem();
			if(itemInHand == null) return;
			AbstractPFItem item = PFItemClass.getItem(itemInHand);
			if(item != null && ((item.getTypes().contains(PFItemType.RIGHT_CLICK) || item.getTypes().contains(PFItemType.SHIFT_RIGHT_CLICK)) || item.getType().equals(PFItemType.RIGHT_CLICK))) {
				e.setCancelled(true); 
				
				Player player = e.getPlayer();
				
				boolean expend = item.checkExpend(e.getPlayer(), itemInHand);
				PFItemUsed event = new PFItemUsed(e.getPlayer(), e.getPlayer().getInventory(), item, expend);
				Bukkit.getPluginManager().callEvent(event);
				if(event.isCancelled()) {
					return;
				}
				if(e.getPlayer().isSneaking() && item.getTypes().contains(PFItemType.SHIFT_RIGHT_CLICK)) {
					item.onItemUse(e.getPlayer(), itemInHand, PFItemType.SHIFT_RIGHT_CLICK);
				} else {
					item.onItemUse(e.getPlayer(), itemInHand, PFItemType.RIGHT_CLICK);
				}
				
				if(expend) {
					player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 1.0F, 1.2F, 0);
					if(itemInHand.getAmount() > 1) {
						itemInHand.setAmount(itemInHand.getAmount() - 1);
					} else {
						player.getInventory().remove(itemInHand);
					}
				}
				else {
					player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0f, 0.6f);
				}	
				
			} 
		}
	}
}
	