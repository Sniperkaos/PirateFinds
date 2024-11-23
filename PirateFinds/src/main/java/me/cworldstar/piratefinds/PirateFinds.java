package me.cworldstar.piratefinds;

import java.util.function.Consumer;
import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.cworldstar.piratefinds.events.BlockBreakHandler;
import me.cworldstar.piratefinds.impl.ListenerClass;
import me.cworldstar.piratefinds.impl.ae.AEExpansion;
import me.cworldstar.piratefinds.impl.ae.items.PFItemListener;
import me.cworldstar.piratefinds.impl.ae.listeners.Locked;
import me.cworldstar.piratefinds.impl.commands.CommandsClass;
import me.cworldstar.piratefinds.impl.drop.Drop;
import me.cworldstar.piratefinds.impl.events.UIUpdateEvent;
import me.cworldstar.piratefinds.impl.papi.ArmorerExpansion;
import me.cworldstar.piratefinds.impl.vault.VaultImpl;
import me.cworldstar.piratefinds.impl.arena.Arena;

public class PirateFinds extends JavaPlugin {
	
	
	private static AEExpansion AEExpansion;
	private static Arena Arena;
	private static Drop DropImpl;
	private static Ticker Ticker;
	
	
	public static NamespacedKey createKey(String key) {
		return new NamespacedKey(getThisPlugin(), key);
	}
	
	@Nullable
	public static AEExpansion getAEExpansion() {
		return AEExpansion;
	}
	
	@Nonnull
	public static Arena getArena() {
		return Arena;
	}
	
	public static enum KEYS {
		
		HEALTH_INSTANCE_KEY(createKey("health")),
		MAX_HEALTH_INSTANCE_KEY(createKey("max_health"));

		protected final NamespacedKey key;
		
		private KEYS(NamespacedKey namespacedKey) {
			this.key = namespacedKey;
		}
		
		public NamespacedKey getKey() {
			return this.key;
		}
	}
	
	public static Ticker getTicker() {
		return PirateFinds.Ticker;
	}
	
	public static void registerTickerTask(Consumer<Integer> task) {
		getTicker().registerTask(task);
	}
	
	public static PirateFinds getThisPlugin() {
		return PirateFinds.getPlugin(PirateFinds.class);
	}
	
	public static Drop getDropImpl() {
		return DropImpl;
	}
	
	public static Server getServerStatic() {
		return PirateFinds.getThisPlugin().getServer();
	}
	
	public static void registerListener(Listener l) {
		PirateFinds.getThisPlugin().getServer().getPluginManager().registerEvents(l, getThisPlugin());
	}
	
	public void reload() {
		this.reloadConfig();
		// other reload stuff goes here
	}
	
