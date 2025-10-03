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