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

/**
 * Represents the set of commands that this plugin is capable of handling, including
 * additional information for each command such as the range of expected amounts of
 * arguments, the required permission level necessary to use a command, and if the
 * command should require a target player.
 * <p>
 * This enumeration is one of two enumerations that represent the set of commands
 * supported by this plugin. Any single command will have two enumerable constants that
 * correspond to it. A command's constant listed here, in {@code Commands}, will contain
 * information about the details of the command, and its expected context. A command's
 * entry in the other enumeration, {@code Executable} will contain an executable object
 * that holds the 'main method' for the command.
 * 
 * @author Zach Ohara
 */
public enum Commands {
	
	LOCATE("locate", 1, 1, Source.OP_ONLY, Target.RESTRICT_CHEZ),
	TAKEDOWN("takedown", 1, 2, Source.CHEZ_ONLY, Target.RESTRICT_CHEZ),
	KILLPLAYER("killplayer", 1, 1, Source.OP_ONLY, Target.RESTRICT_CHEZ),
	GETREKT("getrekt", 0, 1, Source.ALL, Target.RESTRICT_CHEZ),
	PING("ping", 0, 0, Source.ALL, Target.NONE),
	AFK("afk", 0, 1, Source.ALL, Target.ALL),
	NOAFK("noafk", AFK),
	SPEAKFOR("speakfor", 2, -1, Source.ALL, Target.RESTRICT_CHEZ),
	FORCECHAT("forcechat", 2, -1, Source.OP_ONLY, Target.RESTRICT_CHEZ);
	
	/**
	 * The name of the command, as it would be typed in the game or from a console.
	 */
	private String name;
	
	/**
	 * The minimum amount of arguments that should be allowed for the command.
	 */
	private int minArgs;
	
	/**
	 * The maximum amount of arguments that should be allowed for the command.
	 */
	private int maxArgs;
	
	/**
	 * The type or range of sources that are allowed to use the command.
	 */
	private Source accessible;
	
	/**
	 * The type or range of target players that should be allowed to use this command.
	 */
	private Target targetable;
	
	/**
	 * Constructs a new {@code Commands} object based on the required information.
	 * @param name see instance variable {@link #name}
	 * @param minArgs see instance variable {@link #minArgs}
	 * @param maxArgs see instance variable {@link #maxArgs}
	 * @param access see instanace variable {@link #accessible}
	 * @param target see instance variable {@link #targetable}
	 */
	private Commands(String name, int minArgs, int maxArgs, Source access, Target target) {
		this.name = name;
		this.minArgs = minArgs;
		this.maxArgs = maxArgs;
		this.accessible = access;
		this.targetable = target;
	}
	
	/**
	 * Constructs a new {@code Commands} object that should exactly mimic the properties
	 * of a different command.
	 * @param name the name of this command.
	 * @param alias the {@code Command} object that this object should mimic the
	 * properties of.
	 */
	private Commands(String name, Commands alias) {
		this.name = name;
		this.minArgs = alias.minArgs;
		this.maxArgs = alias.maxArgs;
		this.accessible = alias.accessible;
		this.targetable = alias.targetable;
	}
	
	/**
	 * Gets the name of the command, as it would be typed in the game or from a console.
	 * @return the name of the command, as it would be typed in the game or from a
	 * console.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the minimum amount of arguments that should be allowed for the command.
	 * @return the minimum amount of arguments that should be allowed for the command.
	 */
	public int getMinArgs() {
		return minArgs;
	}

	/**
	 * Gets the maximum amount of arguments that should be allowed for the command.
	 * @return the maximum amount of arguments that should be allowed for the command.
	 */
	public int getMaxArgs() {
		return maxArgs;
	}

	/**
	 * Gets the type or range of sources that are allowed to use the command.
	 * @return the type or range of sources that are allowed to use the command.
	 * @see {@link #Commands.Source Commands.Source}
	 */
	public Source getAccessible() {
		return accessible;
	}

	/**
	 * Gets the type or range of target players that should be allowed to use this command.
	 * @return the type or range of target players that should be allowed to use this
	 * command.
	 * @see {@link #Commands.Target Commands.Target}
	 */
	public Target getTargetable() {
		return targetable;
	}
	
	/**
	 * Gets the {@code Commands} constant corresponding to a given name of a command.
	 * @param label the name of the requested command.
	 * @return
	 */
	public static Commands fromString(String label) {
		Commands[] allCommands = Commands.class.getEnumConstants();
		for (Commands c : allCommands) {
			if (c.getName().equals(label))
				return c;
		}
		return null;
	}

	/**
	 * Represents the set of possible sources, or ranges of sources, that may be allowed
	 * to use any single command.
	 */
	public static enum Source {
		ALL,
		OP_ONLY,
		CHEZ_ONLY
	}
	
	/**
	 * Represents the set of possible targets, or ranges of targets, that may be allowed
	 * to be targeted by any single command.
	 */
	public static enum Target {
		NONE,
		ALL,
		RESTRICT_CHEZ
	}

}
