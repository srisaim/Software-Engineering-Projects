package cs410.uno;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Player {

    // A string to store the name of the player.
    // A list to store the "Card" objects from the player's hand.
    private final String name;
    private final List<Card> hand;

    // This initializes a new "Player" object based on the given name.
    // It also initializes "hand" as a new list of "Card" objects.
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    // Simply adds a "Card" object to the hand.
    public void addCard(Card card) {
        hand.add(card);
    }

    // Simply returns the list of "Card" object from the hand.
    public List<Card> getHand() {
        return hand;
    }

    // Simply returns the name of the player.
    public String getName() {
        return name;
    }

    // Simply used to check if a hand is empty.
    public boolean isHandEmpty() {
        return hand.isEmpty();
    }

    // This randomly selects a color for a wild card use, excluding the color "WILD".
    public Card.Color chooseColorForWildCard() {
        Random rand = new Random();
        Card.Color[] col = Card.Color.values();
        return col[rand.nextInt(col.length - 1)];
    }

    // This checks if a player can use any card from their hand, based on the current top card of the discard pile
    // or if a color is specified when a wild card is used.
    // If there are playable cards, one is randomly selected.
    // If there are no playable cards, it returns null.
    public Card playCard(Card topCard, Card.Color wildColor) {
        Random rand = new Random();
        List<Card> allowedCards = new ArrayList<>();

        for (Card card : hand) {
            if (isPlayable(card, topCard, wildColor)) {
                allowedCards.add(card);
            }
        }

        if (allowedCards.isEmpty()) {
            return null;
        }

        Card selectedCard = allowedCards.get(rand.nextInt(allowedCards.size()));
        hand.remove(selectedCard);
        return selectedCard;
    }

    // This determines if a specific card can be played, based on the current top card.
    // It ensures that wild cards can be played on any card and if the top card is a wild card, it checks if the
    // player's card matches the chosen wild card color.
    // By using booleans, it does several checks:
    //      If the player's card matches the color of the top card.
    //      If the player's card matches the number of the top card.
    //      If the player's special card matches the type of the top card.
    // And if any of those checks return TRUE, the card is playable.
    private boolean isPlayable(Card card, Card topCard, Card.Color wildColor) {
        if (card.getType() == Card.Type.WILD) {
            return true;
        }

        if (topCard.getType() == Card.Type.WILD) {
            return card.getColor() == wildColor;
        }

        boolean colMatch = card.getColor() == topCard.getColor();
        boolean numMatch = card.getType() == Card.Type.NUMBER && card.getNumber() == topCard.getNumber();
        boolean spMatch = card.getType() == topCard.getType() && card.getType() != Card.Type.NUMBER;

        return colMatch || numMatch || spMatch;
    }
}
