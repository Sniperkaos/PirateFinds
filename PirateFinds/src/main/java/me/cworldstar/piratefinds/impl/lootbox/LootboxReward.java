package me.cworldstar.piratefinds.impl.lootbox;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class LootboxReward<T> {
	public static enum LootboxRewardType {
		COMMAND,
		ITEM, 
		PF_ITEM
	}
	
	private LootboxRewardType reward;
	private int amount;
	private ItemStack placeholder;
	private int chance = 100;
	private boolean broadcast = false;
	
	
	private String box_name = "undefined";
	private String broadcast_message = "&7&l%player_name% just won a %reward_name% from a reward chest! Congratulations!";
	private ArrayList<String> commands = new ArrayList<String>();
	private ArrayList<ItemStack> items = new ArrayList<ItemStack>(); 
	private ArrayList<AbstractPFItem> pf_items = new ArrayList<AbstractPFItem>(); 
	
	
	public static LootboxReward<?> fromConfigurationSection(ConfigurationSection section) {
		switch(section.getString("reward_type").toUpperCase()) {
			case "COMMAND":
				return new LootboxReward<String>(section.getStringList("commands"), LootboxRewardType.COMMAND);
			default:
				return null;
		}
	}
	
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
	
	public LootboxReward<T> setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
		return this;
	}
	
	public LootboxReward<T> setPlaceholder(ItemStack item) {
		this.placeholder = item;
		return this;
	}
	
	public LootboxReward<T> setChance(int chance) {
		this.chance = chance;
		return this;
	};
	
	public LootboxReward<T> setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public LootboxReward<T> setBoxName(String boxName) {
		this.box_name = boxName;
		return this;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public LootboxRewardType getRewardType() {
		return this.reward;
	}
	
	protected String apply_placeholders(String to_apply, String placeholder, String value) {
		return to_apply.replaceAll(placeholder, value);
	}
	
	private void do_broadcast(Player p) {
		
		String player_applied = apply_placeholders(broadcast_message, "%player_name%", p.getName());
		String reward_name_applied = apply_placeholders(player_applied,  "%reward_name%", this.getPlaceholder().getItemMeta().getItemName()+"&7&l");
		String box_name_applied = apply_placeholders(reward_name_applied, "%box_name%", this.box_name+" Lootbox&7&l");
		
		PirateFinds.getServerStatic().getOnlinePlayers().forEach((Player player) -> {
			player.sendMessage(ChatUtils.createBroadcast(box_name_applied));
		});
	}
	
	public void awardTo(Player p) {
		for(int i=0; i<amount; i++) {
			switch(reward) {
				case COMMAND:
					commands.forEach((String command) -> {
						PirateFinds.getServerStatic().dispatchCommand(Bukkit.getServer().getConsoleSender(), PlaceholderAPI.setPlaceholders(p, command));
					});
					
					if(broadcast) {
						do_broadcast(p);
					}
					
					break;
				case ITEM:
					items.forEach((ItemStack item) -> {
						p.getInventory().addItem(item);
					});
					
					if(broadcast) {
						do_broadcast(p);
					}
					
					break;
				case PF_ITEM:
					pf_items.forEach((AbstractPFItem item) -> {
						p.getInventory().addItem(item.build());
					});
					
					if(broadcast) {
						do_broadcast(p);
					}
					
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

	public LootboxReward<T> setBroadcastMessage(String broadcastMessage) {
		this.broadcast_message = broadcastMessage;
		return this;
	}
	
}
