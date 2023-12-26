package cs410.uno;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    // A list to store the "Card" objects in the deck.
    private final List<Card> cards;

    // This initializes the card list by creating the UNO deck based on the parameters.
    // It uses "initializeDeck" to add the appropriate cards into the deck and shuffles it.
    public Deck(int countDigitCardsPerColor, int countSpecialCardsPerColor, int countWildCards) {
        cards = new ArrayList<>();
        initializeDeck(countDigitCardsPerColor, countSpecialCardsPerColor, countWildCards);
        shuffle();
    }

    // This fills the UNO deck with the specified amount of each card type.
    // While iterating over each color, it will check if the color isn't wild, ensuring only standard colors are used.
    // Still iterating over each color, it will loop through 0-9, adding the necessary amount of digit to color cards.
    // While adding the necessary amount of each digit card for each color, it also handles the quantity,
    // like digit 0 only to be added once per color.
    // Same is done with the skip, reverse, and draw two cards, while iterating over each color.
    // At the end, it also makes sures to add the necessary amount of wild cards.
    private void initializeDeck(int countDigitCardsPerColor, int countSpecialCardsPerColor, int countWildCards) {
        for (Card.Color color : Card.Color.values()) {
            if (color != Card.Color.WILD) {
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < (x == 0 ? 1 : countDigitCardsPerColor); y++) {
                        cards.add(new Card(color, x));
                    }
                }
            }
        }

        for (Card.Color color : Card.Color.values()) {
            if (color != Card.Color.WILD) {
                for (int x = 0; x < countSpecialCardsPerColor; x++) {
                    cards.add(new Card(color, Card.Type.SKIP));
                    cards.add(new Card(color, Card.Type.REVERSE));
                    cards.add(new Card(color, Card.Type.DRAW_TWO));
                }
            }
        }

        for (int x = 0; x < countWildCards; x++) {
            cards.add(new Card(Card.Color.WILD, Card.Type.WILD));
        }
    }

    // Simply adds a card to the deck.
    public void addCard(Card card) {
        cards.add(card);
    }

    // Simply randomizes the order of the cards in the deck.
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // If deck is empty, it just returns null.
    // Otherwise, it simply removes and returns the last card from the deck.
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        } else {
            return cards.remove(cards.size() - 1);
        }
    }

    // Simply checks if the deck is empty.
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
