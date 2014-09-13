package scripts.miscrangeguild.tasks;

import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;

import scripts.miscrangeguild.MiscRangeGuild;
import scripts.miscrangeguild.util.Condition;

public class WalkToArea extends ScriptTask{

	private MiscRangeGuild instance;

	public WalkToArea(MiscRangeGuild m){
		instance = m;
	}

	public Condition inArea = new Condition(){
		@Override
		public boolean accept() {
			return MiscRangeGuild.TARGET_AREA.contains(Player.getRSPlayer().getPosition());
		}
	};

	@Override
	public boolean validate() {
		return !MiscRangeGuild.TARGET_AREA.contains(Player.getRSPlayer().getPosition());
	}

	@Override
	public void execute() {
		System.out.println("Walk back to target area");
		Walking.walkPath(MiscRangeGuild.RUNBACK_PATH);
		instance.sleep(2000);
	}

}
