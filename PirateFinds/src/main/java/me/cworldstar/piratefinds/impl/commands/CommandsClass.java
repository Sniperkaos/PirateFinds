package me.cworldstar.piratefinds.impl.commands;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.commands.consumers.*;

public class CommandsClass {
	
	private MainCommand mc;
	
	public static void executeServer(String command) {
		Server server = PirateFinds.getServerStatic();
		ConsoleCommandSender executor = server.getConsoleSender();
		server.dispatchCommand(executor, command);
	}
	
	public MainCommand getMainCommand() {
		return mc;
	}
	
	public List<String> getRegisteredCommands() {
		return mc.getSubCommands().stream().map(Entry::getKey).toList();
	}
	
	public String getHelpForCommand(String command) {
		return mc.getCommand(command).help();
	}
	
	public CommandsClass() {
		PirateFinds pf = PirateFinds.getThisPlugin();
		mc = new MainCommand(pf.getCommand("PirateFinds"));
		mc.registerCommand("bless", new Bless());
		mc.registerCommand("guarantee", new Guarantee());
		mc.registerCommand("openmenu", new OpenMenu());
		mc.registerCommand("randomenchant", new RandomEnchant());
		mc.registerCommand("arenaenter", new ArenaEnter());
		mc.registerCommand("arenaclose", new ArenaClose());
		mc.registerCommand("lockitem", new LockItem());
		mc.registerCommand("unlockitem", new UnlockItem());
		mc.registerCommand("givelockedset", new GiveLockedSet());
		mc.registerCommand("givelockedgkit", new GiveLockedGkit());
		mc.registerCommand("reload", new ReloadCommand());
		mc.registerCommand("q", new ToggleDrop());
		mc.registerCommand("previewgkit", new PreviewGKit());
		mc.registerCommand("givepfitem", new GivePFItem());
		mc.registerCommand("empty", new Empty());
		mc.registerCommand("unsealitem", new UnsealItem());
		mc.registerCommand("leavearena", new ArenaLeave());
		mc.registerCommand("enchanteater", new EnchantEater());
		mc.registerCommand("sealitem", new SealItem());
		mc.registerCommand("fixbook", new FixBook());
		mc.registerCommand("debug", new Debug());
		mc.registerCommand("checkitem", new CheckItem());
		mc.registerCommand("sharpen", new Sharpen());
		mc.registerCommand("broadcast", new Broadcast());
		mc.registerCommand("removemask", new RemoveMask());
		mc.registerCommand("givetotem", new GiveTotem());
		mc.registerCommand("displayprofile", new DisplayProfile());
		mc.registerCommand("setstat", new SetProfileStat());
		mc.registerCommand("recipes", new Recipes());
		mc.registerCommand("auction", new Auction());
		mc.registerCommand("souls", new Souls());
	}
}
