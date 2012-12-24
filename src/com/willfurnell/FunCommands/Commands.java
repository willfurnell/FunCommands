package com.willfurnell.FunCommands;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.util.Vector;

import com.avaje.ebean.EbeanServer;

public class Commands implements CommandExecutor, Plugin{

	private Main plugin;
	public Commands(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		//START COMMANDS
		
		// /funcommands
		if(cmd.getName().equalsIgnoreCase("funcommands")){
				//We have a few arguments in this one: help credits list
				//Check if its a player
				if (sender instanceof Player) {
					final Player player = (Player) sender;
					if (player.hasPermission("funcommands.fc")) {

					if (args.length == 1) {
						 if (args[0].equalsIgnoreCase("help")) {
							 player.sendMessage(ChatColor.YELLOW + "--- FunCommands Help ---\nFunCommands is a simple plugin that facilitates, you guessed it, fun commands.\n" + ChatColor.GREEN + "Commands:\n" + ChatColor.YELLOW + "/funcommands help\n/funcommands credits\n/funcommands list");
						 }
						 if (args[0].equalsIgnoreCase("credits")) {
							 player.sendMessage(ChatColor.YELLOW + "--- FunCommands Credits ---\nVersion 0.2 (24/12/12)\nBuilt by Will Furnell\nwww.willfurnell.com");
						 }
						 if (args[0].equalsIgnoreCase("list")) {
							 player.sendMessage(ChatColor.YELLOW + "--- FunCommands List ---\n/troll - You're trolling, right?\n/ragequit - Ragequit from the server\n/suicide - Commit suicide\n/rocket <player> - Send a specified player into the air.");
						 }
					} else {
						player.sendMessage(ChatColor.GRAY +"Type '/funcommands help' for help.");
					}
		            return true;
					} else {
						sender.sendMessage(ChatColor.YELLOW + "[FC]" + ChatColor.RED + " You don't have permission to use this command!");
						return true;
					}
		        } else {
		        	// Shit! Its the console, we better alert them!
			       sender.sendMessage("You must be a player!");
		           return true;
		        }
		} 
		
		// /ragequit
		if(cmd.getName().equalsIgnoreCase("ragequit")){ 
				// If the player typed /ragequit then do the following..
				//Check if its a player
				if (sender instanceof Player) {
					final Player player = (Player) sender;
					if (player.hasPermission("funcommands.ragequit")) {
		            Bukkit.getServer().broadcastMessage(ChatColor.RED + sender.getName() + " has ragequit.");
		            //Kick the player too!
		            player.kickPlayer("You have ragequitted from the server!");
		            return true;
					} else {
						sender.sendMessage(ChatColor.YELLOW + "[FC]" + ChatColor.RED + " You don't have permission to use this command!");
						return true;
					}
		        } else {
		        	// Shit! Its the console, we better alert them!
		           sender.sendMessage("You must be a player!");
		           return true;
		        }
		} 
		
		// /suicide
		if(cmd.getName().equalsIgnoreCase("suicide")){
				// If the player typed /suicide then do the following..
				//Check if its a player
				if (sender instanceof Player) {
					final Player player = (Player) sender;
					if (player.hasPermission("funcommands.suicide")) {
		            Bukkit.getServer().broadcastMessage(ChatColor.RED + sender.getName() + " killed themselves...");
		            //Now to kill them...
		            player.setHealth(0);
		            return true;
					} else {
						sender.sendMessage(ChatColor.YELLOW + "[FC]" + ChatColor.RED + " You don't have permission to use this command!");
						return true;
					}
		        } else {
		        	// Shit! Its the console, we better alert them!
		           sender.sendMessage("You must be a player! Why would you wan't to kill the server anyway?!");
		           return true;
		        }
		} 
		
		// /troll
		if(cmd.getName().equalsIgnoreCase("troll")){ 
				// If the player typed /troll then do the following..
				//Check if its a player
				if (sender instanceof Player) {
					final Player player = (Player) sender;
					if (player.hasPermission("funcommands.troll")) {
		            Bukkit.getServer().broadcastMessage(ChatColor.GRAY + sender.getName() + " is a troll!\n /trolling");
		            return true;
					} else {
						sender.sendMessage(ChatColor.YELLOW + "[FC]" + ChatColor.RED + " You don't have permission to use this command!");
						return true;
					}
		        } else {
		        	// Shit! Its the console, we better alert them!
		        	Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "The server is trolling you!");
		           return true;
		        }

		} 
		
		// /rocket <player>
		if(cmd.getName().equalsIgnoreCase("rocket")){
			final Player player = (Player) sender;
				//Check if its a player
				if (sender instanceof Player) {
					if (player.hasPermission("funcommands.rocket")) {
						if (args.length == 1) {
							final String argTarg = args[0];
							final Player target = Bukkit.getServer().getPlayer(argTarg);
							if (target == null) {
								sender.sendMessage(ChatColor.RED + "Player doesn't exist!");
								return true;
							} else {
					
								final float explosionPower = 4F;
								target.setVelocity(new Vector(0, 10, 0));
								Bukkit.getServer().broadcastMessage(ChatColor.RED + argTarg + " was sent into outer space!");
								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
									@Override
									public void run() {
										target.getWorld().createExplosion(target.getLocation(), explosionPower);
										target.setHealth(0);
									}
								}, 40L);
								return true;
							}
						} else {
							player.sendMessage(ChatColor.GRAY +"You need to specify a player!");
							return true;
						}
					} else {
						sender.sendMessage(ChatColor.YELLOW + "[FC]" + ChatColor.RED + " You don't have permission to use this command!");
						return true;
					}
				
				} else {
					sender.sendMessage("You must be a player!");
					return true;
				}
			
		} 
		//END COMMANDS//
		
		//If this has happened the function will break and return true. if this hasn't happened the a value of false will be returned.		
		return false; 
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1,
			String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileConfiguration getConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getDataFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EbeanServer getDatabase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PluginDescriptionFile getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PluginLoader getPluginLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getResource(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNaggable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reloadConfig() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveConfig() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveDefaultConfig() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveResource(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNaggable(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
}