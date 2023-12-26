package cs410;

public class Duration {

    // Create an int variable to store the value of seconds.
    private final int seconds;

    // An IllegalArgumentException is thrown if seconds is negative.
    // Initialization of the seconds variable.
    private Duration(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("Duration can't be less then 0");
        }
        this.seconds = seconds;
    }

    // A public method that takes seconds as args and returns a new Duration object.
    public static Duration of(int seconds) {
        return new Duration(seconds);
    }

    // A public method that takes hours, minutes, and seconds as args and returns a new Duration object.
    // If-conditions that check both minutes and seconds if they range between 0-59.
    // An IllegalArgumentException is thrown if both minutes and seconds are not between 0-59.
    // The calculated overall total number of seconds is stored as int total.
    // Then uses constructor to create and return new object.
    public static Duration of(int hours, int minutes, int seconds) {
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Minutes range from 0-59");
        }
        if (seconds < 0 || seconds > 59) {
            throw new IllegalArgumentException("Seconds range from 0-59");
        }
        int total = (hours * 3600) + (minutes * 60) + seconds;
        return new Duration(total);
    }

    // A public method that adds seconds after taking another Duration object.
    // Saves the sum of seconds in int sum.
    // If sum happens be less than 0, then an IllegalArgumentException is thrown.
    // Returns a new Duration object that is the sum of the two Durations.
    public Duration add(Duration other) {
        int sum = this.seconds + other.seconds;
        if(sum < 0){
            throw new IllegalArgumentException("Sum can't be negative");
        }
        return new Duration(sum);
    }

    // Simply returns the total number of seconds.
    public int seconds() {
        return seconds;
    }

    // Simply returns the string to display the duration in H:MM:SS format.
    @Override
    public String toString() {
        // Hours
        int h = (seconds / 3600);
        // Remainder
        int r = (seconds % 3600);
        // Minutes
        int m = (r / 60);
        // Seconds
        int s = (r % 60);
        // Returns formatted string
        return String.format("%d:%02d:%02d", h, m, s);
    }
}
