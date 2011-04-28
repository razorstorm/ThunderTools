package com.bukkit.jason.thunderTools;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class ThunderToolsEntityListener extends EntityListener
{
	private final ThunderTools plugin;

	public ThunderToolsEntityListener(final ThunderTools instance)
	{
		plugin = instance;
	}

	public void onEntityDamage(EntityDamageEvent event)
	{
		if (event.getCause() == DamageCause.LIGHTNING)
		{
			if (event.getEntity() instanceof Player)
			{
				Player p = (Player) event.getEntity();
				if (plugin.light.containsKey(p.getName()))
				{
					if (plugin.light.get(p.getName()))
					{
						event.setCancelled(true);
					}
				}
			}
			else
			{
				event.setDamage(100);
			}
		}
	}
}
