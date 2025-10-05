package src.main.java.metrics;

/**
 * Tracks algorithm performance metrics.
 */
public class PerformanceTracker {
    private long comparisons = 0;
    private long arrayAccesses = 0;
    private long allocations = 0;
    private long passes = 0;
    private boolean majorityFound = false;

    public void incrementComparisons() {
        comparisons++;
    }

    public void incrementArrayAccesses() {
        arrayAccesses++;
    }

    public void incrementAllocations() {
        allocations++;
    }

    public void incrementPasses() {
        passes++;
    }

    public void setMajorityFound(boolean value) {
        majorityFound = value;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getArrayAccesses() {
        return arrayAccesses;
    }

    public long getAllocations() {
        return allocations;
    }

    public long getPasses() {
        return passes;
    }

    public boolean isMajorityFound() {
        return majorityFound;
    }

    public void reset() {
        comparisons = 0;
        arrayAccesses = 0;
        allocations = 0;
        passes = 0;
        majorityFound = false;
    }

    /**
     * 
     * 
     * Exports metrics as CSV row.
     */
    public String toCsvRow() {
        return String.format("%d,%d,%d,%d,%b",
                comparisons, arrayAccesses, allocations, passes, majorityFound);
    }

    public static String getCsvHeader() {
        return "comparisons,arrayAccesses,allocations,passes,majorityFound";
    }

    @Override
    public String toString() {
        return "PerformanceTracker{" +
                "comparisons=" + comparisons +
                ", arrayAccesses=" + arrayAccesses +
                ", allocations=" + allocations +
                ", passes=" + passes +
                ", majorityFound=" + majorityFound +
                '}';
    }
}