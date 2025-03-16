package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.auctioneer.Auctioneer;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.ui.test.MysteriousManUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class Auction extends CommandConsumer<CommandSender> {

	public Auction() {
		
	}
	
	@Override
	public void execute(CommandSender player, ArrayList<String> args) {
		switch(args.get(0)) {
			case "refresh":
				if(!player.hasPermission("cw.auction.refresh")) break;
				switch(args.size()-1) {
					case 0:
						Auctioneer.refresh((Player) player);
						break;
					default: 
						Auctioneer.refresh(PirateFinds.getServerStatic().getPlayer(args.get(0)));
						break;
				}
				break;
			case "open":
				new MysteriousManUI((Player) player).open();
				break;
			default:
				player.sendMessage(ChatUtils.createBroadcast("This command does not exist."));
		}
	}

	@Override
	public List<String> getCompletions(int length) {
		switch(length) {
			case 1:
				return Arrays.asList(new String[] {
					"open"	
				});
			default:
				return List.of();
		}
	}

}
