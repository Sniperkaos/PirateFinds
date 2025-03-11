package me.cworldstar.piratefinds.impl;

import java.util.HashMap;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;

import me.cworldstar.piratefinds.impl.health.HealthInstance;

@Deprecated(forRemoval=true)
public class HealthImpl {
	
	protected static HashMap<LivingEntity, HealthInstance> healthMap = new HashMap<LivingEntity, HealthInstance>();
	
	private HealthImpl() {
        throw new IllegalStateException("Static class");
	}
	
	public static void startForEntity(LivingEntity e, long max_health) {
		HealthImpl.healthMap.put(
				e, 
				new HealthInstance(e, max_health)
		);
	}
	
	@Nullable
	public static HealthInstance getEntityHealthInstance(LivingEntity e) {
		return healthMap.get(e);
	}
	
}
