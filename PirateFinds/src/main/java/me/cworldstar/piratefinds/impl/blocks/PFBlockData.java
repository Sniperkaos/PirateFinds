package me.cworldstar.piratefinds.impl.blocks;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import me.cworldstar.piratefinds.impl.serialize.SerializeableInventory;

public class PFBlockData implements ConfigurationSerializable {

	private Map<String, Object> data;
	
	public PFBlockData() {
		data = new HashMap<String, Object>();
	}
	
	public PFBlockData(Map<String, Object> data) {
		this.data = data;
	}

	
	
	@NotNull
	public Integer getInt(String id) {
		if(data.get(id) instanceof Integer) {
			return (Integer) data.get(id);
		}
		return 0;
	}
	
	@Nullable
	public String getString(String id) {
		if(data.get(id) instanceof String) {
			return (String) data.get(id);
		}
		return null;
	}
	
	@NotNull
	public Boolean getBoolean(String id) {
		if(data.get(id) instanceof Boolean) {
			return (Boolean) data.get(id);
		}
		return false;
	}
	
	@NotNull
	public Double getDouble(String id) {
		if(data.get(id) instanceof String) {
			return (Double) data.get(id);
		}
		return 0.0;
	}
	
	@NotNull
	public Long getLong(String id) {
		if(data.get(id) instanceof Long) {
			return (Long) data.get(id);
		}
		return 0L;
	}
	
	@Nullable
	public Inventory getInventory(String id) {
		if(data.get(id) instanceof SerializeableInventory) {
			SerializeableInventory inventory = (SerializeableInventory) data.get(id);
			return inventory.getInventory();
		}
		return null;
	}
	
	public void setData(String id, Object d2) {
		data.put(id, d2);
	}
	
	
	@Override
	public Map<String, Object> serialize() {
        Map<String, Object> serialize_data = new HashMap<String, Object>();
        serialize_data.put("data", data);
        return serialize_data;
	}
	
	public static PFBlockData deserialize(Map<String, Object> serialize_data) {
		return new PFBlockData(serialize_data);
	}
	
}
