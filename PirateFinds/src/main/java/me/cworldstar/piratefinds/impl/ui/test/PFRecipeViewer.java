package me.cworldstar.piratefinds.impl.ui.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.Crafting;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;
import me.cworldstar.piratefinds.impl.ui.PageLayout;
import me.cworldstar.piratefinds.impl.ui.PagedUIObject;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class PFRecipeViewer extends PagedUIObject {
	private static ItemStack ui_barrier = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
	private static ItemStack close_head = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2ZmZDA2OWE3YTBlYjhlMTQ5YWU3NjM1M2M1MGZjNjM4MzI5ZDI2NjI2MDgyNGFiMTFjMTY4MzEzZjViMGI4In19fQ==");
	private static ItemStack right_head = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkxYWM0MzJhYTQwZDdlN2E2ODdhYTg1MDQxZGU2MzY3MTJkNGYwMjI2MzJkZDUzNTZjODgwNTIxYWYyNzIzYSJ9fX0=");
	private static ItemStack left_head = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2EyYzEyY2IyMjkxODM4NGUwYTgxYzgyYTFlZDk5YWViZGNlOTRiMmVjMjc1NDgwMDk3MjMxOWI1NzkwMGFmYiJ9fX0=");
	private static ItemStack CRAFTING_HEAD = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTg5ZjFlODc2NGJlZWQ1ZTMzYTY4YjYxOTBhMDM0ODZiMWI0YjExYTNhNTkwNjg4Yzc1YTg5N2I5ZDEwZDk1In19fQ==");

	private static ItemStack IN_WORLD_HEAD = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTU3N2M0ZGUxZjUxYTcwNzIyMDIzZTg1NmI1NDNjZDU3MGYxZDBlZTZiOWQxNjdiNTkwMjhjZTFiYzkyZTQ1OCJ9fX0=");
	private static ItemStack MOB_DROP_HEAD = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGIwZTgxNTk2ODY1MzYxZDE3MjIxZjMxZTg5NzI0MmQyZWZlNjZiYWEyOWY2YzYwZTE1NDVmNmQ2ZTZlNGY2MiJ9fX0=");
	
	private static ItemStack air = new ItemStack(Material.BARRIER, 1);

	
	private static PageLayout DEFAULT_PAGE_LAYOUT = new PageLayout();
	
	static {
		
		ItemMeta inWorldMeta = IN_WORLD_HEAD.getItemMeta();
		inWorldMeta.setDisplayName(ChatUtils.apply("&a&lIn-World Recipe"));
		IN_WORLD_HEAD.setItemMeta(inWorldMeta);
		
		ItemMeta mobMeta = MOB_DROP_HEAD.getItemMeta();
		mobMeta.setDisplayName(ChatUtils.apply("&c&lMob Drop"));
		MOB_DROP_HEAD.setItemMeta(mobMeta);
		
		ItemMeta craftingMeta = CRAFTING_HEAD.getItemMeta();
		craftingMeta.setDisplayName(ChatUtils.apply("&6&lCrafting Recipe"));
		CRAFTING_HEAD.setItemMeta(craftingMeta);
		
		ItemMeta meta = ui_barrier.getItemMeta();
		meta.setDisplayName(" ");
		ui_barrier.setItemMeta(meta);
		
		ItemMeta airmeta = air.getItemMeta();
		airmeta.setDisplayName(ChatUtils.apply("&fEmpty"));
		air.setItemMeta(airmeta);
		
		ItemMeta c_meta = close_head.getItemMeta();
		c_meta.setItemName(ChatUtils.apply("&d&lClose"));
		close_head.setItemMeta(c_meta);
		
		ItemMeta r_meta = right_head.getItemMeta();
		r_meta.setItemName(ChatUtils.apply("&b&lNext Page"));
		right_head.setItemMeta(r_meta);
		
		ItemMeta l_meta = left_head.getItemMeta();
		l_meta.setItemName(ChatUtils.apply("&b&lLast Page"));
		left_head.setItemMeta(l_meta);
		
		
	}
	private static ArrayList<Integer> barrier_slots = new ArrayList<Integer>(); 
	
	static {
		int[] ints = new int[] {0,1,2,6,7,8,9,11,15,17,18,19,20,24,25,26,27,28,29,33,34,35};
		List<Integer> slots = Arrays.stream(ints).boxed().toList();
		barrier_slots.addAll(slots);
		
		DEFAULT_PAGE_LAYOUT.setRightItem(right_head);
		DEFAULT_PAGE_LAYOUT.setLeftItem(left_head);
		DEFAULT_PAGE_LAYOUT.setCloseItem(close_head);
		DEFAULT_PAGE_LAYOUT.setBarrierItem(ui_barrier);
		DEFAULT_PAGE_LAYOUT.addBarriers(barrier_slots);
		
	}
	
	public PFRecipeViewer(Player player) {
		super(player, InventorySize.MEDIUM_LARGE,"&6&lRecipe Viewer");
	}

	@Override
	public void decorateLayout(Inventory i) {

		DEFAULT_PAGE_LAYOUT.addBarriers(barrier_slots);
		DEFAULT_PAGE_LAYOUT.setBarrierItem(ui_barrier);
		
		List<CraftingRecipe> recipies = Crafting.allRecipies();		
		List<ShapedRecipe> shapedRecipes = recipies.stream().filter((recipe -> recipe instanceof ShapedRecipe)).map(e-> (ShapedRecipe) e).collect(Collectors.toList());
		
		for(ShapedRecipe recipe : shapedRecipes) {
			
			PageLayout layout = DEFAULT_PAGE_LAYOUT.clone();
			layout.setParent(this);
			layout.setSlots(32, 30, 31);
			this.addLayout(layout);
			
			layout.addUnclickableItem(16, recipe.getResult());
			layout.addUnclickableItem(10, CRAFTING_HEAD);

			
			Map<Character, ItemStack> key = recipe.getIngredientMap();
			String[] shape = recipe.getShape();
			PirateFinds.logDebug(Arrays.asList(shape).toString());
			PirateFinds.logDebug(key.toString());
			for(String s : shape) {	
				for(char c : s.toCharArray()) {
					if(c == ' ') {
						layout.addUnclickableItem(layout.firstEmpty(), air);
						continue;
					}
					int slot = layout.firstEmpty();
					if(slot == -1) continue;
					
					PirateFinds.logDebug(Integer.toString(slot));
					
					ItemStack item = key.get(c);
					layout.addUnclickableItem(slot, item);
					layout.addMenuClickHandler(slot, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
						ItemStack inSlot = layout.getItem(slot);
						// find the layout in which the item has a recipe for
						PageLayout to_go = this.findLayout(l->l.hasMeta(PFItemClass.getItem(inSlot).getPFItemID()));
						if(to_go != null) {
							this.displayLayout(to_go);
						} else {
							getOwner().sendMessage(ChatUtils.apply("&7This item does not have a registered recipe."));
						}
					}));
				}
			}
			
			layout.addMeta(PFItemClass.getItem(recipe.getResult()).getPFItemID());
			
		}
		
		// these are hardcoded, change later
		PageLayout wardeneye = DEFAULT_PAGE_LAYOUT.clone();
		wardeneye.setParent(this);
		wardeneye.setSlots(32, 30, 31);
		this.addLayout(wardeneye);
		
		ItemStack wardenEyeItem = PFItemClass.getItem("WARDEN_EYE").getPFItem();
		
		wardeneye.addMeta(PFItemClass.getItem("WARDEN_EYE").getPFItemID());
		wardeneye.addUnclickableItem(16, wardenEyeItem);
		wardeneye.addUnclickableItem(10, MOB_DROP_HEAD);
		
		// ender wing
		PageLayout enderwing = DEFAULT_PAGE_LAYOUT.clone();
		enderwing.setParent(this);
		enderwing.setSlots(32, 30, 31);
		this.addLayout(enderwing);
		
		ItemStack enderwingItem = PFItemClass.getItem("ENDER_WING").getPFItem();
		
		enderwing.addMeta(PFItemClass.getItem("ENDER_WING").getPFItemID());
		enderwing.addUnclickableItem(16, enderwingItem);
		enderwing.addUnclickableItem(10, MOB_DROP_HEAD);
		
		
	}




}
