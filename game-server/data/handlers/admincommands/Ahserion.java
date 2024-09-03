package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.panesterra.ahserion.AhserionRaid;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Yeats, Neon
 */
public class Ahserion extends AdminCommand {

	public Ahserion() {
		super("ahserion", "Starts/stops Ahserions Flight.");

		// @formatter:off
		setSyntaxInfo(
			"<start> - Starts Ahserions Flight.",
			"<stop> - Stops Ahserions Flight."
		);
		// @formatter:on
	}

	@Override
	protected void execute(Player admin, String... params) {
		if (params.length == 0) {
			sendInfo(admin);
			return;
		}

		if (params[0].equalsIgnoreCase("start")) {
			if (AhserionRaid.getInstance().isStarted()) {
				sendInfo(admin, "Ahserion's Flight is already running.");
			} else {
				AhserionRaid.getInstance().start();
				sendInfo(admin, "Started Ahserion's Flight.");
			}
		} else if (params[0].equalsIgnoreCase("stop")) {
			if (!AhserionRaid.getInstance().isStarted()) {
				sendInfo(admin, "Ahserion's Flight is not running.");
			} else {
				AhserionRaid.getInstance().stop();
				sendInfo(admin, "Stopped Ahserion's Flight.");
			}
		}
	}
}