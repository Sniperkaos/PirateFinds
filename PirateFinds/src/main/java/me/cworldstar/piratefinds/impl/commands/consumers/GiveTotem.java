package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ae.items.items.EnchantmentTotem;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import net.advancedplugins.ae.api.AEAPI;

public class GiveTotem extends CommandConsumer<CommandSender> {

	public static List<String> ALL_ENCHANTMENTS;
	public static List<String> ANY_NUMBERS = Arrays.asList(
			new String[] {
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
			}
	);
	static {
		ALL_ENCHANTMENTS = Arrays.asList(Registry.ENCHANTMENT.stream()
				.toList().stream()
				.map(Enchantment::getKey)
				.map(NamespacedKey::getKey)
				.toList().toArray(new String[0]));
	}
	
	public GiveTotem() {
		this.setPermission("pf.commands.givepfitem");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		Player p = PirateFinds.getServerStatic().getPlayer(args.get(0));
		if(p != null) {
			EnchantmentTotem totem = (EnchantmentTotem) PFItemClass.getItem(args.get(1));
			ItemStack totem_i = totem.buildSpecific(args.get(2), Integer.valueOf(args.get(3)), Double.valueOf(args.get(4)));
			p.getInventory().addItem(totem_i);
		}
	}

	private List<String> players() {
		List<String> completions = new ArrayList<String>();
		completions.addAll(PirateFinds.getServerStatic().getOnlinePlayers()
				.stream()
				.map(Player::getName).toList()
		);
		return completions;
	}
	
	@Override
	protected List<String> getCompletions(int length) {
		switch(length) {
			case 1:
				return players();
			case 2:
				return PFItemClass.getRegisteredTotems();
			case 3:
				return ALL_ENCHANTMENTS; // enchantment
			case 4:
				return ANY_NUMBERS; // tier
			case 5:
				return ANY_NUMBERS; // chance
			default:
				return Arrays.asList(new String[0]);
		}
	}

}
