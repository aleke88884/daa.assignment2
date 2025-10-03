package src.main.java.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import src.main.java.algorithms.MajorityVoteAlgorithm;
import src.main.java.metrics.CsvExporter;
import src.main.java.metrics.CsvExporter.BenchmarkResult;
import src.main.java.metrics.PerformanceTracker;

/**
 * Command-line benchmark runner with configurable input sizes.
 */
public class BenchmarkRunner {

    public static void main(String[] args) {
        BenchmarkConfig config = parseArgs(args);

        if (config.runFullBenchmark) {
            runFullBenchmark(config);
        } else {
            runSingleBenchmark(config);
        }
    }

    private static void runSingleBenchmark(BenchmarkConfig config) {
        System.out.println("=== Single Benchmark Run ===");
        System.out.println("Array size: " + config.arraySize);
        System.out.println("Random: " + config.random);
        System.out.println();

        Integer[] arr = config.random ? randomArray(config.arraySize, config.seed) : majorityArray(config.arraySize);

        PerformanceTracker tracker = new PerformanceTracker();
        long start = System.nanoTime();
        Optional<Integer> result = MajorityVoteAlgorithm.findMajority(arr, tracker);
        long end = System.nanoTime();

        System.out.println("Majority: " + (result.isPresent() ? result.get() : "None"));
        System.out.println("Time (ns): " + (end - start));
        System.out.println("Time (ms): " + (end - start) / 1_000_000.0);
        System.out.println();
        System.out.println(tracker);
    }

    private static void runFullBenchmark(BenchmarkConfig config) {
        System.out.println("=== Full Benchmark Suite ===");
        System.out.println("Running benchmarks for array sizes: 10, 100, 1000, 10000, 100000");
        System.out.println();

        int[] sizes = { 10, 100, 1000, 10000, 100000 };
        List<BenchmarkResult> results = new ArrayList<>();

        for (int size : sizes) {
            Integer[] arr = config.random ? randomArray(size, config.seed) : majorityArray(size);

            PerformanceTracker tracker = new PerformanceTracker();
            long start = System.nanoTime();
            MajorityVoteAlgorithm.findMajority(arr, tracker);
            long end = System.nanoTime();

            long timeNanos = end - start;
            results.add(new BenchmarkResult(size, timeNanos, tracker));

            System.out.printf("Size: %6d | Time: %10d ns | Comparisons: %6d | Accesses: %6d%n",
                    size, timeNanos, tracker.getComparisons(), tracker.getArrayAccesses());
        }

        // Export to CSV
        if (config.exportCsv) {
            try {
                CsvExporter exporter = new CsvExporter("benchmark_results.csv");
                exporter.export(results);
                System.out.println("\nResults exported to benchmark_results.csv");
            } catch (IOException e) {
                System.err.println("Failed to export CSV: " + e.getMessage());
            }
        }
    }

    private static Integer[] randomArray(int size, long seed) {
        Random r = new Random(seed);
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = r.nextInt(5);
        }
        return arr;
    }

    private static Integer[] majorityArray(int size) {
        Integer[] arr = new Integer[size];
        int majority = size / 2 + 1;
        for (int i = 0; i < majority; i++) {
            arr[i] = 1;
        }
        for (int i = majority; i < size; i++) {
            arr[i] = 2;
        }
        return arr;
    }

    private static BenchmarkConfig parseArgs(String[] args) {
        BenchmarkConfig config = new BenchmarkConfig();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--size":
                case "-s":
                    if (i + 1 < args.length) {
                        config.arraySize = Integer.parseInt(args[++i]);
                    }
                    break;
                case "--random":
                case "-r":
                    config.random = true;
                    break;
                case "--seed":
                    if (i + 1 < args.length) {
                        config.seed = Long.parseLong(args[++i]);
                    }
                    break;
                case "--full":
                case "-f":
                    config.runFullBenchmark = true;
                    break;
                case "--export":
                case "-e":
                    config.exportCsv = true;
                    break;
                case "--help":
                case "-h":
                    printHelp();
                    System.exit(0);
                    break;
            }
        }

        return config;
    }

    private static void printHelp() {
        System.out.println("Usage: java BenchmarkRunner [OPTIONS]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -s, --size <N>     Array size (default: 20)");
        System.out.println("  -r, --random       Use random array (default: majority array)");
        System.out.println("  --seed <N>         Random seed (default: current time)");
        System.out.println("  -f, --full         Run full benchmark suite");
        System.out.println("  -e, --export       Export results to CSV");
        System.out.println("  -h, --help         Show this help message");
    }

    private static class BenchmarkConfig {
        int arraySize = 20;
        boolean random = false;
        long seed = System.nanoTime();
        boolean runFullBenchmark = false;
        boolean exportCsv = false;
    }
}
