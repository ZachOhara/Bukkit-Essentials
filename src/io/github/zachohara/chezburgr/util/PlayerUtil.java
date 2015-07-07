package io.github.zachohara.chezburgr.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtil {
	
	public static final UUID chezburgr = UUID.fromString("5420ca86-36f0-4d54-8096-4352555fd1d6");
	
	public static boolean playerIsChezburgr(Player other) {
		return other.getUniqueId().equals(chezburgr);
	}
	
	public static Player getChezburgr() {
		return Bukkit.getPlayer(chezburgr);
	}
	
	public static String getChezburgrName() {
		return "Chezburgr";
	}

}
