package me.cworldstar.piratefinds.impl.blocks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.serialize.SerializeableInventory;

public class PFBlockData implements ConfigurationSerializable {

	private Map<String, Object> data;
	
	public String toString() {
		String ret = "";
		for(Entry<String, Object> objects : data.entrySet()) {
			ret.concat("|").concat(objects.getKey()).concat(": ").concat(objects.getValue().toString());
		}
		return ret;
	}
	
	public PFBlockData() {
		this.data = new HashMap<String, Object>();
	}
	
	public PFBlockData(Map<String, Object> data) {
		this.data = data;
	}

	
	
	@Nonnull
	public Integer getInt(String id) {
		if(this.data.get(id) instanceof Integer) {
			return (Integer) this.data.get(id);
		}
		return 0;
	}
	
	@Nullable
	public String getString(String id) {
		if(this.data.get(id) instanceof String) {
			return (String) this.data.get(id);
		}
		return null;
	}
	
	@Nonnull
	public Boolean getBoolean(String id) {
		if(this.data.get(id) instanceof Boolean) {
			return (Boolean) this.data.get(id);
		}
		return false;
	}
	
	@Nonnull
	public Double getDouble(String id) {
		if(this.data.get(id) instanceof String) {
			return (Double) this.data.get(id);
		}
		return 0.0;
	}
	
	@Nonnull
	public Long getLong(String id) {
		if(this.data.get(id) instanceof Long) {
			return (Long) this.data.get(id);
		}
		return 0L;
	}
	
	@Nullable
	public Inventory getInventory(String id) {
		if(this.data.get(id) instanceof SerializeableInventory) {
			SerializeableInventory inventory = (SerializeableInventory) this.data.get(id);
			return inventory.getInventory();
		}
		return null;
	}
	
	@Nullable
	public void setInventory(String id, SerializeableInventory inventory) {
		this.data.put(id, inventory);
	}
	
	
	public void setData(String id, Object d2) {
		this.data.put(id, d2);
	}
	
	
	@Override
	public Map<String, Object> serialize() {
		PirateFinds.log("SERIALIZING PFDATA");
		PirateFinds.log(data.toString());
		PirateFinds.log(data.values().toString());
		PirateFinds.log(Integer.toString(data.size()));
        Map<String, Object> serialize_data = new HashMap<String, Object>();
		PirateFinds.log(serialize_data.toString());
        for(Entry<String,Object> object : data.entrySet()) {
    		PirateFinds.log(object.toString());
        	if(!(object.getValue() instanceof SerializeableInventory)) {
        		serialize_data.put(object.getKey(), object.getValue());
        		continue;
        	};
        	// reflection
        	Class<?> clazz = object.getValue().getClass();
        	try {
				Method method = clazz.getDeclaredMethod("serialize");
				@SuppressWarnings("unchecked")
				Map<String, Object> serialized = (Map<String, Object>) method.invoke(object.getValue());
				PirateFinds.log(serialized.toString());
				serialized.put("size", 18);
				serialized.put("class", clazz.getCanonicalName());
	        	serialize_data.put(object.getKey(), serialized);

			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
        }
        return serialize_data;
	}
	
	public static PFBlockData deserialize(Map<String, Object> serialize_data) {
		
		Map<String, Object> to_return = new HashMap<String, Object>();
		
		for(Entry<String, Object> section : serialize_data.entrySet()) {
			if(section.getValue() instanceof MemorySection) {
				try {
					Class<?> clazz = Class.forName(((MemorySection) section.getValue()).getString("class"));
					Constructor<?> c = clazz.getConstructor(Map.class);
					
					Map<String,Object> small_inventory_contents = new HashMap<String,Object>();
					Map<String, Object> inventory_contents = ((MemorySection) section.getValue()).getConfigurationSection("contents").getValues(false);
					for(int i=0; i<((MemorySection) section.getValue()).getInt("size"); i++) {
						small_inventory_contents.put(Integer.toString(i), inventory_contents.get(Integer.toString(i)));
					}
					
					to_return.put("inventory", c.newInstance(small_inventory_contents));
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new PFBlockData(to_return);
	}
	
}
