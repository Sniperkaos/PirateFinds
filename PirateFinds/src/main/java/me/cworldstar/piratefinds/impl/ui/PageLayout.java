package me.cworldstar.piratefinds.impl.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PageLayout {

	
	private Map<Integer, ArrayList<MenuHandler<InventoryClickEvent>>> shiftHandlers = new HashMap<Integer, ArrayList<MenuHandler<InventoryClickEvent>>>();
	private Map<Integer, ArrayList<MenuHandler<InventoryClickEvent>>> handlers = new HashMap<Integer, ArrayList<MenuHandler<InventoryClickEvent>>>();
	private Map<Integer, ItemStack> layout = new HashMap<Integer, ItemStack>();
	
	
	private PagedUIObject parent;
	private ItemStack leftItem;
	private Inventory inventory;
	private ItemStack rightItem;
	private ItemStack closeItem;
	private ItemStack barrierItem;
	
	private int rightSlot;
	private int leftSlot;
	private int closeSlot;
	
	private List<Integer> barrier_slots = new ArrayList<Integer>();
	
	public void setSlots(int right_slot, int left_slot, int close_slot) {
		this.rightSlot = right_slot;
		this.leftSlot = left_slot;
		this.closeSlot = close_slot;
		
		this.addMenuClickHandler(rightSlot, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			e.setCancelled(true);
			this.parent.nextPage();
		}));
		
		this.addMenuClickHandler(leftSlot, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			e.setCancelled(true);
			this.parent.previousPage();
		}));
		
		this.addMenuClickHandler(closeSlot, new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			e.setCancelled(true);
			this.parent.close();
		}));
		
	}
	
	public int getRightSlot() {
		return this.rightSlot;
	}
	
	public int getLeftSlot() {
		return leftSlot;
	}
	
	public int getCloseSlot() {
		return closeSlot;
	}
	
	public void setInventory(Inventory i) {
		this.inventory = i;
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public void addBarriers(List<Integer> barriers) {
		this.barrier_slots.addAll(barriers);
	}
	
	public void setCloseItem(ItemStack i) {
		this.closeItem = i;
	}
	
	public void setRightItem(ItemStack i) {
		this.rightItem = i;
	}
	
	public void setLeftItem(ItemStack i) {
		this.leftItem = i;
	}
	
	
	public void setBarrierItem(ItemStack i) {
		this.barrierItem = i;
	}
	
	public void addHandler(int slot, MenuHandler<InventoryClickEvent> handler) {
		handlers.putIfAbsent(slot, new ArrayList<MenuHandler<InventoryClickEvent>>());
		
		ArrayList<MenuHandler<InventoryClickEvent>> handler_list = handlers.get(slot);
		handler_list.add(handler);
		
		handlers.put(slot, handler_list);
	}
	
	public void addHandlers(int slot, ArrayList<MenuHandler<InventoryClickEvent>> list) {
		handlers.putIfAbsent(slot, new ArrayList<MenuHandler<InventoryClickEvent>>());
		handlers.get(slot).addAll(list);
	}
	
	public void addShiftHandler(int slot, MenuHandler<InventoryClickEvent> handler) {
		shiftHandlers.putIfAbsent(slot, new ArrayList<MenuHandler<InventoryClickEvent>>());
		shiftHandlers.get(slot).add(handler);
	}
	
	public MenuHandler<?>[] getHandlers(int slot) {
		return handlers.get(slot).toArray(new MenuHandler<?>[0]);
	}
	
	public PageLayout clone() {
		PageLayout clone =  new PageLayout(this.parent);
		clone.setBarrierItem(barrierItem);
		clone.setCloseItem(closeItem);
		clone.setLeftItem(leftItem);
		clone.setRightItem(rightItem);
		clone.setSlots(rightSlot, leftSlot, closeSlot);
		clone.addBarriers(barrier_slots);
		this.handlers.forEach((Integer slot, ArrayList<MenuHandler<InventoryClickEvent>> events) -> {
			clone.addHandlers(slot, events);
		});

		return clone;
	}
	
	public void setParent(PagedUIObject parent) {
		this.parent = parent;
	}
	
	public void click(InventoryClickEvent e, int slot) {
		this.handlers.putIfAbsent(slot, new ArrayList<MenuHandler<InventoryClickEvent>>());
		this.handlers.get(slot).forEach((MenuHandler<InventoryClickEvent> event) -> {
			event.run(e);
		});
		
		if(this.barrier_slots.contains(slot)) {
			e.setCancelled(true);
		}
	}
	
	public void shiftClick(InventoryClickEvent e, int slot) {
		this.shiftHandlers.putIfAbsent(slot, new ArrayList<MenuHandler<InventoryClickEvent>>());
		if(e.getClickedInventory().equals(parent.getInventory())) {
			this.shiftHandlers.get(slot).forEach((MenuHandler<InventoryClickEvent> event) -> {
				event.run(e);
			});
		}
	}
	
	public PageLayout() {}
	
	public PageLayout(PagedUIObject parent) {
		this.parent = parent;
	}

	/**
	 * Must be overwritten in the UI object that
	 * extends PagedUIObject. This one, however, should never
	 * be overwritten.
	 * 
	 * @param ui
	 */
	public void decorate(PagedUIObject ui) {
		layout.forEach((Integer slot, ItemStack i) -> {
			ui.setItem(slot, i);
		});
		
		this.barrier_slots.forEach((Integer slot) -> {
			ui.setItem(slot, barrierItem);
		});
		
		ui.setItem(leftSlot, leftItem);
		ui.setItem(rightSlot, rightItem);
		ui.setItem(closeSlot, closeItem);
		
	}

	public void addUnclickableItem(int slot, ItemStack item) {
		// stop null 
		handlers.putIfAbsent(slot, new ArrayList<MenuHandler<InventoryClickEvent>>());
		
		// get handler list
		ArrayList<MenuHandler<InventoryClickEvent>> handler_list = handlers.get(slot);
		handler_list.add(new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			e.setCancelled(true);
		}));
		
		//add handlers
		handlers.put(slot, handler_list);
		
		// set inventory item
		layout.put(slot, item);
	}
	
	public void addItem(int slot, ItemStack item) {
		layout.put(slot, item);
	}

	public void addMenuClickHandler(int slot, MenuHandler<InventoryClickEvent> menuHandler) {
		this.addHandler(slot, menuHandler);
	}

	public void setItem(int slot, ItemStack item) {
		addItem(slot, item);
	}

	public int firstEmpty() {
		for(int i=0; i<parent.getInventory().getSize(); i++) {
			if(this.layout.get(i) == null && 
					!this.barrier_slots.contains(i) &&
					this.rightSlot != i &&
					this.leftSlot != i &&
					this.closeSlot != i
			) {
				return i;
			}
		}
		return -1;
	}
	
}
