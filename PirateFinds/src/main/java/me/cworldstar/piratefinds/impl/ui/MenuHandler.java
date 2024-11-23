package me.cworldstar.piratefinds.impl.ui;

import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuHandler<P> {
	private Consumer<P> consumer;	
	private UUID uuid;
	
	public MenuHandler(Consumer<P> toRun) {
		this.uuid = UUID.randomUUID();
		this.consumer = toRun;
	}
	
	public UUID getIdentifier() {
		return this.uuid;
	}
	
	public void run(P e) {
		this.consumer.accept(e);
	}
}
