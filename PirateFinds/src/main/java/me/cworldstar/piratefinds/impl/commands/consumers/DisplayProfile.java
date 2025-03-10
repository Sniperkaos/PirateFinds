package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.profile.PlayerProfile;
import me.cworldstar.piratefinds.impl.profile.Profile;

public class DisplayProfile extends CommandConsumer<CommandSender>{

	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		Player target = Bukkit.getPlayer(args.get(0));
		Optional<Profile> profile = PlayerProfile.getPlayerProfile(target);
		if(profile.isPresent()) {
			profile.get().print(((Player) player));
		} else {
			target.sendMessage("&[ &c&l! &7] &cYou do not have an active profile!");
		}
	}

	@Override
	protected List<String> getCompletions(int length) {
		return Arrays.asList(new String[0]);
	}
}
