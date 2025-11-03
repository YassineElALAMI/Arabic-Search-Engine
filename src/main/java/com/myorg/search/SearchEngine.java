package com.myorg.search;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * SearchEngine - Main search interface for Arabic documents.
 * Loads TF-IDF matrix, processes queries, and returns ranked results using cosine similarity.
 */
public class SearchEngine {

    private static final String MATRIX_PATH = "src/main/resources/output/matrix.csv";
    
    private final Map<String, Map<String, Double>> documentVectors;
    private final Map<String, Double> idfScores;
    private final Set<String> vocabulary;
    private final Preprocessor preprocessor;
    private final CosineSimilarity cosineSimilarity;

    /**
     * Initialize the search engine by loading the TF-IDF matrix.
     */
    public SearchEngine() throws IOException {
        System.out.println("🔍 Initializing Arabic Search Engine...");
        
        this.preprocessor = new Preprocessor();
        this.cosineSimilarity = new CosineSimilarity();
        this.documentVectors = new LinkedHashMap<>();
        this.idfScores = new HashMap<>();
        this.vocabulary = new LinkedHashSet<>();
        
        loadMatrix(MATRIX_PATH);
        
        System.out.println("✅ Search Engine ready!");
        System.out.println("   📚 Documents indexed: " + documentVectors.size());
        System.out.println("   📖 Vocabulary size: " + vocabulary.size());
    }

