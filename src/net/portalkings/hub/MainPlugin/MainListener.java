package net.portalkings.hub.MainPlugin;

import net.portalkings.hub.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainListener implements Listener {
	Main plugin;
	public MainListener(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		e.setJoinMessage(null);
		e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&3CreativeGames&e]&3 Welcome to the network!"));
		e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
		Player player = e.getPlayer();
		ItemStack pgun = new ItemStack(Material.IRON_BARDING);
		ItemMeta pgm = pgun.getItemMeta();
		pgm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7[&3Paintball &eGun&7]"));
		pgun.setItemMeta(pgm);
		player.getInventory().setItem(0, pgun);
		
	}
}
