package me.cworldstar.piratefinds;

import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;

public class Utils {
	private Utils() {
        throw new IllegalStateException("Static class");
	}

	public static PersistentDataContainer dataOrDefault(LivingEntity entity) {
		return entity.getPersistentDataContainer();
	}
}
