package me.cworldstar.piratefinds.impl;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ExpandedRandom;

public class MobDropImpl implements Listener {
	
	private ConfigurationSection commandDropSection;
	private static List<EntityType> ALL_MOBS = new ArrayList<EntityType>();
	static {
		ALL_MOBS.add(EntityType.CREEPER);
		ALL_MOBS.add(EntityType.ZOMBIE);
		ALL_MOBS.add(EntityType.SPIDER);
		ALL_MOBS.add(EntityType.SKELETON);
		ALL_MOBS.add(EntityType.ALLAY);
		ALL_MOBS.add(EntityType.BLAZE);
		ALL_MOBS.add(EntityType.WITHER_SKELETON);
		ALL_MOBS.add(EntityType.WITCH);
		ALL_MOBS.add(EntityType.WITHER);
		ALL_MOBS.add(EntityType.ZOMBIE_VILLAGER);
		ALL_MOBS.add(EntityType.ZOMBIFIED_PIGLIN);
		ALL_MOBS.add(EntityType.PIGLIN);
		ALL_MOBS.add(EntityType.PIGLIN_BRUTE);
		ALL_MOBS.add(EntityType.BREEZE);
		ALL_MOBS.add(EntityType.WARDEN);
		ALL_MOBS.add(EntityType.ENDER_DRAGON);
		ALL_MOBS.add(EntityType.ENDERMAN);
		ALL_MOBS.add(EntityType.ELDER_GUARDIAN);
		ALL_MOBS.add(EntityType.GUARDIAN);
	}
	
	public MobDropImpl() {
		PirateFinds.registerListener(this);
		commandDropSection = PirateFinds.getThisPlugin().getConfig().getConfigurationSection("command-drops");
	}
	
	@EventHandler
	public void onEntityKillEntity(EntityDeathEvent e) {
		DamageSource source = e.getDamageSource();
		Entity causing_entity = source.getCausingEntity();
		if(causing_entity instanceof Player) {
			EntityType type = e.getEntity().getType();
			ConfigurationSection mob_config = this.commandDropSection.getConfigurationSection(type.toString());
			if(mob_config != null) {
				for(String key : mob_config.getKeys(false).toArray(new String[0])) {
					ExpandedRandom<DropCommand> random = new ExpandedRandom<DropCommand>();
					ConfigurationSection section = mob_config.getConfigurationSection(key);
					int max_chance = section.getInt("max-chance");
					if(max_chance == 0) {
						max_chance = 100;
					}
					random.setMaxChance(max_chance);
					for(String command : section.getStringList("commands")) {
						random.add(new DropCommand(command, section.getString("award-message"), section.getString("award-sound")), section.getInt("chance"));
					}
					DropCommand command = random.resolve();
					if(command != null) {
						((Player) causing_entity).playSound(causing_entity, command.getSound(),1,1);
						causing_entity.sendMessage(ChatUtils.apply(PlaceholderAPI.setPlaceholders((Player) causing_entity, command.getAwardMessage())));
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders((Player) causing_entity, command.getCommand()));
					}
				}
				


			} else {
				ExpandedRandom<DropCommand> random = new ExpandedRandom<DropCommand>();
				for(String key : this.commandDropSection.getConfigurationSection("ALL").getKeys(false).toArray(new String[0])) {

					ConfigurationSection section = this.commandDropSection.getConfigurationSection("ALL").getConfigurationSection(key);
					for(String command : section.getStringList("commands")) {
						random.add(new DropCommand(command, section.getString("award-message"), section.getString("award-sound")), section.getInt("chance"));
					}
				}
				DropCommand command = random.resolve();
				if(command != null) {
					((Player) causing_entity).playSound(causing_entity, command.getSound(),1,1);
					causing_entity.sendMessage(ChatUtils.apply(PlaceholderAPI.setPlaceholders((Player) causing_entity, command.getAwardMessage())));
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders((Player) causing_entity, command.getCommand()));
				};
			}
			if(ALL_MOBS.contains(e.getEntity().getType())) {
				ConfigurationSection all_mob_config = this.commandDropSection.getConfigurationSection("ALL_MOB");
				if(all_mob_config != null) {
					ExpandedRandom<DropCommand> random = new ExpandedRandom<DropCommand>();
					for(String key : all_mob_config.getKeys(false).toArray(new String[0])) {
						ConfigurationSection section = all_mob_config.getConfigurationSection(key);
						int max_chance = section.getInt("max-chance");
						if(max_chance == 0) {
							max_chance = 100;
						}
						random.setMaxChance(max_chance);
						for(String command : section.getStringList("commands")) {
							random.add(new DropCommand(command, section.getString("award-message"), section.getString("award-sound")), section.getInt("chance"));
						}
					}
					DropCommand command = random.resolve();

					if(command == null) {
						return;
					}
					
					((Player) causing_entity).playSound(causing_entity, command.getSound(),2,1);
					causing_entity.sendMessage(ChatUtils.apply(PlaceholderAPI.setPlaceholders((Player) causing_entity, command.getAwardMessage())));
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders((Player) causing_entity, command.getCommand()));
				}
			}
		}

	}
	
}
