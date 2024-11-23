package me.cworldstar.piratefinds.impl.ae.modules;

import org.bukkit.entity.LivingEntity;
import me.cworldstar.piratefinds.PirateFinds;
import net.advancedplugins.ae.impl.effects.effects.actions.execution.ExecutionTask;
import net.advancedplugins.ae.impl.effects.effects.effects.AdvancedEffect;

public class GuardExpansion extends AdvancedEffect {

	public GuardExpansion() {
		super(PirateFinds.getThisPlugin(), "GUARD_ADV");
	}
	
	@Override
	public boolean executeEffect(ExecutionTask task, LivingEntity target, String[] args) {
		return false;
	}
	
}
