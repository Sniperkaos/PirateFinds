package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
						if(args.get(1) == "all") {
							for(Player p : PirateFinds.getServerStatic().getOnlinePlayers()) {
								Auctioneer.refresh(p);
							}
							return;
						}
						Auctioneer.refresh(PirateFinds.getServerStatic().getPlayer(args.get(1)));
						break;
				}
				break;
			case "open":
				new MysteriousManUI((Player) player).open();
				break;
			case "flagrefresh":
				Auctioneer.flagPlayerForReset(PirateFinds.getServerStatic().getOfflinePlayer(UUID.fromString(args.get(1))));
				break;
			default:
				player.sendMessage(ChatUtils.createBroadcast("This command does not exist."));
		}
	}

	@Override
	public List<String> getCompletions(int length) {
		List<String> completions = new ArrayList<String>();
		switch(length) {
			case 1:
				completions.add("open");
				break;
			default:
				return List.of();
		}
		return completions;
	}

}
