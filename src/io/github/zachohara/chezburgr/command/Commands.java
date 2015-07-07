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
	
	private String name;
	private int minArgs;
	private int maxArgs;
	private Source accessible;
	private Target targetable;
	
	private Commands(String name, int minArgs, int maxArgs, Source access, Target target) {
		this.name = name;
		this.minArgs = minArgs;
		this.maxArgs = maxArgs;
		this.accessible = access;
		this.targetable = target;
	}
	
	private Commands(String name, Commands alias) {
		this.name = name;
		this.minArgs = alias.minArgs;
		this.maxArgs = alias.maxArgs;
		this.accessible = alias.accessible;
		this.targetable = alias.targetable;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return the minArgs
	 */
	public int getMinArgs() {
		return minArgs;
	}

	/**
	 * @return the maxArgs
	 */
	public int getMaxArgs() {
		return maxArgs;
	}

	/**
	 * @return the accessible
	 */
	public Source getAccessible() {
		return accessible;
	}

	/**
	 * @return the targetable
	 */
	public Target getTargetable() {
		return targetable;
	}
	
	public static Commands fromString(String label) {
		Commands[] allCommands = Commands.class.getEnumConstants();
		for (Commands c : allCommands) {
			if (c.getName().equals(label))
				return c;
		}
		return null;
	}

	public static enum Source {
		ALL,
		OP_ONLY,
		CHEZ_ONLY
	}
	
	public static enum Target {
		NONE,
		ALL,
		RESTRICT_CHEZ
	}

}
