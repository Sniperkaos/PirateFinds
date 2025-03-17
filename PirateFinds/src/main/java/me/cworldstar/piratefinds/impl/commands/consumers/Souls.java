package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ae.items.items.SoulGem;
import me.cworldstar.piratefinds.impl.ae.items.items.SoulVoucher;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.InventoryUtils;

public class Souls extends CommandConsumer<CommandSender> {
	
	public Souls() {
		
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		Player p = (Player) player;
		ItemStack mainHand = p.getInventory().getItemInMainHand();
		AbstractPFItem item = PFItemClass.getItem(mainHand);
		
		switch(args.get(0)) {

			case "withdraw":
				if(
						!PFItemClass.compare(item, PFItemClass.nullItem()) &&
						PFItemClass.compare(item, PFItemClass.getItem("SoulGem")) == true
				) {
					int souls = SoulGem.getSouls(mainHand);
					SoulGem.setSouls(mainHand, 0);
					InventoryUtils.addOrDropItem(p.getInventory(), ((SoulVoucher) PFItemClass.getItem("SoulVoucher")).create(souls));
				}
				break;
			case "add":
				if(!p.hasPermission("cw.souls.add")) return;
				if(
						!PFItemClass.compare(item, PFItemClass.nullItem()) &&
						PFItemClass.compare(item, PFItemClass.getItem("SoulGem")) == true
				) {
					SoulGem.updateSouls(mainHand, Integer.valueOf(args.get(1)));
				}
				break;
			case "remove": 
				if(!p.hasPermission("cw.souls.remove")) return;
				if(
						!PFItemClass.compare(item, PFItemClass.nullItem()) &&
						PFItemClass.compare(item, PFItemClass.getItem("SoulGem")) == true
				) {
					SoulGem.updateSouls(mainHand, -Integer.valueOf(args.get(1)));
				}
				break;
			default:
				player.sendMessage(ChatUtils.createBroadcast("&7This argument does not exist."));
		}
	}

	@Override
	protected List<String> getCompletions(Player p, int length) {
		
		List<String> completions = new ArrayList<String>();
		
		switch(length) {
			case 1:
				completions.add("withdraw");
				if(p.hasPermission("cw.souls.add")) {
					completions.add("add");
					completions.add("remove");
				}
				break;
			default:
				return Arrays.asList(new String[] {
						""
				});
		}
		
		return completions;

	}
}
