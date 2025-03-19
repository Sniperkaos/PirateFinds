package me.cworldstar.piratefinds.auctioneer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NumberRange;
import org.apache.commons.lang3.Range;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.vault.VaultImpl;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class ConfigurationDeal {
	private double base_price = 0.0;
	private double max_discount = 100;
	private double min_discount = -100;

	private double final_discount;
	private int price;
	private NumberRange<Integer> amount;
	
	private ItemStack displayItem;
	
	private List<String> commands;
	
	public int getAmount() {
		return amount.getMaximum();
	}

	
	public ConfigurationDeal(double base_price, double min_discount, double max_discount, ConfigurationSection displayItem, List<String> commands, NumberRange<Integer> amount) {
		
		
		this.base_price = base_price;
		this.min_discount = min_discount;
		this.max_discount = max_discount;
		this.amount = amount;
		
		this.final_discount = (Math.random() * (this.max_discount - this.min_discount + 1) + this.min_discount);
		
		this.price = (int) Math.round((this.base_price + (this.base_price * (this.final_discount / 100))));
		if(displayItem.getString("material") == "PLAYER_HEAD") {
			this.displayItem = SkullCreator.itemFromBase64(displayItem.getString("skull_id"));
		} else {
			this.displayItem = new ItemStack(Material.valueOf(displayItem.getString("material")));
		}

		ItemMeta meta = this.displayItem.getItemMeta();
		meta.setLore(displayItem.getStringList("lore"));
		meta.setDisplayName(displayItem.getString("name"));
		meta.setCustomModelData(displayItem.getInt("data"));
		this.displayItem.setItemMeta(meta);
		
		this.commands = commands;
	}
	
	public ConfigurationDeal(double base_price, double min_discount, double max_discount, ItemStack displayItem, List<String> commands, NumberRange<Integer> amount) {
		
		this.base_price = base_price;
		this.min_discount = min_discount;
		this.max_discount = max_discount;
		this.amount = amount;
		this.final_discount = (Math.random() * (this.max_discount - this.min_discount + 1) + this.min_discount);
		this.price = (int) Math.round((this.base_price + (this.base_price * (this.final_discount / 100))));
		this.displayItem = displayItem;
		
		this.commands = commands;
	}
	
	public ConfigurationDeal clone() {
		return new ConfigurationDeal(this.base_price, this.min_discount, this.max_discount, displayItem, commands, amount);
	}
	
	public List<String> getCommands() {
		return commands;
	}

	public int getPrice() {
		return price;
	}
	
	public double getDiscount() {
		return final_discount;
	}
	
	public ItemStack getDisplayItem() {
		return displayItem;
	}
	
	public boolean apply(Player p) {
		boolean has_money = VaultImpl.getEconomy().get().has(p, price);
		Map<String, ConfigSound> sounds = Auctioneer.getSounds();
		Map<String, String> messages = Auctioneer.getMessages();
		if(has_money) {
			p.sendMessage(Auctioneer.tags(p, messages.get("purchase")));
			sounds.get("purchase").play(p);
			VaultImpl.getEconomy().get().withdrawPlayer(p, price);
			int amount = (int) Math.round(Math.random() * (this.amount.getMaximum() - this.amount.getMinimum() + 1) + this.amount.getMinimum());
			if(amount > this.amount.getMaximum()) {
				amount = this.amount.getMaximum();
			}
			for(int i=0; i<amount; i++) {
				this.commands.forEach((String command) -> {
					PirateFinds.getServerStatic().dispatchCommand(Bukkit.getServer().getConsoleSender(), PlaceholderAPI.setPlaceholders(p, command));
				});
			}
			return true;
		} else {
			p.sendMessage(Auctioneer.tags(p,messages.get("no-money")));
			sounds.get("no-money").play(p);
			PirateFinds.logDebug("broke asf");
			return false;
		}
	}
	
	public ConfigurationDeal(int price, double discount, ItemStack displayItem, List<String> commands, NumberRange<Integer> range) {
		
		this.price = price;
		this.final_discount = discount;
		this.amount = range;
		this.displayItem = displayItem;
		
		this.commands = commands;
	}

	private Map<String, Object> data = new HashMap<String, Object>();
	
	public void setData(String string, Object dta) {
		data.put(string, dta);
	}

	public Object getData(String dta) {
		return data.get(dta);
	}
	
}
