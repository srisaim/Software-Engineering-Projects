package cs410;

public class Rectangle {
    // Create int variables x1 & y1 to represent coordinates of one corner of the rectangle.
    // And int variables x2 & y2 to represent coordinates of opposite corner of the rectangle.
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    // Initializing the coordinate variables.
    // Using if-else statements and temporary var, the var. x1 & y1 are assigned to the minimum values,
    // while the maximum values are assigned to var. x2 & y2
    private Rectangle(int x1, int y1, int x2, int y2) {
        int temp;

        if (x1 > x2) {
            temp = x1;
            x1 = x2;
            x2 = temp;
        }

        if (y1 > y2) {
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    // A public method that takes four integer parameters, and creates & returns a new Rectangle object.
    // Initialized with the given x & y coordinates.
    public static Rectangle of(int x1, int y1, int x2, int y2) {
        return new Rectangle(x1, y1, x2, y2);
    }

    // Simply calculates and returns the area of the rectangle.
    // The area is calculated as width * height = (x2 - x1) * (y2 - y1).
    public int area() {
        int area;
        area = (x2 - x1) * (y2 - y1);
        return area;
    }

    // Checks whether the rectangle contains a given point (x, y).
    // If x is between x1 & x2, return true or else false.
    // If y is between y1 & y2, return true or else false.
    public boolean contains(int x, int y) {
        boolean a = x >= x1;
        a = a && x <= x2;

        boolean b = y >= y1;
        b = b && y <= y2;

        return a && b;
    }

    // A public method to check whether this rectangle overlaps with another rectangle.
    // Check if this rect left edge is to the left of the other rect right edge.
    // Check if this rect right edge is to the right of the other rect left edge.
    // Check if this rect top edge is above the other rect bottom edge.
    // Check if this rect bottom edge is below the other rect top edge.
    // Returns true if this rectangle and the other rectangle overlap.
    public boolean overlaps(Rectangle other) {
        boolean left = this.x1 < other.x2;
        boolean right = this.x2 > other.x1;
        boolean top = this.y1 < other.y2;
        boolean bottom = this.y2 > other.y1;
        return left && right && top && bottom;
    }
}
