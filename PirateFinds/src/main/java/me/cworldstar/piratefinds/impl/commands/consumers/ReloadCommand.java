package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class ReloadCommand extends CommandConsumer<CommandSender> {

	public ReloadCommand() {
		this.setPermission("pf.commands.reload");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		PirateFinds.getThisPlugin().reload();
		player.sendMessage(ChatUtils.createBroadcast("&7Reloaded successfully!"));
	}

	@Override
	protected List<String> getCompletions(int length) {
		// TODO Auto-generated method stub
		return List.of(new String[0]);
	}

}
