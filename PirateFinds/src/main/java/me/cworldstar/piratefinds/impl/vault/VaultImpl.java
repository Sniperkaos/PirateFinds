package me.cworldstar.piratefinds.impl.vault;

import java.util.Optional;

import org.bukkit.plugin.RegisteredServiceProvider;

import me.cworldstar.piratefinds.PirateFinds;
import net.milkbowl.vault.economy.Economy;

public class VaultImpl {
	
	private static Economy econ;
	
	public VaultImpl() {
		
	}
	
	public static boolean trySetupEconomy() {
		
		PirateFinds plugin = PirateFinds.getThisPlugin();
		
		if(plugin.getServer().getPluginManager().isPluginEnabled("Vault")) {
	        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
	        if(rsp == null) {
	        	return false;
	        }
	        econ = rsp.getProvider();
	        return true;
		}
		
		return false;
		
	}
	
	public static Optional<Economy> getEconomy() {
		return Optional.ofNullable(econ);
	}
	
	
}
