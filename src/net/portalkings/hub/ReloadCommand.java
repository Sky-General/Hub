package net.portalkings.hub;

import net.portalkings.hub.ScoreBoard.SBoardUpdate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
	Main plugin;
	public ReloadCommand(Main plugin, String cmd){
		this.plugin = plugin;
		plugin.getCommand(cmd).setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("preload")){
			if(!sender.hasPermission("pk.dev")){
				sender.sendMessage(ChatColor.DARK_RED + "ERROR");
				return false;
			}
			if(!(args.length == 1)){
				sender.sendMessage(ChatColor.DARK_RED + "ERROR");
				return false;
			}
			try{
			plugin.getServer().getPluginManager().getPlugin(args[0]).reloadConfig();
			for (Player player : Bukkit.getOnlinePlayers())
				Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new SBoardUpdate(player, this.plugin));
			sender.sendMessage(ChatColor.GREEN + "Plugin reloaded");
			} catch(NullPointerException ex){
				sender.sendMessage(ChatColor.DARK_RED + "ERROR");
			}
			
		}
		return false;
	}
	

}
