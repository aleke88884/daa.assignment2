package src.main.java.algorithms;

import java.util.Objects;
import java.util.Optional;

import src.main.java.metrics.PerformanceTracker;

/***
 * Boyer-Moore Majority Vote Algorithm implementation.*Finds the majority
 * element(>n/2 occurrences)in O(n)time and O(1)space.
 */

public class MajorityVoteAlgorithm {

    /**
     * Finds majority element (> n/2 occurrences).
     * 
     * @param arr     input array
     * @param tracker performance metrics tracker
     * @return Optional containing majority element, or empty if none exists
     * @throws IllegalArgumentException if array is null
     */
    public static <T> Optional<T> findMajority(T[] arr, PerformanceTracker tracker) {
        if (arr == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        if (tracker == null) {
            tracker = new PerformanceTracker();
        }

        if (arr.length == 0) {
            return Optional.empty();
        }

        tracker.incrementPasses();
        T candidate = null;
        int count = 0;

        // Phase 1: Find candidate
        for (int i = 0; i < arr.length; i++) {
            tracker.incrementArrayAccesses();
            if (count == 0) {
                candidate = arr[i];
                count = 1;
                tracker.incrementAllocations();
            } else {
                tracker.incrementComparisons();
                if (Objects.equals(candidate, arr[i])) {
                    count++;
                } else {
                    count--;
                }
            }
        }

        // Phase 2: Verify candidate
        tracker.incrementPasses();
        long occurrences = 0;
        for (T value : arr) {
            tracker.incrementArrayAccesses();
            tracker.incrementComparisons();
            if (Objects.equals(candidate, value)) {
                occurrences++;
            }
        }

        if (occurrences > arr.length / 2) {
            tracker.setMajorityFound(true);
            return Optional.of(candidate);
        }

        tracker.setMajorityFound(false);
        return Optional.empty();
    }
}
