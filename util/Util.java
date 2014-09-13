package scripts.miscrangeguild.util;

import java.awt.Point;


import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;

import scripts.miscrangeguild.MiscRangeGuild;

public class Util {

	public static Condition targetWinOpen = new Condition(){
		@Override
		public boolean accept() {
			RSInterface exit = Interfaces.get(325, 88);
			return exit != null && !exit.isHidden();
		}
	};

	public static Condition targetWinNotOpen = new Condition(){
		@Override
		public boolean accept() {
			RSInterface exit = Interfaces.get(325, 88);
			return exit == null || exit.isHidden();
		}
	};
	
	public static boolean isTargetWindowOpen(MiscRangeGuild instance){
		RSInterface exit = Interfaces.get(325, 88);
		return exit != null && !exit.isHidden();
	}
	
	public static void closeTargetWindow(MiscRangeGuild instance){
		RSInterface exit = Interfaces.get(325, 88);
		if(exit != null && !exit.isHidden() && exit.click()){
			instance.waitFor(targetWinNotOpen, 2000);
		}
	}
	
	public static boolean interact(RSNPC npc, String option, String name){
		if(npc == null) return false;
		return DynamicClicking.clickRSTile(npc.getAnimablePosition(), option + " " + name);
	}
	
	public static boolean ChatOptionsContains(String option){
		if(option == null) return false;
		for(String string : NPCChat.getOptions()){
			if(string.equals(option))
				return true;
		}
		return false;
	}
	
	public static Point randomizePoint(Point p, int offset){
		int randx = General.random(-offset, offset);
		int randy = General.random(-offset, offset);
		return new Point(p.x + randx, p.y+randy);
	}
	
}
