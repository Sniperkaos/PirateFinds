package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.ui.test.CheckPFItemUI;

public class CheckItem extends CommandConsumer<CommandSender>  {

	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
	
		Player p = (Player) player;
		
		CheckPFItemUI ui = new CheckPFItemUI(p);
		ui.addUnclickableItem(4, 			
			PFItemClass.getItem(
				p.getInventory().getItemInMainHand()
			).build()
		);
		
		ui.open();
	}

	@Override
	protected List<String> getCompletions(int length) {
		return Arrays.asList(new String[] {});
	}

}
