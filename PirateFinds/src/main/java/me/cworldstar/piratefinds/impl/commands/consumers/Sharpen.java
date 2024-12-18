package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.items.Sharpener;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;

public class Sharpen extends CommandConsumer<CommandSender> {
	public Sharpen() {
		this.setPermission("pf.commands.sharpen");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		Player the_player = PirateFinds.getServerStatic().getPlayer(args.get(0));
		if(!this.hasPermission(the_player)) return;
		
		for(int i=0; i<Integer.parseInt(args.get(1)); i++) {
			Sharpener.APPLY_SHARPENER(the_player.getInventory().getItemInMainHand(), false);
		}
	}

	@Override
	protected List<String> getCompletions(int length) {
		ArrayList<String> completions = new ArrayList<String>();
		
		switch(length) {
			case 1:
				completions.addAll(PirateFinds.getServerStatic().getOnlinePlayers()
					.stream()
					.map(Player::getName)
					.collect(Collectors.toList())
				);
				break;
			case 2:
				return List.of(new String[] {
						"1",
						"2",
						"3",
						"4",
						"5",
						"6",
						"7",
						"8",
						"9",
						"10"
				});
		}
		
		return completions;
		
	}
}
