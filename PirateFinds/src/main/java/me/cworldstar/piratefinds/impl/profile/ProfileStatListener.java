package me.cworldstar.piratefinds.impl.profile;

import java.util.Optional;

import org.bukkit.attribute.Attribute;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.events.StatChangeEvent;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class ProfileStatListener implements Listener {
	public ProfileStatListener() {
		PirateFinds.registerListener(this);
	}
	
	@EventHandler
	public void onStatChangeEvent(StatChangeEvent e) {
		if(e.getOwner() == null) return;
		String stat = e.getStat();
		switch(stat) {
			case "health":
				e.getOwner().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getNewValue() / 5);
				break;
			default:
				break;
		}
	}
	
	@EventHandler
	public void strengthListener(EntityDamageEvent e) {
		if(e.getCause() == DamageCause.ENTITY_ATTACK) {
			DamageSource source = e.getDamageSource();
			Entity sourceEntity = source.getCausingEntity();
			if(sourceEntity instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) sourceEntity;
				if(entity instanceof Player) {
					Player p = (Player) entity;
					Optional<Profile> playerProfile = PlayerProfile.getPlayerProfile(p);
					if(playerProfile.isPresent()) {
						Profile profile = playerProfile.get();
						double increased_damage = profile.getStat("strength") * 0.1;
						if(increased_damage == 0) {
							return;
						}
						p.sendMessage(ChatUtils.apply("&7[ &c&lSTRENGTH: &7Your damage has been increased by &c" + Double.toString(Math.ceil(increased_damage * 100) / 100) + "&7. &7]"));
						e.setDamage(e.getDamage() + increased_damage);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void defenseListener(EntityDamageEvent e) {
		if(e.getCause() == DamageCause.ENTITY_ATTACK) {
			Entity entity = e.getEntity();
			if(entity instanceof Player) {
					Player p = (Player) entity;
					Optional<Profile> playerProfile = PlayerProfile.getPlayerProfile(p);
					if(playerProfile.isPresent()) {
						Profile profile = playerProfile.get();
						double decreased_damage = profile.getStat("defense") * 0.1;
						if(decreased_damage == 0) {
							return;
						}
						p.sendMessage(ChatUtils.apply("&7[ &e&lDEFENSE: &7Your damage taken has been reduced by &c" + Double.toString(Math.ceil(decreased_damage * 100) / 100) + "&7. &7]"));
						
						double damage = e.getDamage() - decreased_damage;
						if(damage <= 0) {
							e.setDamage(1);
							return;
						}
						
						e.setDamage(e.getDamage() - decreased_damage);
					}
			}
		}
	}
	
	
}
