package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.ui.test.PFRecipeViewer;

public class Recipes extends CommandConsumer<CommandSender> {

	public Recipes() {

	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		if(player instanceof Player) {
			new PFRecipeViewer((Player) player).open();;
		}
	}

	@Override
	protected List<String> getCompletions(int length) {
		return new ArrayList<String>(List.of(new String[] {
				
		}));
	}

}
