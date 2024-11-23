package me.cworldstar.piratefinds.impl.ae;

import org.bukkit.inventory.ItemStack;

import net.advancedplugins.ae.api.AEAPI;

public class InternalEnchantment {
	
	private String enchantment;
	private int level;
	
	public InternalEnchantment(String enchantment, int level) {
		this.enchantment = enchantment;
		this.level = level;
	}
	
	public String getEnchantment() {
		return this.enchantment;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void apply(ItemStack item) {
		AEAPI.applyEnchant(enchantment, level, item);
	}
}
