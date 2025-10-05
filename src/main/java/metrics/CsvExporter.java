package src.main.java.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 
 * 
 * 
 * Exports benchmark results to CSV format.
 * 
 * 
 * 
 */
public class CsvExporter {
    private final String filepath;

    public CsvExporter(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Exports benchmark results to CSV file.
     */
    public void export(List<BenchmarkResult> results) throws IOException {
        Path path = Paths.get(filepath);
        Files.createDirectories(path.getParent());

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            // Write header
            writer.println("arraySize,timeNanos," + PerformanceTracker.getCsvHeader());

            // Write data rows
            for (BenchmarkResult result : results) {
                writer.printf("%d,%d,%s%n",
                        result.getArraySize(),
                        result.getTimeNanos(),
                        result.getTracker().toCsvRow());
            }
        }
    }

    public static class BenchmarkResult {
        private final int arraySize;
        private final long timeNanos;
        private final PerformanceTracker tracker;

        public BenchmarkResult(int arraySize, long timeNanos, PerformanceTracker tracker) {
            this.arraySize = arraySize;
            this.timeNanos = timeNanos;
            this.tracker = tracker;
        }

        public int getArraySize() {
            return arraySize;
        }

        public long getTimeNanos() {
            return timeNanos;
        }

        public PerformanceTracker getTracker() {
            return tracker;
        }
    }
}