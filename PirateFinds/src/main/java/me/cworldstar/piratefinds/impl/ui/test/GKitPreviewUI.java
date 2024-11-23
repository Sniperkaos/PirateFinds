package me.cworldstar.piratefinds.impl.ui.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class GKitPreviewUI extends BaseUIObject {
	private static ItemStack ui_barrier = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
	private static ItemStack close_head = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2ZmZDA2OWE3YTBlYjhlMTQ5YWU3NjM1M2M1MGZjNjM4MzI5ZDI2NjI2MDgyNGFiMTFjMTY4MzEzZjViMGI4In19fQ==");
	
	static {
		ItemMeta meta = ui_barrier.getItemMeta();
		meta.setDisplayName(" ");
		
		ui_barrier.setItemMeta(meta);
		
		ItemMeta c_meta = close_head.getItemMeta();
		c_meta.setDisplayName(ChatUtils.apply("&c&lLeft-Click to Close"));
		
		close_head.setItemMeta(meta);
		
	}
	private static ArrayList<Integer> barrier_slots = new ArrayList<Integer>(); 
	
	static {
		int[] ints = new int[] {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,41,42,43,44};
		List<Integer> slots = Arrays.stream(ints).boxed().toList();
		barrier_slots.addAll(slots);
	}
	
	public GKitPreviewUI(Player player) {
		super(player, InventorySize.LARGE,"&7&l[ &c&lPirateFinds GKit Previewer &7&l]");
	}

	@Override
	protected void decorate(Inventory i) {
		barrier_slots.forEach((Integer slot) -> {
			this.addUnclickableItem(slot, ui_barrier);
		});
		
		this.setItem(40, close_head);
		
		this.addMenuClickHandler(40, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			this.close();
			PirateFinds.getServerStatic().dispatchCommand(PirateFinds.getServerStatic().getConsoleSender(), "/dm open gkits " + e.getWhoClicked().getName());
		}));
	}


}
