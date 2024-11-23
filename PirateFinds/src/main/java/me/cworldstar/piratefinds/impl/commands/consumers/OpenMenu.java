package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.ui.test.TestUI;

public class OpenMenu extends CommandConsumer<CommandSender> {

	public OpenMenu() {
		hide = true;
		setPermission("pf.commands.openmenu");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		TestUI ui = new TestUI(PirateFinds.getServerStatic().getPlayer(args.get(0)));
		ui.open();
	}
	
	@Override
	protected List<String> getCompletions(int length) {
		ArrayList<String> completions = new ArrayList<String>(List.of(new String[] {
				
		}));
		
		PirateFinds.getThisPlugin().getServer().getOnlinePlayers().stream().forEach((Player p) -> {
			completions.add(p.getName());
		});
		
		return completions;
	}

}
