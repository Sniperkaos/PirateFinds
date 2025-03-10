package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.AEExpansion;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.ui.test.GKitPreviewUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.utils.YamlFile;

public class PreviewGKit extends CommandConsumer<CommandSender> {

	public PreviewGKit() {
		hide = true;
		this.setPermission("pf.commands.previewgkit");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		Player A = PirateFinds.getServerStatic().getPlayer(args.get(0));
		if(A == null) {
			player.sendMessage(ChatUtils.createBroadcast("&7Invalid player name."));
			return;
		}
		GKitPreviewUI ui = AEExpansion.previewGKit(A, args.get(1));
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
		case 2:
			return new ArrayList<String>(YamlFile.U.getConfig().getConfigurationSection("kits").getKeys(false));
	}
	return new ArrayList<String>(List.of(new String[0]));
	}

}
