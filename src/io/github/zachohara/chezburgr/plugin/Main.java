package io.github.zachohara.chezburgr.plugin;

import io.github.zachohara.bukkit.common.command.CommandExecutables;
import io.github.zachohara.bukkit.common.command.CommandRules;
import io.github.zachohara.bukkit.common.plugin.CommonPlugin;
import io.github.zachohara.chezburgr.command.Commands;
import io.github.zachohara.chezburgr.command.Executable;

public class Main extends CommonPlugin {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends CommandRules> getCommandRuleSet() {
		return Commands.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends CommandExecutables> getCommandExecutableSet() {
		return Executable.class;
	}

}
