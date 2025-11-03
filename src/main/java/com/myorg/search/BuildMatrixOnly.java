package com.myorg.search;

/**
 * Simple class to ONLY build the TF-IDF matrix.
 * No search engine, no interactive mode.
 */
public class BuildMatrixOnly {
    public static void main(String[] args) {
        System.out.println("🚀 Building TF-IDF Matrix ONLY...");
        System.out.println("=".repeat(60));
        
        try {
            TfIdfBuilder builder = new TfIdfBuilder();
            builder.runPipeline();
            System.out.println("=".repeat(60));
            System.out.println("✅ Matrix building completed!");
        } catch (Exception e) {
            System.err.println("❌ Error building matrix:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
