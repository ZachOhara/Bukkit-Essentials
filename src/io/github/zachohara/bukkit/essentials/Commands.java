/* Copyright (C) 2017 Zach Ohara
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

package io.github.zachohara.bukkit.essentials;

import io.github.zachohara.bukkit.simpleplugin.command.CommandInstance;
import io.github.zachohara.bukkit.simpleplugin.command.CommandSet;
import io.github.zachohara.bukkit.simpleplugin.command.Implementation;
import io.github.zachohara.bukkit.simpleplugin.command.Properties;
import io.github.zachohara.bukkit.simpleplugin.command.Properties.Source;
import io.github.zachohara.bukkit.simpleplugin.command.Properties.Target;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * The {@code Commands} interface represents the set of commands supported by this plugin,
 * and contains a {@code Properties} object for each command.
 *
 * @author Zach Ohara
 * @see Properties
 */
public enum Commands implements CommandSet {

	TAKEDOWN(new Properties(1, 2, Source.ADMIN_PLAYER_ONLY, Target.RESTRICT_ADMIN, new Takedown())),
	KILLPLAYER(new Properties(1, 1, Source.OP_ONLY, Target.RESTRICT_ADMIN, new Killplayer())),
	GETREKT(new Properties(0, 1, Source.ALL, Target.RESTRICT_ADMIN, new Getrekt())),
	PING(new Properties(0, 0, Source.ALL, Target.NONE, new Ping())),
	AFK(new Properties(0, 1, Source.ALL, Target.IF_SENDER_OP, new Afk())),
	NOAFK(new Properties(AFK, new Noafk())),
	SPEAKFOR(new Properties(2, -1, Source.ALL, Target.RESTRICT_ADMIN, new Speakfor())),
	FORCECHAT(new Properties(2, -1, Source.OP_ONLY, Target.RESTRICT_ADMIN, new Forcechat()));

	/**
	 * The {@code Properties} object specific to a single command.
	 */
	private Properties properties;

	/**
	 * Constructs a new {@code Commands} with the given {@code Properties} for this
	 * command.
	 *
	 * @param p the {@code Properties} for this command.
	 */
	private Commands(Properties p) {
		this.properties = p;
	}

	@Override
	public Properties getProperties() {
		return this.properties;
	}

	/**
	 * The implementation for the 'takedown' command.
	 */
	private static class Takedown extends Implementation {

		@SuppressWarnings("deprecation")
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			Location senderLoc = instance.getSenderPlayer().getLocation();
			instance.getTargetPlayer().teleport(senderLoc);
			instance.getTargetPlayer().setHealth(0.0);
			if (instance.getArguments().length >= 2 && instance.getArguments()[1].equalsIgnoreCase("ban")) {
				instance.getTargetPlayer().setBanned(true);
			}
			instance.getTargetPlayer().kickPlayer("You have been taken down by " + instance.getSenderName());
			instance.broadcastMessage("%t was@admin ruthlessly taken down@text by the hero %s");
			return true;
		}

	}

	/**
	 * The implementation for the 'killplayer' command.
	 */
	private static class Killplayer extends Implementation {

		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			instance.getTargetPlayer().setHealth(0.0);
			instance.sendTargetMessage("%s has killed you with magic");
			instance.sendMessage("You have killed %t with magic");
			return true;
		}

	}

	/**
	 * The implementation for the 'getrekt' command.
	 */
	private static class Getrekt extends Implementation {

		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.sendTargetMessage("%s has rekt you!\nGet rekt, noob!");
				instance.sendMessage("You have rekt %t");
			} else {
				instance.broadcastMessage("Get rekt, noobs!");
			}
			return true;
		}

	}

	/**
	 * The implementation for the 'ping' command.
	 */
	private static class Ping extends Implementation {

		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			instance.sendMessage("Pong!");
			return true;
		}

	}

	/**
	 * The implementation for the 'afk' command.
	 */
	private static class Afk extends Implementation {

		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.broadcastMessage("%t is now AFK");
			} else {
				instance.broadcastMessage("%s is now AFK");
			}
			return true;
		}

	}

	/**
	 * The implementation for the 'noafk' command.
	 */
	private static class Noafk extends Implementation {

		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.broadcastMessage("%t is no longer AFK");
			} else {
				instance.broadcastMessage("%s is no longer AFK");
			}
			return true;
		}

	}

	/**
	 * The implementation for the 'speakfor' command.
	 */
	private static class Speakfor extends Implementation {

		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			String message = "";
			for (int i = 1; i < instance.getArguments().length; i++) {
				message += " " + instance.getArguments()[i];
			}
			Bukkit.getServer().broadcastMessage("<" + instance.getTargetName() + ">" + message);
			return true;
		}

	}

	/**
	 * The implementation for the 'forcechat' command.
	 */
	private static class Forcechat extends Implementation {

		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			String message = "";
			for (int i = 1; i < instance.getArguments().length; i++) {
				message += instance.getArguments()[i] + " ";
			}
			instance.getTargetPlayer().chat(message);
			return true;
		}

	}

}
