package cs410.uno;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class DeckTest {

    // Simple test to check if Deck is created correctly with right amount of specified counts of digit cards,
    // special cards, and wild cards.
    @Test
    public void testDeckInitialization() {
        int setDigitsCardsPerColor = 2;
        int setSpecialCardsPerColor = 2;
        int setWildCards = 4;
        Deck deck = new Deck(setDigitsCardsPerColor, setSpecialCardsPerColor, setWildCards);
        int rightCount = (10 * setDigitsCardsPerColor * 4) + (3 * setSpecialCardsPerColor * 4) + setWildCards;
        int currentCount = 0;
        while (!deck.isEmpty()) {
            deck.drawCard();
            currentCount++;
        }

        assertEquals(rightCount, currentCount);
    }

    // Simple test to check if Deck contains the correct number of each type of card, after being initialized.
    @Test
    public void testDeckContents() {
        int setDigitsCardsPerColor = 2;
        int setSpecialCardsPerColor = 2;
        int setWildCards = 4;
        Deck deck = new Deck(setDigitsCardsPerColor, setSpecialCardsPerColor, setWildCards);
        int digitCards = 0;
        int specialCards = 0;
        int wildCards = 0;
        while (!deck.isEmpty()) {
            Card c = deck.drawCard();
            if (c.getType() == Card.Type.NUMBER) {
                digitCards++;
            } else if (c.getType() == Card.Type.WILD) {
                wildCards++;
            } else {
                specialCards++;
            }
        }

        assertEquals(40 * setDigitsCardsPerColor, digitCards);
        assertEquals(12 * setSpecialCardsPerColor, specialCards);
        assertEquals(setWildCards, wildCards);
    }

    // Simple test to check if Deck is being shuffled properly, order of cards are being changed.
    @Test
    public void testShuffling() {
        Deck deck = new Deck(2, 2, 4);
        List<Card> cardsPreShuffled = new ArrayList<>();
        while (!deck.isEmpty()) {
            cardsPreShuffled.add(deck.drawCard());
        }
        deck = new Deck(2, 2, 4);
        deck.shuffle();
        boolean order = false;
        int index = 0;
        while (!deck.isEmpty() && index < cardsPreShuffled.size()) {
            if (!deck.drawCard().equals(cardsPreShuffled.get(index))) {
                order = true;
                break;
            }
            index++;
        }

        assertTrue(order);
    }
}
