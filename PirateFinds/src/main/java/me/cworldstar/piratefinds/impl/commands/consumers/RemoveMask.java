package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.impl.ae.items.items.masks.SantaMask;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;

public class RemoveMask extends CommandConsumer<CommandSender> {

	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		SantaMask.remove(
				((Player) player).getInventory().getItemInMainHand()
		);
	}

	@Override
	protected List<String> getCompletions(int length) {
		// TODO Auto-generated method stub
		return null;
	}

}
