package com.zachohara.chezburgr.dataform;

public class CommandProperties {
	
	public int argsMin;
	public int argsMax;
	public boolean limited;
	public boolean args;
	public boolean useTarget;
	public boolean restrictTarget;
	
	public CommandProperties(boolean limited, int argsMin, int argsMax, boolean useTarget, boolean restrictTarget) {
		this.argsMin = argsMin;
		this.argsMax = argsMax;
		this.limited = limited;
		this.useTarget = useTarget;
		this.restrictTarget = restrictTarget;
		if (argsMin == 0 && argsMax == 0) {
			this.args = false;
		} else {
			this.args = true;
		}
	}
}
