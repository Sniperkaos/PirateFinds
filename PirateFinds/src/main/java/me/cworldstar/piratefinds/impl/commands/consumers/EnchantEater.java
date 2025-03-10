package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.ui.test.EnchantEaterUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class EnchantEater extends CommandConsumer<CommandSender>{

		public EnchantEater() {
			hide = true;
		}
		
		@Override
		protected void execute(CommandSender player, ArrayList<String> args) {
			
			//if(player instanceof Player) return;
			
			Player A = PirateFinds.getServerStatic().getPlayer(args.get(0));
			if(A == null) {
				player.sendMessage(ChatUtils.createBroadcast("&7Invalid player name."));
				return;
			}
			EnchantEaterUI ui = new EnchantEaterUI(A);
			ui.open();
		}

		@Override
		protected List<String> getCompletions(int length) {
			switch(length) {
			case 1:
				ArrayList<String> list = new ArrayList<String>();
				for(Player p : PirateFinds.getServerStatic().getOnlinePlayers().toArray(new Player[0])) {
					list.add(p.getName());
				}
				return list;
			}
			return new ArrayList<String>(List.of(new String[] {
				
			}));
		}
}
