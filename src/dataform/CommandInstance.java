package dataform;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInstance {

	public Player sender;
	public CommandSender rawSender;
	public boolean fromConsole;
	public String name;
	public String[] args;
	public String senderName;
	public String fullSenderName;
	public Player target;
	public String targetName;
	public String fullTargetName;
	public boolean targetGiven;
	
	public CommandInstance(CommandSender rawSender, String command, String[] args) {
		this.rawSender = rawSender;
		this.name = command.toLowerCase();
		this.args = args;
		this.targetGiven = false;
		this.targetName = "";
		this.fullTargetName = "";
		if (rawSender instanceof Player) {
			this.sender = (Player) rawSender;
			this.fromConsole = false;
			this.senderName = sender.getName().toLowerCase();
			this.fullSenderName = sender.getName();
		} else {
			this.fromConsole = true;
			this.senderName = "the console";
			this.fullSenderName = "The Console";
		}
	}
	
	public Player setTarget(Player target) {
		this.target = target;
		this.targetName = target.getName().toLowerCase();
		this.targetGiven = true;
		this.fullTargetName = target.getName();
		return this.target;
	}

}
