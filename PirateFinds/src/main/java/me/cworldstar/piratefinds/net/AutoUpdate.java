package me.cworldstar.piratefinds.net;

import java.net.URL;
import java.util.Optional;

public class AutoUpdate {
	
	public static final String AutoUpdateURL = "https://api.github.com/repos/Sniperkaos/PirateFinds/releases/latest";
	
	
	
	public static boolean checkUpdate(final double versionNumber) {
		
		Optional<URL> updateURLExists = URLResolver.createURL(AutoUpdateURL);
		if(updateURLExists.isPresent()) {
			URL updateUrl = updateURLExists.get();			
		}
		
		return false;
	}
	
}
