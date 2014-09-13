package scripts.miscrangeguild.tasks;

import org.tribot.api.DynamicClicking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Player;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;

import scripts.miscrangeguild.MiscRangeGuild;
import scripts.miscrangeguild.util.Condition;
import scripts.miscrangeguild.util.Util;


public class NpcTalk extends ScriptTask{

	private MiscRangeGuild instance;

	Condition chatOpen = new Condition(){
		@Override
		public boolean accept() {
			return NPCChat.getSelectOptionInterface() != null;
		}
	};
	
	Condition onTile = new Condition(){
		@Override
		public boolean accept() {
			return Player.getRSPlayer().getPosition().equals(MiscRangeGuild.SHOOTING_TILE);
		}
	};


	public NpcTalk(MiscRangeGuild m){
		instance = m;
	}

	@Override
	public boolean validate() {
		return Game.getSetting(MiscRangeGuild.NUMSHOTS_SETTING) == 0;
	}

	@Override
	public void execute() {
		MiscRangeGuild.ticketCount = Inventory.getCount(MiscRangeGuild.TICKET_ID) - MiscRangeGuild.startTicketCount;
		//Camera.setCameraAngle(85);
		RSNPC[] npcs = NPCs.find(MiscRangeGuild.JUDGENPC_ID);
		if(npcs.length > 0){
			if(!npcs[0].isOnScreen()){
				System.out.println("walk back to tile");
				Walking.walkTo(MiscRangeGuild.SHOOTING_TILE);
				instance.waitFor(onTile, 3000);
			}
			//DynamicClicking.clickRSNPC(npcs[0], "Talk-to Competition Judge")
			//Util.interact(npcs[0], "Talk-to", "Competition Judge")
			if(Util.interact(npcs[0], "Talk-to", "Competition Judge")){
				for(int i = 0;Game.getSetting(MiscRangeGuild.NUMSHOTS_SETTING) == 0 && i < 10; i++){
					NPCChat.clickContinue(true);
					NPCChat.selectOption("Sure, I'll give it a go.", false);
					instance.sleep(700);
					i++;
				}
			}
			}
			
	}
}
