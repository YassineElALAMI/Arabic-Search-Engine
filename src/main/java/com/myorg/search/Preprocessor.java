package com.myorg.search;

import org.apache.commons.io.FileUtils;
import safar.basic.morphology.stemmer.interfaces.IStemmer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Preprocessor {

    private static final String STOPWORDS_PATH = "src/main/resources/stopwords.txt";
    private static final String CORPUS_PATH = "src/main/resources/Corpus";
    private final Set<String> stopwords = new HashSet<>();
    private final IStemmer stemmer;

    public Preprocessor() {
        loadStopwords();
        this.stemmer = initializeStemmer();
    }

    /** Load stopwords */
    private void loadStopwords() {
        try {
            stopwords.addAll(FileUtils.readLines(new File(STOPWORDS_PATH), StandardCharsets.UTF_8));
            System.out.println("⛔ Stopwords loaded: " + stopwords.size());
        } catch (IOException e) {
            System.out.println("⚠️ Failed to load stopwords.");
        }
    }

    /** Initialize SAFAR stemmer - Light10Stemmer (more reliable) */
    private IStemmer initializeStemmer() {
        System.out.println("🔧 Initializing SAFAR stemmer (Light10)...");
        try {
            // Use Light10Stemmer - more stable and reliable
            IStemmer stemmer = new safar.basic.morphology.stemmer.impl.Light10Stemmer();
            System.out.println("✅ SAFAR Light10 stemmer loaded successfully.");
            return stemmer;
        } catch (Exception e) {
            System.err.println("❌ Failed to initialize SAFAR stemmer:");
            e.printStackTrace();
            System.exit(1); // 🔴 Stop the entire program immediately
            return null; // (unreachable, but required for compilation)
        }
    }


    /** Normalize Arabic text */
    private String normalizeArabic(String text) {
        // Remove non-Arabic characters and diacritics
        String result = text.replaceAll("[^\\p{IsArabic}\\s]", "");
        result = result.replaceAll("[ًٌٍَُِّْ]", ""); // Remove diacritics
        
        // Normalize characters
        result = result.replace("أ", "ا").replace("إ", "ا").replace("آ", "ا");
        result = result.replace("ة", "ه").replace("ى", "ي");
        result = result.replaceAll("\\s+", " "); // Normalize whitespace
        
        return result.trim();
    }

    /** Apply stemming safely - Extract only the morpheme from SAFAR result */
    private String stemWordSafe(String word) {
        try {
            List<?> results = stemmer.stem(word);
            if (results != null && !results.isEmpty()) {
                Object result = results.get(0);
                // SAFAR returns StemmerAnalysis objects
                if (result instanceof safar.basic.morphology.stemmer.model.StemmerAnalysis) {
                    safar.basic.morphology.stemmer.model.StemmerAnalysis analysis = 
                        (safar.basic.morphology.stemmer.model.StemmerAnalysis) result;
                    String morpheme = analysis.getMorpheme();
                    return (morpheme != null && !morpheme.isEmpty()) ? morpheme : word;
                }
                // Fallback: try to parse toString() output
                String str = result.toString();
                if (str.contains("morpheme = ")) {
                    int start = str.indexOf("morpheme = ") + 11;
                    int end = str.indexOf("}", start);
                    if (end > start) {
                        return str.substring(start, end).trim();
                    }
                }
            }
        } catch (Exception e) {
            // If stemming fails, return original word
            System.err.println("⚠️ Stemming failed for: " + word);
        }
        return word;
    }

    /** Tokenize, normalize, remove stopwords, and stem */
    public List<String> preprocessText(String text) {
        return Arrays.stream(normalizeArabic(text).split("\\s+"))
                .filter(t -> !t.isBlank() && !stopwords.contains(t))
                .map(this::stemWordSafe)
                .collect(Collectors.toList());
    }

    /** Load corpus documents */
    public Map<String, List<String>> loadCorpus() {
        Map<String, List<String>> corpus = new HashMap<>();
        File folder = new File(CORPUS_PATH);
        Collection<File> files = FileUtils.listFiles(folder, new String[]{"txt"}, true);

        System.out.println("📄 Documents loaded: " + files.size());
        for (File file : files) {
            try {
                String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                corpus.put(file.getName(), preprocessText(content));
            } catch (IOException e) {
                System.out.println("⚠️ Could not read file: " + file.getName());
            }
        }
        return corpus;
    }
}
