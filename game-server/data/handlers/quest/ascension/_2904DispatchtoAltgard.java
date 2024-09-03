package quest.ascension;

import static com.aionemu.gameserver.model.DialogAction.*;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.AbstractQuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Mr. Poke
 */
public class _2904DispatchtoAltgard extends AbstractQuestHandler {

	public _2904DispatchtoAltgard() {
		super(2904);
	}

	@Override
	public void register() {
		qe.registerOnQuestCompleted(questId);
		qe.registerQuestNpc(204191).addOnTalkEvent(questId);
		qe.registerQuestNpc(203559).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null)
			return false;

		int var = qs.getQuestVarById(0);
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 204191:
					switch (env.getDialogActionId()) {
						case QUEST_SELECT:
							if (var == 0)
								return sendQuestDialog(env, 1352);
							break;
						case SETPRO1:
							if (var == 0) {
								qs.setQuestVarById(0, var + 1);
								updateQuestStatus(env);
								return closeDialogWindow(env);
							}
					}
					return false;
				case 203559:
					switch (env.getDialogActionId()) {
						case QUEST_SELECT:
							if (var == 1) {
								qs.setStatus(QuestStatus.REWARD);
								updateQuestStatus(env);
								return sendQuestDialog(env, 2375);
							}
					}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203559) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}

	@Override
	public void onQuestCompletedEvent(QuestEnv env) {
		defaultOnQuestCompletedEvent(env);
	}
}
