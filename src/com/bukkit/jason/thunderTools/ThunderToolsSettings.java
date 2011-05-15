package com.bukkit.jason.thunderTools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.util.config.Configuration;

public class ThunderToolsSettings
{

	/**
	 * Settings
	 */
	public static boolean strikeEnabled = true;
	public static int strikeItem = 283;
	public static boolean stormEnabled = true;
	public static int stormItem = 280;
	public static boolean redstoneEnabled = true;
	public static int redstoneItem = 61;
	public static boolean explosionEnabled = false;
	public static float explosionForce= 2;
	

	/**
	 * Bukkit config class
	 */
	public static Configuration config = null;

	/**
	 * Load and parse the YAML config file
	 */
	public static void load()
	{
		File dataDirectory = new File("plugins" + File.separator + "ThunderTools" + File.separator);

		dataDirectory.mkdirs();

		File file = new File("plugins" + File.separator + "ThunderTools", "config.yml");

		if (file.exists())
		{
			config = new Configuration(file);
			config.load();
			String version = "";
			version = config.getString("plugin.version", version);
			setSettings();
			if (version == "" || !(version.equals(ThunderTools.pdfFile.getVersion())))
			{
				System.out.println("Version out of date, reconfigurating");
				writeFile(file);
			}
		}
		else
		{
			writeFile(file);
			config = new Configuration(file);
			config.load();
		}
		config.load();
		setSettings();
	}

	private static void writeFile(File file)
	{
		File temp = new File("plugins" + File.separator + "ThunderTools", "temp.yml");
		BufferedWriter bw;
		try
		{
			Configuration tempConfig;
			tempConfig = new Configuration(temp);
			tempConfig.load();
			tempConfig.setProperty("plugin.version", ThunderTools.pdfFile.getVersion());
			tempConfig.setProperty("strike.enabled", strikeEnabled);
			tempConfig.setProperty("strike.item", strikeItem);
			tempConfig.setProperty("storm.enabled", stormEnabled);
			tempConfig.setProperty("storm.item", stormItem);
			tempConfig.setProperty("redstone.enabled", redstoneEnabled);
			tempConfig.setProperty("redstone.item", redstoneItem);
			tempConfig.setProperty("explosion.enabled", explosionEnabled);
			tempConfig.setProperty("explosion.force", explosionForce);
			

			tempConfig.save();

			Scanner sc = new Scanner(temp);

			bw = new BufferedWriter(new FileWriter(file));
			bw.write("#strike.enabled determines whether user controlled lightning strikes should be enabled. Defaults to " + strikeEnabled);
			bw.newLine();
			bw.write("#strike.item chooses which item to control lightning strikes. Defaults to "+strikeItem);
			bw.newLine();
			bw.write("#storm.enabled determines whether user controlled lightning storms should be enabled. Defaults to " + stormEnabled);
			bw.newLine();
			bw.write("#storm.item chooses which item to control lightning storms. Defaults to " + stormItem);
			bw.newLine();
			bw.write("#redstone.enabled determines whether redstone triggered lightning blocks are enabled. Defaults to "+redstoneEnabled);
			bw.newLine();
			bw.write("#redstone.item determines which block acts as redstone triggered lightning block. Defaults to "+redstoneItem);
			bw.newLine();
			bw.write("#explosion.enabled determines whether there is an explosion at the point of lightning Defaults to "+explosionEnabled);
			bw.newLine();
			bw.write("#explosion.force determines the explosion force Defaults to "+explosionForce);
			bw.newLine();
			bw.newLine();
			while (sc.hasNextLine())
			{
				// String l=sc.nextLine();
				// System.out.println(l);
				bw.write(sc.nextLine());
				bw.newLine();
			}
			bw.close();
			temp.delete();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sets the internal variables
	 */
	private static void setSettings()
	{
		strikeEnabled = config.getBoolean("strike.enabled", strikeEnabled);
		strikeItem=config.getInt("strike.item", strikeItem);
		stormEnabled = config.getBoolean("storm.enabled", stormEnabled);
		stormItem=config.getInt("storm.item", stormItem);
		redstoneEnabled = config.getBoolean("redstone.enabled", redstoneEnabled);
		redstoneItem=config.getInt("redstone.item", redstoneItem);
		explosionEnabled=config.getBoolean("explosion.enabled", explosionEnabled);
		explosionForce=(float) config.getDouble("explosion.force", explosionForce);
	}

}