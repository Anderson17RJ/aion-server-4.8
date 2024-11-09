package playercommands;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MacroSet extends PlayerCommand {

	private static final Map<String, Map<String, List<ItemSlot>>> savedSets = new HashMap<>();
	
	public MacroSet() {
		super("macroset", "macro para salvar sets");
		
	}
	
	@Override
	public void execute(Player player, String... params) {
		if (params.length < 2) {
			sendInfo(player);
			return;
		}
		
		String action = params[0];
		String setName = params[1];
		
		switch (action.toLowerCase()) {
			case "save":
				saveSet(player, setName);
				break;
			case "load":
				loadSet(player, setName);
				break;
			case "delete":
				deleteSet(player, setName);
				break;
			case "check":
				checkSet(player, setName);
				break;
			default:
				sendInfo(player);
				break;
		}
	}

	private void saveSet(Player player, String setName) {
		List<ItemSlot> equippedItems = player.getEquipment().getEquippedItems().stream()
			.map(item -> new ItemSlot(item.getItemId(), item.getEquipmentSlot()))
			.collect(Collectors.toList());
		Map<String, List<ItemSlot>> playerSets = savedSets.computeIfAbsent(player.getName(), k -> new HashMap<>());
		playerSets.put(setName, equippedItems);
		PacketSendUtility.sendMessage(player, "Set " + setName + " saved successfully.");
	}

	private void loadSet(Player player, String setName) {
		Map<String, List<ItemSlot>> playerSets = savedSets.get(player.getName());
		if (playerSets == null || !playerSets.containsKey(setName)) {
			PacketSendUtility.sendMessage(player, "Set " + setName + " not found.");
			return;
		}

		List<ItemSlot> setItems = playerSets.get(setName);
		for (ItemSlot itemSlot : setItems) {
			Item item = player.getInventory().getFirstItemByItemId(itemSlot.getItemId());
			if (item != null) {
				long slotIdMask = itemSlot.getSlotId();
				if (slotIdMask != 0) {
					player.getEquipment().equipItem(item.getObjectId(), slotIdMask);
					PacketSendUtility.sendMessage(player, "Equipped item with ID " + itemSlot.getItemId());
				} else {
					PacketSendUtility.sendMessage(player, "Invalid slot for item with ID " + itemSlot.getItemId());
				}
			} else {
				PacketSendUtility.sendMessage(player, "Item with ID " + itemSlot.getItemId() + " not found in inventory.");
			}
		}
		// Atualiza a aparência do jogador
		PacketSendUtility.broadcastPacket(player, new SM_UPDATE_PLAYER_APPEARANCE2(player.getObjectId(), player.getEquipment().getEquippedItemsWithoutStigma()), true);
		PacketSendUtility.sendMessage(player, "Set " + setName + " loaded successfully.");
	}

	private void deleteSet(Player player, String setName) {
		Map<String, List<ItemSlot>> playerSets = savedSets.get(player.getName());
		if (playerSets == null || !playerSets.containsKey(setName)) {
			PacketSendUtility.sendMessage(player, "Set " + setName + " not found.");
			return;
		}

		playerSets.remove(setName);
		PacketSendUtility.sendMessage(player, "Set " + setName + " deleted successfully.");
	}

	private void checkSet(Player player, String setName) {
		Map<String, List<ItemSlot>> playerSets = savedSets.get(player.getName());
		if (playerSets == null || !playerSets.containsKey(setName)) {
			PacketSendUtility.sendMessage(player, "Set " + setName + " not found.");
			return;
		}

		List<ItemSlot> setItems = playerSets.get(setName);
		StringBuilder itemList = new StringBuilder("Items in set " + setName + ":\n");
		for (ItemSlot itemSlot : setItems) {
			itemList.append("[item:").append(itemSlot.getItemId()).append("] in slot ").append(itemSlot.getSlotId()).append("\n");
		}
		PacketSendUtility.sendMessage(player, itemList.toString());
	}
	
	private void sendInfo(Player player) {
		PacketSendUtility.sendMessage(player, "Syntax: .set save <name> | .set load <name> | .set delete <name>");
	}
	
	private static class ItemSlot {
		private final int itemId;
		private final long slotId;
		
		public ItemSlot(int itemId, long slotId) {
			this.itemId = itemId;
			this.slotId = slotId;
		}
		
		public int getItemId() {
			return itemId;
		}
		
		public long getSlotId() {
			return slotId;
		}
	}
}
