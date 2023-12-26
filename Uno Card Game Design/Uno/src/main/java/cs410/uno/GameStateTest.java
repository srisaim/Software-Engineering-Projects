package cs410.uno;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    // Simple test to check if startGame is initializing the game properly with the specified amount of players.
    // Also checks if the initial top card of the discard pile is set up correctly.
    @Test
    public void testGameInitialization() {
        GameState GS = GameState.startGame(4, 7, 2, 2, 4);

        assertEquals(4, GS.getPlayers().size());
        for (Player player : GS.getPlayers()) {
            assertEquals(7, player.getHand().size());
        }

        assertNotNull(GS.getTopCardOfDiscardPile());
    }

    // Simple test to check if the currentIndex updates correctly based on which direction the game is being played.
    @Test
    public void testPlayerTurns() {
        GameState GS = GameState.startGame(4, 7, 2, 2, 4);
        Player p1 = GS.getCurrentPlayer();
        GS.runOneTurn();
        Player p2 = GS.getCurrentPlayer();

        assertNotEquals(p1.getName(), p2.getName());
    }

    // Simple test to check if a player runs out of cards, isGameOver returns true, meaning the game ended.
    @Test
    public void testGameOverCondition() {
        GameState GS = GameState.startGame(2, 1, 2, 2, 4); // Setup with minimal cards for quick end
        GS.runOneTurn();

        assertTrue(GS.isGameOver());
    }

    // Simple test to check if a player is drawing a card when they have no available card in hand.
    @Test
    public void testDrawCardFunctionality() {
        GameState GS = GameState.startGame(4, 7, 2, 2, 4);
        Player currentPlayer = GS.getCurrentPlayer();
        int beforeSize = currentPlayer.getHand().size();
        GS.runOneTurn();
        int afterSize = currentPlayer.getHand().size();

        assertTrue(beforeSize > afterSize);
    }

    // Simple test to check if cards from the draw pile are over, the draw pile is refreshed from the discard pile.
    @Test
    public void testRefreshDrawPile() {
        GameState GS = GameState.startGame(2, 1, 2, 2, 0);
        while (!GS.isDrawPileEmpty()) {
            GS.runOneTurn();
        }

        assertTrue(GS.isDrawPileEmpty());
        GS.runOneTurn();

        assertFalse(GS.isDrawPileEmpty());
    }

    // Since the GameState's logic doesn't have control of the card order in the discard pile, this test will run
    // after a special card is played, rather than creating a scenario for each card.
    // So this test runs turns until a special card is played or 50 turns have passed, to prevent an infinite loop.
    // If a special card was played, it assumes that the applyCardEffects method has been called
    // and the special card's effects have been applied.
    @Test
    public void testSpecialCardEffects() {
        GameState GS = GameState.startGame(4, 7, 2, 2, 4);
        int tCount = 0;
        while (tCount < 50 && !SCWasPlayed(GS)) {
            GS.runOneTurn();
            tCount++;
        }

        assertTrue(SCWasPlayed(GS), "Special cards should've been played.");
    }

    // A helper method to check if a special card was played in the game.
    private boolean SCWasPlayed(GameState gameState) {
        Card topCard = gameState.getTopCardOfDiscardPile();
        return topCard.getType() != Card.Type.NUMBER;
    }
}
