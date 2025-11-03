# Quick Reference - Arabic Search Engine# Quick Reference Guide - Arabic Search Engine



Quick commands and essential information for using the Arabic Search Engine.## 🚀 Quick Commands



## Quick Start### Build & Run

```bash

### Run Desktop Application# Compile the project

```powershellmvn clean compile

mvn javafx:run

```# Build TF-IDF matrix only

mvn exec:java -Dexec.mainClass=com.myorg.search.TfIdfBuilder

### Run Command Line Interface

```powershell# Run full search engine (interactive mode)

mvn exec:java -Dexec.mainClass=com.myorg.search.Mainmvn exec:java -Dexec.mainClass=com.myorg.search.Main

```

# Run demo with predefined queries

### Open Web Demomvn exec:java -Dexec.mainClass=com.myorg.search.SearchEngineDemo

```powershell

start search-demo.html# Run search engine directly

```mvn exec:java -Dexec.mainClass=com.myorg.search.SearchEngine

```

## First-Time Setup

## 📁 Key Files

### Build Project

```powershell### Source Code

mvn clean compile- `Preprocessor.java` - Text preprocessing & SAFAR stemming

```- `TfIdfBuilder.java` - TF-IDF matrix generation

- `CosineSimilarity.java` - Similarity computation

### Generate TF-IDF Matrix- `SearchEngine.java` - Main search interface

```powershell- `Main.java` - Entry point with interactive mode

mvn exec:java -Dexec.mainClass=com.myorg.search.BuildMatrixOnly- `SearchEngineDemo.java` - Demo with test queries

```

### Resources

## Project Structure- `src/main/resources/stopwords.txt` - Stopwords list

- `src/main/resources/Corpus/` - Document collection

### Source Code- `src/main/resources/output/matrix.csv` - TF-IDF matrix

```

src/main/java/com/myorg/search/## 🔧 Module Overview

├── Main.java                  # CLI entry point

├── SearchEngine.java          # Core search functionality### CosineSimilarity.java

├── SearchEngineGUI.java       # JavaFX desktop interface**Purpose**: Compute similarity between query and documents

├── CosineSimilarity.java      # Ranking algorithm

├── TfIdfBuilder.java          # Matrix generation**Key Methods**:

├── Preprocessor.java          # Text preprocessing```java

└── BuildMatrixOnly.java       # Matrix builder utility// Compute similarity between two vectors

