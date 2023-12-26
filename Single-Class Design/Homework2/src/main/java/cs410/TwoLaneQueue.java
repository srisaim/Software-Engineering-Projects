package cs410;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TwoLaneQueue {
    // Create an int counter variable count, to count how many times items have been dequeued from fastlane.
    // Create LinkedLists for fastlane and slowlane to hold String objects.
    private int count;
    private final LinkedList<String> fastlane;
    private final LinkedList<String> slowlane;

    // A public constructor that initializes count to 0 and
    // initializes or creates new LinkedLists for fastlane and slowlane.
    public TwoLaneQueue() {
        count = 0;
        fastlane = new LinkedList<>();
        slowlane = new LinkedList<>();
    }

    // A public method that uses .addLast() to add an item to the end of the fastlane LinkedList.
    public void enqueueFast(String item) {
        fastlane.addLast(item);
    }

    // A public method that uses .addLast() to add an item to the end of the slowlane LinkedList.
    public void enqueueSlow(String item) {
        slowlane.addLast(item);
    }

    // A public method to dequeue an item from a lane.
    // If both the fastlane and slowlane queues are empty, no elements to dequeue, a NoSuchElementException is thrown.
    // If the fastlane is not empty and either or the slowlane is empty, the count of elements dequeued from fastlane
    // is <= to 2, then an element is dequeued from the fastlane.

    // An element is dequeued from the fastlane by incrementing count as we are dequeuing, to keep track of how many
    // elements have been dequeued from the fastlane. And then returning and removing the first item from the fastlane.

    // If the if-condition is not met, it means either fastlane is empty
    // or an element is being dequeued from the slowlane.
    // Therefore, we reset the count to 0 and dequeue from the slowlane,
    // returning and removing the first item from the slowlane.
    public String dequeue() {
        if (fastlane.isEmpty() && slowlane.isEmpty()) {
            throw new NoSuchElementException("Fast & Slow lanes are empty");
        }
        if (!fastlane.isEmpty() && (slowlane.isEmpty() || count <= 2)) {
            count = count + 1;
            return fastlane.removeFirst();
        } else {
            count = 0;
            return slowlane.removeFirst();
        }
    }
}
