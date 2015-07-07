package io.github.zachohara.chezburgr.util;

import io.github.zachohara.chezburgr.command.CommandInstance;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

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
			+ " doesn't exist";
	public static final String ERROR_NOT_CHEZBURGR_MESSAGE = "Only the all-powerful "
			+ "%chez may use this command!\nOverlord %chez has been notified of your futile attempt!";
	public static final String ERROR_NOT_CHEZBURGR_ADMIN_NOTIFICATION = "%s has tried to use /%c on "
			+ "overlord %chez!";
	public static final String ERROR_CHEZBURGR_RESTRICTED_MESSAGE = "You cannot use this command on"
			+ " the all-powerful %chez!\nOverlord %chez has been notified of your futile attempt!";

	public static String getLoadMessage(String status, JavaPlugin plugin) {
		PluginDescriptionFile description = plugin.getDescription();
		String message = ChatColor.WHITE + "[" + description.getName() + "]"
				+ " " + TEXTCOLOR + status
				+ CHEZCOLOR + description.getFullName()
				+ " " + TEXTCOLOR + description.getVersion();
		return message;
	}

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
				{"%c", source.getName()}
		};

		message = color + message;

		for (String[] parseKey : parsingKeys) {
			String substitute = NAMECOLOR + parseKey[1] + TEXTCOLOR;
			while (message.indexOf(parseKey[0]) != 0) {
				int index = message.indexOf(parseKey[0]);
				String a = message.substring(0, index);
				String b = message.substring(index + parseKey[0].length());
				message = a + substitute + b;
			}
		}
		return message;
	}

}
