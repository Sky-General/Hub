package net.portalkings.hub.PaintBall;

import java.util.HashSet;
import java.util.Random;

import net.portalkings.hub.Main;
import net.portalkings.hub.ScoreBoard.SBoardUpdate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class PBallListener implements Listener {
	Main plugin;
	HashSet<String> inArena = new HashSet<String>();

	public PBallListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(e.getPlayer().getItemInHand().getType().equals(Material.IRON_BARDING)){
				if (e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL)) {
					Player player = e.getPlayer();
					player.setGameMode(GameMode.SURVIVAL);
					player.launchProjectile(Snowball.class, player.getEyeLocation().getDirection().multiply(2));
					} else {
						e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&3Hub&e] &3You are not in an PaintBall arena."));
						inArena.remove(e.getPlayer().getName());
						}
				}
			}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e){
		if(e.getEntity() instanceof Snowball){
			Snowball sball = (Snowball) e.getEntity();
			if(sball.getShooter() instanceof Player){
				Player player = (Player) sball.getShooter();
				
					BlockIterator iterator = new BlockIterator(e.getEntity().getWorld(), e.getEntity().getLocation().toVector(), e.getEntity().getVelocity().normalize(), 0, 4);
				    Block hitBlock = null;

				    while(iterator.hasNext()) {
				        hitBlock = iterator.next();
				        if(hitBlock.getTypeId()!=0) //Check all non-solid blockid's here.
				            break;
				    }
				    if(hitBlock.getType().equals(Material.WOOL)){
				    	Random random = new Random();
					    hitBlock.setData((byte) random.nextInt(16 - 0 + 1));
					    Bukkit.getScheduler().runTaskLater(plugin, new ResetBlock(hitBlock), 100);
				    }
				    
				}
		}
	}
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		if(e.isCancelled()) return;
		e.setCancelled(true);
	}
	@EventHandler
	public void onPlayerDamageByEntity(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Snowball){
			if(e.getEntity() instanceof Player){
				Player player = (Player) e.getEntity();
				Snowball sball = (Snowball) e.getDamager();
				if(sball.getShooter() instanceof Player){
					Player shooter = (Player) sball.getShooter();
					
						
						shooter.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&3CreativeGames&e] &3You shot " + player.getName()));
						plugin.getConfig().set("PaintBall.Player." + player.getName() + ".Points", plugin.getConfig().getInt("PaintBall.Player." + player.getName() + ".Points")+1);
						plugin.saveConfig();
						player.setVelocity(new Vector(0,2,0));
						Bukkit.getScheduler().runTask(plugin, new SBoardUpdate(shooter, plugin));
						
					
				}
				
			}
		}
		e.setCancelled(true);
	}


}
