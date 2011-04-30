package com.bukkit.jason.thunderTools;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * 
 * @author <Your Name>
 */
public class ThunderToolsPlayerListener extends PlayerListener
{
	private final ThunderTools plugin;

	public ThunderToolsPlayerListener(final ThunderTools instance)
	{
		plugin = instance;
	}

	public void onPlayerInteract(PlayerInteractEvent event)
	{
		super.onPlayerInteract(event);
		if (event.hasItem())
		{
			final Player player = event.getPlayer();
			if (event.getItem().getTypeId() == ThunderToolsSettings.strikeItem && ThunderToolsSettings.strikeEnabled)
			{
				if (ThunderTools.permission(player, "ThunderTools.strike"))
				{
					if ((event.getAction() == Action.LEFT_CLICK_BLOCK) || (event.getAction() == Action.LEFT_CLICK_AIR))
					{
						player.getWorld().strikeLightning(player.getTargetBlock(null, 3000).getLocation());
						plugin.light.put(player.getName(), true);
						event.getItem().setDurability((short) 9999999);
						plugin.getServer().getScheduler().cancelAllTasks();
						plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable()
						{
							public void run()
							{
								try
								{
									plugin.light.put(player.getName(), false);
								}
								catch (Exception e)
								{
								}
							}
						}, 40l);
					}
				}
			}
			else if (event.getItem().getTypeId() == ThunderToolsSettings.stormItem && ThunderToolsSettings.stormEnabled)
			{
				if (ThunderTools.permission(player, "ThunderTools.storm"))
				{
					Location loc = null;
					if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.RIGHT_CLICK_AIR))
					{
						loc = player.getLocation();
					}
					else if ((event.getAction() == Action.LEFT_CLICK_BLOCK) || (event.getAction() == Action.LEFT_CLICK_AIR))
					{
						loc = player.getTargetBlock(null, 3000).getLocation();
						while (player.getWorld().getBlockTypeIdAt(loc) == 0)
						{
							loc.setY(loc.getY() - 1);
						}
					}
					else
					{
						return;
					}
					int totalMag = 20;
					int semiMag = 15;
					int consistentMag = 10;
					for (int mag = 0; mag < totalMag; mag++)
					{
						int circum = (int) (Math.ceil(2 * mag * Math.PI));
						int actual = 0;
						for (int theta = 0; theta < 360; theta += Math.ceil(360. / circum))
						{
							if (mag <= consistentMag || mag > semiMag && Math.random() > 0.9 || mag > consistentMag && mag <= semiMag && Math.random() > 0.7)
							{
								actual++;
								double x = loc.getX() + mag * (Math.cos(Math.toRadians(theta)));
								double y = loc.getY();
								double z = loc.getZ() + mag * (Math.sin(Math.toRadians(theta)));
								Location temp = new Location(player.getWorld(), x, y, z);
								while (player.getWorld().getBlockTypeIdAt(temp) == 0)
								{
									temp.setY(temp.getY() - 1);
								}
								player.getWorld().strikeLightning(temp);
							}
						}
					}
					plugin.light.put(player.getName(), true);
					plugin.getServer().getScheduler().cancelAllTasks();
					plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable()
					{
						public void run()
						{
							try
							{
								plugin.light.put(player.getName(), false);
							}
							catch (Exception e)
							{
							}
						}
					}, 50l);
				}
			}
		}
	}
}
