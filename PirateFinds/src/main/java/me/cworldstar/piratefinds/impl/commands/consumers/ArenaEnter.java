package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.arena.Arena;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class ArenaEnter extends CommandConsumer<CommandSender> {
	
	
	public ArenaEnter() {
		this.hide = true;
		setPermission("pf.commands.arenaenter");
	}
	
	@Override
	protected void execute(CommandSender player, ArrayList<String> args) {
		
		Player target = PirateFinds.getThisPlugin().getServer().getPlayer(args.get(0));
		
		
		
		Arena arena = PirateFinds.getArena();
		if(arena.getArenaFighter((Player) target) != null) {
			target.sendMessage(ChatUtils.createBroadcast("&7You are already in the &cArena&7!"));
			return;
		}
		arena.arenaEnter(target);
		target.sendMessage(ChatUtils.createBroadcast("&7You have entered the &cArena&7!"));
	}
	
	@Override
	protected List<String> getCompletions(int length) {
		// TODO Auto-generated method stub
		ArrayList<String> completions = new ArrayList<String>(List.of(new String[] {
				
		}));
		
		PirateFinds.getThisPlugin().getServer().getOnlinePlayers().stream().forEach((Player p) -> {
			completions.add(p.getName());
		});
		
		return completions;
		
	}
}
