package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.drop.Drop;
import me.cworldstar.piratefinds.impl.drop.DropImpl;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class ToggleDrop extends CommandConsumer<CommandSender>  {

	@Override
	protected void execute(CommandSender sender, ArrayList<String> args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Drop drop = PirateFinds.getDropImpl();
			DropImpl playerImpl = drop.getDropImpl(player);
			playerImpl.setEnabled(!playerImpl.isEnabled());
			player.sendMessage(ChatUtils.createBroadcast("&7Drop protector is now " + Boolean.toString(playerImpl.isEnabled())));
		}
	}

	@Override
	protected List<String> getCompletions(int length) {
		// TODO Auto-generated method stub
		return List.of(new String[0]);
	}

}
