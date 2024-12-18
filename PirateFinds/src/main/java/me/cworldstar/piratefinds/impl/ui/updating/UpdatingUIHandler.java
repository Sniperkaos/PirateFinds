package me.cworldstar.piratefinds.impl.ui.updating;

import java.util.function.Consumer;

import me.cworldstar.piratefinds.impl.events.UIUpdateEvent;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;

public class UpdatingUIHandler extends MenuHandler<UIUpdateEvent> {
	public UpdatingUIHandler(Consumer<UIUpdateEvent> e) {
		super(e);
	}
}
	