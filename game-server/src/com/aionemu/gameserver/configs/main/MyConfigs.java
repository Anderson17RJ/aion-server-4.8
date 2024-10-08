package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class MyConfigs {
	
	
	
	@Property(key = "gameserver.damage.glad", defaultValue = "1.0")
	public static float DAMAGE_GLAD;

	@Property(key = "gameserver.damage.templar", defaultValue = "1.0")
	public static float DAMAGE_TEMPLAR;

	@Property(key = "gameserver.damage.ranger", defaultValue = "1.0")
	public static float DAMAGE_RANGER;

	@Property(key = "gameserver.damage.assassin", defaultValue = "1.0")
	public static float DAMAGE_ASSASSIN;

	@Property(key = "gameserver.damage.sorcerer", defaultValue = "1.0")
	public static float DAMAGE_SORCERER;

	@Property(key = "gameserver.damage.sm", defaultValue = "1.0")
	public static float DAMAGE_SM;

	@Property(key = "gameserver.damage.cleric", defaultValue = "1.0")
	public static float DAMAGE_CLERIC;

	@Property(key = "gameserver.damage.chanter", defaultValue = "1.0")
	public static float DAMAGE_CHANTER;

	@Property(key = "gameserver.damage.bard", defaultValue = "1.0")
	public static float DAMAGE_BARD;

	@Property(key = "gameserver.damage.gunner", defaultValue = "1.0")
	public static float DAMAGE_GUNNER;

	@Property(key = "gameserver.damage.rider", defaultValue = "1.0")
	public static float DAMAGE_RIDER;

	@Property(key = "gameserver.enable.adjustment", defaultValue = "false")
	public static boolean ENABLE_ADJUSTMENT_DAMAGE;
	
	
	/*
	Custom Enchants Type
	 */
	@Property(key = "gameserver.manastone.cast_delay", defaultValue = "5000")
	public static int ENCHANT_CAST_DELAY;

	@Property(key = "gameserver.manastone.alwayssucess", defaultValue = "false")
	public static boolean ENCHANT_ALWAYSSUCESS;
	
	
	/*
	Custom Level
	 */

	@Property(key = "gameserver.max.startlevel", defaultValue = "1")
	public static int START_MAXLEVEL;

	@Property(key = "gameserver.stigma.custom", defaultValue = "false")
	public static boolean STIGMA_CUSTOM;

	@Property(key = "gameserver.max.startlevel.afterlog", defaultValue = "1")
	public static int START_MAXLEVEL_AFTERLOG;
	
	
}
