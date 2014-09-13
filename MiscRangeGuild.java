package scripts.miscrangeguild;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.RandomEvents;

import scripts.miscrangeguild.tasks.NpcTalk;
import scripts.miscrangeguild.tasks.RunAwayArea;
import scripts.miscrangeguild.tasks.ScriptTask;
import scripts.miscrangeguild.tasks.ShootTarget;
import scripts.miscrangeguild.tasks.WalkToArea;
import scripts.miscrangeguild.util.Condition;
import scripts.miscrangeguild.util.RSArea;
import scripts.miscrangeguild.util.Util;

@ScriptManifest(authors = { "Misc" }, 
category = "Range", 
name = "MiscRangeGuild", 
description="",
version=MiscRangeGuild.VERSION)
public class MiscRangeGuild extends Script implements Painting, RandomEvents{

	/*
	 */
	
	public static final double VERSION = 1.02;
	
	public static final int TICKET_ID = 1464;
	public static final int NUMSHOTS_SETTING = 156;
	public static final int JUDGENPC_ID = 1428;
	public static final int TARGET_ID = 23701;
	public static final int BRONZE_ARROW_ID = 882;

	public static final RSTile SHOOTING_TILE = new RSTile(2670, 3418, 0);
	public static final RSTile RUNAWAY_TILE = new RSTile(2661,3437,0);
	
	public static final RSTile[] RUNAWAY_PATH = {SHOOTING_TILE, new RSTile(2666,3427,0), RUNAWAY_TILE};
	public static final RSTile[] RUNBACK_PATH = {RUNAWAY_TILE, new RSTile(2666,3427,0), SHOOTING_TILE};
	
	public static final RSArea TARGET_AREA = new RSArea(new RSTile(2668,3421,0), 
			new RSTile(2676,3416,0));

	public static boolean scriptRunning, runAway;

	public static int startTicketCount, ticketCount, ticketCountHr, startExp, expGained, expHr,
	startLevel, levelsGained;

	public static long milli,sec,min,hr;



	private final ScriptTask[] TASK_LIST = {
			new RunAwayArea(this),
			new WalkToArea(this),
			new NpcTalk(this),
			new ShootTarget(this)
	};

	@Override
	public void run() {
		System.out.println("Start");
		scriptRunning = true;

		startTicketCount = Inventory.getCount(TICKET_ID);
		startExp = Skills.getXP("Range");
		startLevel = Skills.getActualLevel("Range");

		while(scriptRunning){
			System.out.println(Player.getRSPlayer().isInCombat());
			if(Util.isTargetWindowOpen(this))
				Util.closeTargetWindow(this);
			if(Player.getRSPlayer().isInCombat()){
				System.out.println("combat");
				runAway = true;
			}
			for(ScriptTask task : TASK_LIST){
				if(task.validate())
					task.execute();
			}
			sleep(100);
		}
		System.out.println("End");
	}

	public void waitFor(Condition condition, long timeout){
		for(int i = 0; i < timeout; i+= 100){
			if(condition.accept())
				return;
			sleep(100);
		}
	}

	@Override
	public void onPaint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;

		milli = getRunningTime();
		sec = (milli / 1000);
		min = sec / 60;
		hr = min / 60;
		expGained = Skills.getXP("Range") - startExp;
		if(sec != 0){
			ticketCountHr = (int) (3600 / sec * ticketCount);
			expHr = (int) (3600 / sec * expGained);
		}


		g.setColor(Color.gray);
		g.fillRoundRect(340, 350, 190, 100,10,10);
		g.setColor(Color.RED);
		g.drawString("Runtime:"+hr+":"+min+":"+sec%60, 345, 385);
		g.drawString("Ticket Count: "+ticketCount+" ("+ticketCountHr+"/hr)", 345, 400);
		g.drawString("Exp:" + expGained + "("+expHr+"/hr)", 345, 415);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		g.drawString("MiscRangeGuild v"+VERSION,360,365);
	}

	@Override
	public void onRandom(RANDOM_SOLVERS arg0) {
		while(Util.isTargetWindowOpen(this)){
			Util.closeTargetWindow(this);
		}
		setRandomSolverState(true);
		setRandomSolverState(arg0,true);
	}

	@Override
	public boolean randomFailed(RANDOM_SOLVERS arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void randomSolved(RANDOM_SOLVERS arg0) {
		// TODO Auto-generated method stub

	}
}
