package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class FixBook extends CommandConsumer<CommandSender> {
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		if(!(player instanceof Player)) {
			player.sendMessage("This is a player only command.");
			return;
		}
		
		Player p = (Player) player;
		ItemStack i = p.getInventory().getItemInMainHand();
		if(i == null || i.getType() != Material.ENCHANTED_BOOK) {
			p.sendMessage(ChatUtils.createBroadcast("&7You must be holding a book for this to work."));
			return;
		}
		
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) i.getItemMeta();
		Map<Enchantment, Integer> enchants = meta.getEnchants();
		if(enchants.size() == 0) return;
		for(Entry<Enchantment, Integer> set : enchants.entrySet()) {
			meta.removeEnchant(set.getKey());
			meta.addStoredEnchant(set.getKey(), set.getValue(), true);
		}
		i.setItemMeta(meta);
	}
	
	@Override
	protected List<String> getCompletions(int length) {
		ArrayList<String> completions = new ArrayList<String>(List.of(new String[] {
				
		}));
		return completions;
		
	}
}

