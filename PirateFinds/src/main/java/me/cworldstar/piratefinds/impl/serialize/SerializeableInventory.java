package me.cworldstar.piratefinds.impl.serialize;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SerializeableInventory implements ConfigurationSerializable {

	private Inventory inventory; // inventory is effectively final as this should not be used during runtime
	
	public SerializeableInventory(Inventory inventory) { this.inventory = inventory; }
	
	public SerializeableInventory(Map<String, Object> items) {
		this.inventory = Bukkit.createInventory(null, items.size());
		 
		for(Entry<String, Object> entry : items.entrySet()) {
			this.inventory.setItem(Integer.parseInt(entry.getKey()), (ItemStack) entry.getValue());
		}
	}
	
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
		for(int i=0; i<inventory.getSize(); i++) {
			items.put(i, inventory.getItem(i));
		}
		map.put("contents", items);
		return map;
	}

    public static SerializeableInventory deserialize(Map<String, Object> map) {
    	Object SMap = map.get("contents");
    	if(SMap instanceof HashMap<?,?>) {
    		@SuppressWarnings("unchecked")
			HashMap<Integer, ItemStack> item = (HashMap<Integer,ItemStack>) map.get("contents");
    		Inventory i = Bukkit.createInventory(null, map.size());
    		for(int i2=0; i2<i.getSize(); i2++) {
    			i.setItem(i2, item.get(i2));
    		}
    		return new SerializeableInventory(i);
    	}
    	return null;
    }
	
}
