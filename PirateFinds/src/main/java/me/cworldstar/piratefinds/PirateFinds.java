package me.cworldstar.piratefinds;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.cworldstar.piratefinds.events.BlockBreakHandler;
import me.cworldstar.piratefinds.impl.Crafting;
import me.cworldstar.piratefinds.impl.EnchantmentDealer;
import me.cworldstar.piratefinds.impl.FancyHolograms;
import me.cworldstar.piratefinds.impl.ListenerClass;
import me.cworldstar.piratefinds.impl.MobDropImpl;
import me.cworldstar.piratefinds.impl.ae.AEExpansion;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ae.items.PFItemListener;
import me.cworldstar.piratefinds.impl.ae.items.items.AbstractLootBox;
import me.cworldstar.piratefinds.impl.ae.items.items.masks.effects.MaskEffects;
import me.cworldstar.piratefinds.impl.ae.items.items.masks.effects.MaskListener;
import me.cworldstar.piratefinds.impl.ae.listeners.Locked;
import me.cworldstar.piratefinds.impl.commands.CommandsClass;
import me.cworldstar.piratefinds.impl.drop.Drop;
import me.cworldstar.piratefinds.impl.lands.LandsImpl;
import me.cworldstar.piratefinds.impl.papi.ArmorerExpansion;
import me.cworldstar.piratefinds.impl.papi.EnchantmentExpansion;
import me.cworldstar.piratefinds.impl.papi.ProfileExpansion;
import me.cworldstar.piratefinds.impl.profile.PlayerProfile;
import me.cworldstar.piratefinds.impl.protocols.EnderDragonSilencedProtocol;
import me.cworldstar.piratefinds.impl.serialize.SerializeableInventory;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;
import me.cworldstar.piratefinds.impl.utils.ConfigUtils;
import me.cworldstar.piratefinds.impl.vault.VaultImpl;
import me.cworldstar.piratefinds.impl.arena.Arena;
import me.cworldstar.piratefinds.impl.blocks.BlockConfig;
import me.cworldstar.piratefinds.impl.blocks.PFBlockData;

public class PirateFinds extends JavaPlugin {
	
	
	private static AEExpansion AEExpansion;
	private static Arena Arena;
	private static Drop DropImpl;
	private static Ticker Ticker;
	private static YamlConfiguration boxConfig;
	private static YamlConfiguration totemConfig;
	private static YamlConfiguration backpackConfig;
	private static YamlConfiguration itemConfig;
	private static YamlConfiguration soulConfig;
	private static Random random = new Random();
	private static EnchantmentDealer dealer;
	private static LandsImpl landsImpl;
	private static CommandsClass commandsClass;
	private static YamlConfiguration auctioneerConfig;
	
	public static CommandsClass getPFCommandsClass() {
		return commandsClass;
	}
	
	@Nullable
	public static Optional<LandsImpl> getLandsImpl() {
		return Optional.ofNullable(landsImpl);
	}
	
	public static Random getRandom() {
		return random;
	}
	
	public static void newSeed() {
		random.setSeed(org.joml.Random.newSeed());
	}
	
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
		
		itemConfig = YamlConfiguration.loadConfiguration(new File(getPFConfigFolder(), "items.yml"));
		boxConfig = YamlConfiguration.loadConfiguration(new File(getPFConfigFolder(), "boxes.yml"));
		totemConfig = YamlConfiguration.loadConfiguration(new File(getPFConfigFolder(), "totems.yml"));
		backpackConfig = YamlConfiguration.loadConfiguration(new File(getPFConfigFolder(), "backpacks.yml"));
		
		

		for(String key : PirateFinds.getThisPlugin().getBoxConfig().getKeys(false)) {
			// Unregistering the old box hopefully will fix the bug where upon /pf reloading
			// boxes are able to be opened infinitely.
			PFItemClass.unregister(key);
			AbstractLootBox.buildFromConfig(PirateFinds.getThisPlugin().getBoxConfig().getConfigurationSection(key));
		}
		
		PFItemClass.registerConfigTotems();
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
	
	public YamlConfiguration getItemConfigFile() {
		return itemConfig;
	}
	
	public YamlConfiguration getBoxConfig() {
		return boxConfig;
	}
	
	public YamlConfiguration getBackpackConfig() {
		return backpackConfig;
	}
	
	public YamlConfiguration getSoulConfig() {
		return soulConfig;
	}
	
