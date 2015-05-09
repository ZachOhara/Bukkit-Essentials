/* Commands.java | Contains the properties defined for every command.
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

import com.zachohara.chezburgr.dataform.CommandProperties;

public class Commands {
	
	public static final CommandProperties LOCATE = new CommandProperties(true, 1, 1, true, true);
	public static final CommandProperties TAKEDOWN = new CommandProperties(true, 1, 2, true, true);
	public static final CommandProperties KILLPLAYER = new CommandProperties(true, 1, 1, true, true);
	public static final CommandProperties GETREKT = new CommandProperties(false, 0, 1, true, true);
	public static final CommandProperties PING = new CommandProperties(false, 0, 0, false, false);
	public static final CommandProperties AFK = new CommandProperties(false, 0, 1, true, false);
	public static final CommandProperties NOAFK = AFK;
	public static final CommandProperties SPEAKFOR = new CommandProperties(false, 2, -1, true, true);
	public static final CommandProperties FORCECHAT = new CommandProperties(true, 2, -1, true, true);
	
	public static CommandProperties getProperties(String command) {
		switch (command) {
		case "locate":
			return LOCATE;
		case "takedown":
			return TAKEDOWN;
		case "killplayer":
			return KILLPLAYER;
		case "getrekt":
			return GETREKT;
		case "ping":
			return PING;
		case "afk":
			return AFK;
		case "noafk":
			return NOAFK;
		case "speakfor":
			return SPEAKFOR;
		case "forcechat":
			return FORCECHAT;
		default:
			return null;
		}
	}
	
}