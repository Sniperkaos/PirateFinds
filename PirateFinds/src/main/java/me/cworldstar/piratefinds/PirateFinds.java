package me.cworldstar.piratefinds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.cworldstar.piratefinds.events.BlockBreakHandler;
import me.cworldstar.piratefinds.impl.FancyHolograms;
import me.cworldstar.piratefinds.impl.ListenerClass;
import me.cworldstar.piratefinds.impl.MobDropImpl;
import me.cworldstar.piratefinds.impl.ae.AEExpansion;
import me.cworldstar.piratefinds.impl.ae.items.PFItemListener;
import me.cworldstar.piratefinds.impl.ae.listeners.Locked;
import me.cworldstar.piratefinds.impl.commands.CommandsClass;
import me.cworldstar.piratefinds.impl.drop.Drop;
import me.cworldstar.piratefinds.impl.events.UIUpdateEvent;
import me.cworldstar.piratefinds.impl.papi.ArmorerExpansion;
import me.cworldstar.piratefinds.impl.utils.ConfigUtils;
import me.cworldstar.piratefinds.impl.vault.VaultImpl;
import me.cworldstar.piratefinds.impl.arena.Arena;
import me.cworldstar.piratefinds.impl.blocks.BlockConfig;

public class PirateFinds extends JavaPlugin {
	
	
	private static AEExpansion AEExpansion;
	private static Arena Arena;
	private static Drop DropImpl;
	private static Ticker Ticker;
	private static YamlConfiguration boxConfig;
	
	
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
		
		this.itemConfig = YamlConfiguration.loadConfiguration(new File(getPFConfigFolder(), "items.yml"));
		
	}
	
	
	
	
	public YamlConfiguration getBlockConfig() {
		return YamlConfiguration.loadConfiguration(new File(getPFDataFolder(), "blockdata.yml"));
	}
	
	public File getPFConfigFolder() {
		return new File(this.getDataFolder(), "config");
	}
	
	public File getBlockConfigFile() {
		return new File(getPFDataFolder(), "blockdata.yml");
	}
	
	private YamlConfiguration itemConfig;
	
	public YamlConfiguration getItemConfigFile() {
		return itemConfig;
	}
	
	public YamlConfiguration getBoxConfig() {
		return boxConfig;
	}
	
	public File getPFDataFolder() {
		return new File(this.getDataFolder(), "data");
	}
	
	private static BlockConfig blockConfig;
	
	private File blockFile;
	
	public static BlockConfig getMadeBlockConfig() {
		return blockConfig;
	}
	
	@Override
	public void onDisable() {
		PirateFinds.log("Saving all blocks");
		blockConfig.saveAll(blockFile);
	}
	
	private static FancyHolograms hologramCompat;
	
	public static FancyHolograms getHologramCompat() {
		return hologramCompat;
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
		
		// FancyHolograms
		if(Bukkit.getPluginManager().isPluginEnabled("FancyHolograms")) {
			PirateFinds.log("Creating FancyHologram compat");
			hologramCompat = new FancyHolograms();
		}
		
		File dataFolder = new File(this.getDataFolder(), "data");
		dataFolder.mkdir();
		
		File itemConfigFolder = new File(this.getDataFolder(), "config");
		itemConfigFolder.mkdir();
		
		File itemConfigFile = new File(itemConfigFolder, "items.yml");
		ConfigUtils.saveDefault(itemConfigFile, "items.yml");
		
		itemConfig = YamlConfiguration.loadConfiguration(itemConfigFile);
		
		blockFile = new File(dataFolder, "blockdata.yml");
		ConfigUtils.saveDefault(blockFile, "blockdata.yml");
		
		PirateFinds.log("Loading box config...");
		
		File boxFile = new File(itemConfigFolder, "boxes.yml");
		
		ConfigUtils.saveDefault(boxFile, "boxes.yml");
		boxConfig = YamlConfiguration.loadConfiguration(boxFile);
		
		PirateFinds.log("Loading saved blocks...");
		
		blockConfig = new BlockConfig(YamlConfiguration.loadConfiguration(blockFile));
		blockConfig.loadAll();
		
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
		
		//mob drop impl
		PirateFinds.log("Starting MobDropImpl");
		new MobDropImpl();

		PirateFinds.log("Initializing PF items");
		new PFItemListener();
		
		BlockConfig.setupPersistance();
		
		// PlaceholderAPI compat
		
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
