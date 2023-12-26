package cs410.uno;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    // Simple test to check if Number Cards are being created properly.
    @Test
    public void testNumberCardCreation() {
        Card card = new Card(Card.Color.RED, 5);

        assertEquals(Card.Color.RED, card.getColor());
        assertEquals(Card.Type.NUMBER, card.getType());
        assertEquals(5, card.getNumber());
        assertEquals("RED 5", card.toString());
    }

    // Simple test to check if Skip Cards are being created properly.
    @Test
    public void testSkipCardCreation() {
        Card card = new Card(Card.Color.BLUE, Card.Type.SKIP);

        assertEquals(Card.Color.BLUE, card.getColor());
        assertEquals(Card.Type.SKIP, card.getType());
        assertEquals(-1, card.getNumber());
        assertEquals("BLUE SKIP", card.toString());
    }

    // Simple test to check if Reverse Cards are being created properly.
    @Test
    public void testReverseCardCreation() {
        Card card = new Card(Card.Color.GREEN, Card.Type.REVERSE);

        assertEquals(Card.Color.GREEN, card.getColor());
        assertEquals(Card.Type.REVERSE, card.getType());
        assertEquals(-1, card.getNumber());
        assertEquals("GREEN REVERSE", card.toString());
    }

    // Simple test to check if Draw Two Cards are being created properly.
    @Test
    public void testDrawTwoCardCreation() {
        Card card = new Card(Card.Color.YELLOW, Card.Type.DRAW_TWO);

        assertEquals(Card.Color.YELLOW, card.getColor());
        assertEquals(Card.Type.DRAW_TWO, card.getType());
        assertEquals(-1, card.getNumber());
        assertEquals("YELLOW DRAW_TWO", card.toString());
    }

    // Simple test to check if Wild Cards are being created properly.
    @Test
    public void testWildCardCreation() {
        Card card = new Card(Card.Color.WILD, Card.Type.WILD);

        assertEquals(Card.Color.WILD, card.getColor());
        assertEquals(Card.Type.WILD, card.getType());
        assertEquals(-1, card.getNumber());
        assertEquals("WILD WILD", card.toString());
    }
}
