package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.AEExpansion;
import me.cworldstar.piratefinds.impl.ae.listeners.Locked;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;

public class UnlockItem extends CommandConsumer<CommandSender> {

	public UnlockItem() {
		setPermission("pf.commands.lockitem");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		if(player instanceof Player) {
			PirateFinds.getThisPlugin();
			AEExpansion expansion = PirateFinds.getAEExpansion();
			if(expansion.isLoaded()) {
				Locked.unlock(((Player) player).getInventory().getItemInMainHand());
				
			} else {
				((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[PirateFinds]: &e&lMODERATE:&r &cAE is not loaded, so this command will not work."));
			}
		}
	}
	
	@Override
	protected List<String> getCompletions(int length) {
		// TODO Auto-generated method stub
		return new ArrayList<String>(List.of(new String[] {
				
		}));
	}

}