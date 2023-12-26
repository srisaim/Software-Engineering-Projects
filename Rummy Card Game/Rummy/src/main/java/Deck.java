import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;

public class Deck {

    // A list to store the "Card" objects in the deck.
    private final List<Card> cards;

    // This initializes the card list by creating a standard deck.
    // It uses "initializeDeck" to add the appropriate cards into the deck and shuffles it.
    public Deck(int numDecks) {
        cards = new ArrayList<>();
        initializeDeck(numDecks);
        shuffle();
    }

    // An overloaded constructor to allow creating an empty deck.
    // If createEmpty is true, we leave the deck empty.
    public Deck() {
        cards = new ArrayList<>();
    }

    // This is to peek at the top card of a deck without removing it.
    public Card peekTopCard() {
        if (!cards.isEmpty()) {
            return cards.get(cards.size() - 1);
        }
        return null;
    }

    // This fills the standard deck with the specified amount of each card rank.
    // While iterating over each suit, it will check if the suit isn't BLANK, ensuring only standard suits are used.
    // Still iterating over each suit, it will loop through all ranks, adding the necessary amount of rank to suit cards.
    // After adding all type of standard cards to the deck, two Jokers are also added at the end.
    protected void initializeDeck(int numDecks) {
        for(int i = 0; i < numDecks; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                if (suit != Card.Suit.BLANK) {
                    for (Card.Rank rank : Card.Rank.values()) {
                        if (rank != Card.Rank.JOKER) {
                            cards.add(new Card(suit, rank));
                        }
                    }
                }
            }

            cards.add(Card.createJoker());
            cards.add(Card.createJoker());
        }
    }

    // Simply randomizes the order of the cards in the deck.
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Reshuffles cards from the discard pile into this deck.
    public void reshuffleDiscardIntoDeck(Deck discardPile) {
        Card topCard = discardPile.drawCard();
        while (!discardPile.isEmpty()) {
            this.addCard(discardPile.drawCard());
        }
        this.shuffle(); // Reshuffle the deck
        if (topCard != null) {
            discardPile.addCard(topCard);
        }
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

    // Simply adding a card to the deck.
    public void addCard(Card card) {
        cards.add(card);
    }

    // Simply check if the deck is empty.
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // Creating a method to serialize Deck to JSON.
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this.cards);
    }
}

