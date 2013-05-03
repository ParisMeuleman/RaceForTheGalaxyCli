package feaisil.raceforthegalaxy.gui.cli;

import java.io.FileInputStream;
import java.util.ArrayList;

import feaisil.raceforthegalaxy.Expansion;
import feaisil.raceforthegalaxy.game.Game;
import feaisil.raceforthegalaxy.game.Player;

public class CliGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		

		ArrayList<Expansion> expList = new ArrayList<Expansion>();
		expList.add(Expansion.BaseGame);
		Game.Configuration aConfig = new Game.Configuration(
				expList,
				false,
				false,
				false, 
				new FileInputStream("res/raw/rftg_card_reference"));
		Game aGame = new Game(aConfig);

		CommandLineInterface cli = new CommandLineInterface(aGame);
		@SuppressWarnings("unused")
		Player aPl1 = new Player(aGame, "God", cli);
		@SuppressWarnings("unused")
		Player aPl2 = new Player(aGame, "Blatteman", cli);
		
		aGame.playGame();
		
		System.out.println(aGame);
	}

}
