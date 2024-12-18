package me.cworldstar.piratefinds.impl;

import org.bukkit.Sound;

public class DropCommand {

	private String awardMessage = "&7If you read this, this reward has no dropmessage.";
	private String command;
	private String sound = Sound.BLOCK_BELL_RESONATE.toString();
	
	public DropCommand(String command, String awardMessage) {
		this.command = command;
		this.awardMessage = awardMessage;
	}
	
	public DropCommand(String command, String awardMessage, String sound) {
		this.command = command;
		this.awardMessage = awardMessage;
		if(sound == null) {
			return;
		}
		this.sound = sound;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getAwardMessage() { 
		return awardMessage;
	}
	
	public Sound getSound() {
		return Sound.valueOf(sound);
	}
	
}
