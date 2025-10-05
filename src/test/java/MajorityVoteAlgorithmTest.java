package src.test.java;

import java.util.Arrays;

import src.main.java.algorithms.MajorityVoteAlgorithm;
import src.main.java.metrics.PerformanceTracker;

/**
 * Comprehensive test suite with edge cases.
 */
public class MajorityVoteAlgorithmTest {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("Running Majority Vote Algorithm Tests...\n");

        testEmptyArray();
        testSingleElement();
        testMajority();
        testNoMajority();
        testAllEqual();
        testTwoElements();
        testExactHalf();
        testNullArray();
        testMajorityAtEnd();
        testAlternating();

        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total:  " + (passed + failed));
    }

    private static void testEmptyArray() {
        Integer[] arr = {};
        PerformanceTracker t = new PerformanceTracker();
        assertTrue(MajorityVoteAlgorithm.findMajority(arr, t).isEmpty(), "testEmptyArray");
    }

    private static void testSingleElement() {
        Integer[] arr = { 7 };
        PerformanceTracker t = new PerformanceTracker();
        assertEquals(7, MajorityVoteAlgorithm.findMajority(arr, t).get(), "testSingleElement");
    }

    private static void testMajority() {
        Integer[] arr = { 2, 2, 1, 2, 3, 2, 2 };
        PerformanceTracker t = new PerformanceTracker();
        assertEquals(2, MajorityVoteAlgorithm.findMajority(arr, t).get(), "testMajority");
    }

    private static void testNegativeNumbers() {
        Integer[] arr = { -1, -1, 2, -1, 3, -1, -1 };
        PerformanceTracker t = new PerformanceTracker();
        assertEquals(-1, MajorityVoteAlgorithm.findMajority(arr, t).get(), "testNegativeNumbers");
    }

    private static void propertyBasedRandomTests() {
        java.util.Random rand = new java.util.Random();

        for (int i = 0; i < 100; i++) {
            int size = 1000 + rand.nextInt(9000);
            Integer[] arr = new Integer[size];
            for (int j = 0; j < size; j++) {
                arr[j] = rand.nextInt(100); // случайные значения 0..99
            }

            PerformanceTracker t = new PerformanceTracker();
            var result = MajorityVoteAlgorithm.findMajority(arr, t);

            // Проверяем свойство: если majority есть, его частота > size / 2
            result.ifPresent(value -> {
                long count = Arrays.stream(arr).filter(v -> v.equals(value)).count();
                assertTrue(count > size / 2, "propertyBasedRandomTests-majorityValidation");
            });
        }

        pass("propertyBasedRandomTests");
    }

    private static void crossValidateWithNaive() {
        Integer[] arr = { 1, 2, 2, 3, 2, 2, 2, 4 };
        PerformanceTracker t = new PerformanceTracker();
        var result1 = MajorityVoteAlgorithm.findMajority(arr, t);
        var result2 = naiveMajority(arr);

        assertEquals(result2, result1, "crossValidateWithNaive");
    }

    private static java.util.Optional<Integer> naiveMajority(Integer[] arr) {
        java.util.Map<Integer, Long> freq = new java.util.HashMap<>();
        for (int v : arr)
            freq.put(v, freq.getOrDefault(v, 0L) + 1);
        int half = arr.length / 2;
        return freq.entrySet().stream()
                .filter(e -> e.getValue() > half)
                .map(java.util.Map.Entry::getKey)
                .findFirst();
    }


    private static void testLargeInputSameElement() {
        Integer[] arr = new Integer[100000];
        Arrays.fill(arr, 42);
        PerformanceTracker t = new PerformanceTracker();
        assertEquals(42, MajorityVoteAlgorithm.findMajority(arr, t).get(), "testLargeInputSameElement");
    }

    private static void testNoMajority() {
        Integer[] arr = { 1, 2, 3, 2 };
        PerformanceTracker t = new PerformanceTracker();
        assertTrue(MajorityVoteAlgorithm.findMajority(arr, t).isEmpty(), "testNoMajority");
    }

    private static void testAllEqual() {
        Integer[] arr = new Integer[5];
        Arrays.fill(arr, 9);
        PerformanceTracker t = new PerformanceTracker();
        assertEquals(9, MajorityVoteAlgorithm.findMajority(arr, t).get(), "testAllEqual");
    }

    private static void testTwoElements() {
        Integer[] arr = { 5, 5 };
        PerformanceTracker t = new PerformanceTracker();
        assertEquals(5, MajorityVoteAlgorithm.findMajority(arr, t).get(), "testTwoElements");
    }

    private static void testExactHalf() {
        Integer[] arr = { 1, 1, 2, 2 };
        PerformanceTracker t = new PerformanceTracker();
        assertTrue(MajorityVoteAlgorithm.findMajority(arr, t).isEmpty(), "testExactHalf");
    }

    private static void testNullArray() {
        try {
            MajorityVoteAlgorithm.findMajority(null, new PerformanceTracker());
            fail("testNullArray - should throw exception");
        } catch (IllegalArgumentException e) {
            pass("testNullArray");
        }
    }

    private static void testMajorityAtEnd() {
        Integer[] arr = { 1, 2, 3, 4, 4, 4, 4, 4 };
        PerformanceTracker t = new PerformanceTracker();
        assertEquals(4, MajorityVoteAlgorithm.findMajority(arr, t).get(), "testMajorityAtEnd");
    }

    private static void testAlternating() {
        Integer[] arr = { 1, 2, 1, 2, 1, 2, 1 };
        PerformanceTracker t = new PerformanceTracker();
        assertEquals(1, MajorityVoteAlgorithm.findMajority(arr, t).get(), "testAlternating");
    }

    private static void assertTrue(boolean condition, String testName) {
        if (condition) {
            pass(testName);
        } else {
            fail(testName);
        }
    }

    private static void assertEquals(Object expected, Object actual, String testName) {
        if (expected.equals(actual)) {
            pass(testName);
        } else {
            fail(testName + " - expected: " + expected + ", got: " + actual);
        }
    }

    private static void pass(String testName) {
        System.out.println("✓ " + testName + " PASSED");
        passed++;
    }

    private static void fail(String testName) {
        System.out.println("✗ " + testName + " FAILED");
        failed++;
    }
}