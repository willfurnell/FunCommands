package com.willfurnell.FunCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin{
	
	public void versionCheck() {
		// Our plugin version! This is /very/ important.
		
    	URL url = null;
		try {
			url = new URL("http://www.willfurnell.com/intranet/bukkit/fc.ver");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String line = null;
    	try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String version = "0.2";
    	if (!version.equals(line)) {
    		getLogger().info("An update to FunCommands is available! You are running version 0.2, whereas the latest version is " + line);
    	} else {
    		getLogger().info("FunCommands is up to date");
    	}
    	
	}
	
	
    @Override
    public void onEnable() {
    	versionCheck();
    	getLogger().info("FunCommands by willfurnell enabled.");
    	setupCommands();
    	
    }
    
    public void setupCommands() {
    	getCommand("ragequit").setExecutor(new Commands(this));
    	getCommand("troll").setExecutor(new Commands(this));
    	getCommand("funcommands").setExecutor(new Commands(this));
    	getCommand("suicide").setExecutor(new Commands(this));
    	getCommand("rocket").setExecutor(new Commands(this));
    }
    
	@Override
    public void onDisable() {
    	getLogger().info("FunCommands disabled.");
    }
	
}
