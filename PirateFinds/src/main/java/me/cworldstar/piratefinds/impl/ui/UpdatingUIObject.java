package me.cworldstar.piratefinds.impl.ui;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.cworldstar.piratefinds.impl.events.UIUpdateEvent;
import me.cworldstar.piratefinds.impl.ui.updating.UpdatingUIHandler;

public abstract class UpdatingUIObject extends BaseUIObject implements Listener {

	private HashMap<Integer, UpdatingUIHandler> update_handlers = new HashMap<Integer, UpdatingUIHandler>();
	
	public UpdatingUIObject(Player player, InventorySize size) {
		super(player, size);
	}
	
	public UpdatingUIObject(Player player, InventorySize size, String title) {
		super(player, size, title);
	}
	
	private void onUpdate(UIUpdateEvent e) {
		update_handlers.forEach((Integer slot, UpdatingUIHandler handler) -> {
			handler.run(e);
		});
	}
	
	public void addUpdatingHandler(int slot, UpdatingUIHandler handler) {
		update_handlers.put(slot, handler);
	}
	
	public void update(UIUpdateEvent e) {
		onUpdate(e);
	}
	

}
