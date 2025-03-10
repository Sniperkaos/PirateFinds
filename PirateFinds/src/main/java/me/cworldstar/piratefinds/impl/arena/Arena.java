package me.cworldstar.piratefinds.impl.arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.events.TickerTickEvent;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class Arena implements Listener {

	protected HashMap<Player, HashMap<Player, Integer>> killstreakMap = new HashMap<Player, HashMap<Player, Integer>>();
	protected ArrayList<ArenaFighter> fighters = new ArrayList<ArenaFighter>();
	
	
	@Nullable
	public ArenaFighter getArenaFighter(Player p) {
		ArenaFighter fighter = null;
		for(ArenaFighter a_fighter : fighters.toArray(new ArenaFighter[0])) {
			if(a_fighter.getFighter().getUniqueId().equals(p.getUniqueId())) {
				fighter = a_fighter;
				break;
			}
			
			continue;
		}
		
		return fighter;
	}
	
	public boolean doesArenaFighterExist(Player p) {
		for(ArenaFighter a_fighter : fighters.toArray(new ArenaFighter[0])) {
			if(a_fighter.getFighter().getUniqueId().equals(p.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	public Arena() {
		PirateFinds.getThisPlugin().getServer().getPluginManager().registerEvents(this, PirateFinds.getThisPlugin());
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		
		Player player = e.getPlayer();
		ArenaFighter fighter = getArenaFighter(player);
		if(fighter == null) return;
		
		ArrayList<String> blacklisted = new ArrayList<String>(PirateFinds.getThisPlugin().getConfig().getStringList("arena.arena-blacklist-commands"));
		String command = e.getMessage();
		
		PirateFinds.log(command);
		
		if(blacklisted.contains(command.split(" ")[0])) {
			e.getPlayer().sendMessage(ChatUtils.createBroadcast("&7You cannot use this command while in the arena! To leave the arena, stand still and use /pf leavearena."));
			e.setMessage("/pf empty");
			e.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void onTickerTick(TickerTickEvent e) {
		for(ArenaFighter a_fighter : fighters.toArray(new ArenaFighter[0])) {
			if(a_fighter.getFighter().isFlying()) {
				a_fighter.getFighter().setFlying(false);
				a_fighter.getFighter().sendMessage(ChatUtils.createBroadcast("&7You cannot fly in the arena."));
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player killed = e.getEntity();
		DamageSource source = e.getDamageSource();
		Entity attacker = source.getCausingEntity();
		if(attacker instanceof Player) {
			
			ArenaFighter af_killed = getArenaFighter(killed);
			ArenaFighter af_killer = getArenaFighter((Player) attacker);

			
			// leave the arena when killed
			if(af_killed != null) {
				PirateFinds.log("Arena: Killed " + killed.getName() + " is being ejected from the arena.");
				arenaLeave(killed);
			}
			
			
			
			if(
					(af_killer == null)
			) {
				PirateFinds.log("Arena: Killed " + killed.getName() + " did not have an arena killer. Awarding kill to nearest player instead.");
				List<Entity> nearest_entities = killed.getNearbyEntities(20, 20, 20);
				for(Entity entity : nearest_entities.toArray(new Entity[0])) {
					if(entity instanceof Player) {
						ArenaFighter nearest_fighter = getArenaFighter((Player) entity);
						if (nearest_fighter == null) continue;
						PirateFinds.log("Arena: Killer " + nearest_fighter.getFighter().getName() + " is being awarded for the kill.");
						nearest_fighter.awardKill(af_killed);
						nearest_fighter.increaseStreak();
						break;
					}
				}
				return;
			}

			PirateFinds.log("Arena: Killer " + attacker.getName() + " is being awarded for the kill.");
			af_killer.awardKill(af_killed);
			af_killer.increaseStreak();
		}
		else {
			ArenaFighter af_killed = getArenaFighter(killed);
			
			if(af_killed != null) {
				PirateFinds.log("Arena: Killed " + killed.getName() + " is being ejected from the arena.");
				arenaLeave(killed);
			}
			
			
			PirateFinds.log("Arena: Killed " + killed.getName() + " did not have an arena killer. Awarding kill to nearest player instead.");
			List<Entity> nearest_entities = killed.getNearbyEntities(20, 20, 20);
			for(Entity entity : nearest_entities.toArray(new Entity[0])) {
				if(entity instanceof Player) {
					ArenaFighter nearest_fighter = getArenaFighter((Player) entity);
					if (nearest_fighter == null) continue;
					PirateFinds.log("Arena: Killer " + nearest_fighter.getFighter().getName() + " is being awarded for the kill.");
					nearest_fighter.awardKill(af_killed);
					nearest_fighter.increaseStreak();
					break;
				}
			}
			return;
		}
	}
	
	public void arenaEnter(Player p) {
		if(p.isFlying()) {
			p.setFlying(false);
			p.sendMessage(ChatUtils.createBroadcast("&7You cannot fly in the arena."));
		}
		this.fighters.add(new ArenaFighter(p));
	}
	
	public void arenaLeave(Player p) {
		for(ArenaFighter fighter : fighters.toArray(new ArenaFighter[0])) {
			if(fighter.getFighter().getUniqueId().equals(p.getUniqueId())) {
				fighters.remove(fighters.indexOf(fighter));
			}
		}
	}
	
}