	@Override
	public void onEnable() {
		
		long start = System.currentTimeMillis();
		
		// lol
		PirateFinds.log("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────" + "\n" 
			+"	─██████████████─██████████─████████████████───██████████████─██████████████─██████████████─██████████████─██████████─██████──────────██████─████████████───██████████████────" + "\n"
			+"	─██░░░░░░░░░░██─██░░░░░░██─██░░░░░░░░░░░░██───██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░██─██░░██████████──██░░██─██░░░░░░░░████─██░░░░░░░░░░██────"  + "\n"
			+"	─██░░██████░░██─████░░████─██░░████████░░██───██░░██████░░██─██████░░██████─██░░██████████─██░░██████████─████░░████─██░░░░░░░░░░██──██░░██─██░░████░░░░██─██░░██████████────"  + "\n"
			+"	─██░░██──██░░██───██░░██───██░░██────██░░██───██░░██──██░░██─────██░░██─────██░░██─────────██░░██───────────██░░██───██░░██████░░██──██░░██─██░░██──██░░██─██░░██────────────" + "\n"
			+"	─██░░██████░░██───██░░██───██░░████████░░██───██░░██████░░██─────██░░██─────██░░██████████─██░░██████████───██░░██───██░░██──██░░██──██░░██─██░░██──██░░██─██░░██████████────" + "\n"
			+"	─██░░░░░░░░░░██───██░░██───██░░░░░░░░░░░░██───██░░░░░░░░░░██─────██░░██─────██░░░░░░░░░░██─██░░░░░░░░░░██───██░░██───██░░██──██░░██──██░░██─██░░██──██░░██─██░░░░░░░░░░██────" + "\n"
			+"	─██░░██████████───██░░██───██░░██████░░████───██░░██████░░██─────██░░██─────██░░██████████─██░░██████████───██░░██───██░░██──██░░██──██░░██─██░░██──██░░██─██████████░░██────" + "\n"
			+"	─██░░██───────────██░░██───██░░██──██░░██─────██░░██──██░░██─────██░░██─────██░░██─────────██░░██───────────██░░██───██░░██──██░░██████░░██─██░░██──██░░██─────────██░░██────" + "\n"
			+"	─██░░██─────────████░░████─██░░██──██░░██████─██░░██──██░░██─────██░░██─────██░░██████████─██░░██─────────████░░████─██░░██──██░░░░░░░░░░██─██░░████░░░░██─██████████░░██────" + "\n"
			+"	─██░░██─────────██░░░░░░██─██░░██──██░░░░░░██─██░░██──██░░██─────██░░██─────██░░░░░░░░░░██─██░░██─────────██░░░░░░██─██░░██──██████████░░██─██░░░░░░░░████─██░░░░░░░░░░██────" + "\n"
			+"	─██████─────────██████████─██████──██████████─██████──██████─────██████─────██████████████─██████─────────██████████─██████──────────██████─████████████───██████████████────" + "\n"
			+"	─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────" + "\n"
			+"	──────────────────────────────────────────────────────────────────────────────────────────────────────────────" + "\n"
			+"	─██████─────────██████████████─██████████████─████████████───██████████─██████──────────██████─██████████████─" + "\n"
			+"	─██░░██─────────██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░████─██░░░░░░██─██░░██████████──██░░██─██░░░░░░░░░░██─" + "\n"
			+"	─██░░██─────────██░░██████░░██─██░░██████░░██─██░░████░░░░██─████░░████─██░░░░░░░░░░██──██░░██─██░░██████████─" + "\n"
			+"	─██░░██─────────██░░██──██░░██─██░░██──██░░██─██░░██──██░░██───██░░██───██░░██████░░██──██░░██─██░░██─────────" + "\n"
			+"	─██░░██─────────██░░██──██░░██─██░░██████░░██─██░░██──██░░██───██░░██───██░░██──██░░██──██░░██─██░░██─────────" + "\n"
			+"	─██░░██─────────██░░██──██░░██─██░░░░░░░░░░██─██░░██──██░░██───██░░██───██░░██──██░░██──██░░██─██░░██──██████─" + "\n"
			+"	─██░░██─────────██░░██──██░░██─██░░██████░░██─██░░██──██░░██───██░░██───██░░██──██░░██──██░░██─██░░██──██░░██─" + "\n"
			+"	─██░░██─────────██░░██──██░░██─██░░██──██░░██─██░░██──██░░██───██░░██───██░░██──██░░██████░░██─██░░██──██░░██─" + "\n"
			+"	─██░░██████████─██░░██████░░██─██░░██──██░░██─██░░████░░░░██─████░░████─██░░██──██░░░░░░░░░░██─██░░██████░░██─" + "\n"
			+"	─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░██──██░░██─██░░░░░░░░████─██░░░░░░██─██░░██──██████████░░██─██░░░░░░░░░░██─" + "\n"
			+"	─██████████████─██████████████─██████──██████─████████████───██████████─██████──────────██████─██████████████─" + "\n"
			+"	──────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		
		this.saveDefaultConfig();
		
		//InputStream stream = this.getResource("sets.yml");
		//Config cfg = new Config(new File(this.getDataFolder().getAbsolutePath() + File.pathSeparator + "sets.yml"));
		//if(!cfg.exists()) {
		//	Config.saveDefault(new File(this.getDataFolder().getAbsolutePath() + File.pathSeparator + "sets.yml"), stream);
		//}
		
		// listeners
		PirateFinds.log("Starting listeners");
		new ListenerClass();
		// comands
		PirateFinds.log("Registering commands");
		new CommandsClass();
		// silk touch
		PirateFinds.log("Starting silk touch spawners");
		new BlockBreakHandler();
		// locked
		PirateFinds.log("Initiating AE locked");
		new Locked();
		// drop impl
		PirateFinds.log("Starting ticker task and Q drop handler");
		PirateFinds.DropImpl = new Drop();
		PirateFinds.Ticker = new Ticker();
		Ticker.registerTask((Integer id) -> {
			UIUpdateEvent e = new UIUpdateEvent();
			Bukkit.getPluginManager().callEvent(e);
		});

		PirateFinds.log("Initializing PF items");
		new PFItemListener();
		
		if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			this.getLogger().log(Level.INFO, "[PirateFinds]: Registering PAPI Armorer expansion.");
			PirateFinds.log("PlaceholderAPI installed! Creating armorer expansion.");
			ArmorerExpansion expansion = new ArmorerExpansion();
			expansion.register();
			
		}
		
		PirateFinds.log("Doing vault impl.");
		VaultImpl.trySetupEconomy();
		// AE expansion
		PirateFinds.log("Starting AE expansion.");
		PirateFinds.AEExpansion = new AEExpansion();
		PirateFinds.log("Starting arena.");
		PirateFinds.Arena = new Arena();
		
		PirateFinds.log("Done! Started up in " + Double.toString((System.currentTimeMillis() - start) / 1000) + " seconds.");
		
	}

	public static void log(String string) {
		getThisPlugin().getLogger().log(Level.INFO, "[PirateFinds]: " + string);
	}
}
