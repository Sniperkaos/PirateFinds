package me.cworldstar.piratefinds.impl.ui.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.Deals;
import me.cworldstar.piratefinds.impl.EnchantmentDealer;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ae.items.items.ExperienceCrystal;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.InventoryUtils;
import me.cworldstar.piratefinds.impl.vault.VaultImpl;
import net.advancedplugins.ae.impl.utils.SkullCreator;
import net.milkbowl.vault.economy.Economy;

public class EnchantmentShardSellerUI extends BaseUIObject {

	public static ItemStack UI_BARRIER = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
	public static ItemStack UI_DESC_HEAD = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGExNjAzOGJjOGU2NTE4YWZhOTE0OThkYWI3Njc1YzAxY2IzMWExMjVkMjFjNDliODYxMjk0ZDM5ZTFjNTYwYyJ9fX0=");
	public static ItemStack UI_SHARD_BUY = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjdmOGQ4YzM5Zjk5MjFhNjRkN2I3NTVkZTMyMmU0NDFmZGVkNGI3NzVhNzY1N2UwYjMxOTZiNmZkYTg2MzY0NiJ9fX0=");
	
	static {
		ItemMeta meta = UI_BARRIER.getItemMeta();
		meta.setItemName(" ");
		UI_BARRIER.setItemMeta(meta);
		
		ItemMeta meta2 = UI_DESC_HEAD.getItemMeta();
		meta2.setItemName(ChatUtils.apply("&f&lInformation"));
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("&fThe shard seller refreshes every day.");
		lore.add("&fIf you sell out, don't worry!");
		lore.add("");
		lore.add("&7[&c&l!&7] &cIf you do not have a &fHoly White Scroll");
		lore.add("&cattached to your &fItem&c, if the shard fails enough,");
		lore.add("&cit will be destroyed!");
		meta2.setLore(ChatUtils.apply(lore));
		UI_DESC_HEAD.setItemMeta(meta2);
		
		ItemMeta meta3 = UI_SHARD_BUY.getItemMeta();
		meta3.setItemName(ChatUtils.apply("&e&lPurchase Shard"));
		List<String> lore2 = new ArrayList<String>();
		lore2.add("");
		lore2.add("&fCost: &e$50,000");
		meta3.setLore(ChatUtils.apply(lore2));
		UI_SHARD_BUY.setItemMeta(meta3);
		
	}
	
	
	public EnchantmentShardSellerUI(Player player) {
		super(player, InventorySize.MEDIUM);
	}
	
	private static ArrayList<Integer> barrier_slots = new ArrayList<Integer>();

	static {
		int[] ints = new int[] {0,1,2,3,4,5,6,7,8,9,17,18,26};
		List<Integer> slots = Arrays.stream(ints).boxed().toList();
		barrier_slots.addAll(slots);	
	}
	
	private boolean checkCurrency(InventoryClickEvent e, int price) {
		
		// find an item stack resembling the PFItem ExperienceCrystal.
		Inventory i = this.getOwner().getInventory();
		Map<Integer, ItemStack> currency = new HashMap<Integer, ItemStack>();
		int total_amount = 0;
		int slot = 0;
		for(ItemStack item : i.getContents()) {
			if(item == null) {
				slot += 1;
				continue;
			}
			
			if(PFItemClass.getItem(item) instanceof ExperienceCrystal) {
				currency.put(slot, item);
				total_amount += item.getAmount();
			}
			
			slot += 1;
		}
		
		return total_amount > price;
	}
	
	private void removeCurrency(InventoryClickEvent e, int price) {
		
		// find an item stack resembling the PFItem ExperienceCrystal.
		Inventory i = this.getOwner().getInventory();
		int total_amount = price;
		for(ItemStack item : i.getContents()) {
			if(item == null) {
				continue;
			}
			
			if(PFItemClass.getItem(item) instanceof ExperienceCrystal) {
				
				if(total_amount <= 0) {
					continue;
				}
				
				int toRemove = total_amount;
				if(toRemove > item.getAmount()) {
					toRemove = item.getAmount();
				}
				
				item.setAmount(item.getAmount() - toRemove);
				total_amount -= toRemove;
			}
			
		}
	}
	
	@Override
	protected void decorate(Inventory i) {
		barrier_slots.forEach((Integer slot) -> {
			this.addUnclickableItem(slot, UI_BARRIER);
		});
		
		this.addUnclickableItem(20, UI_DESC_HEAD);
		this.addUnclickableItem(24, UI_SHARD_BUY);
		this.addMenuClickHandler(24, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			Optional<Economy> econ = VaultImpl.getEconomy();
			if(econ.isPresent()) {
				Player p = getOwner();
				Economy econ2 = econ.get();
				if(!econ2.has(getOwner(), 5000)) {
					p.sendMessage(ChatUtils.apply("&7[ &c&l! &7] &cYou do not have enough currency to complete this transaction!"));
					return;
				}
				
				econ2.withdrawPlayer(p, 5000);
				InventoryUtils.addOrDropItem(p.getInventory(), PFItemClass.getItem("experience_crystal").build());
				
			}
		}));
		
		
		Deals deals = EnchantmentDealer.getDeals(this.getOwner());
		for(Entry<ItemStack, Integer> deal : deals.toEntrySet()) {
			int slot = this.getFirstClearSlot();
			ItemStack built = deal.getKey();
			
			ItemStack cloned = built.clone();
			ItemMeta meta = cloned.getItemMeta();
			List<String> lore = meta.getLore();
			lore.add(" ");
			lore.add(ChatUtils.apply("&a&l-=-=-=-= Experience Crystal Cost: " + Integer.toString(deal.getValue()) + " &a&l-=-=-=-="));
			meta.setLore(lore);
			cloned.setItemMeta(meta);
			
			int price = deal.getValue();
			this.addUnclickableItem(slot, cloned);
			this.addEmptyClickHandler(new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
				e.getWhoClicked().sendMessage(ChatUtils.apply("&c&lYou cannot put items into this inventory!"));
				e.setCancelled(true);
			}));
			this.addMenuClickHandler(slot, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
				boolean currency_check = checkCurrency(e, price);
				Player p = getOwner();

				if(currency_check) {
					
					removeCurrency(e, price);
					this.getInventory().remove(cloned);
					InventoryUtils.addOrDropItem(getOwner().getInventory(), built);
					deals.close(deal);
					p.playSound(p, Sound.ENTITY_VILLAGER_TRADE, 0.8F, 0.4F);
				} else {
					p.sendMessage(ChatUtils.apply("&7[ &c&l! &7] &cYou do not have enough currency to complete this transaction!"));
					p.playSound(p, Sound.ENTITY_VILLAGER_NO, 0.8F, 0.4F);
				}
			}));
		}
		
	}

}
