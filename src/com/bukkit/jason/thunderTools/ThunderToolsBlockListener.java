package com.bukkit.jason.thunderTools;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

/**
 * Handle events for all Player related events
 * 
 * @author <Your Name>
 */
public class ThunderToolsBlockListener extends BlockListener
{
	@SuppressWarnings("unused")
	private final ThunderTools plugin;

	public ThunderToolsBlockListener(final ThunderTools instance)
	{
		plugin = instance;
	}

	public void onBlockRedstoneChange(BlockRedstoneEvent event)
	{
		Block b = event.getBlock().getRelative(BlockFace.DOWN);
		if (event.getOldCurrent() == 0 && event.getNewCurrent() > 0 && ThunderToolsSettings.redstoneEnabled)
		{
			if (b.getRelative(BlockFace.WEST).getTypeId() == ThunderToolsSettings.redstoneItem)
			{
				thunder(b.getRelative(BlockFace.WEST));
			}
			if (b.getRelative(BlockFace.NORTH).getTypeId() == ThunderToolsSettings.redstoneItem)
			{
				thunder(b.getRelative(BlockFace.NORTH));
			}
			if (b.getRelative(BlockFace.SOUTH).getTypeId() == ThunderToolsSettings.redstoneItem)
			{
				thunder(b.getRelative(BlockFace.SOUTH));
			}
			if (b.getRelative(BlockFace.EAST).getTypeId() == ThunderToolsSettings.redstoneItem)
			{
				thunder(b.getRelative(BlockFace.EAST));
			}
		}
	}


	private void thunder(final Block b)
	{
		if(b.getRelative(BlockFace.DOWN).getTypeId()==0)
		{
			Location temp=b.getRelative(BlockFace.DOWN).getLocation();
			while (b.getWorld().getBlockTypeIdAt(temp) == 0)
			{
				temp.setY(temp.getY() - 1);
			}
			ThunderTools.customLightning(b.getWorld(),temp);
		}
	}
}
