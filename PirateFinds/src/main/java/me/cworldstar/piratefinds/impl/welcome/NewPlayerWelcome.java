package me.cworldstar.piratefinds.impl.welcome;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.auctioneer.Auctioneer;
import me.cworldstar.piratefinds.impl.EnchantmentDealer;
import me.cworldstar.piratefinds.impl.commands.consumers.Bless;
import me.cworldstar.piratefinds.impl.profile.PlayerProfile;
import me.cworldstar.piratefinds.impl.profile.Profile;
import net.md_5.bungee.api.ChatColor;

public class NewPlayerWelcome implements Listener {
	public NewPlayerWelcome() {
		PirateFinds.registerListener(this);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		
		InventoryView i = e.getPlayer().getOpenInventory();
		
		PlayerProfile.savePlayerProfile(e.getPlayer());
	}
	
	
	@EventHandler
	public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent e) {
		Player player = e.getPlayer();
		Profile profile = PlayerProfile.loadPlayerProfile(e.getPlayer());
		long health = profile.getStat("health");
		// set the player's max HP based on the profile's health stat.
		AttributeInstance maxHP = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		maxHP.setBaseValue(health / 5);
		
		// auto bless when a player moves to a new world
		Bless.bless(player);
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
		
		EnchantmentDealer dealer = PirateFinds.getEnchantmentDealer();
		dealer.time(e.getPlayer());
		dealer.addDealsForPlayer(e.getPlayer());
		
		Profile profile = PlayerProfile.loadPlayerProfile(e.getPlayer());
		PlayerProfile.memorySaveProfile(profile);
		
		profile.print(player);
		
		long health = profile.getStat("health");
		
		// set the player's max HP based on the profile's health stat.
		AttributeInstance maxHP = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		maxHP.setBaseValue(health / 5);
		
		Auctioneer.onPlayerJoin(player);
		
		if(e.getPlayer().getLastPlayed() == 0) {
			
			List<String> message = PirateFinds.getThisPlugin().getConfig().getStringList("first-join.first-join-message");
			List<String> commands = PirateFinds.getThisPlugin().getConfig().getStringList("first-join.first-join-commands");
			
			commands = PlaceholderAPI.setPlaceholders(e.getPlayer(), commands);
			commands.replaceAll(command -> ChatColor.translateAlternateColorCodes('&', command));
			
			message = PlaceholderAPI.setPlaceholders(e.getPlayer(), message);
			message.replaceAll(loreline -> ChatColor.translateAlternateColorCodes('&', loreline));
			
			for(Player p: PirateFinds.getServerStatic().getOnlinePlayers().toArray(new Player[0])) {
				for(String lore : message.toArray(new String[0])) {
					p.sendMessage(lore);
				}
			}
			
			for(String s : commands.toArray(new String[0])) {
				PirateFinds.getServerStatic().dispatchCommand(Bukkit.getServer().getConsoleSender(),  s);
			}
		}
	}
}
