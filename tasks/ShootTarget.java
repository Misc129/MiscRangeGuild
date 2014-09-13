package scripts.miscrangeguild.tasks;

import java.awt.Point;

import org.tribot.api.DynamicClicking;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSInterface;

import scripts.miscrangeguild.MiscRangeGuild;
import scripts.miscrangeguild.util.Util;

public class ShootTarget extends ScriptTask{

	public static final Point EXIT_BUTTON_POS = new Point(486,40);
	
	private MiscRangeGuild instance;

	public ShootTarget(MiscRangeGuild m){
		instance = m;
	}

	

	@Override
	public boolean validate() {
		return Game.getSetting(MiscRangeGuild.NUMSHOTS_SETTING) > 0;
	}

	@Override
	public void execute() {
		System.out.println("Shoot target");
		Camera.setCameraAngle(33);
		Camera.setCameraRotation(310);
		RSObject[] objects = Objects.find(20, "Target");
		if(objects.length > 0){
			if(DynamicClicking.clickRSObject(objects[0], "Fire-at")){
				Mouse.move(Util.randomizePoint(EXIT_BUTTON_POS, 20));
				instance.waitFor(Util.targetWinOpen, 2000);
				instance.sleep(300,400);
				if(emptyMessage()){
					System.out.println("out of arrows");
					//equip bronze arrows
					Inventory.open();
					RSItem[] items = Inventory.find(MiscRangeGuild.BRONZE_ARROW_ID);
					if(items != null && items.length > 0){
						items[0].click();
						instance.sleep(1500);
					}
				}
			}
		}
	}

	private boolean emptyMessage(){
		RSInterface chat = Interfaces.get(241, 2);
		if(chat != null){
			return chat.getText().contains("use the 10 bronze arrows");
		}
		return false;
	}

}
