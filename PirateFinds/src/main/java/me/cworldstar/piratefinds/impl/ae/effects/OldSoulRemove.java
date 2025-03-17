package me.cworldstar.piratefinds.impl.ae.effects;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.souls.SoulAPI;
import me.cworldstar.piratefinds.impl.ae.souls.Souls;
import net.advancedplugins.ae.impl.effects.effects.actions.execution.ExecutionTask;
import net.advancedplugins.ae.impl.effects.effects.effects.AdvancedEffect;

public class OldSoulRemove extends AdvancedEffect {
	public OldSoulRemove() {
		super(PirateFinds.getThisPlugin(), "PFSOUL_REMOVE");
		addArgument(1, Integer.class);
	}
	
	@Override
	 public boolean executeEffect(ExecutionTask task, LivingEntity entity, String[] args) {
		List<String> types = task.getAbility().getTypes();
		if(types.contains("ATTACK") || types.contains("ATTACK_MOB")) {
			LivingEntity attacker = task.getBuilder().getAttacker();
			if(attacker instanceof Player) {
				Souls soul = SoulAPI.getSouls((Player) attacker);
				soul.expendSouls(Integer.valueOf(args[0]));
			}
		} else if(types.contains("DEFENSE") || types.contains("DEFENSE_MOB")) {
			LivingEntity defender = task.getBuilder().getVictim();
			if(defender instanceof Player) {
				Souls soul = SoulAPI.getSouls((Player) defender);
				soul.expendSouls(Integer.valueOf(args[0]));
			}
		}
		
		return true;
	};
}
