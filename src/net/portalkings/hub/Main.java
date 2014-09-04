package net.portalkings.hub;

import java.util.ArrayList;

import net.portalkings.hub.DoubleJump.DoubleJumpListener;
import net.portalkings.hub.Item.ItemListener;
import net.portalkings.hub.MainPlugin.MainListener;
import net.portalkings.hub.MainPlugin.SpawnCommand;
import net.portalkings.hub.PaintBall.PBallListener;
import net.portalkings.hub.ScoreBoard.SBoardListener;
import net.portalkings.hub.ScoreBoard.SBoardUpdate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin {
  public ScoreboardManager manager;
  public Scoreboard board;
  public Objective score;
  public ArrayList<String> list;

  public void onEnable()
  {
    saveDefaultConfig();
    this.list = ((ArrayList<String>)getConfig().getStringList("Messages"));
    this.manager = Bukkit.getScoreboardManager();
    this.board = this.manager.getNewScoreboard();
    this.score = this.board.registerNewObjective("dummy", "dummy");
    new SBoardListener(this);
    new ItemListener(this);
    new DoubleJumpListener(this);
    new PBallListener(this);
    new ReloadCommand(this, "preload");
    new SpawnCommand(this, "spawn");
    new MainListener(this);
    for (Player player : Bukkit.getOnlinePlayers())
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new SBoardUpdate(player, this));
  }
}