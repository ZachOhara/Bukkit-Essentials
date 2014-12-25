package data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import dataform.CommandInstance;

public class PlayerCommands {
	
	public static boolean doCommand(CommandInstance instance) {
		switch(instance.name) {
		case "locate":
			return locate(instance);
		case "takedown":
			return takedown(instance);
		case "killplayer":
			return killplayer(instance);
		case "getrekt":
			return getrekt(instance);
		case "ping":
			return ping(instance);
		case "afk":
			return afk(instance, true);
		case "noafk":
			return afk(instance, false);
		case "speakfor":
			return speakfor(instance);
		case "forcechat":
			return forcechat(instance);
		default:
			return false;
		}
	}
	
	public static boolean locate(CommandInstance instance) {
		Location loc = instance.target.getLocation();
		String locString = ChatColor.GREEN + "(" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")";
		instance.rawSender.sendMessage(ChatColor.WHITE + instance.target.getName() + ChatColor.AQUA + " is currently at position " + locString);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean takedown(CommandInstance instance) {
		Location senderLoc = instance.sender.getLocation();
		instance.target.teleport(senderLoc);
		instance.target.setHealth((double)0);
		String[] bans = {"ban", "banhammer", "hammer"};
		if (contains(bans, instance.args[0])) {
			instance.target.setBanned(true);
		}
		instance.target.kickPlayer("You have been kicked form the server");
		return true;
	}
	
	public static boolean killplayer(CommandInstance instance) {
		instance.target.damage((double) 20);
		instance.target.sendMessage(ChatColor.WHITE + instance.fullSenderName + ChatColor.AQUA + " killed you with magic");
		instance.rawSender.sendMessage(ChatColor.AQUA + "You have killed " + ChatColor.WHITE + instance.fullTargetName + ChatColor.AQUA + " with magic");
		return true;
	}
	
	public static boolean getrekt(CommandInstance instance) {
		if (instance.targetGiven) {
			instance.target.sendMessage(ChatColor.WHITE + instance.fullSenderName + ChatColor.AQUA + " has rekt you!");
			instance.target.sendMessage(ChatColor.AQUA + "Get rekt, noob!");
			instance.rawSender.sendMessage(ChatColor.AQUA + "You have rekt " + ChatColor.WHITE + instance.fullTargetName);
		} else {
			Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "Get rekt, noobs!");
		}
		return true;
	}
	
	public static boolean ping(CommandInstance instance) {
		instance.rawSender.sendMessage("Pong!");
		return true;
	}
	
	public static boolean afk(CommandInstance instance, boolean away) {
		String message = " is no longer AFK";
		if (away) {
			message = " is now AFK";
		}
		if (!instance.targetGiven) {
			Bukkit.getServer().broadcastMessage(ChatColor.WHITE + instance.fullSenderName + ChatColor.AQUA + message);
		} else {
			if (instance.fromConsole || instance.sender.isOp()) {
				instance.rawSender.getServer().broadcastMessage(ChatColor.WHITE + instance.fullTargetName + ChatColor.AQUA + message);
			} else {
				instance.rawSender.sendMessage(ChatColor.RED + "You must be an OP to use this command for someone else");
			}
		}
		return true;
	}
	
	public static boolean speakfor(CommandInstance instance) {
		String message = "";
		for (int i = 1; i < instance.args.length; i++) {
			message += instance.args[i] + " ";
		}
		Bukkit.getServer().broadcastMessage("<" + instance.fullTargetName + "> " + message);
		return true;
	}

	public static boolean forcechat(CommandInstance instance) {
		String message = "";
		for (int i = 1; i < instance.args.length; i++) {
			message += instance.args[i] + " ";
		}
		instance.target.chat(message);
		return true;
	}

	private static boolean contains(String[] set, String element) {
		boolean result = false;
		for (int i=0; i < set.length; i++) {
			if (set[i].equals(element)) {
				result = true;
			}
		}
		return result;
	}
}
