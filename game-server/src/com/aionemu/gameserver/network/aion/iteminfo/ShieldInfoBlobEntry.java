package com.aionemu.gameserver.network.aion.iteminfo;

import java.nio.ByteBuffer;

import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;

/**
 * This blob is sent for shields. It keeps info about slots that shield can be equipped to.
 *
 * @author -Nemesiss-, Rolandas
 */
public class ShieldInfoBlobEntry extends ItemBlobEntry {

	ShieldInfoBlobEntry() {
		super(ItemBlobType.SLOTS_SHIELD);
	}

	@Override
	public void writeThisBlob(ByteBuffer buf) {
		writeQ(buf, ItemSlot.getSlotFor(ownerItem.getItemTemplate().getItemSlot()).getSlotIdMask());
		writeQ(buf, 0); // TODO! secondary slot?
		writeDyeInfo(buf, ownerItem.getItemColor()); // 4 bytes
	}

	@Override
	public int getSize() {
		return 20;
	}
}