    /**
     * Load TF-IDF matrix from CSV file.
     * Format: Term, doc1, doc2, ..., docN
     */
    private void loadMatrix(String path) throws IOException {
        System.out.println("📊 Loading TF-IDF matrix from: " + path);
        
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        
        if (lines.isEmpty()) {
            throw new IOException("❌ Matrix file is empty!");
        }

        // Parse header (document names)
        String[] header = lines.get(0).split(",");
        List<String> docNames = new ArrayList<>();
        
        for (int i = 1; i < header.length; i++) {
            String docName = extractDocumentName(header[i].trim());
            docNames.add(docName);
            documentVectors.put(docName, new HashMap<>());
        }

        // Parse TF-IDF values
        for (int lineIdx = 1; lineIdx < lines.size(); lineIdx++) {
            String line = lines.get(lineIdx);
            String[] parts = line.split(",");
            
            if (parts.length < 2) continue;
            
            String term = parts[0].trim();
            vocabulary.add(term);
            
            // Store TF-IDF for each document
            for (int docIdx = 0; docIdx < docNames.size() && (docIdx + 1) < parts.length; docIdx++) {
                try {
                    double tfidf = Double.parseDouble(parts[docIdx + 1].trim());
                    if (tfidf > 0.0) {
                        documentVectors.get(docNames.get(docIdx)).put(term, tfidf);
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid values
                }
            }
        }
        
        // Compute IDF scores from the loaded TF-IDF matrix
        computeIDFFromMatrix();
    }

    /**
     * Extract clean document name from full path.
     * Example: "src\main\resources\Corpus\Hespres\مجتمع\txt4.txt" → "txt4.txt"
     */
    private String extractDocumentName(String fullPath) {
        return Paths.get(fullPath).getFileName().toString();
    }

    /**
     * Compute IDF scores from the loaded matrix.
     * IDF(term) = log(N / df) where df is the number of documents containing the term.
     */
    private void computeIDFFromMatrix() {
        int N = documentVectors.size();
        
        for (String term : vocabulary) {
            int df = 0;
            for (Map<String, Double> docVector : documentVectors.values()) {
                if (docVector.containsKey(term)) {
                    df++;
                }
            }
            idfScores.put(term, Math.log((double) N / (1 + df)));
        }
    }

    /**
     * Search for documents matching the query.
     * 
     * @param query Raw Arabic query text
     * @param topK Number of top results to return
     * @return List of search results with document names and scores
     */
    public List<SearchResult> search(String query, int topK) {
        System.out.println("\n🔎 Processing query: " + query);
        
        // Step 1: Preprocess query (tokenize, normalize, stem)
        List<String> queryTerms = preprocessor.preprocessText(query);
        
        if (queryTerms.isEmpty()) {
            System.out.println("⚠️ Query resulted in no valid terms after preprocessing.");
            return new ArrayList<>();
        }
        
        System.out.println("   ✓ Query terms after preprocessing: " + queryTerms);

        // Step 2: Build query TF-IDF vector
        Map<String, Double> queryVector = buildQueryVector(queryTerms);
        
        if (queryVector.isEmpty()) {
            System.out.println("⚠️ Query contains no terms in the vocabulary.");
            return new ArrayList<>();
        }

        // Step 3: Compute cosine similarity with all documents
        List<Map.Entry<String, Double>> rankedDocs = 
            cosineSimilarity.rankDocuments(queryVector, documentVectors, topK);

        // Step 4: Convert to SearchResult objects
        List<SearchResult> results = new ArrayList<>();
        for (Map.Entry<String, Double> entry : rankedDocs) {
            if (entry.getValue() > 0.0) {
                SearchResult result = new SearchResult(entry.getKey(), entry.getValue());
                // Load file path and content
                loadFileDetails(result);
                results.add(result);
            }
        }

        return results;
    }

    /**
     * Load file path and content for a search result.
     */
    private void loadFileDetails(SearchResult result) {
        try {
            // Find the file in the corpus
            String fileName = result.getDocumentName();
            java.io.File corpusDir = new java.io.File("src/main/resources/Corpus");
            java.io.File foundFile = findFile(corpusDir, fileName);
            
            if (foundFile != null) {
                // Set absolute path
                result.setFilePath(foundFile.getAbsolutePath());
                
                // Read content (limit to first 500 characters for preview)
                String content = org.apache.commons.io.FileUtils.readFileToString(foundFile, 
                    java.nio.charset.StandardCharsets.UTF_8);
                
                // Limit content length for display
                if (content.length() > 500) {
                    content = content.substring(0, 500) + "...";
                }
                result.setContent(content);
            }
        } catch (Exception e) {
            result.setFilePath("Path not found");
            result.setContent("Content not available");
        }
    }

    /**
     * Recursively find a file by name in a directory.
     */
    private java.io.File findFile(java.io.File directory, String fileName) {
        if (directory.isDirectory()) {
            java.io.File[] files = directory.listFiles();
            if (files != null) {
                for (java.io.File file : files) {
                    if (file.isFile() && file.getName().equals(fileName)) {
                        return file;
                    } else if (file.isDirectory()) {
                        java.io.File found = findFile(file, fileName);
                        if (found != null) return found;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Build TF-IDF vector for the query.
     * TF-IDF(term) = TF(term in query) × IDF(term from corpus)
     */
    private Map<String, Double> buildQueryVector(List<String> queryTerms) {
        Map<String, Double> queryVector = new HashMap<>();
        
        // Step 1: Compute term frequency in query
        Map<String, Integer> termFreq = new HashMap<>();
        for (String term : queryTerms) {
            termFreq.put(term, termFreq.getOrDefault(term, 0) + 1);
        }
        
        // Step 2: Compute TF-IDF for each term
        int totalTerms = queryTerms.size();
        for (Map.Entry<String, Integer> entry : termFreq.entrySet()) {
            String term = entry.getKey();
            
            // Only include terms that exist in the corpus vocabulary
            if (vocabulary.contains(term)) {
                double tf = (double) entry.getValue() / totalTerms;
                double idf = idfScores.getOrDefault(term, 0.0);
                double tfidf = tf * idf;
                
                if (tfidf > 0.0) {
                    queryVector.put(term, tfidf);
                }
            }
        }
        
        return queryVector;
    }

    /**
     * Interactive search loop (for testing).
     */
    public void interactiveSearch() {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("🔍 Arabic Search Engine - Interactive Mode");
            System.out.println("=".repeat(60));
            System.out.println("Type your query in Arabic, or 'exit' to quit.\n");

            while (true) {
                System.out.print("🔎 Query: ");
                String query = scanner.nextLine().trim();

                if (query.equalsIgnoreCase("exit") || query.equalsIgnoreCase("quit")) {
                    System.out.println("👋 Goodbye!");
                    break;
                }

                if (query.isEmpty()) {
                    continue;
                }

                // Search and display results
                List<SearchResult> results = search(query, 5);
                displayResults(results);
            }
        } catch (Exception e) {
            System.err.println("❌ Error in interactive mode: " + e.getMessage());
        }
    }

    /**
     * Display search results in a formatted way.
     */
    private void displayResults(List<SearchResult> results) {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("📋 Top " + results.size() + " Results:");
        System.out.println("-".repeat(60));

        if (results.isEmpty()) {
            System.out.println("   ❌ No relevant documents found.");
        } else {
            int rank = 1;
            for (SearchResult result : results) {
                System.out.printf("   %d. %s (Score: %.4f)\n", 
                    rank++, result.getDocumentName(), result.getScore());
            }
        }
        
        System.out.println("-".repeat(60) + "\n");
    }

    /**
     * SearchResult - Represents a single search result.
     */
    public static class SearchResult {
        private final String documentName;
        private final double score;
        private String filePath;
        private String content;

        public SearchResult(String documentName, double score) {
            this.documentName = documentName;
            this.score = score;
        }

        public String getDocumentName() {
            return documentName;
        }

        public double getScore() {
            return score;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return String.format("SearchResult{document='%s', score=%.4f}", documentName, score);
        }
    }

    /**
     * Main method for testing the search engine.
     */
    public static void main(String[] args) {
        try {
            SearchEngine engine = new SearchEngine();
            
            // Example: programmatic search
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Example Search: الاقتصاد المغربي");
            System.out.println("=".repeat(60));
            
            List<SearchResult> results = engine.search("الاقتصاد المغربي", 5);
            engine.displayResults(results);
            
            // Interactive mode
            engine.interactiveSearch();
            
        } catch (Exception e) {
            System.err.println("❌ Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
