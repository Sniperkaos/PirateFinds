package me.cworldstar.piratefinds.impl.lootbox;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cworldstar.piratefinds.PirateFinds;

public class LootboxReward<T> {
	public static enum LootboxRewardType {
		COMMAND,
		ITEM
	}
	
	private LootboxRewardType reward;
	private int amount;
	private ItemStack placeholder;
	private int chance = 100;
	
	private ArrayList<String> commands = new ArrayList<String>();
	private ArrayList<ItemStack> items = new ArrayList<ItemStack>(); 
	
	@SuppressWarnings("unchecked")
	public LootboxReward(List<T> object, LootboxRewardType type) {
		this.reward = type;
		this.amount = 1;
		if(object instanceof List<?>) {
			List<?> list = (List<?>) object;
	        if (list.stream().allMatch(String.class::isInstance)) {
	            commands.addAll((List<String>) list);
	        } else if (list.stream().allMatch(ItemStack.class::isInstance)) {
	        	items.addAll((List<ItemStack>) list);
	        }
		}
	}
	
	public void setPlaceholder(ItemStack item) {
		this.placeholder = item;
	}
	
	public void setChance(int chance) {
		this.chance = chance;
	};
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public LootboxRewardType getRewardType() {
		return this.reward;
	}
	
	public void awardTo(Player p) {
		for(int i=0; i<=amount; i++) {
			switch(reward) {
				case COMMAND:
					commands.forEach((String command) -> {
						PirateFinds.getServerStatic().dispatchCommand(Bukkit.getServer().getConsoleSender(), PlaceholderAPI.setPlaceholders(p, command));
					});
					break;
				case ITEM:
					items.forEach((ItemStack item) -> {
						p.getInventory().addItem(item);
					});
					break;
				default:
					break;
			}
		}
	}

	public ItemStack getPlaceholder() {
		return this.placeholder;
	}

	public int getChance() {
		return this.chance;
	}
	
}
