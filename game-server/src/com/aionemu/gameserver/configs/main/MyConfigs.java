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
	
}
