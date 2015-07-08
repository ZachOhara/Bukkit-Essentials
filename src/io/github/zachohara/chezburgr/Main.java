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

package io.github.zachohara.chezburgr;

import io.github.zachohara.bukkit.common.command.CommandInstance;
import io.github.zachohara.bukkit.common.command.Implementation;
import io.github.zachohara.chezburgr.command.Commands;
import io.github.zachohara.chezburgr.command.Executable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The entry point for the plugin.
 *
 * @author Zach Ohara
 */
public class Main extends JavaPlugin {

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		CommandInstance instance = new CommandInstance(sender, command, args, Commands.class);
		boolean valid = instance.verifyCommand();
		if (valid) {
			Implementation i = Executable.fromString(instance.getName());
			i.doCommand(instance);
		}
		return true;
	}

}
