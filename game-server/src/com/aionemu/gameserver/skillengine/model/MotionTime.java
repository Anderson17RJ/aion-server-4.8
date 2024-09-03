package com.aionemu.gameserver.skillengine.model;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

import com.aionemu.gameserver.model.Gender;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.templates.item.enums.ItemGroup;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "motion_time")
public class MotionTime {

	@XmlElement(name = "asmodian_female")
	private List<Times> asmodianFemale;
	@XmlElement(name = "asmodian_male")
	private List<Times> asmodianMale;
	@XmlElement(name = "elyos_female")
	private List<Times> elyosFemale;
	@XmlElement(name = "elyos_male")
	private List<Times> elyosMale;
	@XmlElement(name = "robot")
	private List<Times> robot;

	@XmlAttribute(required = true)
	private String name; // TODO enum

	public String getName() {
		return name;
	}

	@XmlTransient
	private HashMap<WeaponTypeWrapper, HashMap<Integer, Times>> asmodianFemaleTimeForWeaponType = new HashMap<>();

	@XmlTransient
	private HashMap<WeaponTypeWrapper, HashMap<Integer, Times>> asmodianMaleTimeForWeaponType = new HashMap<>();

	@XmlTransient
	private HashMap<WeaponTypeWrapper, HashMap<Integer, Times>> elyosFemaleTimeForWeaponType = new HashMap<>();

	@XmlTransient
	private HashMap<WeaponTypeWrapper, HashMap<Integer, Times>> elyosMaleTimeForWeaponType = new HashMap<>();

	@XmlTransient
	HashMap<Integer, Times> robotTimes = new HashMap<>();

	/**
	 * @param name
	 *          the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Times getTimesFor(Race race, Gender gender, WeaponTypeWrapper weapons, boolean robot, int id) {
		for (int i = id; i > 0; i--) {
			if (robot) {
				if (robotTimes.get(i) != null) {
					return robotTimes.get(i);
				}
			} else {
				HashMap<Integer, Times> times = null;
				switch (race) {
					case ASMODIANS:
						if (gender == Gender.FEMALE) {
							times = asmodianFemaleTimeForWeaponType.get(weapons);

						} else {
							times = asmodianMaleTimeForWeaponType.get(weapons);
						}
						break;
					case ELYOS:
						if (gender == Gender.FEMALE) {
							times = elyosFemaleTimeForWeaponType.get(weapons);

						} else {
							times = elyosMaleTimeForWeaponType.get(weapons);
						}
						break;
				}
				if (times != null) {
					return times.get(i);
				}
			}
		}
		return null;
	}

	void afterUnmarshal(Unmarshaller u, Object parent) {
		parseTimesFrom(asmodianFemale, asmodianFemaleTimeForWeaponType);
		parseTimesFrom(asmodianMale, asmodianMaleTimeForWeaponType);
		parseTimesFrom(elyosFemale, elyosFemaleTimeForWeaponType);
		parseTimesFrom(elyosMale, elyosMaleTimeForWeaponType);
		if (robot != null) {
			for (Times time : robot) {
				robotTimes.put(time.getId(), time);
			}
		}

		asmodianFemale = null;
		asmodianMale = null;
		elyosFemale = null;
		elyosMale = null;
		robot = null;
	}

	private void parseTimesFrom(List<Times> times, HashMap<WeaponTypeWrapper, HashMap<Integer, Times>> map) {
		if (times == null) {
			return;
		}
		for (Times t : times) {
			WeaponTypeWrapper wrapper;
			switch (t.getWeapon()) {
				case "1hand":
					wrapper = new WeaponTypeWrapper(ItemGroup.SWORD, null);
					break;
				case "2hand":
					wrapper = new WeaponTypeWrapper(ItemGroup.GREATSWORD, null);
					break;
				case "keyblade":
					wrapper = new WeaponTypeWrapper(ItemGroup.KEYBLADE, null);
					break;
				case "polearm":
					wrapper = new WeaponTypeWrapper(ItemGroup.POLEARM, null);
					break;
				case "dagger":
					wrapper = new WeaponTypeWrapper(ItemGroup.DAGGER, null);
					break;
				case "mace":
					wrapper = new WeaponTypeWrapper(ItemGroup.MACE, null);
					break;
				case "staff":
					wrapper = new WeaponTypeWrapper(ItemGroup.STAFF, null);
					break;
				case "2weapon":
					wrapper = new WeaponTypeWrapper(ItemGroup.DAGGER, ItemGroup.DAGGER);
					map.computeIfAbsent(new WeaponTypeWrapper(ItemGroup.DAGGER, ItemGroup.SWORD), k -> new HashMap<>()).put(t.getId(), t);
					map.computeIfAbsent(new WeaponTypeWrapper(ItemGroup.SWORD, ItemGroup.DAGGER), k -> new HashMap<>()).put(t.getId(), t);
					map.computeIfAbsent(new WeaponTypeWrapper(ItemGroup.SWORD, ItemGroup.SWORD), k -> new HashMap<>()).put(t.getId(), t);
					break;
				case "noweapon":
					wrapper = new WeaponTypeWrapper(null, null);
					break;
				case "book":
					wrapper = new WeaponTypeWrapper(ItemGroup.SPELLBOOK, null);
					break;
				case "orb":
					wrapper = new WeaponTypeWrapper(ItemGroup.ORB, null);
					break;
				case "1gun":
					wrapper = new WeaponTypeWrapper(ItemGroup.GUN, null);
					break;
				case "2gun":
					wrapper = new WeaponTypeWrapper(ItemGroup.GUN, ItemGroup.GUN);
					break;
				case "cannon":
					wrapper = new WeaponTypeWrapper(ItemGroup.CANNON, null);
					break;
				case "bow":
					wrapper = new WeaponTypeWrapper(ItemGroup.BOW, null);
					break;
				case "harp":
					wrapper = new WeaponTypeWrapper(ItemGroup.HARP, null);
					break;
				default:
					throw new IllegalArgumentException(t.getWeapon() + " is not implemented");
			}
			map.computeIfAbsent(wrapper, k -> new HashMap<>()).put(t.getId(), t);
		}
	}
}
