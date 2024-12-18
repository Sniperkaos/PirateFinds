package me.cworldstar.piratefinds.impl.ui;

import org.bukkit.inventory.ItemStack;

public class StyleFunction {
	

	BaseUIObject edit;
	
	
	public StyleFunction setBorderObject(ItemStack item) {
		return this;
	}
	
	public StyleFunction(BaseUIObject to_edit) {
		this.edit = to_edit;
	}
}
