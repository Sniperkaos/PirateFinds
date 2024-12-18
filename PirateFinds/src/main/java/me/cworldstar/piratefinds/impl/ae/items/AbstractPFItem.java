package me.cworldstar.piratefinds.impl.ae.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import me.cworldstar.piratefinds.PirateFinds;

public abstract class AbstractPFItem {
	
	public static enum PFItemType {
		
		DRAG_AND_DROP,
		RIGHT_CLICK,
		SHIFT_RIGHT_CLICK,
		DROP_ITEM,
		INVALID, 
		TICK, 
		BLOCK_PLACE, 
		STATIC;
		
		@NotNull
		public PFItemType fromString(String s) {
			PFItemType type = PFItemType.valueOf(s);
			if(type != null) {
				return type;
			}
			return PFItemType.INVALID;
		}
		
	}
	
	@SuppressWarnings("unused")
	private static ItemStack item;
	private List<PFItemType> types;
	private PFItemType type;
	protected String pf_item_id = "UNSET";
	protected NamespacedKey COOLDOWN_KEY = new NamespacedKey(PirateFinds.getThisPlugin(), "PFITEM_COOLDOWN");
	
	public AbstractPFItem(String id) {
		this.pf_item_id = id;
	}
	
	public ItemStack getPFItem() {
		return item;
	}
	
	public String getPFItemID() {
		return this.pf_item_id;
	}
	
	public abstract ItemStack build();
	public void make(ItemStack item) {} // TODO: remove this method
	public abstract boolean checkExpend(Player p, ItemStack on);
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

	
	public void onItemUse(Player player, ItemStack itemActual, PFItemType dropItem, BlockPlaceEvent e) {}
	public void onItemUse(Player player, ItemStack itemActual, PFItemType dropItem, PlayerDropItemEvent e) {}
	public void onItemUse(Player whoClicked, ItemStack currentItem, PFItemType type,ItemStack itemOnCursor) {};
	
}
