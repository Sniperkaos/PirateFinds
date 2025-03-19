package me.cworldstar.piratefinds.hunter;

import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.impl.entities.AbstractPFEntity;

public class HunterDeal {
	
	// what the hunter deal will alwyas have
	private Player owner;
	private Location at;
	private AbstractPFEntity entity;
	private UUID id;
	
	// instanced
	public Entity spawned;
	
	
	public HunterDeal(Player p, World where, AbstractPFEntity toSpawn, int coordinateBounds) {
		this.owner = p;
		this.entity = toSpawn;
		this.at = Hunter.generateCoordinate(where, coordinateBounds);
		this.id = UUID.randomUUID();
	}
	
	@Nonnull
	public Player getOwner() {
		return owner;
	}
	
	@Nonnull
	public Location getWhere() {
		return at;
	}
	
	@Nonnull
	public AbstractPFEntity getEntityTemplate() {
		return entity;
	}
	
	public void toConfigurationSection(YamlConfiguration config) {
		ConfigurationSection section = config.createSection(id.toString());
		section.set("owner", owner);
		section.set("location", at);
		section.set("entity", spawned.getUniqueId());
	}

}
