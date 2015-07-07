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

import io.github.zachohara.chezburgr.command.CommandInstance;
import io.github.zachohara.chezburgr.util.StringUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onLoad() {
		this.logStatusChange("Loading");
	}
	
	@Override
	public void onEnable() {
		this.logStatusChange("Enabling");
	}
	
	@Override
	public void onDisable() {
		this.logStatusChange("Disabling");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		CommandInstance instance = new CommandInstance(sender, command, args);
		boolean valid = instance.verifyCommand();
		if (valid)
			instance.executeCommand();
		return true;
	}
	
	private void logStatusChange(String status) {
		this.getLogger().info(StringUtil.getLoadMessage(status, this));
	}

}
