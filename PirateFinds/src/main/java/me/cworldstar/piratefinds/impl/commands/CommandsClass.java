package me.cworldstar.piratefinds.impl.commands;

import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.commands.consumers.*;

public class CommandsClass {
	
	public static void executeServer(String command) {
		Server server = PirateFinds.getServerStatic();
		ConsoleCommandSender executor = server.getConsoleSender();
		server.dispatchCommand(executor, command);
	}
	
	public CommandsClass() {
		PirateFinds pf = PirateFinds.getThisPlugin();
		MainCommand mc = new MainCommand(pf.getCommand("PirateFinds"));
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
	}
}
