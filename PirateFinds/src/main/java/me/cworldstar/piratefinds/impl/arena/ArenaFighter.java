package me.cworldstar.piratefinds.impl.arena;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.vault.VaultImpl;
import net.advancedplugins.ae.api.AEAPI;
import net.advancedplugins.ae.enchanthandler.enchantments.AdvancedEnchantment;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class ArenaFighter {
	
	private Player player;
	private int bounty=0;
	private int killStreak=0;
	
	public enum AwardType {
		XP,
		MONEY,
		ITEM
	}
	
	
	public ArenaFighter(Player p) {
		this.player = p;
	}
	
	public void awardKill(ArenaFighter killed) {
		int bounty = killed.getBounty();
		int streak = killed.getKillstreak();
		
		
		ConfigurationSection awards = PirateFinds.getThisPlugin().getConfig().getConfigurationSection("arena.arena-award");
		for(String key : awards.getKeys(false)) {
			
			ConfigurationSection current_section = awards.getConfigurationSection(key);
			
			String SECTION_GETTER = "arena.arena-award" + key;
			
			AwardType award = AwardType.valueOf(current_section.getString("type").toUpperCase());
			if(award == null) {
				award = AwardType.MONEY;
			}
			
			int killstreakValue = current_section.getInt("killstreak");
			if(killstreakValue > killStreak) {
				continue;
			}

			switch(award) {
				case MONEY:
					int money_amt = current_section.getInt("amount");
					Optional<Economy> econ = VaultImpl.getEconomy();
					if(econ.isPresent()) {
						Economy economy = econ.get();
						economy.depositPlayer(player, bounty + money_amt);
						player.sendMessage(ChatUtils.createBroadcast("Gained " + String.valueOf(bounty + money_amt) + "$ from killing a player."));
					}
					break;
				case XP:
					int xp_amt = current_section.getInt("amount");;
					player.giveExp(bounty + xp_amt);
					player.sendMessage(ChatUtils.createBroadcast("Gained " + String.valueOf(bounty) + " XP from killing a player."));
					break;
				case ITEM:
					String material = current_section.getString("material");
					String itemName = current_section.getString("item-name");
					List<String> lore = current_section.getStringList("lore");
					lore.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));
					ItemStack item = new ItemStack(Material.valueOf(material.toUpperCase()));
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemName));
					if(current_section.getBoolean("glowing")) {
						meta.setEnchantmentGlintOverride(true);
					}
					
					List<String> enchants = current_section.getStringList("enchants");
					
					if(enchants != null) {
						for(String enchantStr : enchants.toArray(new String[0])) {
							
							String enchantStrSanitized;
							int enchantLevel;
							
							String[] enchantSplit = enchantStr.split(":");
							enchantStrSanitized = enchantSplit[0];
							enchantLevel = Integer.parseInt(enchantSplit[1]);
							
							
							Enchantment enchant = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(enchantStrSanitized));
							if(enchant != null) {
								meta.addEnchant(enchant, enchantLevel, true);
								continue;
							} else {
								boolean aEnchant = AEAPI.getAllEnchantments().contains(enchantStrSanitized);
								if(aEnchant) {
									AEAPI.applyEnchant(enchantStrSanitized, enchantLevel, item);
								}
							}
						}
					}
					
					meta.setLore(lore);
					item.setItemMeta(meta);
					player.getInventory().addItem(item);
			}	
		}
		
		if(killStreak > 2) {
			player.sendMessage(ChatUtils.createBroadcast(this.player.getName() + " is on a " + Integer.toString(killStreak) + " streak! Will anyone shut them down?"));
		}
		
		if(streak > 2) {
			PirateFinds.getThisPlugin().getServer().getOnlinePlayers().forEach((Player player) -> {
				player.sendMessage(ChatUtils.createBroadcast(this.player.getName() + " has shutdown the " + Integer.toString(streak) + " streak of " + killed.getFighter().getName() +"!"));
			});
		}
	}
	
	public Player getFighter() {
		return this.player;
	}
	
	public int getBounty() {
		return this.bounty;
	}
	
	public int getKillstreak() {
		return this.killStreak;
	}

	public void increaseStreak() {
		this.killStreak += 1;
	}
	
	
	
}
