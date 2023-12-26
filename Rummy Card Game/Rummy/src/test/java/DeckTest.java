import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckInitialization() {
        Deck deck = new Deck(1);
        assertFalse(deck.isEmpty(), "Deck should not be empty after initialization");

        Deck emptyDeck = new Deck();
        assertTrue(emptyDeck.isEmpty(), "Empty deck should be empty");
    }

    @Test
    void testShuffle() {
        Deck deck = new Deck(1);
        Card topCardBeforeShuffle = deck.peekTopCard();
        deck.shuffle();
        Card topCardAfterShuffle = deck.peekTopCard();
        assertNotEquals(topCardBeforeShuffle, topCardAfterShuffle, "Top card should likely change after shuffle");
    }

    @Test
    void testDrawCard() {
        Deck deck = new Deck(1);
        assertNotNull(deck.drawCard(), "Should be able to draw a card from a non-empty deck");
    }

    @Test
    void testAddAndDrawCard() {
        Deck deck = new Deck();
        Card card = new Card(Card.Suit.CLUBS, Card.Rank.TEN);
        deck.addCard(card);
        assertEquals(card, deck.drawCard(), "Drawn card should be the same as the card added");
    }

    @Test
    void toJson() {
        Deck deck = new Deck();
        deck.initializeDeck(1);
        assertEquals(deck.toJson(), "[{\"suit\":\"HEARTS\",\"rank\":\"ACE\"},{\"suit\":\"HEARTS\",\"rank\":\"TWO\"},{\"suit\":\"HEARTS\",\"rank\":\"THREE\"},{\"suit\":\"HEARTS\",\"rank\":\"FOUR\"},{\"suit\":\"HEARTS\",\"rank\":\"FIVE\"},{\"suit\":\"HEARTS\",\"rank\":\"SIX\"},{\"suit\":\"HEARTS\",\"rank\":\"SEVEN\"},{\"suit\":\"HEARTS\",\"rank\":\"EIGHT\"},{\"suit\":\"HEARTS\",\"rank\":\"NINE\"},{\"suit\":\"HEARTS\",\"rank\":\"TEN\"},{\"suit\":\"HEARTS\",\"rank\":\"JACK\"},{\"suit\":\"HEARTS\",\"rank\":\"QUEEN\"},{\"suit\":\"HEARTS\",\"rank\":\"KING\"},{\"suit\":\"DIAMONDS\",\"rank\":\"ACE\"},{\"suit\":\"DIAMONDS\",\"rank\":\"TWO\"},{\"suit\":\"DIAMONDS\",\"rank\":\"THREE\"},{\"suit\":\"DIAMONDS\",\"rank\":\"FOUR\"},{\"suit\":\"DIAMONDS\",\"rank\":\"FIVE\"},{\"suit\":\"DIAMONDS\",\"rank\":\"SIX\"},{\"suit\":\"DIAMONDS\",\"rank\":\"SEVEN\"},{\"suit\":\"DIAMONDS\",\"rank\":\"EIGHT\"},{\"suit\":\"DIAMONDS\",\"rank\":\"NINE\"},{\"suit\":\"DIAMONDS\",\"rank\":\"TEN\"},{\"suit\":\"DIAMONDS\",\"rank\":\"JACK\"},{\"suit\":\"DIAMONDS\",\"rank\":\"QUEEN\"},{\"suit\":\"DIAMONDS\",\"rank\":\"KING\"},{\"suit\":\"CLUBS\",\"rank\":\"ACE\"},{\"suit\":\"CLUBS\",\"rank\":\"TWO\"},{\"suit\":\"CLUBS\",\"rank\":\"THREE\"},{\"suit\":\"CLUBS\",\"rank\":\"FOUR\"},{\"suit\":\"CLUBS\",\"rank\":\"FIVE\"},{\"suit\":\"CLUBS\",\"rank\":\"SIX\"},{\"suit\":\"CLUBS\",\"rank\":\"SEVEN\"},{\"suit\":\"CLUBS\",\"rank\":\"EIGHT\"},{\"suit\":\"CLUBS\",\"rank\":\"NINE\"},{\"suit\":\"CLUBS\",\"rank\":\"TEN\"},{\"suit\":\"CLUBS\",\"rank\":\"JACK\"},{\"suit\":\"CLUBS\",\"rank\":\"QUEEN\"},{\"suit\":\"CLUBS\",\"rank\":\"KING\"},{\"suit\":\"SPADES\",\"rank\":\"ACE\"},{\"suit\":\"SPADES\",\"rank\":\"TWO\"},{\"suit\":\"SPADES\",\"rank\":\"THREE\"},{\"suit\":\"SPADES\",\"rank\":\"FOUR\"},{\"suit\":\"SPADES\",\"rank\":\"FIVE\"},{\"suit\":\"SPADES\",\"rank\":\"SIX\"},{\"suit\":\"SPADES\",\"rank\":\"SEVEN\"},{\"suit\":\"SPADES\",\"rank\":\"EIGHT\"},{\"suit\":\"SPADES\",\"rank\":\"NINE\"},{\"suit\":\"SPADES\",\"rank\":\"TEN\"},{\"suit\":\"SPADES\",\"rank\":\"JACK\"},{\"suit\":\"SPADES\",\"rank\":\"QUEEN\"},{\"suit\":\"SPADES\",\"rank\":\"KING\"},{\"suit\":\"BLANK\",\"rank\":\"JOKER\"},{\"suit\":\"BLANK\",\"rank\":\"JOKER\"}]");
    }
}
