/* Copyright (C) 2015 Zach Ohara
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

package io.github.zachohara.chezburgr.command;

import io.github.zachohara.chezburgr.util.PlayerUtil;
import io.github.zachohara.chezburgr.util.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInstance {
	
	private String name;
	private String[] arguments;
	private Commands rules;
	
	private CommandSender senderRaw;
	private Player senderPlayer;
	private String senderName;
	
	private Player targetPlayer;
	private String targetName;
	private String givenTarget;
	
	public CommandInstance(CommandSender rawSender, Command rawCommand, String[] args) {
		this.name = rawCommand.getName().toLowerCase();
		this.arguments = args;
		this.rules = Commands.valueOf(name);
		this.initializeSender(rawSender);		
		this.initializeSenderName();
		this.initializeTarget();
	}
	
	public boolean isFromPlayer() {
		return this.senderPlayer != null;
	}
	
	public boolean isFromConsole() {
		return this.senderPlayer == null;
	}
	
	public boolean hasTarget() {
		return this.targetPlayer != null;
	}
	
	public boolean verifyCommand() {
		return this.verifyArguments()
			&& this.verifyValidTarget()
			&& this.verifyValidSource();
	}
	
	private boolean verifyArguments() {
		if (this.arguments.length < this.rules.getMinArgs()) {
			this.sendError(StringUtil.ERROR_TOO_FEW_ARGS_MESSAGE);
			return false;
		}
		if (this.arguments.length > this.rules.getMaxArgs()) {
			this.sendError(StringUtil.ERROR_TOO_MANY_ARGS_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean verifyValidTarget() {
		switch (this.rules.getTargetable()) {
		case NONE:
			return true;
		case RESTRICT_CHEZ:
			if (!this.givenTarget.equalsIgnoreCase(PlayerUtil.getChezburgrName())) {
				return true;
			} else {
				this.sendMessage(StringUtil.ERROR_CHEZBURGR_RESTRICTED_MESSAGE);
				this.reportToAdmins(StringUtil.ERROR_NOT_CHEZBURGR_ADMIN_NOTIFICATION);
			}
		case ALL:
			if (this.hasTarget()) {
				return true;
			} else {
				this.sendError(StringUtil.ERROR_TARGET_OFFLINE);
				return false;
			}
		}
		return false;
	}
	
	private boolean verifyValidSource() {
		if (this.isFromConsole())
			return true;
		switch(this.rules.getAccessible()) {
		case ALL:
			return true;
		case OP_ONLY:
			if (this.senderPlayer.isOp())
				return true;
			else 
				this.sendError(StringUtil.ERROR_NOT_OP_MESSAGE);
		case CHEZ_ONLY:
			if (PlayerUtil.playerIsChezburgr(this.targetPlayer)) {
				return true;
			} else {
				this.sendMessage(StringUtil.ERROR_NOT_CHEZBURGR_MESSAGE);
				this.reportToAdmins(StringUtil.ERROR_NOT_CHEZBURGR_ADMIN_NOTIFICATION);
			}
		}
		return false;
	}
	
	public void executeCommand() {
		//TODO
	}
	
	public void sendTargetMessage(String message) {
		this.targetPlayer.sendMessage(StringUtil.parseString(message, this));
	}
	
	public void broadcaseMessage(String message) {
		Bukkit.getServer().broadcastMessage(StringUtil.parseString(message, this));
	}
	
	public void sendMessage(String message) {
		this.senderRaw.sendMessage(StringUtil.parseString(message, this));
	}
	
	public void sendError(String message) {
		this.senderRaw.sendMessage(StringUtil.parseError(message, this));
	}
	
	public void reportToAdmins(String rawMessage) {
		String message = StringUtil.parseString(rawMessage, this);
		Bukkit.getConsoleSender().sendMessage(message);
		PlayerUtil.getChezburgr().sendMessage(message);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String[] getArguments() {
		return this.arguments;
	}
	
	public CommandSender getSender() {
		return this.senderRaw;
	}
	
	public Player getSenderPlayer() {
		return this.senderPlayer;
	}
	
	public String getSenderName() {
		return this.senderName;
	}
	
	public Player getTargetPlayer() {
		return this.targetPlayer;
	}
	
	public String getTargetName() {
		if (this.hasTarget())
			return this.targetName;
		else
			return this.givenTarget;
	}
	
	public String getGivenTarget() {
		return this.givenTarget;
	}
	
	private void initializeSender(CommandSender sender) {
		this.senderRaw = sender;
		if (this.senderRaw instanceof Player)
			this.senderPlayer = (Player) this.senderRaw;
	}
	
	private void initializeSenderName() {
		if (this.isFromPlayer()) {
			this.senderName = this.senderPlayer.getName();
		} else {
			this.senderName = "The Console";
		}
	}
	
	@SuppressWarnings("deprecation")
	private void initializeTarget() {
		if (this.arguments.length > 0)
			this.givenTarget = this.arguments[0];
		else
			this.givenTarget = "";
		if (this.rules.getTargetable() != Commands.Target.NONE)
			this.targetPlayer = Bukkit.getPlayer(givenTarget);
		if (this.hasTarget())
			this.targetName = this.targetPlayer.getName();
		else
			this.targetName = "[No Target]";
	}

}
