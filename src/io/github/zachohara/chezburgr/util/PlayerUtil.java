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

public class PlayerUtil {
	
	public static final UUID chezburgr = UUID.fromString("5420ca86-36f0-4d54-8096-4352555fd1d6");
	
	public static boolean playerIsChezburgr(Player other) {
		return other.getUniqueId().equals(chezburgr);
	}
	
	public static Player getChezburgr() {
		return Bukkit.getPlayer(chezburgr);
	}
	
	public static String getChezburgrName() {
		return "Chezburgr";
	}

}
