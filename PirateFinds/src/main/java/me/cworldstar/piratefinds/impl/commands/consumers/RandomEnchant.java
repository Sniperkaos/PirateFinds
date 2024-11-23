package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.AEExpansion;
import me.cworldstar.piratefinds.impl.ae.InternalEnchantment;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;

public class RandomEnchant extends CommandConsumer<CommandSender> {

	public RandomEnchant() {
		this.setPermission("pf.commands.randomenchant");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		if(player instanceof Player) {
			PirateFinds.getThisPlugin();
			AEExpansion expansion = PirateFinds.getAEExpansion();
			if(expansion.isLoaded()) {
				ItemStack item = ((Player) player).getInventory().getItemInMainHand();
				int amount_of_enchants = Integer.parseInt(args.get(0));
				args.remove(0);
				ArrayList<InternalEnchantment> enchants = expansion.getRandomEnchantments((Player) player, item.getType(), amount_of_enchants, args);
				enchants.forEach((InternalEnchantment enchant) -> {
					enchant.apply(item);
				});
				
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
