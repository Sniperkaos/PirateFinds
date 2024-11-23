package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;

public class GivePFItem extends CommandConsumer<CommandSender> {

	public GivePFItem() {
		this.setPermission("pf.commands.givepfitem");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		Player to_give = PirateFinds.getServerStatic().getPlayer(args.get(0));
		AbstractPFItem item = PFItemClass.getItem(args.get(1));
		if(item != null) {
			to_give.getInventory().addItem(item.build());
		}
	}

	@Override
	protected List<String> getCompletions(int length) {
		switch(length) {
			case 1:
				ArrayList<String> completions = new ArrayList<String>(List.of(new String[] {
						
				}));
				
				PirateFinds.getThisPlugin().getServer().getOnlinePlayers().stream().forEach((Player p) -> {
					completions.add(p.getName());
				});
				return completions;
			case 2:
				return List.of(PFItemClass.getItems().keySet().toArray(new String[0]));
		}
		return List.of(new String[] {
				
		});
	}

}
