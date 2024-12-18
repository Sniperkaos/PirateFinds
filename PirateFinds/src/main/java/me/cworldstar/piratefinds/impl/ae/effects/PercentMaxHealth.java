package me.cworldstar.piratefinds.impl.ae.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.advancedplugins.ae.impl.effects.effects.actions.execution.ExecutionTask;
import net.advancedplugins.ae.impl.effects.effects.effects.AdvancedEffect;

public class PercentMaxHealth extends AdvancedEffect {
	public PercentMaxHealth(JavaPlugin arg0) {
		super(arg0, "MAX_HP_DAMAGE");
		this.addArgument(1, Double.class);
	}
	
	@Override
	 public boolean executeEffect(ExecutionTask task, LivingEntity entity, String[] args) {
		Event event = task.getBuilder()
			.getEvent();
		if(event instanceof EntityDamageEvent) {
			EntityDamageEvent e = ((EntityDamageEvent) event);
			e.setDamage(e.getDamage() + (entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * Double.parseDouble(args[0])));
		}
		return true;
	};
}
