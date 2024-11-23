package me.cworldstar.piratefinds.impl.health;

import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.Utils;

public class HealthInstance {
	
	private long current_health;
	private long max_health;
	
	public HealthInstance(LivingEntity entity, long max_health) {
		PersistentDataContainer data = Utils.dataOrDefault(entity);
		
		this.current_health = max_health;
		this.max_health = max_health;
		// set health
		data.set(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG, max_health);
		// set max hp
		data.set(PirateFinds.KEYS.MAX_HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG, max_health);
	}
	
	public void damage(LivingEntity entity, double damage) {
		PersistentDataContainer data = Utils.dataOrDefault(entity);
		
		if(data == null) {
			entity.damage(damage);
		}
		
		long health = data.get(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG);
		if(health <= 0) {
			entity.damage(health);
		}
	
		
		data.set(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG, Math.round(health - damage));
		this.current_health -= damage;
	}
	
	public void damage(LivingEntity entity, long damage) {
		PersistentDataContainer data = Utils.dataOrDefault(entity);
		
		if(data == null) {
			entity.damage(damage);
		}
		
		long health = data.get(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG);
		if(health <= 0) {
			entity.damage(health);
		}
	
		
		data.set(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG, health - damage);
		this.current_health -= damage;
	}
	
	public void damage(LivingEntity entity, int damage) {
		PersistentDataContainer data = Utils.dataOrDefault(entity);
		
		if(data == null) {
			entity.damage(damage);
		}
		
		long health = data.get(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG);
		if(health <= 0) {
			entity.damage(health);
		}
	
		
		data.set(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG,health - damage);
		this.current_health -= damage;
	}
	
	public void regen(LivingEntity entity, long damage) {
		PersistentDataContainer data = Utils.dataOrDefault(entity);
		
		if(data == null) {
			entity.damage(damage);
		}
		
		long health = data.get(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG);
		if(health <= 0) {
			entity.damage(health);
		}
		
		data.set(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG,health + damage);
		this.current_health += damage;
	}
}