	public YamlConfiguration getTotemConfig() {
		return totemConfig;
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
		
		PirateFinds.log("Saving player profiles");
		PlayerProfile.saveAll();
		
		PirateFinds.log("Closing all open GUI instances");
		BaseUIObject.openUIObjects.forEach((BaseUIObject object) -> {
			object.forcefullyClose();
		});
		
		try {
			auctioneerConfig.save(new File(getDataFolder(), "auctioneerData.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static EnchantmentDealer getEnchantmentDealer() {
		return dealer;
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
		
		PirateFinds.log("Loading configs...");
		
		File boxFile = new File(itemConfigFolder, "boxes.yml");
		
		ConfigUtils.saveDefault(boxFile, "boxes.yml");
		boxConfig = YamlConfiguration.loadConfiguration(boxFile);
		
		File totemFile = new File(itemConfigFolder, "totems.yml");
		ConfigUtils.saveDefault(totemFile, "totems.yml");
		totemConfig = YamlConfiguration.loadConfiguration(totemFile);
		
		File backpackFile = new File(itemConfigFolder, "backpacks.yml");
		ConfigUtils.saveDefault(backpackFile, "backpacks.yml");
		backpackConfig = YamlConfiguration.loadConfiguration(backpackFile);
		
		File soulFile = new File(itemConfigFolder, "souls.yml");
		ConfigUtils.saveDefault(soulFile, "souls.yml");
		soulConfig = YamlConfiguration.loadConfiguration(soulFile);
		
		PirateFinds.log("Loading saved blocks...");
		
		blockConfig = new BlockConfig(YamlConfiguration.loadConfiguration(blockFile));
		blockConfig.loadAll();
		
		PirateFinds.log("Loading auctioneer config...");

		File auctioneerConfigFile = new File(dataFolder, "auctioneerData.yml");
		ConfigUtils.saveDefault(auctioneerConfigFile, "auctioneerData.yml");
		auctioneerConfig = YamlConfiguration.loadConfiguration(auctioneerConfigFile);
		
		//InputStream stream = this.getResource("sets.yml");
		//Config cfg = new Config(new File(this.getDataFolder().getAbsolutePath() + File.pathSeparator + "sets.yml"));
		//if(!cfg.exists()) {
		//	Config.saveDefault(new File(this.getDataFolder().getAbsolutePath() + File.pathSeparator + "sets.yml"), stream);
		//}
		
		ConfigurationSerialization.registerClass(SerializeableInventory.class);
		ConfigurationSerialization.registerClass(PFBlockData.class);
		
		dealer = new EnchantmentDealer();
		
		// listeners
		PirateFinds.log("Starting listeners");
		new ListenerClass();
		// comands
		PirateFinds.log("Registering commands");
		commandsClass = new CommandsClass();
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

		// mask stuff
		PirateFinds.log("Starting MaskListener");
		MaskEffects.registerMaskEffects();
		new MaskListener();
		
		
		PirateFinds.log("Initializing PF items");
		new PFItemListener();
		
		PirateFinds.log("Creating crafting recipes");
		Crafting.setupRecipies();
		
		BlockConfig.setupPersistance();
		
		// PlaceholderAPI compat
		
		if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			this.getLogger().log(Level.INFO, "[PirateFinds]: Registering PAPI Armorer expansion.");
			PirateFinds.log("PlaceholderAPI installed! Creating armorer expansion.");
			ArmorerExpansion expansion = new ArmorerExpansion();
			expansion.register();
			PirateFinds.log("PlaceholderAPI installed! Creating enchantment expansion.");
			EnchantmentExpansion expansion2 = new EnchantmentExpansion();
			expansion2.register();
			PirateFinds.log("PlaceholderAPI installed! Creating profile expansion.");
			ProfileExpansion expansion3 = new ProfileExpansion();
			expansion3.register();
		}
		
		if(Bukkit.getPluginManager().isPluginEnabled("Lands")) {
			log("[PirateFinds]: Lands enabled! Enabling lands impl.");
			landsImpl = new LandsImpl();
		}
		
		PirateFinds.log("Loading ProtocolLib stuff");
		new EnderDragonSilencedProtocol();
		
		PirateFinds.log("Doing vault impl.");
		VaultImpl.trySetupEconomy();
		// AE expansion
		PirateFinds.log("Starting AE expansion.");
		PirateFinds.AEExpansion = new AEExpansion();
		PirateFinds.log("Starting arena.");
		PirateFinds.Arena = new Arena();
		
		PirateFinds.log("Done! Started up in " + Double.toString((System.currentTimeMillis() - start) / 1000) + " seconds.");
		
	}

	public static Logger logger() {
		return getThisPlugin().getLogger();
	}
	
	public static void log(String string) {
		getThisPlugin().getLogger().log(Level.INFO, "[PirateFinds]: " + string);
	}

	public static void logDebug(String string) {
		if(PirateFinds.getThisPlugin().getConfig().getBoolean("options.debug-mode")) {
			log(string);
		}
	}

	public static ConfigurationSection getAuctioneerConfig() {
		return auctioneerConfig;
	}
}
