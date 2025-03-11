package me.cworldstar.piratefinds.impl.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.cworldstar.piratefinds.impl.ui.test.PFMenuGUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class MainCommand extends ExtendedCommand implements TabExecutor, Listener {

	public MainCommand(String command, String description, String permission) {
		super(command, description, permission);
	}
	
	public MainCommand(PluginCommand command) {
		super(command.getName(), command.getDescription(), command.getPermission());
        command.setExecutor(this);
        command.setTabCompleter(this);
	}

	private HashMap<String, CommandConsumer<CommandSender>> commands = new HashMap<String, CommandConsumer<CommandSender>>();
	
	public HashMap<String, CommandConsumer<CommandSender>> getCommands() {
		return commands;
	}
	
	
	public void registerCommand(String id, CommandConsumer<CommandSender> consumer) {
		this.commands.putIfAbsent(id, consumer);
	}
	
	
	// totally stole this :D
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> strings = new ArrayList<>();
        complete(sender, args, strings);
        List<String> returnList = new ArrayList<>();
        String arg = args[args.length - 1].toLowerCase(Locale.ROOT);
        for (String item : strings) {
            if (item.toLowerCase(Locale.ROOT).contains(arg)) {
                returnList.add(item);
                if (returnList.size() >= 64) {
                    break;
                }
            }
            else if (item.equalsIgnoreCase(arg)) {
                return Collections.emptyList();
            }
        }
        return returnList;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		execute(sender, args);
		return true;
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if(args.length <= 0) {
			new PFMenuGUI((Player) sender).open();
			return;
		}
		
		if(args[0].toLowerCase() == "help") {
			new PFMenuGUI((Player) sender).open();
			return;
		}
		
		CommandConsumer<CommandSender> command = this.commands.get(args[0]);
		if(command != null) {
			boolean permission = true;
			if(sender instanceof Player) {
				permission = command.hasPermission((Player) sender);
			}
			if(permission) {
				command.accept(sender, new ArrayList<String>(Arrays.asList(args)));
			} else {
				sender.sendMessage(ChatUtils.createBroadcast("&7You do not have permission to use this command."));
			}
		} 
		else {
				sender.sendMessage(ChatUtils.createBroadcast("&cInvalid command."));
		}
	}

	@Override
	protected void complete(CommandSender sender, String[] args, List<String> completions) {
		switch(args.length) {
			case 1:
				this.commands.forEach((String id, CommandConsumer<CommandSender> command) -> {
					if(command.hide) {
						return;
					}
					if(command.hasPermission((Player) sender)) {
						completions.add(id);
					}
				});
				break;
			default:
				CommandConsumer<CommandSender> command = commands.get(args[0]);
				if(command != null) {
					boolean permission = true;
					if(sender instanceof Player) {
						permission = command.hasPermission((Player) sender);
					}
					if(permission) {
						completions.addAll(command.getCompletions(args.length-1));
					}
				}
				break;
		}
	}

	public void addAlias(PluginCommand command) {
		command.setExecutor(this);
        command.setTabCompleter(this);
	}

	@Nonnull
	public Set<Entry<String, CommandConsumer<CommandSender>>> getSubCommands() {
		return this.commands.entrySet();
	}

	@Nullable
	public CommandConsumer<CommandSender> getCommand(String command) {
		return this.commands.get(command);
	}

}
