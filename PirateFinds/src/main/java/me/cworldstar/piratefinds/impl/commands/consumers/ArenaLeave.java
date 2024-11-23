package me.cworldstar.piratefinds.impl.commands.consumers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.arena.Arena;
import me.cworldstar.piratefinds.impl.commands.CommandConsumer;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class ArenaLeave extends CommandConsumer<CommandSender> {

	private static HashMap<UUID, BukkitTask> leaving = new HashMap<UUID, BukkitTask>();
	
	@Override
	protected void execute(CommandSender sender, ArrayList<String> args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Arena arena = PirateFinds.getArena();
			if(leaving.get(player.getUniqueId()) != null) {
				player.sendMessage(ChatUtils.createBroadcast("&7You are already leaving the arena!"));
				return;
			}
			
			player.sendMessage(ChatUtils.createBroadcast("&7You are leaving the arena! Please stand still."));
			Location last_location = player.getLocation();
			
			leaving.put(player.getUniqueId(), 
			new BukkitRunnable() {
				
				int count=0;
				
				@Override
				public void run() {
					if(!last_location.equals(player.getLocation())) {
						player.sendMessage(ChatUtils.createBroadcast("&7You moved, so your leaving has been cancelled."));
						leaving.get(player.getUniqueId()).cancel();
						leaving.remove(player.getUniqueId());
						return;
					}
					
					player.sendTitle(Integer.toString(count), "", 1, 0, 1);
					
					if(count >= 5) {
						player.sendMessage(ChatUtils.createBroadcast("&7You have left the arena!"));
						arena.arenaLeave(player);
						player.teleport(player.getWorld().getSpawnLocation());
					}
					
					count++;
				}
				
			}.runTaskTimer(PirateFinds.getThisPlugin(), 20L, 0));
		}
	}

	@Override
	protected List<String> getCompletions(int length) {
		return List.of(new String[] {
				
		});
	}

}
