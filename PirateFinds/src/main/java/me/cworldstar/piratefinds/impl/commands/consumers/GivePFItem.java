package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.ui.test.PFItemGiveGUI;
import me.cworldstar.piratefinds.impl.utils.InventoryUtils;

public class GivePFItem extends CommandConsumer<CommandSender> {

	public GivePFItem() {
		this.setPermission("pf.commands.givepfitem");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		
		if(args.size() == 0) {
			new PFItemGiveGUI((Player) player).open();
			return;
		}
		
		if(args.get(0).contains("all")) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				AbstractPFItem item = PFItemClass.getItem(args.get(1));
				if(item != null) {
					if(args.size() >= 3) {
						for(int i=0; i<Integer.parseInt(args.get(2)); i++) {
							InventoryUtils.addOrDropItem(p.getInventory(), item.build());
						}
					} else {
						InventoryUtils.addOrDropItem(p.getInventory(), item.build());
					}
				}
			}
			return;
		}
		
		Player to_give = PirateFinds.getServerStatic().getPlayer(args.get(0));
		if(args.size() == 1) {
			new PFItemGiveGUI(to_give).open();
			return;
		}
		AbstractPFItem item = PFItemClass.getItem(args.get(1));

		if(item != null) {
			if(args.get(2) != null) {
				for(int i=0; i<Integer.parseInt(args.get(2)); i++) {
					to_give.getInventory().addItem(item.build());
				}
			} else {
				to_give.getInventory().addItem(item.build());
			}

		}
	}

	@Override
	protected List<String> getCompletions(int length) {
		switch(length) {
			case 1:
				ArrayList<String> completions = new ArrayList<String>(List.of(new String[] {
						"all"
				}));
				
				PirateFinds.getThisPlugin().getServer().getOnlinePlayers().stream().forEach((Player p) -> {
					completions.add(p.getName());
				});
				return completions;
			case 2:
				return List.of(PFItemClass.getItems().keySet().toArray(new String[0]));
			case 3:
				return List.of(new String[] {
					"1",
					"2",
					"3",
					"4",
					"5",
					"6",
					"7",
					"8",
					"9",
					"10"
				});
		}
		return List.of(new String[] {
				
		});
	}

}
