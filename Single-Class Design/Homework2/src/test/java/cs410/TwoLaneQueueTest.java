package cs410;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TwoLaneQueueTest {
    // This method tests for the enqueueFast and dequeue methods.
    // Create a new TwoLaneQueue object.
    // Enqueue a string using the enqueueFast method, which is fast1.
    // Dequeue an element from the queue and assert that it equals fast1.
    @Test
    void testEnqueueFastAndDequeue() {
        TwoLaneQueue queue = new TwoLaneQueue();
        queue.enqueueFast("fast1");
        assertEquals("fast1", queue.dequeue());
    }

    // This method tests the for the enqueueSlow and dequeue methods.
    // Create a new TwoLaneQueue object.
    // Enqueue a string using the enqueueSlow method, which is slow1.
    // Dequeue an element from the queue and assert that it equals slow1.
    @Test
    void testEnqueueSlowAndDequeue() {
        TwoLaneQueue queue = new TwoLaneQueue();
        queue.enqueueSlow("slow1");
        assertEquals("slow1", queue.dequeue());
    }

    // This method is meant to test for the fairness of the dequeue method.
    // Create a new TwoLaneQueue object.
    // Enqueue three strings using the enqueueFast method.
    // Enqueue a string using the enqueueSlow method, which is slow1.
    // Dequeue elements from the queue and assert they were dequeued in the same order they were enqueued.
    @Test
    void testFairness() {
        TwoLaneQueue queue = new TwoLaneQueue();
        queue.enqueueFast("fast1");
        queue.enqueueFast("fast2");
        queue.enqueueFast("fast3");
        queue.enqueueSlow("slow1");

        // The first element enqueued.
        // The second element enqueued.
        // The third element enqueued.
        // The fourth element enqueued.
        assertEquals("fast1", queue.dequeue());
        assertEquals("fast2", queue.dequeue());
        assertEquals("fast3", queue.dequeue());
        assertEquals("slow1", queue.dequeue());
    }
}