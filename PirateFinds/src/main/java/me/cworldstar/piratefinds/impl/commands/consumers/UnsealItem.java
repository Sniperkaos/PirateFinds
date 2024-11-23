package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.AEExpansion;
import me.cworldstar.piratefinds.impl.ae.seal.Unseal;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;

public class UnsealItem extends CommandConsumer<CommandSender> {

	public UnsealItem() {
		this.setPermission("pf.commands.unsealitem");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		
		if(!(player instanceof Player)) {
			player.sendMessage("This command can only be executed by a player.");
			return;
		}

		AEExpansion expansion = PirateFinds.getAEExpansion();
		if(expansion.isLoaded()) {
			Unseal.unsealItem((Player) player, ((Player) player).getInventory().getItemInMainHand());
			
		} else {
			((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[PirateFinds]: &e&lMODERATE:&r &cAE is not loaded, so this command will not work."));
		}
	}

	@Override
	protected List<String> getCompletions(int length) {
		// TODO Auto-generated method stub
		return List.of(new String[0]);
	}

}
