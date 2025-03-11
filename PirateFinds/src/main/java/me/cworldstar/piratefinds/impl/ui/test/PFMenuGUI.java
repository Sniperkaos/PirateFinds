package me.cworldstar.piratefinds.impl.ui.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;
import me.cworldstar.piratefinds.impl.ui.PageLayout;
import me.cworldstar.piratefinds.impl.ui.PagedUIObject;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class PFMenuGUI extends PagedUIObject {

	private static ItemStack ui_barrier = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
	private static ItemStack close_head = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2ZmZDA2OWE3YTBlYjhlMTQ5YWU3NjM1M2M1MGZjNjM4MzI5ZDI2NjI2MDgyNGFiMTFjMTY4MzEzZjViMGI4In19fQ==");
	private static ItemStack info_head = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGIzNTAxOGM3MTUzOTI3NTA4OWMyNjhhMTk3OGEzMDc5N2YwOThiYzEzYzcxZjVmY2RlZWIwZTFmOGEyMDVjMCJ9fX0=");

	private static ItemStack right_head = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkxYWM0MzJhYTQwZDdlN2E2ODdhYTg1MDQxZGU2MzY3MTJkNGYwMjI2MzJkZDUzNTZjODgwNTIxYWYyNzIzYSJ9fX0=");
	private static ItemStack left_head = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2EyYzEyY2IyMjkxODM4NGUwYTgxYzgyYTFlZDk5YWViZGNlOTRiMmVjMjc1NDgwMDk3MjMxOWI1NzkwMGFmYiJ9fX0=");
	
	private static PageLayout DEFAULT_PAGE_LAYOUT = new PageLayout();
	
	static {
		ItemMeta meta = ui_barrier.getItemMeta();
		meta.setDisplayName(" ");
		ui_barrier.setItemMeta(meta);
		
		ItemMeta c_meta = close_head.getItemMeta();
		c_meta.setItemName(ChatUtils.apply("&d&lClose"));
		close_head.setItemMeta(c_meta);
		
		ItemMeta r_meta = right_head.getItemMeta();
		r_meta.setItemName(ChatUtils.apply("&b&lNext Page"));
		right_head.setItemMeta(r_meta);
		
		ItemMeta l_meta = left_head.getItemMeta();
		l_meta.setItemName(ChatUtils.apply("&b&lLast Page"));
		left_head.setItemMeta(l_meta);
		
		ItemMeta i_meta = info_head.getItemMeta();
		i_meta.setItemName(ChatUtils.apply("&f&lInformation"));
		info_head.setItemMeta(i_meta);
	}
	
	private static ArrayList<Integer> barrier_slots = new ArrayList<Integer>(); 
	
	static {
		int[] ints = new int[] {0,1,2,3,4,5,6,7,8,18,19,20,21,23,24,25,26};
		List<Integer> slots = Arrays.stream(ints).boxed().toList();
		barrier_slots.addAll(slots);
		
		DEFAULT_PAGE_LAYOUT.setRightItem(right_head);
		DEFAULT_PAGE_LAYOUT.setLeftItem(left_head);
		DEFAULT_PAGE_LAYOUT.setCloseItem(close_head);
		DEFAULT_PAGE_LAYOUT.setBarrierItem(ui_barrier);
		DEFAULT_PAGE_LAYOUT.addBarriers(barrier_slots);
		
	}
	
	public PFMenuGUI(Player player) {
		super(player, InventorySize.MEDIUM, "&7&l[ &c&lMain Menu &7&l]");
	}

	@Override
	public void decorateLayout(Inventory i) {
		DEFAULT_PAGE_LAYOUT.addBarriers(barrier_slots);
		DEFAULT_PAGE_LAYOUT.setBarrierItem(ui_barrier);
		
		PageLayout layout1 = DEFAULT_PAGE_LAYOUT.clone();
		layout1.setParent(this);
		layout1.setSlots(17, 9, 22);
		
		
		layout1.addUnclickableItem(13, info_head);
		layout1.addMenuClickHandler(13, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			e.getWhoClicked().sendMessage(ChatUtils.apply("TODO: Finish information."));
		})) ;
		
		layout1.addUnclickableItem(14, info_head);
		layout1.addMenuClickHandler(14, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			e.getWhoClicked().sendMessage(ChatUtils.apply("TODO: Finish information."));
		})) ;
		
		layout1.addUnclickableItem(12, info_head);
		layout1.addMenuClickHandler(12, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			e.getWhoClicked().sendMessage(ChatUtils.apply("TODO: Finish information."));
		})) ;
		
		this.addLayout(layout1);
		
		for(Entry<String, CommandConsumer<CommandSender>> commands : PirateFinds.getPFCommandsClass().getMainCommand().getCommands().entrySet()) {
			
			boolean shouldCreate = shouldCreate(this.getOwner(), commands.getValue());
			
			if(!shouldCreate) {
				continue;
			}
			
			int firstClear = layout1.firstEmpty();
			if(firstClear == -1) {
				layout1 = DEFAULT_PAGE_LAYOUT.clone();
				layout1.setParent(this);
				layout1.setSlots(17, 9, 22);
				layout1.addUnclickableItem(layout1.firstEmpty(), createCommandItem(commands.getValue()));
				this.addLayout(layout1);
				continue;
			}
			
			layout1.addUnclickableItem(firstClear, createCommandItem(commands.getValue()));
		}
		
		
	}

	private boolean shouldCreate(Player owner, CommandConsumer<CommandSender> value) {
		if(value.hasPermission(this.getOwner()) && !value.hide) {
			return true;
		}
		return false;
	}

	private ItemStack createCommandItem(CommandConsumer<CommandSender> value) {
		ItemStack base = new ItemStack(Material.BOOK);
		ItemMeta meta = base.getItemMeta();
		meta.setItemName(ChatUtils.apply("&6&l" + value.getClass().getSimpleName()));
		meta.setDisplayName(meta.getItemName());
		meta.setLore(Arrays.asList(new String[] {value.help()}));
		base.setItemMeta(meta);
		
		return base;
	}

}
