package com.myorg.search;

import java.util.*;

/**
 * CosineSimilarity module for computing similarity between query and documents.
 * Uses TF-IDF vectors and cosine similarity formula.
 */
public class CosineSimilarity {

    /**
     * Compute cosine similarity between two vectors (sparse representation as maps).
     * 
     * Formula: cos(θ) = (A · B) / (||A|| × ||B||)
     * 
     * @param vectorA First TF-IDF vector (term → weight)
     * @param vectorB Second TF-IDF vector (term → weight)
     * @return Cosine similarity score [0, 1]
     */
    public double compute(Map<String, Double> vectorA, Map<String, Double> vectorB) {
        if (vectorA.isEmpty() || vectorB.isEmpty()) {
            return 0.0;
        }

        double dotProduct = computeDotProduct(vectorA, vectorB);
        double magnitudeA = computeMagnitude(vectorA);
        double magnitudeB = computeMagnitude(vectorB);

        if (magnitudeA == 0.0 || magnitudeB == 0.0) {
            return 0.0;
        }

        return dotProduct / (magnitudeA * magnitudeB);
    }

    /**
     * Compute dot product: A · B = Σ(a_i × b_i)
     */
    private double computeDotProduct(Map<String, Double> vectorA, Map<String, Double> vectorB) {
        double sum = 0.0;
        
        // Iterate over the smaller vector for efficiency
        Map<String, Double> smaller = vectorA.size() < vectorB.size() ? vectorA : vectorB;
        Map<String, Double> larger = smaller == vectorA ? vectorB : vectorA;

        for (Map.Entry<String, Double> entry : smaller.entrySet()) {
            String term = entry.getKey();
            if (larger.containsKey(term)) {
                sum += entry.getValue() * larger.get(term);
            }
        }
        
        return sum;
    }

    /**
     * Compute magnitude (L2 norm): ||A|| = √(Σ(a_i²))
     */
    private double computeMagnitude(Map<String, Double> vector) {
        double sumOfSquares = 0.0;
        
        for (Double value : vector.values()) {
            sumOfSquares += value * value;
        }
        
        return Math.sqrt(sumOfSquares);
    }

    /**
     * Rank all documents against a query vector.
     * 
     * @param queryVector Query TF-IDF vector
     * @param documentVectors All document TF-IDF vectors (docName → vector)
     * @param topK Number of top results to return
     * @return List of (docName, score) pairs sorted by score descending
     */
    public List<Map.Entry<String, Double>> rankDocuments(
            Map<String, Double> queryVector,
            Map<String, Map<String, Double>> documentVectors,
            int topK) {

        List<Map.Entry<String, Double>> scores = new ArrayList<>();

        // Compute similarity for each document
        for (Map.Entry<String, Map<String, Double>> docEntry : documentVectors.entrySet()) {
            String docName = docEntry.getKey();
            Map<String, Double> docVector = docEntry.getValue();
            
            double similarity = compute(queryVector, docVector);
            scores.add(new AbstractMap.SimpleEntry<>(docName, similarity));
        }

        // Sort by score descending
        scores.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // Return top K results
        return scores.subList(0, Math.min(topK, scores.size()));
    }
}
