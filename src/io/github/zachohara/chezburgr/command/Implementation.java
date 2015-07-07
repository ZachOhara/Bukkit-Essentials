package io.github.zachohara.chezburgr.command;

public abstract class Implementation {
	
	public boolean doCommand(CommandInstance instance) {
		if (instance.isFromPlayer())
			return this.doPlayerCommand(instance);
		else 
			return this.doConsoleCommand(instance);
	}
	
	public abstract boolean doPlayerCommand(CommandInstance instance);
	
	public boolean doConsoleCommand(CommandInstance instance) {
		return this.doPlayerCommand(instance);
	}
	
	public abstract String getName();

}
