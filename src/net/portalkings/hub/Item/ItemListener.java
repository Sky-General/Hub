package net.portalkings.hub.Item;

import net.portalkings.hub.Main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemListener implements Listener {
	Main plugin;
	public ItemListener(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player player = e.getPlayer();
		if(e.getPlayer().getLocation().getBlock().getType().equals(Material.GOLD_PLATE)){
			try{
				ItemStack i = new ItemStack(plugin.getConfig().getInt("Items." + ((int) player.getLocation().getX()) + ((int) player.getLocation().getY()) + ((int) player.getLocation().getZ()))); 
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("" + i.getTypeId() + ".name")));
				i.setItemMeta(im);
				i.setDurability((short) plugin.getConfig().getInt("" + i.getTypeId() + ".data"));
				player.getInventory().addItem(i);
				player.updateInventory();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(e.getClickedBlock().getType().equals(Material.GOLD_PLATE)){
				Block b = e.getClickedBlock();
				plugin.getConfig().set("Items." + ((int) b.getLocation().getX()) + ((int) b.getLocation().getY()) + ((int) b.getLocation().getZ()), player.getItemInHand().getTypeId());
				plugin.getConfig().set("" + player.getItemInHand().getTypeId() + ".name", "&f" + player.getItemInHand().getType().toString());
				plugin.getConfig().set("" + player.getItemInHand().getTypeId() + ".data", player.getItemInHand().getDurability());
				plugin.saveConfig();
				e.setCancelled(true);
				
				
			}
		}
	}

}
