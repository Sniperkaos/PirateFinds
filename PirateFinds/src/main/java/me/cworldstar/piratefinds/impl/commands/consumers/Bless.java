package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import net.md_5.bungee.api.ChatColor;

public class Bless extends CommandConsumer<CommandSender> {

	public Bless() {
		this.setPermission("pf.commands.bless");
	}
	
	private static final List<PotionEffectType> negative_effects = Arrays.asList(new PotionEffectType[] {
			PotionEffectType.BAD_OMEN,
			PotionEffectType.BLINDNESS,
			PotionEffectType.DARKNESS,
			PotionEffectType.HUNGER,
			PotionEffectType.INFESTED,
			PotionEffectType.WITHER,
			PotionEffectType.WEAKNESS,
			PotionEffectType.UNLUCK,
			PotionEffectType.SLOWNESS,
			PotionEffectType.MINING_FATIGUE,
			PotionEffectType.NAUSEA,
			PotionEffectType.POISON
	});
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		
		if(player instanceof Player) {
			Player the_player = (Player) player;
			if(!this.hasPermission(the_player)) return;
			the_player.getActivePotionEffects().forEach((PotionEffect effect)-> {
				if(negative_effects.contains(effect.getType())) {
					the_player.removePotionEffect(effect.getType());
				}
			});
			the_player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have been blessed!"));
		}
	}

	public static void bless(Player p) {
		if(p.hasPermission("pf.commands.bless")) return;
		p.getActivePotionEffects().forEach((PotionEffect effect)-> {
			if(negative_effects.contains(effect.getType())) {
				p.removePotionEffect(effect.getType());
			}
		});
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have been blessed!"));
	}
	
	@Override
	protected List<String> getCompletions(int length) {
		// TODO Auto-generated method stub
		return new ArrayList<String>(List.of(new String[] {
				
		}));
	}

}
