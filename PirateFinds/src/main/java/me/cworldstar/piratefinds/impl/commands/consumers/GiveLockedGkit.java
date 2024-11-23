package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.AEExpansion;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import net.advancedplugins.ae.utils.YamlFile;

public class GiveLockedGkit extends CommandConsumer<CommandSender> {
	public GiveLockedGkit() {
		this.setPermission("pf.commands.givelockedgkit");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		PirateFinds plugin = PirateFinds.getThisPlugin();
		AEExpansion expansion = PirateFinds.getAEExpansion();
		if(expansion.isLoaded()) {
			expansion.givePlayerLockedGkit((plugin.getServer().getPlayer(args.get(0))), args.get(1));
		} else {
			((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[PirateFinds]: &e&lMODERATE:&r &cAE is not loaded, so this command will not work."));
		}
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
			case 2:
				return new ArrayList<String>(YamlFile.c.getConfig().getConfigurationSection("kits").getKeys(false));
		}
		return new ArrayList<String>(List.of(new String[0]));
	}
}
