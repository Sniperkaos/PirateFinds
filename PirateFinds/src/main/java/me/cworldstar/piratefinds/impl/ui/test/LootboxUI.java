package me.cworldstar.piratefinds.impl.ui.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.lootbox.LootboxReward;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.InventoryUtils;
import me.cworldstar.piratefinds.impl.utils.WeightedRandom;
import net.advancedplugins.ae.impl.utils.SkullCreator;
/**
 * 
 * LootboxUI extends BaseUIObject.
 * Instanced, so don't worry about not using this variables.
 * Instead, use 
 * {@code 
 * 	new LootboxUI(Player p, Integer int, ArrayList<LootboxReward<?>> rewards).open()
 *  or 
 *  this.open(Material material)} method
 * @see LootboxReward
 * @see MenuHandler
 * @author cw
 *
 */
public class LootboxUI extends BaseUIObject {

	private static ItemStack ui_barrier = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
	private static ItemStack close_head = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2ZmZDA2OWE3YTBlYjhlMTQ5YWU3NjM1M2M1MGZjNjM4MzI5ZDI2NjI2MDgyNGFiMTFjMTY4MzEzZjViMGI4In19fQ==");
	private static ItemStack item_finished = new ItemStack(Material.BARRIER, 1);
	private static ItemStack chest_item = new ItemStack(Material.CHEST, 1);
	private static ItemStack even_glass = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
	private static ItemStack odd_glass = new ItemStack(Material.RED_STAINED_GLASS_PANE);
	
	static {
		ItemMeta meta = ui_barrier.getItemMeta();
		meta.setDisplayName(" ");
		ui_barrier.setItemMeta(meta);
		even_glass.setItemMeta(meta);
		odd_glass.setItemMeta(meta);
		//---------------------------------------
		ItemMeta ch_meta = chest_item.getItemMeta();
		ch_meta.setDisplayName(ChatUtils.apply("&c&lReward"));
		ch_meta.setEnchantmentGlintOverride(true);;
		chest_item.setItemMeta(ch_meta);
		//---------------------------------------
		ItemMeta c_meta = close_head.getItemMeta();
		c_meta.setDisplayName(ChatUtils.apply("&c&lLeft-Click to Close"));
		close_head.setItemMeta(c_meta);
		//---------------------------------------
		ItemMeta f_meta = item_finished.getItemMeta();
		f_meta.setDisplayName(ChatUtils.apply("&c&lThis slot has been used!"));
		item_finished.setItemMeta(f_meta);
		//---------------------------------------
	}
	
	
	private int used_clicks = 0;
	private int max_clicks;
	private ItemStack item;
	private ArrayList<LootboxReward<?>> rewards = new ArrayList<LootboxReward<?>>();
	private HashMap<String, Integer> integer_properties = new HashMap<String, Integer>();
	private BukkitTask animate_ending_task;
	
	public LootboxUI(Player player, int max_clicks, ArrayList<LootboxReward<?>> rewards, ItemStack item) {
		super(player, InventorySize.LARGE);
		this.max_clicks = max_clicks;
		this.rewards = rewards;
		this.item = item;
	}
	
	public LootboxUI(Player player, int max_clicks, ArrayList<LootboxReward<?>> rewards, ItemStack item, InventorySize size) {
		super(player, size);
		this.max_clicks = max_clicks;
		this.rewards = rewards;
		this.item = item;
	}

	private static ArrayList<Integer> barrier_slots = new ArrayList<Integer>(); 
	private static ArrayList<Integer> chest_slots = new ArrayList<Integer>(); 
	
	
	public ArrayList<Integer> getBarrierSlots() {
		return LootboxUI.barrier_slots;
	}
	
	public void createProperty(String id, int property) {
		this.integer_properties.putIfAbsent(id, property);
	}
	
	public int getProperty(String id) {
		return this.integer_properties.get(id);
	}
	
	public void increaseProperty(String id) {
		this.integer_properties.put(id, this.integer_properties.get(id) + 1);
	}
	
	public ArrayList<Integer> getChestSlots() {
		return LootboxUI.chest_slots;
	}
	
