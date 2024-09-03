package com.aionemu.gameserver.model.account;

import java.sql.Timestamp;
import java.util.*;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.items.storage.Storage;

/**
 * This class represents game account and is holding such informations as:
 * <ul>
 * <li>account id</li>
 * <li>account name</li>
 * <li>{@link AccountTime account time info}</li>
 * <li>a list of {@link PlayerAccountData} objects each of which keeping information about player that must be available on character selection
 * screen.</li>
 * </ul>
 * 
 * @author SoulKeeper, cura
 */
public class Account implements Iterable<PlayerAccountData> {

	/** Unique id of this account (it's generated by login server) */
	private final int id;
	/** Unique name of this account */
	private String name;
	/**
	 * Time of account creation, measured in milliseconds since 1.1.1970 0:00 UTC
	 */
	private long creationDate;
	/**
	 * Access level
	 */
	private byte accessLevel;
	/**
	 * Membership of this account
	 */
	private byte membership;

	private AccountTime accountTime;

	private Map<Integer, PlayerAccountData> players = new HashMap<>();

	private Storage accountWarehouse;

	private int numberOfAsmos = 0;

	private int numberOfElyos = 0;

	private CharacterPasskey characterPasskey;

	private String securityToken = "";

	private String allowedHddSerial;

	private int stamps = 0;
	private PassportsList playerPassports;
	private Timestamp lastStamp;

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String token) {
		this.securityToken = token;
	}

	public Account(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The account creation date, measured in milliseconds since 1.1.1970 0:00 UTC
	 */
	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public AccountTime getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(AccountTime accountTime) {
		this.accountTime = accountTime;
	}

	public byte getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(byte accessLevel) {
		this.accessLevel = accessLevel;
	}

	public byte getMembership() {
		return membership;
	}

	public void setMembership(byte membership) {
		this.membership = membership;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof Account)) {
			return false;
		}

		Account account = (Account) o;

		return id == account.id;
	}

	@Override
	public int hashCode() {
		return id;
	}

	public PlayerAccountData getPlayerAccountData(int chaOid) {
		return players.get(chaOid);
	}

	public void addPlayerAccountData(PlayerAccountData accPlData) {
		PlayerAccountData oldData = players.put(accPlData.getPlayerCommonData().getPlayerObjId(), accPlData);
		if (oldData != null) {
			decrementCountOf(oldData.getPlayerCommonData().getRace());
		}
		switch (accPlData.getPlayerCommonData().getRace()) {
			case ASMODIANS:
				numberOfAsmos++;
				break;
			case ELYOS:
				numberOfElyos++;
				break;
		}
	}

	public Storage getAccountWarehouse() {
		return accountWarehouse;
	}

	public void setAccountWarehouse(Storage accountWarehouse) {
		this.accountWarehouse = accountWarehouse;
	}

	public CharacterPasskey getCharacterPasskey() {
		if (characterPasskey == null)
			characterPasskey = new CharacterPasskey();
		return characterPasskey;
	}

	/** Returns the number of players that are on this account */
	public int size() {
		return players.size();
	}

	public List<PlayerAccountData> getPlayerAccDataList() {
		return new ArrayList<>(players.values());
	}

	@Override
	public Iterator<PlayerAccountData> iterator() {
		return players.values().iterator();
	}

	public int getNumberOf(Race race) {
		switch (race) {
			case ASMODIANS:
				return numberOfAsmos;
			case ELYOS:
				return numberOfElyos;
		}
		return 0;
	}

	public void decrementCountOf(Race race) {
		switch (race) {
			case ASMODIANS:
				numberOfAsmos--;
				break;
			case ELYOS:
				numberOfElyos--;
				break;
		}
	}

	private long tollCount;

	public void setToll(long toll) {
		tollCount = toll;
	}

	public long getToll() {
		return tollCount;
	}

	public String getAllowedHddSerial() {
		return allowedHddSerial;
	}

	public void setAllowedHddSerial(String allowedHddSerial) {
		this.allowedHddSerial = allowedHddSerial;
	}

	public boolean isEmpty() {
		return numberOfAsmos == 0 && numberOfElyos == 0;
	}

	public int getMaxPlayerLevel() {
		int maxLevel = 1;
		for (PlayerAccountData pad : players.values()) {
			if (pad.getPlayerCommonData().getLevel() > maxLevel) {
				maxLevel = pad.getPlayerCommonData().getLevel();
			}
		}
		return maxLevel;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + "]";
	}

	public int getPassportStamps() {
		return stamps;
	}

	public void setPassportStamps(int value) {
		this.stamps = value;
	}

	public void increasePassportStamps() {
		this.stamps++;
	}

	public PassportsList getPassportsList() {
		return playerPassports;
	}

	public void setPassportsList(PassportsList pp) {
		playerPassports = pp;
	}

	public Timestamp getLastStamp() {
		return lastStamp;
	}

	public void setLastStamp(Timestamp timestamp) {
		this.lastStamp = timestamp;
	}
}