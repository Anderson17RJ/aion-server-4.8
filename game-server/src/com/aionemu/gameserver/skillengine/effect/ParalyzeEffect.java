package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParalyzeEffect")
public class ParalyzeEffect extends EffectTemplate {

	@Override
	public void applyEffect(Effect effect) {
		effect.addToEffectedController();
	}

	@Override
	public void calculate(Effect effect) {
		super.calculate(effect, StatEnum.PARALYZE_RESISTANCE, null);
	}

	@Override
	public void startEffect(Effect effect) {
		final Creature effected = effect.getEffected();
		effected.getController().cancelCurrentSkill(effect.getEffector());
		if (effected instanceof Player player) {
			player.getFlyController().onStopGliding();
			player.getMoveController().abortMove();
			if (effect.getEffector().getMaster() instanceof Player) {
				long duration = getDuration2() + ((long) getDuration1()) * effect.getSkillLevel();
				if (getRandomTime() > 0 )
					duration -= getRandomTime()/2;
				player.incrementParalyzeCountAndUpdateExpirationTime(duration);
			}
		}
		effect.setAbnormal(AbnormalState.PARALYZE);
		effect.getEffected().getEffectController().setAbnormal(AbnormalState.PARALYZE);

	}

	@Override
	public void endEffect(Effect effect) {
		effect.getEffected().getEffectController().unsetAbnormal(AbnormalState.PARALYZE);
	}
}
