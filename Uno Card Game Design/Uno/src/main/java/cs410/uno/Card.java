package cs410.uno;

public class Card {

    // Color Enum represents all possible colors a card can have.
    public enum Color {
        RED, BLUE, GREEN, YELLOW, WILD
    }

    // Type Enum represents all possible types a card can be.
    public enum Type {
        NUMBER, REVERSE, SKIP, DRAW_TWO, WILD
    }

    // Variable color stores the color of the card.
    // Variable type stores the type of the card.
    // Variable num stores the number on the card.
    private final Color color;
    private final Type type;
    private final int num;

    // This constructs a regular number card by taking the color and number as parameters.
    // Sets the type to NUMBER by using the provided num.
    public Card(Color color, int number) {
        this.color = color;
        this.type = Type.NUMBER;
        this.num = number;
    }

    // This constructs a special card by taking the color and type as parameters.
    // Sets the type to type and since there is no number, num is set to -1.
    public Card(Color color, Type type) {
        this.color = color;
        this.type = type;
        this.num = -1;
    }

    // Simply returns the color of the card.
    public Color getColor() {
        return color;
    }

    // Simply returns the type of the card.
    public Type getType() {
        return type;
    }

    // Simply returns the number of the card.
    // If it's a special card, it returns -1.
    public int getNumber() {
        return num;
    }

    // This provides a string representation of the card by Overriding the class's "toString" method.
    // String representation is shown as "color" and either or the "number" or "type".
    @Override
    public String toString() {
        if (this.type == Type.NUMBER) {
            return this.color + " " + this.num;
        } else {
            return this.color + " " + this.type;
        }
    }
}
