package com.myorg.search;

import java.util.List;

/**
 * Demo class to test the SearchEngine with predefined queries.
 * This demonstrates programmatic usage without interactive mode.
 */
public class SearchEngineDemo {

    public static void main(String[] args) {
        try {
            System.out.println("🚀 Arabic Search Engine Demo");
            System.out.println("=".repeat(60) + "\n");

            // Initialize the search engine
            SearchEngine engine = new SearchEngine();

            // Test queries
            String[] testQueries = {
                "الاقتصاد المغربي",
                "كرة القدم",
                "السياسة والحكومة",
                "الثقافة والفنون",
                "الأخبار المغربية"
            };

            // Run each test query
            for (String query : testQueries) {
                System.out.println("=".repeat(60));
                System.out.println("🔎 Query: " + query);
                System.out.println("=".repeat(60));

                List<SearchEngine.SearchResult> results = engine.search(query, 5);

                if (results.isEmpty()) {
                    System.out.println("   ❌ No results found.\n");
                } else {
                    System.out.println("\n📋 Top " + results.size() + " Results:");
                    System.out.println("-".repeat(60));
                    int rank = 1;
                    for (SearchEngine.SearchResult result : results) {
                        System.out.printf("   %d. %-40s Score: %.4f\n",
                            rank++, result.getDocumentName(), result.getScore());
                    }
                    System.out.println();
                }
            }

            System.out.println("=".repeat(60));
            System.out.println("✅ Demo completed successfully!");

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
