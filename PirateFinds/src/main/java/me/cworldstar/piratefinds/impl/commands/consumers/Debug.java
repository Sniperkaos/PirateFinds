package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class Debug extends CommandConsumer<CommandSender> {

	public Debug() {
		this.setPermission("pf.debugcommand");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		switch(args.get(0)) {
			case "checkItem":
				Player p = (Player) player;
				p.sendMessage(PFItemClass.getItem(p.getInventory().getItemInMainHand()).getPFItemID());
				break;
			default:
				player.sendMessage(ChatUtils.createBroadcast("&7This argument does not exist."));
		}
	}

	@Override
	protected List<String> getCompletions(int length) {
		switch(length) {
			case 1:
				return Arrays.asList(new String[] {
						"checkItem"
				});
			default:
				return Arrays.asList(new String[] {
						"checkItem"
				});
		}

	}

}
