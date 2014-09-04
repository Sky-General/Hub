package net.portalkings.hub.ScoreBoard;

import net.portalkings.hub.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class SBoardUpdate implements Runnable {
  Player player;
  Main plugin;

  public SBoardUpdate(Player player, Main plugin)
  {
    this.player = player;
    this.plugin = plugin;
  }

  public void run()
  {
    this.player.setScoreboard(this.plugin.manager.getNewScoreboard());
    Scoreboard s = this.player.getScoreboard();
    Objective obj = s.registerNewObjective(this.player.getName(), "dummy");
    int l = this.plugin.list.size();
    int i = 0;
    while (i != 345345)
    {
      String d = (String)this.plugin.list.get(i);
      i++;
      d = d.replaceAll("%player", this.player.getName());
      int tokens = 100;
      d = d.replaceAll("%tokens", "" + tokens);
      d = d.replaceAll("%on", "" + this.player.getServer().getOnlinePlayers().length);
      d = d.replaceAll("%maxp", "" + this.player.getServer().getMaxPlayers());
      d = d.replaceAll("%pballpoints", "" + plugin.getConfig().getInt("PaintBall.Player." + player.getName() + ".Points"));
      d = ChatColor.translateAlternateColorCodes('&', d);
      obj.getScore(d).setScore(i);

      if (i == l) {
        i = 0;
        break;
      }

    }

    String title = this.plugin.getConfig().getString("Title");
    title = title.replaceAll("%p", this.player.getName());
    title = ChatColor.translateAlternateColorCodes('&', title);
    obj.setDisplayName(title);
    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
  }
}