package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.profile.PlayerProfile;
import me.cworldstar.piratefinds.impl.profile.Profile;

public class SetProfileStat extends CommandConsumer<CommandSender>{

	
	public SetProfileStat() {
		this.hide = true;
		this.setPermission("pf.profile.set");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		Player target = Bukkit.getPlayer(args.get(0));
		Optional<Profile> profile = PlayerProfile.getPlayerProfile(target);
		if(profile.isPresent()) {
			profile.get().setStat(args.get(1), Long.valueOf(args.get(2)));
		} else {
			target.sendMessage("&[ &c&l! &7] &cYou do not have an active profile!");
		}
	}

	@Override
	protected List<String> getCompletions(int length) {
		switch(length) {
			case 1:
				return Arrays.asList(PirateFinds.getThisPlugin().getServer().getOnlinePlayers().stream()
						.map(Player::getName).toList().toArray(new String[0]));
			case 2:
				return Arrays.asList(new String[] {
						"health",
						"mana",
						"critical",
						"strength",
						"intelligence",
						"dexterity"
				});
		}
		return Arrays.asList(new String[0]);
	}
}
