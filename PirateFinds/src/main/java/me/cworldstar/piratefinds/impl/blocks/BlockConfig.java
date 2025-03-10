package me.cworldstar.piratefinds.impl.blocks;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.serialize.SerializeableInventory;

public class BlockConfig {
	
	private HashMap<Location, PFBlock> blocks = new HashMap<Location, PFBlock>();
	
	
	public static void setupPersistance() {
		ConfigurationSerialization.registerClass(PFBlockData.class);
		ConfigurationSerialization.registerClass(SerializeableInventory.class);
	}
	
	public void createBlockAt(Location loc, PFBlock block) {
		this.blocks.putIfAbsent(loc, block);
	}
	
	public void removeBlockAt(Location loc) {
		this.blockSave.set(this.blocks.get(loc).getUUID(), null);
		this.blocks.remove(loc);

	}
	
	private YamlConfiguration blockSave;
	
	public BlockConfig(YamlConfiguration blockSave) {
		this.blockSave = blockSave;
	}
	
	public void save(PFBlock block) {
		Location loc = block.getBlock().getLocation();
		ConfigurationSection section = this.blockSave.createSection(block.getUUID());
		section.set("location", loc);
		section.set("block-class", block.getClass().getName());
		section.set("id", block.getPFBlockId());
		section.set("data", block.getPFData().serialize());
		for(String key : section.getKeys(false)) { // lazy, this will error on nesting configuration sections, todo later
			section.set(key, section.get(key));
		}
	}
	
	public void remove(PFBlock block) {
		this.blockSave.set(block.getUUID(), null);
	}
		
	public void load(ConfigurationSection block, UUID uuid) {
		Location loc = block.getLocation("location");
			
		PFBlock instance = null;
		try {
			try {
				instance = (PFBlock) Class.forName(block.getString("block-class")).getConstructor(Location.class, Block.class, UUID.class).newInstance(loc, loc.getBlock(), uuid);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
				
			PirateFinds.log("ERROR: Block at " + Integer.toString(loc.getBlockX()) + "X, " + Integer.toString(loc.getBlockY()) + "Y, " + Integer.toString(loc.getBlockZ())+"Z has failed to load. Check the console for more info!");
				
			return;
		}
		if(block.contains("data")) {
			instance.setData(PFBlockData.deserialize((Map<String, Object>) block.getConfigurationSection("data").getValues(false)));
		}
			
		blocks.putIfAbsent(loc, instance);
	}
	
	public void loadAll() {
		for(String key : this.blockSave.getKeys(false)) {
			this.load(this.blockSave.getConfigurationSection(key), UUID.fromString(key));
		}
	}

	public void saveAll(File blockFile) {
		for(Entry<Location, PFBlock> blocks : blocks.entrySet()) {
			save(blocks.getValue());
		}
		
		try {
			blockSave.save(blockFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean doesBlockExist(Location location) {
		return blocks.get(location) != null;
	}
}
