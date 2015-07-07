/* Copyright (C) 2015 Zach Ohara
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

package io.github.zachohara.chezburgr.util;

import io.github.zachohara.chezburgr.command.CommandInstance;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class StringUtil {

	public static final ChatColor CHEZCOLOR = ChatColor.LIGHT_PURPLE;
	public static final ChatColor TEXTCOLOR = ChatColor.AQUA;
	public static final ChatColor NAMECOLOR = ChatColor.WHITE;
	public static final ChatColor ERRORCOLOR = ChatColor.RED;
	public static final ChatColor LOCATIONCOLOR = ChatColor.GREEN;

	public static final String ERROR_NOT_OP_MESSAGE = "You must be an OP to use this command";
	public static final String ERROR_TOO_FEW_ARGS_MESSAGE = "Not enough arguments!";
	public static final String ERROR_TOO_MANY_ARGS_MESSAGE = "Too many arguments!";
	public static final String ERROR_TARGET_OFFLINE = "%gt either is not online right now or"
			+ " doesn't exist.";
	public static final String ERROR_NOT_CHEZBURGR_MESSAGE = "Only the all-powerful "
			+ "%chez may use this command!\nOverlord %chez has been notified of your futile attempt!";
	public static final String ERROR_NOT_CHEZBURGR_ADMIN_NOTIFICATION = "%s has tried to use %c on "
			+ "overlord %chez!";
	public static final String ERROR_CHEZBURGR_RESTRICTED_MESSAGE = "You cannot use this command on"
			+ " the all-powerful %chez!\nOverlord %chez has been notified of your futile attempt!";

	public static String getLocationString(Location loc) {
		return LOCATIONCOLOR + "(" + loc.getBlockX() + ", "
				+ loc.getBlockY() + ", "
				+ loc.getBlockZ() + ")";
	}

	public static String parseString(String message, CommandInstance source) {
		return parseText(message, TEXTCOLOR, source);
	}

	public static String parseError(String message, CommandInstance source) {
		return parseText(message, ERRORCOLOR, source);
	}

	private static String parseText(String message, ChatColor color, CommandInstance source) {		
		final String[][] parsingKeys = {
				{"%chez", CHEZCOLOR + "Chezburgr"},
				{"%s", source.getSenderName()},
				{"%t", source.getTargetName()},
				{"%gt", source.getGivenTarget()},
				{"%c", "/" + source.getName()}
		};

		message = color + message;

		for (String[] parseKey : parsingKeys) {
			String substitute = NAMECOLOR + parseKey[1] + color;
			while (message.indexOf(parseKey[0]) != -1) {
				int index = message.indexOf(parseKey[0]);
				String a = message.substring(0, index);
				String b = message.substring(index + parseKey[0].length());
				message = a + substitute + b;
			}
		}
		return message;
	}

}
