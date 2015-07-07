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
