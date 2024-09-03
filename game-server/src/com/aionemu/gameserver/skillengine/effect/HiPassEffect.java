package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.model.Effect;

public class HiPassEffect extends BufEffect {

	@Override
	public void calculate(Effect effect) {
		effect.addSuccessEffect(this);
	}
}