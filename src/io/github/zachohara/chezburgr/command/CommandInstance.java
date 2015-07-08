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

/**
 * A {@code CommandInstance} object represents a single invocation of any command that is
 * registered to this plugin. The object stores relevant information about the command, such
 * as the player or console that sent the command, the player that was targeted by the command
 * (if applicable), as well as any additional arguments that were sent with the command.
 * 
 * @author Zach Ohara
 */
public class CommandInstance {
	
	/**
	 * The name of the command that was called.
	 */
	private String name;
	
	/**
	 * The arguments (if any) that were sent along with the command.
	 */
	private String[] arguments;
	
	/**
	 * The general, non-instance-specific format and properties of the sent command.
	 */
	private Commands rules;
	
	
	/**
	 * The entity that sent the command. This may be a player or the console.
	 */
	private CommandSender senderRaw;
	
	/**
	 * The player that sent the command. {@code null} if the command was sent by the console.
	 */
	private Player senderPlayer;
	
	/**
	 * The name of the entity that sent the command.
	 */
	private String senderName;
	
	
	/**
	 * The player that was targeted by the command (if applicable).
	 */
	private Player targetPlayer;
	
	/**
	 * The name of the player that was targeted by the command (if applicable).
	 */
	private String targetName;
	
	/**
	 * The name that was supplied as a target, regardless of whether or not the name is a
	 * valid target.
	 */
	private String givenTarget;
	
	
	/**
	 * Constructs a new {@code CommandInstance} based on availble information about the command.
	 * @param rawSender the entity that sent the command.
	 * @param rawCommand a {@code Command} object representing the command.
	 * @param args all additional arguments sent with the command.
	 */
	public CommandInstance(CommandSender rawSender, Command rawCommand, String[] args) {
		this.name = rawCommand.getName().toLowerCase();
		this.arguments = args;
		this.rules = Commands.fromString(name);
		this.initializeSender(rawSender);		
		this.initializeSenderName();
		this.initializeTarget();
	}
	
	/**
	 * Returns {@code true} if an in-game player sent the command, or {@code false} if the
	 * command was sent by the server console.
	 * @return {@code true} if the command was sent by a player; {@code false} otherwise.
	 * @see {@link #isFromConsole()}
	 */
	public boolean isFromPlayer() {
		return this.senderPlayer != null;
	}
	
	/**
	 * Returns {@code true} if the server console sent the command, or {@code false} if an
	 * in-game player sent the command.
	 * @return {@code true} if the command was sent by the console; {@code false} otherwise.
	 * @see {@link #isFromPlayer()}
	 */
	public boolean isFromConsole() {
		return this.senderPlayer == null;
	}
	
	/**
	 * Returns {@code true} if and only if this command was send with a valid
	 * specified target player.
	 * @return {@code true} if there is a valid target player attached to this command;
	 * {@code false} otherwise.
	 */
	public boolean hasTarget() {
		return this.targetPlayer != null;
	}
	
	/**
	 * Checks the validity of the conditions that this command was sent with. The method
	 * will verify the following things about the conditions of the command:
	 * <ol>
	 * <li>An appropriate number of arguments were sent with the command.</li>
	 * <li>The target player that was specified with the command (if applicable) is a valid
	 * player given the conditions of the specific command.</li>
	 * <li>The entity that sent the command has permission to use the specific command.</li>
	 * </ol>
	 * If all the above conditions are met given the circumstances, the command has been
	 * successfully verified, and this method will return {@code true}.  
	 * @return {@code true} if and only if all prerequisite conditions for the command are
	 * met; {@code false} otherwise.
	 * @see {@link #verifyArguments()}
	 * @see {@link #verifyValidTarget()}
	 * @see {@link #verifyValidSource()}
	 */
	public boolean verifyCommand() {
		return this.verifyArguments()
			&& this.verifyValidTarget()
			&& this.verifyValidSource();
	}
	
	/**
	 * Verifies that the command was sent with an appropriate amount of arguments. If the
	 * amount of arguments is not valid, this method will return an appropriate response
	 * to the player or console that sent the command.
	 * @return {@code true} if and only if the amount of arguments that were sent with
	 * the command match the expected conditions for the command; {@code false} otherwise.
	 * @see {@link #verifyCommand()}
	 */
	private boolean verifyArguments() {
		if (this.arguments.length < this.rules.getMinArgs()) {
			this.sendError(StringUtil.ERROR_TOO_FEW_ARGS_MESSAGE);
			return false;
		}
		if (this.rules.getMaxArgs() != -1
				&& this.arguments.length > this.rules.getMaxArgs()) {
			this.sendError(StringUtil.ERROR_TOO_MANY_ARGS_MESSAGE);
			return false;
		}
		return true;
	}
	
