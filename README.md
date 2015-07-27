# Bukkit Essentials

Bukkit Essentials is a plugin for a Bukkit server that adds a small but useful set of command-line tools. It is last confirmed to work on Bukkit version 1.7.9-R2. A full list of commands that this plugin adds is included below.

This plugin uses my Bukkit Common Library. The server *must* have that plugin installed for this plugin to work. The Bukkit Common Library can be downloaded from [GitHub](http://github.com/zachohara/bukkit-common)

Along with all of the source code, in the root folder of this repository you'll find [detailed documentation](javadoc) and a compiled .jar version of the project.

I may or may not support this software in the future, but feel free to send a pull request if you think you have a way to improve it. There is no warranty on this software, and I am absolutely not going to do full-time tech support for it, but I will try to be as helpful as I can if you're having problems. Send me an email, or create a new issue.

This entire repository is made available under the GNU General Public License v3.0. A full copy of this license is available as the [LICENSE](LICENSE) file in this repository, or at [gnu.org/licenses](http://www.gnu.org/licenses/).

## Installation

Download the "Essentials v___.jar" from the root folder of this repository, or check out the [releases page](https://github.com/ZachOhara/Bukkit-Essentials/releases) and download the latest version. Drop either file into the 'plugins' folder on your server.

## Added Commands:

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

### NoAFK

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
