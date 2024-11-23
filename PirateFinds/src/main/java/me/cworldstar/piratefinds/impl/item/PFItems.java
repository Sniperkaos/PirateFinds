package me.cworldstar.piratefinds.impl.item;

import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.PirateFinds;

/**
 * This is the registry for PF items.
 * PFItems can be registered by extending {@link PFItem}.
 * 
 * @author cworldstar
 *
 */

public class PFItems implements Listener {
	private static HashMap<String, PFItem> items = new HashMap<String, PFItem>();
	
	private static NamespacedKey PF_ITEM_KEY = new NamespacedKey(PirateFinds.getThisPlugin(), "pf_item");
	
	public static void registerItem(String id, PFItem item) {
		PFItems.items.put(id, item);
	}
	
	public PFItems() {
		PirateFinds plugin = PirateFinds.getThisPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e) {
		// check if the item is a PFItem
		String pf_item_id = parsePFItem(e.getItem());
		if(pf_item_id != null) {
			PFItem item = items.get(pf_item_id);
			item.execute(e);
		}
	}

	
	@Nullable
	public static String parsePFItem(ItemStack item) {
		// check if the item is a pf item
		
		//method 1: data check
		ItemMeta meta = item.getItemMeta();
		if(meta == null) {
			return null;
		}
		
		PersistentDataContainer data = meta.getPersistentDataContainer();
		if(data.get(PF_ITEM_KEY, PersistentDataType.STRING) != null) {
			return data.get(PF_ITEM_KEY, PersistentDataType.STRING);
		}
		
		return null;
		
	}
}
