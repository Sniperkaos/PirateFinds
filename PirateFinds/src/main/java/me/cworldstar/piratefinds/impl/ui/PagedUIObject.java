package me.cworldstar.piratefinds.impl.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
		layout.setPage(this.layouts.size());
		this.layouts.add(layout);
	}
	
	public int getPage() {
		return this.page;
	}
	
	@Override
	public void decorate(Inventory i) {
		to_decorate = i;
	}
	
	/**
	 * 
	 * This method takes a predicate and returns the first matching result.
	 * 
	 * @param predicate {@link Predicate}<PageLayout>
	 * @return {@link PageLayout}
	 */
	@Nullable
	public PageLayout findLayout(Predicate<PageLayout> predicate) {
		for(PageLayout layout : this.layouts) {
			if(predicate.test(layout)) {
				return layout;
			}
		}
		return null;
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
	
	/**
	 * 
	 * This method forcefully displays a given layout, but will error
	 * if the parent {@link PagedUIObject} is null or not this one.
	 * 
	 * @param {@link PageLayout} layout
	 */
	@ErrorsIf(Reason="Invalid page layout")
	@ErrorsIf(Reason="PageLayout has no page.")
	public void displayLayout(@Nonnull PageLayout layout) {
		
		assert layout.getPage() == -1 : "PageLayout given had an invalid page.";
		assert layout.getParent().equals(this) : "PageLayout given did not have this UIObject as parent!";

		
		int page = layout.getPage();
		this.page = page;
		
		this.getInventory().clear();
		this.decoratePageWithLayout(layout);
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
