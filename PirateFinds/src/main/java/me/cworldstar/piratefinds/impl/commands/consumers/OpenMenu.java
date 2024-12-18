package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;



public class OpenMenu extends CommandConsumer<CommandSender> {

	public OpenMenu() {
		hide = true;
		setPermission("pf.commands.openmenu");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		
		try {
			Class<?> c = Class.forName("me.cworldstar.piratefinds.impl.ui.test." + args.get(1));
			try {
				Constructor<?> construct = c.getConstructor(Player.class);
				try {
					BaseUIObject object = (BaseUIObject) construct.newInstance(PirateFinds.getServerStatic().getPlayer(args.get(0)));
					object.open();
				
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected List<String> getCompletions(int length) {
		ArrayList<String> completions = new ArrayList<String>(List.of(new String[] {
				
		}));
		
		switch(length) {
			case 1:
				PirateFinds.getThisPlugin().getServer().getOnlinePlayers().stream().forEach((Player p) -> {
					completions.add(p.getName());
				});
				break;
			case 2:
				completions.addAll(Arrays.asList(new String[] {
						"EnchantEaterUI",
						"GKitPreviewUI",
						"LootboxUI",
						"MysteriousManUI",
						"PFItemGiveGUI",
						"RepairMasterUI",
						"TestUI"
				}));
		}
		

		
		return completions;
	}

}
