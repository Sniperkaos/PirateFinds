package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class Broadcast extends CommandConsumer<CommandSender> {

	public Broadcast() {
		this.setPermission("pf.commands.broadcast");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		
		
		PirateFinds.getServerStatic().getOnlinePlayers().forEach((Player p) -> {
			String s = String.join(" ", args);
			String sep = "";
			for(int i=0; i<(s.length() * 2); i++) {
				sep = sep.concat("-=");
			}
			
			p.sendMessage(ChatUtils.apply("&4&l").concat(sep));
			p.sendMessage(ChatUtils.apply("&7&l[ &c&lPirateFinds Broadcast&7&l ]"));
			p.sendMessage(ChatUtils.apply("&c&l       ").concat(s));
			p.sendMessage(ChatUtils.apply("&4&l").concat(sep));
		});
	}

	@Override
	protected List<String> getCompletions(int length) {
		return Arrays.asList("");
	}

}