```double compute(Map<String, Double> vectorA, Map<String, Double> vectorB)



### Resources// Rank all documents against query

```List<Entry<String, Double>> rankDocuments(

src/main/resources/    Map<String, Double> queryVector,

├── stopwords.txt              # Arabic stopwords (800 terms)    Map<String, Map<String, Double>> documentVectors,

├── Corpus/                    # Document collection    int topK)

│   ├── agadir24/```

│   ├── Hespres/

│   ├── Medi1TV/**Algorithm**:

│   └── Voice of morocco/- Dot product: A · B = Σ(a_i × b_i)

└── output/- Magnitude: ||A|| = √(Σ(a_i²))

    └── matrix.csv             # TF-IDF matrix (generated)- Cosine: cos(θ) = (A · B) / (||A|| × ||B||)

```

### SearchEngine.java

## Example Queries**Purpose**: Main search interface with query processing



Try these Arabic queries:**Key Methods**:

```java

| Arabic | English | Category |// Initialize and load TF-IDF matrix

|--------|---------|----------|SearchEngine() throws IOException

| الاقتصاد | Economy | Economics |

| كرة القدم | Football | Sports |// Search for documents

| السياسة | Politics | Politics |List<SearchResult> search(String query, int topK)

| الثقافة | Culture | Culture |

| التعليم | Education | Society |// Interactive search mode

| الصحة | Health | Health |void interactiveSearch()

| التكنولوجيا | Technology | Tech |```

| البيئة | Environment | Environment |

**Features**:

## Common Commands- Loads pre-computed TF-IDF matrix

- Preprocesses queries using SAFAR

### Development- Computes query TF-IDF vector

```powershell- Ranks documents by cosine similarity

# Clean build- Returns top-K results

mvn clean

## 🔄 Workflow

# Compile code

mvn compile### 1. First Time Setup

```bash

# Package JAR# Compile

mvn packagemvn clean compile



# Run tests# Build TF-IDF matrix (required first time)

mvn testmvn exec:java -Dexec.mainClass=com.myorg.search.TfIdfBuilder

``````



### Rebuild Matrix### 2. Search Usage

```powershell```bash

# Delete old matrix# Interactive mode

Remove-Item src\main\resources\output\matrix.csvmvn exec:java -Dexec.mainClass=com.myorg.search.Main



# Generate new matrix# Or demo mode

mvn exec:java -Dexec.mainClass=com.myorg.search.BuildMatrixOnlymvn exec:java -Dexec.mainClass=com.myorg.search.SearchEngineDemo

``````



### Check Status### 3. Adding New Documents

```powershell```bash

# Check matrix exists# 1. Add .txt files to src/main/resources/Corpus/

dir src\main\resources\output\matrix.csv# 2. Rebuild matrix

mvn exec:java -Dexec.mainClass=com.myorg.search.TfIdfBuilder

# Count corpus documents# 3. Search again

dir src\main\resources\Corpus -Recurse -Filter *.txt | Measure-Objectmvn exec:java -Dexec.mainClass=com.myorg.search.Main

```

# Check Java version

java -version## 💡 Usage Examples



# Check Maven version### Programmatic Usage

mvn -version```java

```// Initialize

SearchEngine engine = new SearchEngine();

## Workflow

// Single query

### 1. Indexing Phase (One-time)List<SearchResult> results = engine.search("الاقتصاد المغربي", 5);

```

Corpus Documents// Process results

    ↓for (SearchResult result : results) {

Preprocessor (normalize + stem)    String doc = result.getDocumentName();

    ↓    double score = result.getScore();

TfIdfBuilder (calculate weights)    System.out.printf("%s: %.4f\n", doc, score);

    ↓}

matrix.csv (save to disk)```

```

### Interactive Mode

### 2. Search Phase (Runtime)```

```🔎 Query: كرة القدم

User Query📋 Top 5 Results:

    ↓   1. file3.txt (Score: 0.8234)

Preprocessor (normalize + stem)   2. file1.txt (Score: 0.7891)

    ↓   3. txt2.txt (Score: 0.6543)

SearchEngine (build query vector)   ...

    ↓```

CosineSimilarity (rank documents)

    ↓## 🎯 Sample Queries

Results (top-K documents)

```### Arabic Queries

- `الاقتصاد المغربي` - Economy

## Core Components- `كرة القدم` - Football

- `السياسة` - Politics  

### Preprocessor- `الثقافة` - Culture

- **Purpose**: Arabic text normalization and stemming- `الأخبار` - News

- **Features**: Character normalization, tokenization, stopword removal, SAFAR stemming- `الرياضة` - Sports

- **Output**: List of stemmed terms- `المجتمع` - Society



### TfIdfBuilder## 🐛 Common Issues

- **Purpose**: Generate term-document matrix

- **Algorithm**: TF-IDF vectorization### Issue: "SAFAR failed to initialize"

- **Output**: matrix.csv**Solution**: 

- Check `lib/SAFAR_v2.jar` exists

### CosineSimilarity- Verify Java 17+ installed

- **Purpose**: Document ranking- System.exit(1) will trigger - this is intentional

- **Algorithm**: Cosine similarity between vectors

- **Output**: Ranked document list### Issue: "No results found"

**Solution**:

### SearchEngine- Verify matrix.csv exists in `src/main/resources/output/`

- **Purpose**: Search orchestration- Rebuild matrix if corpus changed

- **Features**: Load matrix, process queries, rank results- Check query is in Arabic

- **Output**: List of SearchResult objects

### Issue: "Matrix file not found"

### SearchEngineGUI**Solution**:

- **Purpose**: Desktop interface```bash

- **Framework**: JavaFX 17mvn exec:java -Dexec.mainClass=com.myorg.search.TfIdfBuilder

- **Features**: Card-based results, interactive modals, full content viewer```



## Programmatic Usage### Issue: UTF-8 encoding problems

**Solution** (Windows):

### Basic Search```bash

```javachcp 65001

SearchEngine engine = new SearchEngine();```

List<SearchResult> results = engine.search("الاقتصاد", 5);

## 📊 Architecture

for (SearchResult result : results) {

    System.out.println(result.getDocumentName() + ": " + result.getScore());```

}Query (Arabic Text)

```    ↓

[Preprocessor]

### Interactive Mode├─ Normalize Arabic characters

```java├─ Tokenize

SearchEngine engine = new SearchEngine();├─ Remove stopwords

engine.interactiveSearch();└─ SAFAR ISRI stemming

```    ↓

Query Terms (List<String>)

## Troubleshooting    ↓

[SearchEngine.buildQueryVector]

### No GUI Opens├─ Compute TF

**Check**: JavaFX installed└─ Apply IDF (from corpus)

```powershell    ↓

mvn javafx:run -XQuery Vector (Map<String, Double>)

```    ↓

[CosineSimilarity.rankDocuments]

### No Results Found├─ Compute dot product with each document

**Check**: Matrix exists and query is Arabic├─ Compute magnitudes

```powershell└─ Calculate cosine similarity

dir src\main\resources\output\matrix.csv    ↓

```Ranked Results (List<SearchResult>)

    ↓

### SAFAR ErrorsDisplay Top-K Documents

**Check**: Libraries exist```

```powershell

dir lib\SAFAR_v2.jar## 🔍 Performance Tips

dir lib\jython-standalone-2.7.3.jar

```1. **Matrix Loading**: Done once at startup (~1-2s for 100 docs)

2. **Query Processing**: SAFAR stemming adds ~100-200ms per query

### Encoding Issues3. **Similarity Computation**: O(V×D) where V=vocabulary size, D=num docs

**Fix**: Set UTF-8 in PowerShell4. **Optimization**: Pre-filter documents with at least one query term

```powershell

chcp 65001## 📈 Statistics

```

After running, check:

### Memory Issues- Number of documents indexed

**Fix**: Increase heap size- Vocabulary size

```powershell- Matrix file size

$env:MAVEN_OPTS="-Xmx2g"- Average query processing time

mvn javafx:run

```---



## Performance Notes**For more details, see README.md**


- **Matrix Loading**: 1-2 seconds (one-time)
- **Query Processing**: 200-300ms per query
- **SAFAR Stemming**: ~100-200ms overhead
- **Similarity Computation**: O(V × N) where V = vocabulary, N = documents

## Key Algorithms

### TF-IDF Formula
```
TF(t,d) = count(t in d) / total_terms(d)
IDF(t) = log(total_docs / docs_with_term(t))
TF-IDF(t,d) = TF(t,d) × IDF(t)
```

### Cosine Similarity
```
cos(θ) = (A · B) / (||A|| × ||B||)

Where:
A · B   = Dot product
||A||   = Vector magnitude
```

## Documentation

- **README.md** - Complete project overview
- **ARCHITECTURE.md** - System design and structure
- **IMPLEMENTATION.md** - Technical implementation details
- **HOW-TO-RUN.md** - Detailed running instructions
- **LICENSE** - MIT License

## Features

- Full Arabic text processing
- SAFAR Light10 morphological stemming
- TF-IDF vectorization
- Cosine similarity ranking
- Modern desktop GUI (JavaFX)
- Web demo interface (HTML)
- Interactive result cards
- Full document content viewer
- Smart matrix caching

## Requirements

- Java 17+
- Maven 3.6+
- Windows (or macOS/Linux with adjusted commands)
- 2GB RAM minimum
- UTF-8 terminal support

## Quick Tips

1. **First run**: Matrix is generated automatically
2. **Subsequent runs**: Matrix is reused from disk
3. **Adding documents**: Regenerate matrix
4. **Query language**: Arabic text only
5. **Best interface**: JavaFX desktop GUI

---

For complete documentation, see **README.md**.
