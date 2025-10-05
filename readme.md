Overview

This project implements the Boyer–Moore Majority Vote Algorithm in Java.
It determines whether an array contains a majority element — a value that appears more than half of the time.
The project also includes tools for performance tracking, benchmarking, and automated testing.

ASSIGNMENT 2/
├── docs/
│   └── Analysis Report.pdf
├── src/
│   ├── main/java/
│   │   ├── algorithms/
│   │   │   └── MajorityVoteAlgorithm.java
│   │   ├── cli/
│   │   │   └── BenchmarkRunner.java
│   │   └── metrics/
│   │       ├── CsvExporter.java
│   │       └── PerformanceTracker.java
│   └── test/java/
│       └── MajorityVoteAlgorithmTest.java
└── readme.md

Implementation Details

MajorityVoteAlgorithm.java – Implements the Boyer–Moore majority vote algorithm in O(n) time and O(1) space.

PerformanceTracker.java – Tracks execution metrics (operations, duration, and memory).

CsvExporter.java – Exports collected performance data to CSV format for visualization.

BenchmarkRunner.java – Command-line utility for running large-scale performance benchmarks.

MajorityVoteAlgorithmTest.java – Comprehensive test suite covering correctness, property-based, and performance testing.

Testing

The project includes a detailed test suite ensuring both correctness and performance:

✅ Correctness Validation

Unit Tests: Covers all edge cases — empty arrays, single elements, duplicates, and non-majority scenarios.

Property-Based Testing: Validates the majority rule across 100+ random datasets.

Cross-Validation: Compares algorithm results with a naïve frequency-count implementation.

⚙️ Performance Testing

Scalability tested on input sizes from 10² to 10⁵.

Input distributions: random, sorted, reverse-sorted, and nearly sorted arrays.

Memory profiling integrated via the PerformanceTracker class.
Automation

Continuous testing is integrated using GitHub Actions.

Each commit automatically compiles, runs tests, and outputs summary statistics.

Results

All unit and property-based tests pass successfully.

The algorithm demonstrates linear performance (O(n)) and minimal memory consumption.

Benchmarks confirm stable scalability across different input distributions.