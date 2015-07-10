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

import io.github.zachohara.bukkit.common.command.CommandRules;
import io.github.zachohara.bukkit.common.command.CommandRulesEntry;

/**
 * The {@code Rules} enumeration represents the set of commands that this plugin
 * supports, including additional information for each command such as the range of
 * expected amounts of arguments, the required permission level necessary to use a
 * command, and if the command should require a target player.
 * 
 * @author Zach Ohara
 * 
 * @see {@link io.github.zachohara.bukkit.common.command.CommandRules CommandRules}
 * @see {@link io.github.zachohara.bukkit.common.command.CommandRulesEntry CommandRulesEntry}
 */
public enum Rules implements CommandRules {
	
	LOCATE(new CommandRulesEntry("locate", 1, 1, Source.OP_ONLY, Target.RESTRICT_ADMIN)),
	TAKEDOWN(new CommandRulesEntry("takedown", 1, 2, Source.ADMIN_PLAYER_ONLY, Target.RESTRICT_ADMIN)),
	KILLPLAYER(new CommandRulesEntry("killplayer", 1, 1, Source.OP_ONLY, Target.RESTRICT_ADMIN)),
	GETREKT(new CommandRulesEntry("getrekt", 0, 1, Source.ALL, Target.RESTRICT_ADMIN)),
	PING(new CommandRulesEntry("ping", 0, 0, Source.ALL, Target.NONE)),
	AFK(new CommandRulesEntry("afk", 0, 1, Source.ALL, Target.IF_SENDER_OP)),
	NOAFK(new CommandRulesEntry("noafk", AFK.getRulesEntry())),
	SPEAKFOR(new CommandRulesEntry("speakfor", 2, -1, Source.ALL, Target.RESTRICT_ADMIN)),
	FORCECHAT(new CommandRulesEntry("forcechat", 2, -1, Source.OP_ONLY, Target.RESTRICT_ADMIN));

	private CommandRulesEntry ruleEntry;
	
	private Rules(CommandRulesEntry rules) {
		this.ruleEntry = rules;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommandRulesEntry getRulesEntry() {
		return this.ruleEntry;
	}

}
