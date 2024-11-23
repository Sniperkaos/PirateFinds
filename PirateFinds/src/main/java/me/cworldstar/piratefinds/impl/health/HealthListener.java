package me.cworldstar.piratefinds.impl.health;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.Utils;

public class HealthListener implements Listener {
	
	public HealthListener() {
		PirateFinds plugin = PirateFinds.getThisPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		
		if(!(e.getEntity() instanceof LivingEntity)) {
			return;
		}
		
		LivingEntity damaged_entity = (LivingEntity) e.getEntity();
	
		PersistentDataContainer data = Utils.dataOrDefault(damaged_entity);
		if(data == null) {
			return;
		}
		
		if(data.get(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG) == null) return;
		long health = data.get(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG);
		if(health <= 0) {
			return;
		}
		
		e.setDamage(0);
		
		data.set(PirateFinds.KEYS.HEALTH_INSTANCE_KEY.getKey(), PersistentDataType.LONG, Math.round(health - e.getFinalDamage()));
		
	}
	
	
}
