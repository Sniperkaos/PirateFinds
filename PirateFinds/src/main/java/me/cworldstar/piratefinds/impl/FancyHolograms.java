package me.cworldstar.piratefinds.impl;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Display;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class FancyHolograms {
	
	private HologramManager manager;
	
	public HologramManager getHologramManager() {
		return manager;
	}
	
	public FancyHolograms() {
		manager = FancyHologramsPlugin.get().getHologramManager();
	}
	
	public void create_hologram(String string, Location at, List<String> lore) {
		TextHologramData hologramData = new TextHologramData(string, at);
		hologramData.setLocation(at);
		hologramData.removeLine(0);
		hologramData.setBillboard(Display.Billboard.CENTER);
		for(String s : lore.toArray(new String[0])) {
			hologramData.addLine(ChatUtils.apply(s));
		}
		
		Hologram hologram = manager.create(hologramData);
		manager.addHologram(hologram);
	}
	
}
