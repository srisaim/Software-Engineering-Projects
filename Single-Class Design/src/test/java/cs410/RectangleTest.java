package cs410;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    // This test checks if the area() method in Rectangle class is working.
    // A new Rectangle object is created with corners as (0,0) and (5,5).
    // Using asserts, it checks if the area of the rectangle is 25.
    // If the area is not 25, the test fails.
    @Test
    void testArea() {
        Rectangle rect = Rectangle.of(0, 0, 5, 5);
        assertEquals(25, rect.area());
    }

    // This test checks if the contains() method in Rectangle class is working.
    // A new Rectangle object is created with corners as (0,0) and (5,5).
    // Using asserts, it checks if the rectangle contains the point (3,3).
    // If it doesn't, the test fails.
    // Using asserts, it checks if the rectangle does not contain the point (6,6).
    // If it does, the test fails.
    @Test
    void testContains() {
        Rectangle rect = Rectangle.of(0, 0, 5, 5);
        assertTrue(rect.contains(3, 3));
        assertFalse(rect.contains(6, 6));
    }

    // This test checks if the overlaps() method in Rectangle class is working.
    // Creates three Rectangle objects for testing the overlaps() method.
    // Rectangle 1 will be corners as (0,0) and (5,5).
    // Rectangle 2 will be overlapping, with corners as (4,4) and (6,6).
    // Rectangle 3 will be non-overlapping, with corners as (6,6) and (8,8).
    // Using asserts, it checks if Rectangle 1 and Rectangle 2 overlap.
    // If not, the test fails.
    // Using asserts, it checks if Rectangle 1 and Rectangle 3 do not overlap.
    // If they do, the test fails.
    @Test
    void testOverlaps() {
        Rectangle rect1 = Rectangle.of(0, 0, 5, 5);
        Rectangle rect2 = Rectangle.of(4, 4, 6, 6);
        Rectangle rect3 = Rectangle.of(6, 6, 8, 8);

        assertTrue(rect1.overlaps(rect2));
        assertFalse(rect1.overlaps(rect3));
    }
}