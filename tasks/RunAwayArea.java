package scripts.miscrangeguild.tasks;

import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;

import scripts.miscrangeguild.MiscRangeGuild;

public class RunAwayArea extends ScriptTask{

	private MiscRangeGuild instance;

	public RunAwayArea(MiscRangeGuild m){
		instance = m;
	}

	@Override
	public boolean validate() {
		return MiscRangeGuild.runAway;
	}

	@Override
	public void execute() {
		System.out.println("Run away! "+MiscRangeGuild.RUNAWAY_TILE.distanceTo(Player.getRSPlayer().getPosition()));
		while(MiscRangeGuild.RUNAWAY_TILE.distanceTo(Player.getRSPlayer().getPosition()) > 5){
			if(Walking.walkPath(MiscRangeGuild.RUNAWAY_PATH))
				instance.sleep(2000);
		}
		MiscRangeGuild.runAway = false;
	}

}
