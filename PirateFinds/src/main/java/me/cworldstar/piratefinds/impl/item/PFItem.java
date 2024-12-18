package me.cworldstar.piratefinds.impl.item;

import java.util.logging.Level;

import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.PirateFinds;

@Deprecated
public abstract class PFItem {

	private static NamespacedKey PF_ITEM_KEY = new NamespacedKey(PirateFinds.getThisPlugin(), "pf_item");
	public static PirateFinds plugin = PirateFinds.getThisPlugin();
	
	/**
	 * 
	 * Call new PFItem(String, ItemStack) every time you give an
	 * item to a player. It uses PDC, so each item must be
	 * individually instanced.
	 * 
	 * @param id
	 * @param item
	 */
	public PFItem(String id, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if(meta == null) {
			plugin.getLogger().log(Level.SEVERE, id + " has no item meta! Consider using a different item.");
		}

		PersistentDataContainer data = meta.getPersistentDataContainer();
		data.set(PF_ITEM_KEY, PersistentDataType.STRING, id);
	}
	
	protected abstract void execute(PlayerInteractEvent e);

}
