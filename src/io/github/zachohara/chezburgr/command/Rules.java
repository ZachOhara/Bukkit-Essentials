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

import io.github.zachohara.bukkit.common.command.CommandRules;

/**
 * The {@code Rules} enumeration represents the set of commands that this plugin
 * supports, including additional information for each command such as the range of
 * expected amounts of arguments, the required permission level necessary to use a
 * command, and if the command should require a target player.
 * 
 * @author Zach Ohara
 * 
 * @see {@link io.github.zachohara.bukkit.common.command.CommandRules CommandRules}
 */
public enum Rules implements CommandRules {
	
	LOCATE("locate", 1, 1, Source.OP_ONLY, Target.RESTRICT_ADMIN),
	TAKEDOWN("takedown", 1, 2, Source.ADMIN_ONLY, Target.RESTRICT_ADMIN),
	KILLPLAYER("killplayer", 1, 1, Source.OP_ONLY, Target.RESTRICT_ADMIN),
	GETREKT("getrekt", 0, 1, Source.ALL, Target.RESTRICT_ADMIN),
	PING("ping", 0, 0, Source.ALL, Target.NONE),
	AFK("afk", 0, 1, Source.ALL, Target.ALL),
	NOAFK("noafk", AFK),
	SPEAKFOR("speakfor", 2, -1, Source.ALL, Target.RESTRICT_ADMIN),
	FORCECHAT("forcechat", 2, -1, Source.OP_ONLY, Target.RESTRICT_ADMIN);
	
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
	 * Constructs a new {@code Rules} object based on the required information.
	 * @param name see instance variable {@link #name}
	 * @param minArgs see instance variable {@link #minArgs}
	 * @param maxArgs see instance variable {@link #maxArgs}
	 * @param access see instanace variable {@link #accessible}
	 * @param target see instance variable {@link #targetable}
	 */
	private Rules(String name, int minArgs, int maxArgs, Source access, Target target) {
		this.name = name;
		this.minArgs = minArgs;
		this.maxArgs = maxArgs;
		this.accessible = access;
		this.targetable = target;
	}
	
	/**
	 * Constructs a new {@code Rules} object that should exactly mimic the properties
	 * of a different command.
	 * @param name the name of this command.
	 * @param alias the {@code Command} object that this object should mimic the
	 * properties of.
	 */
	private Rules(String name, Rules alias) {
		this.name = name;
		this.minArgs = alias.minArgs;
		this.maxArgs = alias.maxArgs;
		this.accessible = alias.accessible;
		this.targetable = alias.targetable;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinArgs() {
		return minArgs;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxArgs() {
		return maxArgs;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Source getAccessible() {
		return accessible;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Target getTargetable() {
		return targetable;
	}

}
