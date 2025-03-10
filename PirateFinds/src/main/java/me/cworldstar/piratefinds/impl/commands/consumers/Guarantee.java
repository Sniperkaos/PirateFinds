package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.armorer.ArmorSets;
import me.cworldstar.piratefinds.impl.armorer.ArmorSets.Sets;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.md_5.bungee.api.ChatColor;

public class Guarantee extends CommandConsumer<CommandSender> {

	public Guarantee() {
		this.hide = true;
		this.setPermission("pf.commands.guarantee");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		
		Player target = Bukkit.getPlayer(args.get(1));
		if(target == null ) {
			player.sendMessage(ChatUtils.apply("&7[PirateFinds]: &cThis player was not found."));
			return;
		}
		ArmorSets.guaranteeNextForge(target, args.get(0));
		target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[PirateFinds]: &aGuaranteed next forge " + args.get(0)));
	}
	
	private List<String> players() {
		List<String> completions = new ArrayList<String>();
		completions.addAll(PirateFinds.getServerStatic().getOnlinePlayers()
				.stream()
				.map(Player::getName).toList()
		);
		return completions;
	}
	
	@Override
	protected List<String> getCompletions(int length) {
		switch(length) {
			case 1:
				ArrayList<String> sets = new ArrayList<String>();
				for (Sets set : ArmorSets.Sets.values()) {
					sets.add(set.getSetString());
				}
				
				return sets;
			case 2:
				return players();
			default:
				return Arrays.asList(new String[0]);
		}
	}

}