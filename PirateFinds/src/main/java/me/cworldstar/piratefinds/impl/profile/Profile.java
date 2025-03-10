package me.cworldstar.piratefinds.impl.profile;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import me.cworldstar.piratefinds.events.StatChangeEvent;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class Profile {

	public static Profile NULL_PROFILE;
	static {
		
		YamlConfiguration config = new YamlConfiguration();
		
		config.set("player", null);
		config.set("health", 100L);
		config.set("mana", 100L);
		
		config.set("hit_critical", 0L);
		config.set("skill_critical", 0L);
		config.set("dodge", 0L);
		
		config.set("strength", 0L); // strength adds +0.1 damage to attacks.
		config.set("defense", 0L);
		config.set("intelligence", 0L); // intelligence adds +1 to mana, and 0.01% spell critical rate.
		config.set("dexterity", 0L); // dexterity adds 0.001% dodge rate, and 0.02% critical rate.
		
		NULL_PROFILE = new Profile(config);
		
		for(String key : config.getKeys(false)) {
			if(key == "player") continue;
			NULL_PROFILE.setStat(key, config.getLong(key));
		}
		
		
	}
	protected HashMap<String, Long> stats = new HashMap<String, Long>();
	private Player owner;	
	private YamlConfiguration config;
	
	public static Profile fromConfiguration(YamlConfiguration config) {
		
		Profile to_return = new Profile(config.getObject("player", Player.class));
		
		for(String key : config.getKeys(false)) {
			if(key == "player") continue;
			to_return.setStat(key, config.getLong(key));
		}

		to_return.setConfig(config);
		
		return to_return;

	}
	
	public Profile clone() { 
		Profile profile = new Profile();
		profile.setConfig(this.config);
		profile.setOwner(this.owner);
		this.stats.forEach((String key, Long value) -> {
			profile.setStat(key, value);
		});
		
		return profile;
	}
	
	public void setConfig(YamlConfiguration config) {
		this.config = config;
	}
	
	public YamlConfiguration getConfig() {
		return this.config;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;		
		config.set("player", owner);
	}
	
	public String serialize() {
		
		JsonObject serialized = new JsonObject();
		
		this.stats.forEach((String id, Long value) -> {
			serialized.add(id, new JsonPrimitive(value));
		});
		
		return serialized.toString();
	}
	
	public Profile() {
		
	}
	
	public Profile(YamlConfiguration config) {
		this.setConfig(config);
	}
	
	public Profile(Player owner) {
		this.owner = owner;
		
		Profile profile = NULL_PROFILE.clone();
		profile.setOwner(owner);
		
		this.setConfig(profile.getConfig());
	}
	
	public long getStat(String stat) {
		
		if(this.stats.containsKey(stat)) {
			return this.stats.get(stat);
		}
		
		else {
			return 0L;
		}
	}
	
	public void setStat(String stat, long entry) {
				
		StatChangeEvent e = new StatChangeEvent(this.owner, stat, this.getStat(stat), entry);
		Bukkit.getPluginManager().callEvent(e);
		this.stats.put(stat, e.getNewValue());
	}

	public Player getOwner() {
		return this.owner;
	}

	public void print(Player player) {
		player.sendMessage(ChatUtils.apply("&7[ &a Stats: &7]"));
		this.stats.forEach((String id, Long value) -> {
			player.sendMessage(ChatUtils.apply("&7[ &a" + id + " &7]:" + Long.toString(value)));
		});
	}

	public void saveToConfig() {
		this.stats.forEach((String key, Long value) -> {
			this.config.set(key, value);
		});
	}

}
