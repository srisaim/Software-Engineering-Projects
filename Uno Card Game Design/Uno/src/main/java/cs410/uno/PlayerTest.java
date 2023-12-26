package cs410.uno;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class PlayerTest {

    // Simple test to check if the Player is created properly, as it should return the right name.
    @Test
    public void testPlayerNameRetrieval() {
        Player player = new Player("Tatum");

        assertEquals("Tatum", player.getName());
    }

    // Simple test to check if the cards are being correctly added into the player's hand.
    @Test
    public void testAddingCardsToHand() {
        Player player = new Player("Lebron");
        Card c1 = new Card(Card.Color.RED, 5);
        Card c2 = new Card(Card.Color.BLUE, Card.Type.SKIP);
        player.addCard(c1);
        player.addCard(c2);
        List<Card> hand = player.getHand();

        assertTrue(hand.contains(c1) && hand.contains(c2));
    }

    // Simple test to check the color selected by the Player for their wild card use is allowed.
    @Test
    public void testChoosingColorForWildCard() {
        Player player = new Player("Kobe");
        Card.Color selectedColor = player.chooseColorForWildCard();

        assertTrue(selectedColor == Card.Color.RED || selectedColor == Card.Color.BLUE ||
                selectedColor == Card.Color.GREEN || selectedColor == Card.Color.YELLOW);
    }

    // Simple test to check that a card played by the Player is a legal move.
    @Test
    public void testPlayingACard() {
        Player player = new Player("Jordan");
        Card c1 = new Card(Card.Color.RED, 5);
        Card c2 = new Card(Card.Color.BLUE, Card.Type.SKIP);
        player.addCard(c1);
        player.addCard(c2);
        Card topCard = new Card(Card.Color.RED, 3);
        Card playedCard = player.playCard(topCard, Card.Color.RED);

        assertNotNull(playedCard);
        assertEquals(1, player.getHand().size());
        assertFalse(player.getHand().contains(playedCard));
    }
}
