/* Main.java | The primary file for running the program
 * Copyright (C) 2014 Zach Ohara
 *
 * This file is part of the Chezburgr's Essentials Plugin for Bukkit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.zachohara.chezburgr.main;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.zachohara.chezburgr.data.Commands;
import com.zachohara.chezburgr.data.ConsoleCommands;
import com.zachohara.chezburgr.data.PlayerCommands;
import com.zachohara.chezburgr.dataform.CommandInstance;
import com.zachohara.chezburgr.dataform.CommandProperties;

public class Main extends JavaPlugin {

	public static final Player chezburgr = Bukkit.getPlayer(UUID.fromString("5420ca86-36f0-4d54-8096-4352555fd1d6"));
	public static final String chezburgrPurple = ChatColor.LIGHT_PURPLE + "Chezburgr" + ChatColor.AQUA;
	
	public static int prepare(CommandInstance instance, CommandProperties properties) {
		// if sender is illegally using the command, inform them and the admin
		if (!instance.fromConsole && properties.limited && !instance.sender.equals(chezburgr)) {
			instance.reportAccessDenied();
			return 1;
		}
		// if too few args, tell player
		if (instance.args.length < properties.argsMin) {
			instance.error("Not enough arguments");
			return 0;
		}
		// if too many args, tell player
		if (properties.argsMax != -1 && instance.args.length > properties.argsMax) {
			instance.error("Too many arguments");
			return 0;
		}
		// if the command needs a target, get the target
		if (properties.useTarget) {
			// if target is chezburgr and this is not allowed, tell them and an admin
			if (instance.targetGiven && properties.restrictTarget && instance.targetName.equals("chezburgr")) {
				instance.reportBlockTarget();
				return 1;
			} else if (instance.args.length > 0) {
				if (instance.setTarget(instance.args[0]) == null) {
					instance.error("That player either does not exist or is not online right now");
					return 1;
				}
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
	public static void notifyAdmin(Player player, String sender, String command, String target) {
		String message = ChatColor.WHITE + sender + ChatColor.AQUA
						+ " has attempted to use " + ChatColor.WHITE + "/" + command
						+ ChatColor.AQUA + " on " + ChatColor.WHITE + target;
		if (player != null)
			player.sendMessage(message);
		Bukkit.getConsoleSender().sendMessage(message);
	}
}
