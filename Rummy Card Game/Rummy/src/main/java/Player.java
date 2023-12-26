import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class Player {
    final private String name;
    final private List<Card> hand;
    final private Meld melds;
    private Card lastDrawnCard;
    private boolean win;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.melds = new Meld();
        this.win = false;
    }

    public void setGameWinner(boolean hasWon) {
        this.win = hasWon;
    }

    // For a player to draw a card from the deck.
    public void drawCard(Deck deck, boolean fromDiscardPile) {
        Card drawnCard = deck.drawCard();
        if (drawnCard != null) {
            hand.add(drawnCard);
            if (fromDiscardPile) {
                lastDrawnCard = drawnCard;
            }
        }
    }

    public boolean canDiscard(Card card) {
        return !card.equals(lastDrawnCard);
    }

    public void endTurn() {
        lastDrawnCard = null;
    }

    // For a player to discard a card to the discard pile.
    // The player is a game winner if they discard their last card.
    public boolean discardCard(Card card, Deck discardPile) {
        if (card.equals(lastDrawnCard)) {
            System.out.println("You can't discard the same card you just drew.");
            return false;
        }
        if (hand.contains(card)) {
            hand.remove(card);
            discardPile.addCard(card);
            if (hand.isEmpty()) {
                win = true;
            }
            return true;
        }
        return false;
    }

    // For a player to form a meld from a list of cards.
    public boolean formMeld(List<Card> cards) {
        if (melds.canMeld(cards)) {
            melds.addMeld(cards);
            hand.removeAll(cards);
            return true;
        } else {
            System.out.println("Invalid meld. Please try a different action.");
            return false;
        }
    }

    // This is to calculate points based on Rummy specifications and the cards remaining in other player's hands.
    public int calculatePoints() {
        int totalPoints = 0;
        for (Card card : hand) {

            switch (card.rank()) {
                case ACE:
                    totalPoints += 1;
                    break;
                case TWO: case THREE: case FOUR: case FIVE:
                case SIX: case SEVEN: case EIGHT: case NINE: case TEN:
                    totalPoints += card.rank().ordinal() + 1;
                    break;
                case JACK: case QUEEN: case KING:
                    totalPoints += 10;
                    break;
                default:
                    break;
            }
        }
        return totalPoints;
    }

    // Simply returns name.
    public String getName() { return name; }

    // Simply returns hand.
    public List<Card> getHand() { return hand; }

    // Simply returns melds.
    public Meld getMelds() { return melds; }

    // Simply returns win.
    public boolean gw() { return win; }

    // Creating a method to serialize Player to JSON.
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

