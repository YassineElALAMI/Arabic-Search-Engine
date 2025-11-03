package com.myorg.search;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Arabic Search Engine starting...");
        System.out.println("👉 Modules: Preprocessing → TF-IDF → Search");

        try {
            // Step 1: Build TF-IDF matrix (only if needed)
            File matrixFile = new File("src/main/resources/output/matrix.csv");
            
            if (!matrixFile.exists()) {
                System.out.println("\n" + "=".repeat(60));
                System.out.println("⚠️ Matrix not found - Building TF-IDF Matrix...");
                System.out.println("=".repeat(60));
                TfIdfBuilder builder = new TfIdfBuilder();
                builder.runPipeline();
            } else {
                System.out.println("\n✅ Using existing TF-IDF matrix");
                System.out.println("   (To rebuild, delete: " + matrixFile.getAbsolutePath() + ")");
            }

            // Step 2: Launch Search Engine
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Step 2: Launching Search Engine");
            System.out.println("=".repeat(60));
            SearchEngine engine = new SearchEngine();
            
            // Interactive search mode
            engine.interactiveSearch();

        } catch (Exception e) {
            System.err.println("❌ Error occurred:");
            e.printStackTrace();
        }

        System.out.println("\n✅ Finished successfully.");
    }
}
