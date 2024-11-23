package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.cworldstar.piratefinds.impl.armorer.ArmorSets;
import me.cworldstar.piratefinds.impl.armorer.ArmorSets.Sets;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import net.md_5.bungee.api.ChatColor;

public class Guarantee extends CommandConsumer<CommandSender> {

	public Guarantee() {
		this.hide = true;
		this.setPermission("pf.commands.guarantee");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		
		if(player instanceof Player) {
			Player the_player = (Player) player;
			ArmorSets.guaranteeNextForge(the_player, args.get(0));
			the_player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[PirateFinds]: Guaranteed next forge " + args.get(0)));
		}
	}
	
	@Override
	protected List<String> getCompletions(int length) {
		// TODO Auto-generated method stub
		
		ArrayList<String> sets = new ArrayList<String>();
		for (Sets set : ArmorSets.Sets.values()) {
			sets.add(set.getSetString());
		}
		
		return sets;
	}

}