package me.cworldstar.piratefinds.impl.ae.items;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import me.cworldstar.piratefinds.PirateFinds;

public abstract class AbstractPFItem {
	
	public static enum PFItemType {
		
		/**
		 * The {@link PFItemType} is fired when a player drags this {@link AbstractPFItem}
		 *  over another item, dropping the {@link AbstractPFItem} onto the {@link ItemStack}.
		 */
		DRAG_AND_DROP,
		/**
		 * The {@link PFItemType} is fired when a player tries to right click,
		 * holding the {@link AbstractPFItem}. Not to be confused with SHIFT_RIGHT_CLICK,
		 * this one does not fire if the player is shifting.
		 */
		RIGHT_CLICK,
		/**
		 * The {@link PFItemType} is fired when a player tries to shift right click,
		 * holding the {@link AbstractPFItem}.
		 */
		SHIFT_RIGHT_CLICK,
		
		/**
		 * The {@link PFItemType} is fired when a player tries to right click a block,
		 * holding the {@link AbstractPFItem}.
		 */
		RIGHT_CLICK_ON_BLOCK,
		
		/**
		 * The {@link PFItemType} is fired when a player tries to drop an item,
		 * whether from pressing Q or from dropping from inventory.
		 */
		DROP_ITEM,
		/**
		 * The {@link PFItemType} Invalid should never be used in a pfItem.
		 * This is the default {@link PFItemType} and is used to show errors.
		 */
		INVALID, 
		/**
		 * The {@link PFItemType} is fired when the {@link Ticker} ticks.
		 */
		TICK, 
		/**
		 * The {@link PFItemType} is fired when the {@link AbstractPFItem} is placed.
		 */
		BLOCK_PLACE, 
		/**
		 * The {@link PFItemType} should never be fired.
		 */
		NULL,
		/**
		 * The {@link PFItemType} will fire itself.
		 */
		STATIC, 
		
		BUCKET_USED, 
		
		EAT;
		
		@Nonnull
		public PFItemType fromString(String s) {
			PFItemType type = PFItemType.valueOf(s);
			if(type != null) {
				return type;
			}
			return PFItemType.INVALID;
		}
		
	}
	
	private static ItemStack item;
	private List<PFItemType> types;
	private PFItemType type;
	protected String pf_item_id = "UNSET";
	protected NamespacedKey COOLDOWN_KEY = new NamespacedKey(PirateFinds.getThisPlugin(), "PFITEM_COOLDOWN");
	
	public AbstractPFItem(String id) {
		this.pf_item_id = id;
	}
	
	public abstract ItemStack getPFItem();
	
	public String getPFItemID() {
		return this.pf_item_id;
	}
	
	public abstract ItemStack build();
	public void make(ItemStack item) {} // TODO: remove this method
	public abstract boolean checkExpend(Player p, ItemStack on);
	public boolean checkExpendWithItem(Player p, ItemStack on, ItemStack thisItem) {
		return false;
	};

	public void onItemUse(Player p, ItemStack on, PFItemType type) {};
	
	
	
	public abstract PFItemType getType();
	public List<PFItemType> getTypes() {
		if(this.types == null) {
			
			return Arrays.asList(new PFItemType[] {
					this.type
			});
		}
		
		return types;
	}

	public <T extends AbstractPFItem> void register(T clazz) {
		PFItemClass.registerAnyItem(clazz);
	}
	
	public <T extends AbstractPFItem> void register(T clazz, String id) {
		
		this.pf_item_id = id;
		
		PFItemClass.registerAnyItem(clazz);
	}
	
	
	public void onItemUse(Player player, ItemStack item, PFItemType rcob, Block on) {};
	public void onItemUse(Player player, ItemStack itemActual, PFItemType dropItem, BlockPlaceEvent e) {};
	public void onItemUse(Player player, ItemStack itemActual, PFItemType dropItem, PlayerDropItemEvent e) {};
	public void onItemUse(Player whoClicked, ItemStack currentItem, PFItemType type,ItemStack itemOnCursor) {}
	public void onItemUse(Player player, ItemStack maybeBucket, PFItemType bucketUsed, PlayerBucketEmptyEvent e) {};
	
}
