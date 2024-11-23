package me.cworldstar.piratefinds;

import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;

public class Utils {
	private Utils() {
        throw new IllegalStateException("Static class");
	}

	public static PersistentDataContainer dataOrDefault(LivingEntity entity) {
		// TODO Auto-generated method stub
		return entity.getPersistentDataContainer();
	}
}
