import com.google.gson.Gson;

public record Card(Card.Suit suit, Card.Rank rank) {

    // This Enum represents all possible suits a card can have.
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES, BLANK
    }

    // This Enum represents all possible ranks a card can be.
    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, JOKER
    }

    // This constructs a playing card.
    public static Card of(Card.Suit suit, Card.Rank rank) {
        return new Card(suit, rank);
    }

    // This constructs only a joker playing card.
    public static Card createJoker() {
        return new Card(Suit.BLANK, Rank.JOKER);
    }

    // Creating a method to serialize Card to JSON.
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // Returns a string representation of the card. (Used for debugging)
    @Override
    public String toString() {
        if (this.rank == Rank.JOKER) {
            return "Joker";
        } else {
            return rank + " of " + suit;
        }
    }
}

