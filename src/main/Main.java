package main;

//import java.util.logging.Logger;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import data.Commands;
import data.ConsoleCommands;
import data.PlayerCommands;
import dataform.CommandInstance;
import dataform.CommandProperties;

public class Main extends JavaPlugin {

	private static final Player chezburgr = Bukkit.getPlayer(UUID.fromString("5420ca86-36f0-4d54-8096-4352555fd1d6"));
	private static final String chezburgrPurple = ChatColor.LIGHT_PURPLE + "Chezburgr" + ChatColor.AQUA;

	@SuppressWarnings("deprecation")
	public static int prepare(CommandInstance instance, CommandProperties properties) {
		// if sender is illegally using the command, inform them and the admin
		if (!instance.fromConsole && properties.limited && !instance.sender.equals(chezburgr)) {
			instance.sender.sendMessage(ChatColor.AQUA + "Only the all-powerfull overlord "
					+ chezburgrPurple + " may use this command!");
			instance.sender.sendMessage(ChatColor.AQUA + "Overlord " + chezburgrPurple
					+ " has been notified of your futile attempt!");
			String target = "[no target]";
			if (instance.args.length > 0)
				target = instance.args[0];
			notifyAdmin(chezburgr, instance.fullSenderName, instance.name, target);
			return 1;
		}
		// if too few args, tell player
		if (instance.args.length < properties.argsMin) {
			instance.rawSender.sendMessage(ChatColor.RED + "Not enough arguments");
			return 0;
		}
		// if too many args, tell player
		if (properties.argsMax != -1 && instance.args.length > properties.argsMax) {
			instance.rawSender.sendMessage(ChatColor.RED + "Too many arguments");
			return 0;
		}
		// if the command needs a target, get the target
		if (properties.useTarget) {
			if (instance.args.length > 0) {
				if (instance.setTarget(Bukkit.getPlayer(instance.args[0])) == null) {
					instance.rawSender.sendMessage(ChatColor.RED
							+ "That player either does not exist or is not online right now");
					return 1;
				}
			}
			// if target is chezburgr and this is not allowed, tell them and an admin
			if (instance.targetGiven && properties.restrictTarget && instance.targetName.equals("chezburgr")) {
				instance.rawSender.sendMessage(ChatColor.AQUA + "The all-powerful overlord "
						+ chezburgrPurple + " is immune to this command!");
				instance.rawSender.sendMessage(ChatColor.AQUA + "Overlord " + chezburgrPurple
						+ " has been notified of your futile attempt!");
				notifyAdmin(chezburgr, instance.fullSenderName, instance.name, instance.fullTargetName);
				return 1;
			}
		}
		return 2;
	}
	
	// overall method called by Bukkit when a registered command is sent
	public boolean onCommand(CommandSender rawSender, Command rawCommand, String commandLabel, String[] args){
		CommandInstance instance = new CommandInstance(rawSender, rawCommand.getName().toLowerCase(), args);

		int result = prepare(instance, Commands.getProperties(instance.name));
		if (result == 0)
			return false;
		else if (result == 1)
			return true;
		else {
			if (instance.fromConsole)
				return ConsoleCommands.doCommand(instance);
			else
				return PlayerCommands.doCommand(instance);
		}
	}
	
	// squeal to the admin and to the specified player that $sender used $command on $target
	private static void notifyAdmin(Player player, String sender, String command, String target) {
		String message = ChatColor.WHITE + sender + ChatColor.AQUA
						+ " has attempted to use " + ChatColor.WHITE + "/" + command
						+ ChatColor.AQUA + " on " + ChatColor.WHITE + target;
		if (player != null)
			player.sendMessage(message);
		Bukkit.getConsoleSender().sendMessage(message);
	}
}
