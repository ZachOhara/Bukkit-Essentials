/* CommandInstance.java | Represents some details or conditions for
 * a single invocation of any command.
 * Copyright (C) 2014 Zach Ohara
 *
 * This file is part of the Chezburgr's Essentials Plugin for Bukkit
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

package com.zachohara.chezburgr.dataform;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zachohara.chezburgr.main.Main;

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
	
	private Player setTarget(Player target) {
		this.target = target;
		this.targetName = target.getName().toLowerCase();
		this.targetGiven = true;
		this.fullTargetName = target.getName();
		return this.target;
	}
	
	@SuppressWarnings("deprecation")
	public Player setTarget(String target) {
		Player targetPlayer;
		if ((targetPlayer = Bukkit.getPlayer(target)) != null)
			return this.setTarget(targetPlayer);
		else
			return null;
	}
	
	// A shortcut to reporting error messages
	public void error(String message) {
		this.rawSender.sendMessage(ChatColor.RED + message);
	}
	
	public void reportAccessDenied() {
			this.rawSender.sendMessage(ChatColor.AQUA + "Only the all-powerfull overlord " + Main.chezburgrPurple + " may use this command!");
			this.rawSender.sendMessage(ChatColor.AQUA + "Overlord " + Main.chezburgrPurple + " has been notified of your futile attempt!");
			
			String targetStr;
			if (this.args.length > 0)
				targetStr = this.args[0];
			else
				targetStr = "[no target]";
			Main.notifyAdmin(Main.chezburgr, this.fullSenderName, this.name, targetStr);
	}
	
	public void reportBlockTarget() {
		this.rawSender.sendMessage(ChatColor.AQUA + "The all-powerful overlord " + Main.chezburgrPurple + " is immune to this command!");
		this.rawSender.sendMessage(ChatColor.AQUA + "Overlord " + Main.chezburgrPurple + " has been notified of your futile attempt!");
		
		Main.notifyAdmin(Main.chezburgr, this.fullSenderName, this.name, this.fullTargetName);
	}

}
