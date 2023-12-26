import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testCardToString() {
        Card card = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        assertEquals("ACE of HEARTS", card.toString(), "Card toString should match rank and suit");

        Card joker = Card.createJoker();
        assertEquals("Joker", joker.toString(), "Joker should be represented as 'Joker'");
    }

    @Test
    void testCreateJoker() {
        Card joker = Card.createJoker();
        assertNotNull(joker, "Joker card should not be null");
        assertEquals(Card.Suit.BLANK, joker.suit(), "Joker suit should be BLANK");
        assertEquals(Card.Rank.JOKER, joker.rank(), "Joker rank should be JOKER");
    }

    @Test
    void toJson() {
        Card card = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        assertEquals(card.toJson(), "{\"suit\":\"HEARTS\",\"rank\":\"ACE\"}");
    }
}
