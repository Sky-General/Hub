package net.portalkings.hub.ScoreBoard;

import net.portalkings.hub.Main;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class SBoardListener implements Listener {
  Main plugin;

  public SBoardListener(Main plugin){
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
	  for (Player player : Bukkit.getOnlinePlayers()) Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new SBoardUpdate(player, this.plugin)); 
	  }
  @EventHandler
  public void onPlayerJoin(PlayerQuitEvent e) { for (Player player : Bukkit.getOnlinePlayers()) Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new SBoardUpdate(player, this.plugin));
  }
  }