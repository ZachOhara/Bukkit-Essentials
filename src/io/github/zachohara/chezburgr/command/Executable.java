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

package io.github.zachohara.chezburgr.command;

import io.github.zachohara.bukkit.common.command.CommandExecutables;
import io.github.zachohara.bukkit.common.command.CommandInstance;
import io.github.zachohara.bukkit.common.command.Implementation;
import io.github.zachohara.bukkit.common.util.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Represents the set of commands that this plugin is capable of handling, including
 * executable objects for each command that act as the main procedure for a command.
 * <p>
 * This enumeration is one of two enumerations that represent the set of commands
 * supported by this plugin. Any single command will have two enumerable constants that
 * correspond to it. A command's constant listed here, in {@code Executable}, will contain
 * an executable object that holds the 'main method' for the command. A command's
 * entry in the other enumeration, {@code Commands} will contain
 * information about the details of the command, and its expected context.
 * 
 * @author Zach Ohara
 */
public enum Executable implements CommandExecutables {
	
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
	 * Constructs a new {@code Executable} object using a given implementation of a command.
	 * @param implement the {@code Implementation} of the command.
	 */
	private Executable(Implementation implement) {
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
	 * @see {@link io.github.zachohara.bukkit.common.command.Implementation Implementation}
	 */
	private static class Locate extends Implementation {
		
		@Override
		public String getName() {
			return "locate";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			Location loc = instance.getTargetPlayer().getLocation();
			String locString = StringUtil.getLocationString(loc);
			instance.sendMessage("%t is currently at " + locString);
			return true;
		}
		
	}

	/**
	 * The implementation for the 'takedown' command.
	 * @see {@link io.github.zachohara.bukkit.common.command.Implementation Implementation}
	 */
	private static class Takedown extends Implementation {
		
		@Override
		public String getName() {
			return "takedown";
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			Location senderLoc = instance.getSenderPlayer().getLocation();
			instance.getTargetPlayer().teleport(senderLoc);
			instance.getTargetPlayer().setHealth(0.0);
			if (instance.getArguments().length >= 2
					&& instance.getArguments()[1].equalsIgnoreCase("ban"))
				instance.getTargetPlayer().setBanned(true);
			instance.getTargetPlayer().kickPlayer("You have been kicked from the Server");
			return true;
		}
		
		@Override
		public boolean doConsoleCommand(CommandInstance instance) {
			instance.sendError("This command is only usable as a player");
			return true;
		}
		
	}

	/**
	 * The implementation for the 'killplayer' command.
	 * @see {@link io.github.zachohara.bukkit.common.command.Implementation Implementation}
	 */
	private static class Killplayer extends Implementation {
		
		@Override
		public String getName() {
			return "killplayer";
		}
		
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
	 * @see {@link io.github.zachohara.bukkit.common.command.Implementation Implementation}
	 */
	private static class Getrekt extends Implementation {
		
		@Override
		public String getName() {
			return "getrekt";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.sendTargetMessage("%s has rekt you!\nGet rekt, noob!");
				instance.sendMessage("You have rekt %t");
			} else {
				instance.broadcaseMessage("Get rekt, noobs!");
			}
			return true;
		}
		
	}

	/**
	 * The implementation for the 'ping' command.
	 * @see {@link io.github.zachohara.bukkit.common.command.Implementation Implementation}
	 */
	private static class Ping extends Implementation {
		
		@Override
		public String getName() {
			return "ping";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			instance.sendMessage("Pong!");
			return true;
		}
		
	}

	/**
	 * The implementation for the 'afk' command.
	 * @see {@link io.github.zachohara.bukkit.common.command.Implementation Implementation}
	 */
	private static class Afk extends Implementation {
		
		@Override
		public String getName() {
			return "afk";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.broadcaseMessage("%t is now AFK");
			} else {
				instance.broadcaseMessage("%s is now AFK");
			}
			return true;
		}
		
	}

	/**
	 * The implementation for the 'noafk' command.
	 * @see {@link io.github.zachohara.bukkit.common.command.Implementation Implementation}
	 */
	private static class Noafk extends Implementation {
		
		@Override
		public String getName() {
			return "noafk";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.broadcaseMessage("%t is no longer AFK");
			} else {
				instance.broadcaseMessage("%s is no longer AFK");
			}
			return true;
		}
		
	}

	/**
	 * The implementation for the 'speakfor' command.
	 * @see {@link io.github.zachohara.bukkit.common.command.Implementation Implementation}
	 */
	private static class Speakfor extends Implementation {
		
		@Override
		public String getName() {
			return "speakfor";
		}
		
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
	 * @see {@link io.github.zachohara.bukkit.common.command.Implementation Implementation}
	 */
	private static class Forcechat extends Implementation {
		
		@Override
		public String getName() {
			return "forcechat";
		}
		
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
