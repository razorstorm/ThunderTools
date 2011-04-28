package com.bukkit.jason.thunderTools;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Road for Bukkit
 * 
 * @author <Your Name>
 */
public class ThunderTools extends JavaPlugin
{
	private final ThunderToolsPlayerListener playerListener = new ThunderToolsPlayerListener(this);
	private final ThunderToolsEntityListener entityListener = new ThunderToolsEntityListener(this);
	private final ThunderToolsBlockListener blockListener = new ThunderToolsBlockListener(this);
	// private final RoadBlockListener blockListener = new
	// RoadBlockListener(this);
	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	public HashMap<String, Boolean> light = new HashMap<String, Boolean>();
	public static PluginDescriptionFile pdfFile;

	public void onEnable()
	{
		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pdfFile = this.getDescription();
		ThunderToolsSettings.load();

		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.REDSTONE_CHANGE, blockListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Normal, this);
		// EXAMPLE: Custom code, here we just output some info so we can check
		// all is well
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	public void onDisable()
	{
		// TODO: Place any custom disable code here

		// NOTE: All registered events are automatically unregistered when a
		// plugin is disabled

		// EXAMPLE: Custom code, here we just output some info so we can check
		// all is well
		System.out.println("Goodbye world!");
	}

	public boolean isDebugging(final Player player)
	{
		if (debugees.containsKey(player))
		{
			return debugees.get(player);
		}
		else
		{
			return false;
		}
	}

	public void setDebugging(final Player player, final boolean value)
	{
		debugees.put(player, value);
	}
}
