/* ConsoleCommands.java | Implementations of all the added commands
 * as they may be sent by a server console controlling the game.
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

package com.zachohara.chezburgr.data;

import org.bukkit.ChatColor;

import com.zachohara.chezburgr.dataform.CommandInstance;

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
