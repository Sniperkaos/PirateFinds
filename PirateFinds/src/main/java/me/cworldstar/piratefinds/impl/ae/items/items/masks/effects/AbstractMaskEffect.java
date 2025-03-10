package me.cworldstar.piratefinds.impl.ae.items.items.masks.effects;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class AbstractMaskEffect {
	
	private List<PotionEffect> effects = new ArrayList<PotionEffect>();
	private List<BiConsumer<Player, ItemStack>> handlers = new ArrayList<BiConsumer<Player, ItemStack>>();
	
	public AbstractMaskEffect() {
	
	}
	
	public void addHandler(BiConsumer<Player, ItemStack> handler) {
		this.handlers.add(handler);
	}
	
	public void addEffect(PotionEffect effect) {
		this.effects.add(effect);
	}
	
	public void run(Player player, ItemStack item) {
		this.handlers.forEach((BiConsumer<Player, ItemStack> handler) -> {
			handler.accept(player, item);
		});
	}
	
}
