package com.myorg.search;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class TfIdfBuilder {

    public void runPipeline() throws Exception {
        String outputPath = "src/main/resources/output/matrix.csv";

        // Preprocessor automatically knows where stopwords and corpus are located
        Preprocessor prep = new Preprocessor();
        Map<String, List<String>> docs = prep.loadCorpus();

        Map<String, Map<String, Double>> tf = computeTF(docs);
        Map<String, Double> idf = computeIDF(tf);
        Map<String, Map<String, Double>> tfidf = computeTFIDF(tf, idf);

        saveCSV(tfidf, outputPath);
        System.out.println("✅ TF-IDF matrix saved to: " + outputPath);
    }


    /** Term frequency per document */
    private Map<String, Map<String, Double>> computeTF(Map<String, List<String>> docs) {
        Map<String, Map<String, Double>> tf = new HashMap<>();
        for (var e : docs.entrySet()) {
            Map<String, Double> freq = new HashMap<>();
            for (String w : e.getValue())
                freq.put(w, freq.getOrDefault(w, 0.0) + 1.0);
            int total = e.getValue().size();
            freq.replaceAll((k, v) -> v / total);
            tf.put(e.getKey(), freq);
        }
        return tf;
    }

    /** Inverse document frequency */
    private Map<String, Double> computeIDF(Map<String, Map<String, Double>> tf) {
        Map<String, Double> idf = new HashMap<>();
        int N = tf.size();
        Set<String> allTerms = new HashSet<>();
        tf.values().forEach(map -> allTerms.addAll(map.keySet()));
        for (String t : allTerms) {
            int df = 0;
            for (var m : tf.values()) if (m.containsKey(t)) df++;
            idf.put(t, Math.log((double) N / (1 + df)));
        }
        return idf;
    }

    /** TF × IDF */
    private Map<String, Map<String, Double>> computeTFIDF(Map<String, Map<String, Double>> tf,
                                                         Map<String, Double> idf) {
        Map<String, Map<String, Double>> out = new LinkedHashMap<>();
        for (var doc : tf.keySet()) {
            Map<String, Double> map = new HashMap<>();
            for (var term : tf.get(doc).keySet()) {
                map.put(term, tf.get(doc).get(term) * idf.getOrDefault(term, 0.0));
            }
            out.put(doc, map);
        }
        return out;
    }

    /** Save TF-IDF to CSV */
    private void saveCSV(Map<String, Map<String, Double>> tfidf, String path) throws IOException {
        Set<String> allTerms = new LinkedHashSet<>();
        tfidf.values().forEach(map -> allTerms.addAll(map.keySet()));
        List<String> docs = new ArrayList<>(tfidf.keySet());
        Files.createDirectories(Paths.get(path).getParent());
        try (BufferedWriter w = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8)) {
            w.write("Term," + String.join(",", docs.stream()
                    .map(s -> s.replace(',', '_')).toList()) + "\n");
            for (String term : allTerms) {
                w.write(term);
                for (String d : docs)
                    w.write("," + tfidf.get(d).getOrDefault(term, 0.0));
                w.write("\n");
            }
        }
    }
}
