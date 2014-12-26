package data;

import org.bukkit.ChatColor;

import dataform.CommandInstance;

public class ConsoleCommands extends PlayerCommands {
	
	public static boolean doCommand(CommandInstance instance) {
		switch(instance.name) {
		case "takedown":
			return takedown(instance);
		default:
			return PlayerCommands.doCommand(instance);
		}
	}

	public static boolean takedown(CommandInstance instance) {
		instance.rawSender.sendMessage(ChatColor.RED + "This command is not yet operational in the console");
		return true;
	}
}
