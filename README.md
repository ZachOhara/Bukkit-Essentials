# Bukkit Essentials

This is a Bukkit server plugin I wrote a long while ago. It adds some extra commands to any server running the Craftbukkit implementation of Minecraft. It is last confirmed to work on version 1.7.9_R2 of Craftbukkit. The commands and their functionality are outlined below.

This plugin uses my Bukkit Common library to run. The Bukkit Common library is also available on [GitHub](https://github.com/ZachOhara/Bukkit-Common).

I will probably not ever *actually* support this software ever again, but if you have a way to improve it, feel free to submit a pull request. I may occasionally commit a few changes every now and again when I find out something's been broken. As the license states, there is absolutely no garunteed warranty on this software; that being said, if you have a problem using it, feel free to ask for help. If I'm feeling nice, I'll help you out (but I reserve the right to refuse help for any reason).

This entire repository is made available under the GNU General Public License v3.0. A full copy of this license is available as the [LICENSE](LICENSE) file in this repository, or at [gnu.org/licenses](http://www.gnu.org/licenses/).

## Added Commands:

### Locate

Usage: `/locate <player>`

This command is usable only by players with admin privileges, or directly by the console. It will send you the <x, y, z> coordinates of the given player. You can press F3 while in-game to display the debug information, which includes your own coordinates. You can use these two things together to follow any other player in the game.

### Killplayer

Usage: `/killplayer <player>`

This one's pretty self-explanitory. It kills any given player at their location, dumping their items and some of their experience points on to the ground around them. For obvious reasons, this command is usable only by a player with admin privileges, or directly by the console.

### GetRekt

Usage: `/getrekt [player]`

The player argument is optional. If a player is specified, a message will be sent to that player telling them to "Get rekt, noob!" If no player is given, the message is broadcast to all players on the server.

### Ping

Usage: `/ping`

The ping command is used to test the lag of the server. The server will respond with "pong!" as soon as it is allowed to process and return the request.

### AFK

Usage: `/afk [player]`

The AFK command, sent without arguments, will announce to all players on the server that you are now AFK (away from the keyboard). If the player sending the command is an admin, or the command is being sent from the console, the name of a player can be specified, and the command will announce that the specified player is AFK instead of the sender.

### NOAFK

Usage: `/noafk [player]`

This behaves exactly like the AFK command, but it will announce that a player is no longer AFK.

### SpeakFor

Usage: `/speakfor <player> <message>

This command is usable by anyone. It can be used to fabricate chat messages to display as if they were sent by another player. This command is limited to fabricating chat messages; it cannot be used to send server commands as another player.

### ForceChat

Usage: `/forcechat <player> <message>`

This command behaves similarly to SpeakFor, but it is not limited to only chat messages. This command can fabricate commands to seem like they came from another player, and the server will respond appropriately to the false sender. To prevent otherwise rampant abuse, this command is limited only to use by admins or the server console.

### Takedown

Usage: `/takedown <player> ['ban']`

The Takedown command should be reserved only for emergencies. The takedown command can only be used by players with admin privileges. When the command is used, the specified player will be teleported immmediately to the player that sent the command, and killed. This is different from the KillPlayer command in that the KillPlayer command will kill the player at their current location, with their items falling on to the ground wherever they are, while this command will teleport the player to an admin before killing them, so that all items may be safely recovered. If the word "ban" is sent as a third argument to the command, the targeted player will be kicked and banned from the server immediately following their death.
