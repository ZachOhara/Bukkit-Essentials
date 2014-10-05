package chezburgr.essentials.data;

import chezburgr.essentials.dataform.CommandProperties;

public interface Commands {

	public static final CommandProperties LOCATE = new CommandProperties(true, 1, 1, true, true);
	public static final CommandProperties TAKEDOWN = new CommandProperties(true, 1, 2, true, true);
	public static final CommandProperties KILLPLAYER = new CommandProperties(true, 1, 1, true, true);
	public static final CommandProperties GETREKT = new CommandProperties(false, 0, 1, true, true);
	public static final CommandProperties PING = new CommandProperties(false, 0, 0, false, false);
	public static final CommandProperties AFK = new CommandProperties(false, 0, 1, true, false);
	public static final CommandProperties SPEAKFOR = new CommandProperties(false, 2, -1, true, true);
	public static final CommandProperties FORCECHAT = new CommandProperties(true, 2, -1, true, true);
	
}