	static {
		int[] ints = new int[] {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,41,42,43,44};
		int[] chest_ints = new int[] {10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34};
		List<Integer> slots = Arrays.stream(ints).boxed().toList();
		List<Integer> c_slots = Arrays.stream(chest_ints).boxed().toList();
		barrier_slots.addAll(slots);	
		chest_slots.addAll(c_slots);
	}
	
	
	private ArrayList<Integer> used_slots = new ArrayList<Integer>();
	
	@Override
	protected void decorate(Inventory i) {
		
		barrier_slots.forEach((Integer slot) -> {
			this.addUnclickableItem(slot, ui_barrier);
		});
		
		chest_slots.forEach((Integer slot) -> {
			this.addUnclickableItem(slot, chest_item);
			this.addMenuClickHandler(slot, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
				
				Player player = (Player) e.getWhoClicked();
				
				if(used_slots.contains(slot)) return;
				used_slots.add(slot);
				
				if(used_clicks >= max_clicks) {
					player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1.0f, 0.2f);
					player.sendMessage(ChatUtils.createBroadcast("&7You are out of reward chests. Please exit the menu."));
					return;
				}
				
				PirateFinds.log("Player: " + player.getName());
				if(!e.getCurrentItem().isSimilar(chest_item)) {
					return;
				}
				
				
				WeightedRandom<LootboxReward<?>> random = new WeightedRandom<LootboxReward<?>>();
				for(LootboxReward<?> iter : rewards.toArray(new LootboxReward<?>[0])) {
					random.add(iter, iter.getChance());
				}
				LootboxReward<?> reward = random.resolve();
				reward.awardTo((Player) e.getWhoClicked());
				player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.7f);
				
				ItemStack rewardPlaceholder = reward.getPlaceholder();
				if(rewardPlaceholder != null) {
					this.setItem(slot, rewardPlaceholder);
				} else {
					this.setItem(slot, item_finished);
				}
				
				used_clicks++;
				if(used_clicks >= max_clicks) {
					player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0f, 1.2f);
					new BukkitRunnable() {
						@Override
						public void run() {
							player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0f, 1.2f);
						}
					}.runTaskLater(PirateFinds.getThisPlugin(), 30);
					animate_ending_task = 
					new BukkitRunnable() {
						boolean even = true;
						@Override
						public void run() {
							chest_slots.forEach((Integer slot) -> {
								if(slot % 2 == 0) {
									if(even) {
										LootboxUI.this.setItem(slot, even_glass);
									} else {
										LootboxUI.this.setItem(slot, odd_glass);
									}

								} else {
									if(even) {
										LootboxUI.this.setItem(slot, odd_glass);
									} else {
										LootboxUI.this.setItem(slot, even_glass);
									}
								}

							});
							if(even) {
								even = false;
							} else {
								even = true;
							}
						}
					}.runTaskTimer(PirateFinds.getThisPlugin(), 0L, 10L);
					
					player.sendMessage(ChatUtils.createBroadcast("&7You are out of reward chests. Please exit the menu."));
				}
			}));
		});
		
		this.addMenuCloseHandler(new MenuHandler<InventoryCloseEvent>((InventoryCloseEvent e) -> {
			if(used_clicks >= max_clicks) {
				this.animate_ending_task.cancel();
				((Player) e.getPlayer()).playSound(e.getPlayer(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 1.2f);
				int thisItemSlot = InventoryUtils.locateMutableStack(e.getPlayer().getInventory(), PFItemClass.getItem(item));
				if(thisItemSlot == -1) {
					return;
				}
				ItemStack thisItem = e.getPlayer().getInventory().getItem(thisItemSlot);
				if(thisItem.getAmount() > 1) {
					thisItem.setAmount(thisItem.getAmount() - 1);
				} else {
					e.getPlayer().getInventory().clear(thisItemSlot);
				}
			} else {
				this.okay_to_close = false;
			}
		}));
		
		this.disableDrop(item);
		
		
		this.setItem(40, close_head);
		this.addMenuClickHandler(40, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			
			if(e.getCursor() != null) {
				e.setCancelled(true);
				e.getWhoClicked().sendMessage(ChatUtils.createBroadcast("You may not edit the contents of this slot."));
				return;
			}
			
			if(used_clicks >= max_clicks) {
				this.close();
			} else {
				e.getWhoClicked().sendMessage(ChatUtils.createBroadcast("&7You have not opened every chest."));
			}

		}));
		
		
		
	}

	public void open(Material material) {
		this.open();
	}

}
