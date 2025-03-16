package me.cworldstar.piratefinds.auctioneer;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ConfigSound {
	private Sound sound;
	private double pitch;
	private double volume;
	
	public ConfigSound(Sound sound, double pitch, double volume) {
		this.sound = sound; this.pitch = pitch; this.volume = volume;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public double getPitch() {
		return pitch;
	}
	
	public double getVolume() {
		return volume;
	}

	public void play(Player owner) {
		owner.playSound(owner, sound, (float) volume, (float) pitch);
	}
	
}
