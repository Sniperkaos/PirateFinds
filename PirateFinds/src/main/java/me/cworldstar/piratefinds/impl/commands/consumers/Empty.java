package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.cworldstar.piratefinds.impl.commands.CommandConsumer;

public class Empty extends CommandConsumer<CommandSender> {

	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		
	}

	@Override
	protected List<String> getCompletions(int length) {
		return List.of(new String[0]);
	}

}
