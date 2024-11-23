package me.cworldstar.piratefinds.impl.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public abstract class CommandConsumer<P> {

	private String permission;
	public boolean hide = false;
	
	protected abstract void execute(P player, ArrayList<String> args);
	
	public CommandConsumer() {
		
	}
	
	public String help() {
		return "The command does not implement a help string.";
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public boolean hasPermission(Player player) {
		
		if(this.permission == null) {
			return true;
		}
		
		return player.hasPermission(this.permission);
	}
	 
	public void accept(P player, ArrayList<String> args) {
		args.remove(0);
		this.execute(player, args);
	}

	protected abstract List<String> getCompletions(int length);
}