	/**
	 * Verifies that the specified target player is a valid, online, player that is not
	 * especially protected from this command. If the specified target player is not valid,
	 * this method will return an appropriate response to the player or console that sent
	 * the command.
	 * @return {@code true} if and only if the specified target player is a valid target
	 * for this command;  {@code false} otherwise.
	 * @see {@link #verifyCommand()}
	 */
	private boolean verifyValidTarget() {
		switch (this.rules.getTargetable()) {
		case NONE:
			return true;
		case RESTRICT_ADMIN:
			if (this.givenTarget.equalsIgnoreCase(PlayerUtil.getAdminName())) {
				this.sendMessage(StringUtil.ERROR_ADMIN_PROTECTED_MESSAGE);
				this.reportToAdmins(StringUtil.ERROR_ADMIN_PROTECTED_ADMIN_NOTIFICATION);
				return false;
			}
		case ALL:
			if (this.hasTarget() || this.arguments.length == 0) {
				return true;
			} else {
				this.sendError(StringUtil.ERROR_TARGET_OFFLINE);
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Verifies that the entity that sent this command has permission to do so. If the
	 * sender does not have the required permission to use this command, this method will
	 * return an appropriate response to the player or console that sent this command.
	 * @return {@code true} if and only if the entity that sent this command has the
	 * required permission and ability to do so; {@code false} otherwise.
	 * @see {@link #verifyCommand()}
	 */
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
		case ADMIN_ONLY:
			if (PlayerUtil.playerIsAdmin(this.senderPlayer)) {
				return true;
			} else {
				this.sendMessage(StringUtil.ERROR_ADMIN_ONLY_MESSAGE);
				this.reportToAdmins(StringUtil.ERROR_ADMIN_ONLY_ADMIN_NOTIFICATION);
			}
		}
		return false;
	}
	
	/**
	 * Sends a given message to the target player specified by this command. The message
	 * will be formatted and colored before it is sent. If there is no valid target player
	 * attached to this command, a {@code NullPointerException} will be thrown.
	 * @param message the message to be sent.
	 */
	public void sendTargetMessage(String message) {
		this.targetPlayer.sendMessage(StringUtil.parseString(message, this));
	}
	
	/**
	 * Sends a given message to all players and consoles on the server. The message will
	 * be formatted and colored before it is sent.
	 * @param message the message to be sent.
	 */
	public void broadcaseMessage(String message) {
		Bukkit.getServer().broadcastMessage(StringUtil.parseString(message, this));
	}
	
	/**
	 * Sends a given message to the player or console that sent this command. The message
	 * will be formatted before and colored it is sent.
	 * @param message the message to be sent.
	 */
	public void sendMessage(String message) {
		this.senderRaw.sendMessage(StringUtil.parseString(message, this));
	}
	
	/**
	 * Sends a given error message to the player or console that sent this command. The
	 * error message will be formatted and colored before it is sent.
	 * @param message the error message to be sent.
	 */
	public void sendError(String message) {
		this.senderRaw.sendMessage(StringUtil.parseError(message, this));
	}
	
	/**
	 * Sends a given message to the console and to the admin of the server. The message
	 * will be formatted and colored before it is sent.
	 * @param message the message to be sent.
	 */
	public void reportToAdmins(String message) {
		String formattedMessage = StringUtil.parseString(message, this);
		Bukkit.getConsoleSender().sendMessage(formattedMessage);
		PlayerUtil.sendAdmin(formattedMessage);
	}
	
	/**
	 * Gets the name of the command that was sent.
	 * @return the name of the command that was sent.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the arguments that were sent with this command.
	 * @return the arguments that were sent with this command.
	 */
	public String[] getArguments() {
		return this.arguments;
	}
	
	/**
	 * Gets the {@code CommandSender} object that sent this command, regardless of
	 * whether the command was sent by a player or console.
	 * @return the entity that sent this command.
	 */
	public CommandSender getSender() {
		return this.senderRaw;
	}
	
	/**
	 * Gets the {@code Player} object that sent this command, if the command was sent
	 * by a player. Returns {@code null} if the command was not sent by a player.
	 * @return the {@code Player} object that sent this command, or {@code null} if the
	 * command was not sent by a player.
	 */
	public Player getSenderPlayer() {
		return this.senderPlayer;
	}
	
	/**
	 * Gets the name of the player that sent this command or {@code "The Console"} if the
	 * command was sent by the console.
	 * @return the name of the player or console that sent this command.
	 */
	public String getSenderName() {
		return this.senderName;
	}
	
	/**
	 * Gets the target player attached to this command, or {@code null} if no valid target
	 * player was specified.
	 * @return the target player attached to this command.
	 */
	public Player getTargetPlayer() {
		return this.targetPlayer;
	}
	
	/**
	 * Gets the name of the target player attached to this command, or whatever was specified
	 * as a target player if the target player is not valid.
	 * @return the name of the target player attached to this command.
	 */
	public String getTargetName() {
		if (this.hasTarget())
			return this.targetName;
		else
			return this.givenTarget;
	}
	
	/**
	 * Gets the name of the player that was specified as the target of this command,
	 * regardless of whether the specified player is valid.
	 * @return the specified name of the target player.
	 */
	public String getGivenTarget() {
		return this.givenTarget;
	}
	
	/**
	 * Determines if the command was sent by a console or a player, and initializes this
	 * {@code CommandInstance} object accordingly.
	 * @param sender the raw {@code CommandSender} object that represents the source of
	 * this command.
	 */
	private void initializeSender(CommandSender sender) {
		this.senderRaw = sender;
		if (this.senderRaw instanceof Player)
			this.senderPlayer = (Player) this.senderRaw;
	}
	
	/**
	 * Initializes instance variables according to the name of the player that sent this
	 * command, or {@code "The Console"} if the command was sent by a console. 
	 */
	private void initializeSenderName() {
		if (this.isFromPlayer()) {
			this.senderName = this.senderPlayer.getName();
		} else {
			this.senderName = "The Console";
		}
	}
	
	/**
	 * Determines if a target player was specified as an argument to this command, and
	 * initializes instance variables accordingly.
	 */
	@SuppressWarnings("deprecation")
	private void initializeTarget() {
		if (this.arguments.length > 0)
			this.givenTarget = this.arguments[0];
		else
			this.givenTarget = "";
		if (this.rules.getTargetable() != Commands.Target.NONE && this.givenTarget != "")
			this.targetPlayer = Bukkit.getPlayer(this.givenTarget);
		if (this.hasTarget())
			this.targetName = this.targetPlayer.getName();
		else
			this.targetName = "[No Target]";
	}

}
