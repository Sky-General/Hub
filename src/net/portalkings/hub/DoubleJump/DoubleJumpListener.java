package net.portalkings.hub.DoubleJump;

import java.util.HashSet;

import net.portalkings.hub.Main;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class DoubleJumpListener implements Listener {
	Main plugin;

	public DoubleJumpListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	HashSet<String> FallingPlayers = new HashSet<String>();
	HashSet<String> PermPlayers = new HashSet<String>();

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if ((e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
				&& ((e.getEntity() instanceof Player))) {
			Player player = (Player) e.getEntity();
			if (this.FallingPlayers.contains(player.getName()))
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerClick(PlayerMoveEvent e) {
		if (e.getPlayer().hasPermission("pk.doublejump"))
			this.PermPlayers.add(e.getPlayer().getName());
		else {
			return;
		}
		if (this.PermPlayers.contains(e.getPlayer().getName())) {
			Player player = e.getPlayer();
			if (player.getGameMode() != GameMode.CREATIVE) {
				if (player.getLocation().getBlock().getRelative(BlockFace.DOWN)
						.getType() != Material.AIR) {
					this.FallingPlayers.remove(player.getName());
					player.setAllowFlight(true);
				}
				if (player.isFlying()) {
					if (!player.hasPermission("pk.infajump")) {
						this.FallingPlayers.add(player.getName());
						player.setAllowFlight(false);
					} else {
						player.setFlying(false);
					}
					Vector direction = e.getPlayer().getEyeLocation()
							.getDirection().multiply(1.5D).setY(1.0D);
					player.setVelocity(direction);
					Particles.sendToPlayer(Particles.GREEN_SPARKLE, player, player.getLocation(), 1F, 1F, 1F, 0.2F, 30);
					this.FallingPlayers.add(e.getPlayer().getName());
					if (player.getLocation().getBlock()
							.getRelative(BlockFace.DOWN).getType() == Material.AIR)
						if (!player.hasPermission("pk.infajump")) {
							this.FallingPlayers.add(player.getName());
							player.setAllowFlight(false);
						} else {
							player.setFlying(false);
						}
				}
			}
		}
	}
}
