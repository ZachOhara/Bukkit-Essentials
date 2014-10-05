package chezburgr.essentials.data;

import org.bukkit.ChatColor;

import chezburgr.essentials.dataform.CommandInstance;

public class ConsoleCommands extends PlayerCommands {

	public static boolean takedown(CommandInstance instance) {
		instance.rawSender.sendMessage(ChatColor.RED + "This command is not yet operational in the console");
		return true;
	}
}
