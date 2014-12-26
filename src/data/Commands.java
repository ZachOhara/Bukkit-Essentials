package data;

import dataform.CommandProperties;

public class Commands {
	
	public static final CommandProperties LOCATE = new CommandProperties(true, 1, 1, true, true);
	public static final CommandProperties TAKEDOWN = new CommandProperties(true, 1, 2, true, true);
	public static final CommandProperties KILLPLAYER = new CommandProperties(true, 1, 1, true, true);
	public static final CommandProperties GETREKT = new CommandProperties(false, 0, 1, true, true);
	public static final CommandProperties PING = new CommandProperties(false, 0, 0, false, false);
	public static final CommandProperties AFK = new CommandProperties(false, 0, 1, true, false);
	public static final CommandProperties NOAFK = AFK;
	public static final CommandProperties SPEAKFOR = new CommandProperties(false, 2, -1, true, true);
	public static final CommandProperties FORCECHAT = new CommandProperties(true, 2, -1, true, true);
	
	public static CommandProperties getProperties(String command) {
		switch (command) {
		case "locate":
			return LOCATE;
		case "takedown":
			return TAKEDOWN;
		case "killplayer":
			return KILLPLAYER;
		case "getrekt":
			return GETREKT;
		case "ping":
			return PING;
		case "afk":
			return AFK;
		case "noafk":
			return NOAFK;
		case "speakfor":
			return SPEAKFOR;
		case "forcechat":
			return FORCECHAT;
		default:
			return null;
		}
	}
	
}