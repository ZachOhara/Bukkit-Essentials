package io.github.zachohara.chezburgr.command;

import io.github.zachohara.chezburgr.util.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Executable {
	
	LOCATE(new Locate()),
	TAKEDOWN(new Takedown()),
	KILLPLAYER(new Killplayer()),
	GETREKT(new Getrekt()),
	PING(new Ping()),
	AFK(new Afk()),
	NOAFK(new Noafk()),
	SPEAKFOR(new Speakfor()),
	FORCECHAT(new Forcechat());
	
	private Implementation implement;
	
	private Executable(Implementation implement) {
		this.implement = implement;
	}
	
	public Implementation getImplementation() {
		return this.implement;
	}
	
	private static class Locate extends Implementation {
		
		@Override
		public String getName() {
			return "locate";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			Location loc = instance.getTargetPlayer().getLocation();
			String locString = StringUtil.getLocationString(loc);
			instance.sendMessage("%t is currently at " + locString);
			return true;
		}
		
	}
	
	private static class Takedown extends Implementation {
		
		@Override
		public String getName() {
			return "takedown";
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			Location senderLoc = instance.getSenderPlayer().getLocation();
			instance.getTargetPlayer().teleport(senderLoc);
			instance.getTargetPlayer().setHealth(0.0);
			if (instance.getArguments().length >= 1
					&& instance.getArguments()[1].equalsIgnoreCase("ban"))
				instance.getTargetPlayer().setBanned(true);
			instance.getTargetPlayer().kickPlayer("You have been kicked from the Server");
			return true;
		}
		
	}
	
	private static class Killplayer extends Implementation {
		
		@Override
		public String getName() {
			return "killplayer";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			instance.getTargetPlayer().setHealth(0.0);
			instance.sendTargetMessage("%s has killed you with magic");
			instance.sendMessage("You have killed %t with magic");
			return true;
		}
		
	}
	
	private static class Getrekt extends Implementation {
		
		@Override
		public String getName() {
			return "getrekt";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.sendTargetMessage("%s has rekt you!\nGet rekt, noob!");
				instance.sendMessage("You have rekt %t");
			} else {
				instance.broadcaseMessage("Get rekt, noobs!");
			}
			return true;
		}
		
	}
	
	private static class Ping extends Implementation {
		
		@Override
		public String getName() {
			return "ping";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			instance.sendMessage("Pong!");
			return true;
		}
		
	}
	
	private static class Afk extends Implementation {
		
		@Override
		public String getName() {
			return "afk";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.broadcaseMessage("%t is now AFK");
			} else {
				instance.broadcaseMessage("%s is now AFK");
			}
			return true;
		}
		
	}
	
	private static class Noafk extends Implementation {
		
		@Override
		public String getName() {
			return "noafk";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.broadcaseMessage("%t is no longer AFK");
			} else {
				instance.broadcaseMessage("%s is no longer AFK");
			}
			return true;
		}
		
	}
	
	private static class Speakfor extends Implementation {
		
		@Override
		public String getName() {
			return "speakfor";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			String message = "";
			for (int i = 1; i < instance.getArguments().length; i++)
				message += " " + instance.getArguments()[i];
			Bukkit.getServer().broadcastMessage("<" + instance.getTargetName() + ">" + message);
			return true;
		}
		
	}
	
	private static class Forcechat extends Implementation {
		
		@Override
		public String getName() {
			return "forcechat";
		}
		
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			String message = "";
			for (int i = 1; i < instance.getArguments().length; i++)
				message += instance.getArguments()[i] + " ";
			instance.getTargetPlayer().chat(message);
			return true;
		}
		
	}

}
