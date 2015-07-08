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

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The {@code PlayerUtil} class outlines some useful static methods that are relevant to
 * players in the game.
 * 
 * @author Zach Ohara
 */
public class PlayerUtil {
	
	/**
	 * The UUID of the player that is the admin of the server that this plugin was
	 * originally written for. If this plugin ever needs to be adapted to work on another
	 * server, this UUID string should be changed to reflect the admin of the new server
	 * (if any). 
	 */
	public static final UUID adminUUID = UUID.fromString("5420ca86-36f0-4d54-8096-4352555fd1d6");
	
	/**
	 * The name of the player that is the admin of the server that this plugin was
	 * originally written for. If this plugin ever needs to be adapted to work on another
	 * server, this name string should be changed to reflect the admin of the new server
	 * (if any). 
	 */
	public static final String adminName = "Chezburgr";
	
	/**
	 * Determines if a given player is the admin of this server by comparing UUID values.
	 * @param other the player to compare to the admin.
	 * @return {@code true} if the given player is the local admin; {@code false} otherwise.
	 */
	public static boolean playerIsAdmin(Player other) {
		return other.getUniqueId().equals(adminUUID);
	}
	
	/**
	 * Determines if the local admin is online.
	 * @return {@code true} if the admin is currently online; {@code false} otherwise;
	 */
	public static boolean adminIsOnline() {
		return getAdmin() != null;
	}
	
	/**
	 * Gets a {@code Player} object representing the admin of this server. {@code null}
	 * is returned if the admin is currently offline.
	 * @return the admin of this server.
	 */
	public static Player getAdmin() {
		return Bukkit.getPlayer(adminUUID);
	}
	
	/**
	 * Gets the name of the admin of this server, regardless of wheter the admin is
	 * currently online.
	 * @return the name of the admin.
	 */
	public static String getAdminName() {
		return adminName;
	}
	
	/**
	 * Sends a given message to the admin of this server.
	 * @param message the message to be sent to the admin.
	 */
	public static void sendAdmin(String message) {
		if (adminIsOnline())
			getAdmin().sendMessage(message);
	}

}
