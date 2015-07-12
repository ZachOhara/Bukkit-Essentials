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

package io.github.zachohara.bukkit.essentials.command;

import io.github.zachohara.bukkit.common.command.CommandExecutables;
import io.github.zachohara.bukkit.common.command.CommandInstance;
import io.github.zachohara.bukkit.common.command.Implementation;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * The {@code Executables} interface represents the set of commands supported by this
 * plugin, and contains an executable object for each command that acts as the main
 * procedure for the command.
 * 
 * @author Zach Ohara
 */
public enum Executables implements CommandExecutables {

	LOCATE(new Locate()),
	TAKEDOWN(new Takedown()),
	KILLPLAYER(new Killplayer()),
	GETREKT(new Getrekt()),
	PING(new Ping()),
	AFK(new Afk()),
	NOAFK(new Noafk()),
	SPEAKFOR(new Speakfor()),
	FORCECHAT(new Forcechat());

	/**
	 * The subclass of {@code Implementation} that contains an implementation for the
	 * command.
	 */
	private Implementation implement;

	/**
	 * Constructs a new constant with the given implementation.
	 * 
	 * @param implement the implementation of the command.
	 */
	private Executables(Implementation implement) {
		this.implement = implement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Implementation getImplementation() {
		return this.implement;
	}

	/**
	 * The implementation for the 'locate' command.
	 */
	private static class Locate extends Implementation {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "locate";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			instance.sendMessage("%t is currently at %tloc");
			return true;
		}

	}

	/**
	 * The implementation for the 'takedown' command.
	 */
	private static class Takedown extends Implementation {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "takedown";
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("deprecation")
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			Location senderLoc = instance.getSenderPlayer().getLocation();
			instance.getTargetPlayer().teleport(senderLoc);
			instance.getTargetPlayer().setHealth(0.0);
			if (instance.getArguments().length >= 2
					&& instance.getArguments()[1].equalsIgnoreCase("ban"))
				instance.getTargetPlayer().setBanned(true);
			instance.getTargetPlayer().kickPlayer("You have been taken down by " + instance.getSenderName());
			instance.broadcastMessage("%t was@admin ruthlessly taken down@text by the hero %s");
			return true;
		}

	}

	/**
	 * The implementation for the 'killplayer' command.
	 */
	private static class Killplayer extends Implementation {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "killplayer";
		}

		/**
		 * {@inheritDoc}
		 */
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

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "getrekt";
		}

		/**
		 * {@inheritDoc}
		 */
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

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "ping";
		}

		/**
		 * {@inheritDoc}
		 */
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

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "afk";
		}

		/**
		 * {@inheritDoc}
		 */
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

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "noafk";
		}

		/**
		 * {@inheritDoc}
		 */
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

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "speakfor";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			String message = "";
			for (int i = 1; i < instance.getArguments().length; i++)
				message += " " + instance.getArguments()[i];
			Bukkit.getServer().broadcastMessage("<" + instance.getTargetName() + ">" + message);
			return true;
		}

	}

	/**
	 * The implementation for the 'forcechat' command.
	 */
	private static class Forcechat extends Implementation {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "forcechat";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			String message = "";
			for (int i = 1; i < instance.getArguments().length; i++)
				message += instance.getArguments()[i] + " ";
			instance.getTargetPlayer().chat(message);
			return true;
		}

	}

}
