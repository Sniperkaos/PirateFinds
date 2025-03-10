package me.cworldstar.piratefinds.impl.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;


public abstract class PagedUIObject extends BaseUIObject {

	private List<PageLayout> layouts;
	private Inventory to_decorate;
	private int page = 0;
	private int lslot = 1;
	private int rslot = 2;
	
	public void addLayout(PageLayout layout) {
		this.layouts.add(layout);
	}
	
	public int getPage() {
		return this.page;
	}
	
	@Override
	public void decorate(Inventory i) {
		to_decorate = i;
	}
	
	public void setup() {
		
		decorateLayout(this.to_decorate);
		
		this.addGlobalMenuClickHandler(new MenuHandler<InventoryClickEvent>((InventoryClickEvent e)-> {
			PageLayout layout = this.layouts.get(this.page);
			if(e.isShiftClick()) {
				layout.shiftClick(e, e.getSlot());
			} else {
				layout.click(e, e.getSlot());
			}
		}));
		
		this.layouts.get(0).decorate(this);
		
	}
	
	public PagedUIObject(Player player, InventorySize size) {
		super(player, size);
		
		this.layouts = new ArrayList<PageLayout>();
		
		setup();
	}

	public PagedUIObject(Player player, InventorySize extraLarge, String string) {
		super(player, extraLarge, string);
		
		this.layouts = new ArrayList<PageLayout>();
		
		setup();
	}

	protected void decoratePageWithLayout(PageLayout layout) {
		layout.decorate(this);
	}
	
	public void firePageHandlers(InventoryClickEvent e) {
		this.layouts.get(this.page).click(e, lslot);
	}
	
	public int getPageSlotLeft() {
		return this.lslot;
	}
	
	public int getPageSlotRight() {
		return this.rslot;
	}
	
	
	public void nextPage() {
		if(page + 1 >= layouts.size()) {
			this.page = 0;
		} else {
			this.page = this.page+1;
		}
		
		// clear page
		this.getInventory().clear();
		// decorate new item
		this.decoratePageWithLayout(this.layouts.get(page));
	}
	
	public void previousPage() {
		if(page-1<0) {
			this.page = layouts.size()-1;
		} else {
			this.page = this.page - 1;
		}
		
		this.getInventory().clear();
		// decorate new item
		this.decoratePageWithLayout(this.layouts.get(page));
	}

	public abstract void decorateLayout(Inventory i);
	
}
