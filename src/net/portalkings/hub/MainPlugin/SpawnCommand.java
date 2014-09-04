package net.portalkings.hub.MainPlugin;

import net.portalkings.hub.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
	Main plugin;
	public SpawnCommand(Main plugin, String cmd){
		this.plugin = plugin;
		plugin.getCommand(cmd).setExecutor(this);
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("spawn")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				player.teleport(player.getWorld().getSpawnLocation());
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&3CreativeGames&e] &3You have to been teleported to spawn."));
			}
		}
		return false;
	}

}
