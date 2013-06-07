package feaisil.raceforthegalaxy.gui.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import feaisil.raceforthegalaxy.card.Card;
import feaisil.raceforthegalaxy.game.Game;
import feaisil.raceforthegalaxy.game.Player;
import feaisil.raceforthegalaxy.gui.PlayerUserInterface;
import feaisil.raceforthegalaxy.gui.QueryType;
import feaisil.raceforthegalaxy.gui.Warning;

public class CommandLineInterface implements PlayerUserInterface
{
  private Game   gameRef;
  private Player currentPlayer;

  public CommandLineInterface(Game gameRef)
  {
    super();
    this.gameRef = gameRef;
    currentPlayer = null;
  }

  public void displayCards(List<Card> iCards)
  {
    for (int i = 0; i < iCards.size(); i++)
      System.out.println(" " + (i) + " - " + iCards.get(i).toString());
  }

  public List<Card> getChoosenCards(List<Card> iCards, int iMin, int iMax)
  {
    System.out.println("Choose " + iMin + " up to " + iMax + " cards");
    System.out
        .println("Enter the number of the cards you want to choose (format: N N N)");

    List<Card> aResult = null;

    InputStreamReader inputStreamReader = new InputStreamReader(System.in);

    BufferedReader reader = new BufferedReader(inputStreamReader);
    String aBuffer = "";
    try
    {
      while (true)
      {
        aResult = new ArrayList<Card>();
        aBuffer = reader.readLine();
        for (String aStr : aBuffer.split("[^\\d]"))
        {
          try
          {
            if (Integer.parseInt(aStr) < iCards.size())
              aResult.add(iCards.get(Integer.parseInt(aStr)));
          } catch (java.lang.NumberFormatException iEx)
          {
            // Input not a number...
          }
        }
        if (aResult.size() >= iMin && aResult.size() <= iMax)
          break;
        System.out.println("Invalid input, please try again.");
      }
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return aResult;
  }

  public void switchToPlayer(Player iP)
  {
    if (iP.equals(currentPlayer))
      return;
    currentPlayer = iP;

    for (int i = 0; i < 10; i++)
      System.out.println();
    for (int i = 0; i < 20; i++)
      System.out.print("=");
    System.out.println();
    System.out.println("TURN " + gameRef.getTurn());
    System.out.println("* Game stats:");
    System.out.println("Deck size: " + gameRef.getDeck());
    System.out.println("Discard size: " + gameRef.getDiscardPile());
    System.out.println("Remaining Vps: " + gameRef.getRemainingVp());
    System.out.println("Remaining goals: " + gameRef.getGoals());
    System.out.println();
    for (Player p : gameRef.getPlayers())
    {
      if (!p.equals(currentPlayer))
      {
        System.out.println(p.getColor().toString() + " player:");
        System.out.println("Vps: " + p.getVictoryPoints() + ", hand size "
            + p.getHand().size());
        System.out.println("board: ");
        displayCards(currentPlayer.getBoard());
      }
    }
    System.out.println();
    for (int i = 0; i < ("* Player " + currentPlayer.getClientId() + " *")
        .length(); i++)
      System.out.print("*");
    System.out.println();
    System.out.println("* Player " + currentPlayer.getClientId() + " *");
    for (int i = 0; i < ("* Player " + currentPlayer.getClientId() + " *")
        .length(); i++)
      System.out.print("*");
    System.out.println();
    System.out.println("Player board: ");
    displayCards(currentPlayer.getBoard());
    System.out.println();
    System.out.println("Player hand: ");
    displayCards(currentPlayer.getHand());
    System.out.println();
  }

  @Override
  public List<Card> query(Player iP, QueryType query, List<Card> cards,
      int minCards, int maxCards)
  {

    switchToPlayer(iP);

    switch (query)
    {
      case startingPhaseChooseWorld:
        System.out.println("Choose your starting world");
        break;
      case startingPhaseDiscardHand:
        System.out.println("Discard two cards from your starting hand");
        break;
      case chooseAction:
        System.out.println("Choose an action to play");
        break;
      case exploreDiscard:
        System.out.println("Explore phase, discard cards");
        break;
      case developChoose:
        System.out.println("You may choose a card to develop");
        break;
      case developDiscard:
        System.out.println("Choose paiement for your development");
        break;
      case settleChoose:
        System.out.println("You may choose a card to settle");
        break;
      case settleDiscard:
        System.out.println("Explore phase, discard cards");
        break;
      case finalizeDiscardHand:
        System.out.println("Too much cards in hand, discard cards");
        break;
      default:
    }
    displayCards(cards);

    return getChoosenCards(cards, minCards, maxCards);
  }

  @Override
  public void sendWarning(Player iP, Warning warning)
  {
    switchToPlayer(iP);

    System.out.print("/!\\ ");
    switch (warning)
    {
      case PrestigeActionAlreadyUsed:
        System.out.print("Prestige action already used!");
        break;
      case TwoActionSelectedButNotPrestige:
        System.out.print("Two actions selected but none prestige!");
        break;
      default:
        System.out.print("Unknown warning");
    }
    System.out.println(" /!\\");

  }
}
