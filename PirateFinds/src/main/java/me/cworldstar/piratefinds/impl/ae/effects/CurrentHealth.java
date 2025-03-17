package me.cworldstar.piratefinds.impl.ae.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.advancedplugins.ae.impl.effects.effects.actions.execution.ExecutionTask;
import net.advancedplugins.ae.impl.effects.effects.effects.AdvancedEffect;

public class CurrentHealth extends AdvancedEffect {
	public CurrentHealth(JavaPlugin arg0) {
		super(arg0, "CURRENT_HP_DAMAGE");
		this.addArgument(1, Double.class);
	}
	
	@Override
	 public boolean executeEffect(ExecutionTask task, LivingEntity entity, String[] args) {
		Event event = task.getBuilder()
			.getEvent();
		if(event instanceof EntityDamageEvent) {
			EntityDamageEvent e = ((EntityDamageEvent) event);
			e.setDamage(e.getDamage() + task.getBuilder().getVictim().getHealth() * Double.parseDouble(args[0]));
		}
		return true;
	};
}
