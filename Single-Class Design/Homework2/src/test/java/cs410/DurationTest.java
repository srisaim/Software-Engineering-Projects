package cs410;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DurationTest {
    // This test checks if the of(int seconds) method in Duration class is working.
    // Create a Duration object with 3600 seconds.
    // Using assert, checks if it returns the correct seconds and string representation.
    @Test
    void testOfSeconds() {
        Duration d = Duration.of(3600);
        assertEquals(3600, d.seconds());
        assertEquals("1:00:00", d.toString());
    }

    // Test checks if (int hours, int minutes, int seconds) method in Duration class is working.
    // Creates a Duration object of 1 hour, 30 minutes, and 30 seconds
    // Using assert, checks if it returns the correct seconds and string representation.
    @Test
    void testOfHoursMinutesSeconds() {
        Duration d = Duration.of(1, 30, 30);
        assertEquals(5430, d.seconds());
        assertEquals("1:30:30", d.toString());
    }

    // This test checks if the add() method in Duration class is working.
    // Creates two Duration objects and adds them together.
    // Using assert, checks if the existing Duration object has the correct seconds and string representation.
    @Test
    void testAdd() {
        Duration d1 = Duration.of(1, 0, 0);
        Duration d2 = Duration.of(0, 59, 59);
        Duration d3 = d1.add(d2);
        assertEquals(7199, d3.seconds());
        assertEquals("1:59:59", d3.toString());
    }

    // Simply checks if the of(int seconds) method in Duration class throws an IllegalArgumentException for
    // invalid values.
    @Test
    void testNegativeSeconds() {
        assertThrows(IllegalArgumentException.class, () -> Duration.of(-1));
    }

    // Simply checks if the of(int hours, int minutes, int seconds) method in Duration class throws an
    // IllegalArgumentException for invalid values.
    @Test
    void testInvalidMinutes() {
        assertThrows(IllegalArgumentException.class, () -> Duration.of(0, 60, 0));
    }

    // Simply checks if the of(int hours, int minutes, int seconds) method in Duration class throws an
    // IllegalArgumentException for invalid values.
    @Test
    void testInvalidSeconds() {
        assertThrows(IllegalArgumentException.class, () -> Duration.of(0, 0, 60));
    }
}