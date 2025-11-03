# System Architecture - Arabic Search Engine# Class Architecture - Arabic Search Engine



## Overview## 📐 Class Diagram



The Arabic Search Engine is designed as a modular information retrieval system with clear separation of concerns. The architecture follows a pipeline pattern for document processing and a client-server pattern for search operations.```

┌─────────────────────────────────────────────────────────────────┐

## High-Level Architecture│                           Main.java                             │

│                                                                 │

```│  + main(String[] args)                                         │

┌─────────────────────────────────────────────────────────────┐│    ├─ Creates TfIdfBuilder                                     │

│                      User Interface Layer                    ││    ├─ Creates SearchEngine                                     │

│  ┌──────────────────┐         ┌──────────────────┐         ││    └─ Launches interactive search                             │

│  │  SearchEngineGUI │         │  search-demo.html│         │└─────────────────────────────────────────────────────────────────┘

│  │    (JavaFX)      │         │   (Web Demo)     │         │                    │                    │

│  └──────────────────┘         └──────────────────┘         │                    │                    │

└─────────────────────────────────────────────────────────────┘        ┌───────────┘                    └───────────┐

                           │        ↓                                            ↓

                           ↓┌─────────────────────┐                  ┌─────────────────────────┐

┌─────────────────────────────────────────────────────────────┐│   TfIdfBuilder      │                  │    SearchEngine         │

│                     Search Engine Layer                      │├─────────────────────┤                  ├─────────────────────────┤

│  ┌──────────────────────────────────────────────────────┐  ││ - Preprocessor      │                  │ - documentVectors       │

│  │                 SearchEngine.java                     │  ││                     │                  │ - idfScores             │

│  │  - Query processing                                   │  ││ + runPipeline()     │                  │ - vocabulary            │

│  │  - Result ranking                                     │  ││ - computeTF()       │                  │ - Preprocessor          │

│  │  - Matrix management                                  │  ││ - computeIDF()      │                  │ - CosineSimilarity      │

│  └──────────────────────────────────────────────────────┘  ││ - computeTFIDF()    │                  │                         │

└─────────────────────────────────────────────────────────────┘│ - saveCSV()         │                  │ + search(query, topK)   │

                           │└─────────────────────┘                  │ + interactiveSearch()   │

            ┌──────────────┴──────────────┐        │                                │ - loadMatrix()          │

            ↓                             ↓        │                                │ - buildQueryVector()    │

┌───────────────────────┐      ┌─────────────────────┐        │ uses                           │ - displayResults()      │

│   Preprocessor.java   │      │ CosineSimilarity    │        ↓                                └─────────────────────────┘

│  - Normalization      │      │  - Similarity       │┌─────────────────────┐                          │       │

│  - Tokenization       │      │  - Ranking          ││   Preprocessor      │←─────────────────────────┘       │

│  - SAFAR stemming     │      │  - Score calc       │├─────────────────────┤          uses                    │

│  - Stopword removal   │      └─────────────────────┘│ - stopwords         │                                  │ uses

└───────────────────────┘│ - stemmer (SAFAR)   │                                  ↓

            ││                     │                  ┌─────────────────────────┐

            ↓│ + loadCorpus()      │                  │  CosineSimilarity       │

┌─────────────────────────────────────────────────────────────┐│ + preprocessText()  │                  ├─────────────────────────┤

│                   Indexing Layer                             ││ - normalizeArabic() │                  │                         │

│  ┌──────────────────────────────────────────────────────┐  ││ - stemWordSafe()    │                  │ + compute(vecA, vecB)   │

│  │              TfIdfBuilder.java                        │  │└─────────────────────┘                  │ + rankDocuments()       │

│  │  - Corpus processing                                  │  │                                         │ - computeDotProduct()   │

│  │  - TF calculation                                     │  │                                         │ - computeMagnitude()    │

│  │  - IDF calculation                                    │  │                                         └─────────────────────────┘

│  │  - Matrix generation                                  │  │

│  └──────────────────────────────────────────────────────┘  │        ┌─────────────────────────────────────────┐

└─────────────────────────────────────────────────────────────┘        │         SearchEngineDemo                │

                           │        │                                         │

                           ↓        │  + main(String[] args)                 │

┌─────────────────────────────────────────────────────────────┐        │    └─ Runs predefined test queries     │

│                      Data Layer                              │        └─────────────────────────────────────────┘

│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │                    │

│  │   Corpus/    │  │ stopwords.txt│  │  matrix.csv  │     │                    │ uses

│  │  (Documents) │  │              │  │  (TF-IDF)    │     │                    ↓

│  └──────────────┘  └──────────────┘  └──────────────┘     │        ┌─────────────────────────┐

└─────────────────────────────────────────────────────────────┘        │    SearchEngine         │

```        └─────────────────────────┘

```

## Component Responsibilities

## 🔄 Data Flow

### 1. User Interface Layer

```

#### SearchEngineGUI.java (JavaFX Desktop)1. CORPUS PROCESSING (TfIdfBuilder)

- **Purpose**: Provides interactive desktop application   ────────────────────────────────

- **Features**:   Corpus Files (.txt)

  - Search input with validation        ↓

  - Real-time result display   Preprocessor.loadCorpus()

  - Card-based result visualization        ↓

  - Full document content viewer   Preprocessed Documents

  - Progress indicators   (Map<String, List<String>>)

- **Dependencies**: JavaFX 17, SearchEngine        ↓

   TfIdfBuilder.computeTF()

#### search-demo.html (Web Interface)        ↓

- **Purpose**: Demonstrates search functionality in browser   Term Frequencies

- **Features**:   (Map<String, Map<String, Double>>)

  - Responsive design        ↓

  - Sample data demonstration   TfIdfBuilder.computeIDF()

  - Interactive result cards        ↓

  - Modal content viewer   IDF Scores

- **Note**: Uses static sample data (not connected to backend)   (Map<String, Double>)

        ↓

### 2. Search Engine Layer   TfIdfBuilder.computeTFIDF()

        ↓

#### SearchEngine.java   TF-IDF Matrix

- **Purpose**: Core search orchestration        ↓

- **Responsibilities**:   TfIdfBuilder.saveCSV()

  - Load and manage TF-IDF matrix        ↓

  - Process user queries   matrix.csv

  - Build query vectors

  - Coordinate ranking2. SEARCH PROCESSING (SearchEngine)

  - Return sorted results   ─────────────────────────────────

- **Key Methods**:   User Query (Arabic text)

  - `search(query, topK)`: Main search API        ↓

  - `loadMatrix(path)`: Initialize from disk   Preprocessor.preprocessText()

  - `buildQueryVector(terms)`: Query vectorization        ↓

- **Data Structures**:   Query Terms (List<String>)

  - `documentVectors`: Map<String, Map<String, Double>>        ↓

  - `idfScores`: Map<String, Double>   SearchEngine.buildQueryVector()

  - `vocabulary`: Set<String>        ↓

   Query TF-IDF Vector

### 3. Processing Components   (Map<String, Double>)

        ↓

#### Preprocessor.java   CosineSimilarity.rankDocuments()

- **Purpose**: Arabic text normalization and stemming        ↓

- **Processing Pipeline**:   Ranked Documents

  1. Arabic normalization (character standardization)   (List<Entry<String, Double>>)

  2. Tokenization (split into words)        ↓

  3. Stopword removal (filter common words)   Search Results

  4. Morphological stemming (SAFAR Light10)   (List<SearchResult>)

- **Key Methods**:        ↓

  - `loadCorpus()`: Read all documents   Display to User

  - `preprocessText(text)`: Process single text```

  - `normalizeArabic(text)`: Character normalization

  - `stemWordSafe(word)`: SAFAR stemming with fallback## 📦 Module Responsibilities

- **External Dependencies**: SAFAR v2, Jython

### Preprocessor

#### CosineSimilarity.java**Role**: Arabic text normalization and stemming

- **Purpose**: Document similarity computation- Input: Raw Arabic text

- **Algorithm**: Cosine similarity between vectors- Output: List of stemmed terms

- **Formula**: `cos(θ) = (A·B) / (||A|| × ||B||)`- Dependencies: SAFAR_v2.jar, stopwords.txt

- **Key Methods**:

  - `compute(vecA, vecB)`: Calculate similarity### TfIdfBuilder

  - `rankDocuments(query, docs, k)`: Sort by relevance**Role**: Document vectorization

- **Optimization**: Sparse vector handling, early termination- Input: Corpus documents

- Output: TF-IDF matrix (CSV)

### 4. Indexing Layer- Dependencies: Preprocessor



#### TfIdfBuilder.java### CosineSimilarity

- **Purpose**: Generate TF-IDF matrix from corpus**Role**: Similarity computation

- **Process**:- Input: Two vectors (query, document)

  1. Load all documents via Preprocessor- Output: Similarity score [0, 1]

  2. Calculate term frequencies (TF)- Dependencies: None (pure math)

  3. Calculate inverse document frequencies (IDF)

  4. Compute TF-IDF scores### SearchEngine

  5. Persist to CSV file**Role**: Search orchestration

- **Key Methods**:- Input: User query

  - `runPipeline()`: Execute full pipeline- Output: Ranked search results

  - `computeTF()`: Term frequency calculation- Dependencies: Preprocessor, CosineSimilarity, matrix.csv

  - `computeIDF()`: IDF calculation

  - `computeTFIDF()`: Final matrix generation### Main

  - `saveCSV()`: Persist to disk**Role**: Application entry point

- **Output**: matrix.csv (terms × documents)- Coordinates: TfIdfBuilder → SearchEngine

- Provides: Interactive search interface

## Data Flow

### SearchEngineDemo

### Indexing Phase (One-time)**Role**: Testing and demonstration

- Shows: Predefined query examples

```- Purpose: Development and QA

Corpus Documents (.txt files)

        ↓## 🎯 Key Data Structures

Preprocessor.loadCorpus()

        ↓```java

Normalized & Stemmed Documents// Document Vector (sparse)

  Map<String, List<String>>Map<String, Double>

        ↓    "term1" → 0.234

TfIdfBuilder.computeTF()    "term2" → 0.567

        ↓    "term3" → 0.123

Term Frequencies

  Map<String, Map<String, Double>>// All Document Vectors

        ↓Map<String, Map<String, Double>>

TfIdfBuilder.computeIDF()    "doc1.txt" → {"term1": 0.234, "term2": 0.567}

        ↓    "doc2.txt" → {"term1": 0.123, "term3": 0.789}

IDF Scores

  Map<String, Double>// IDF Scores

        ↓Map<String, Double>

TfIdfBuilder.computeTFIDF()    "term1" → 2.345

        ↓    "term2" → 3.456

TF-IDF Matrix    "term3" → 1.234

        ↓

TfIdfBuilder.saveCSV()// Search Results

        ↓List<SearchResult>

matrix.csv (persisted to disk)    SearchResult{"doc1.txt", 0.8234}

```    SearchResult{"doc3.txt", 0.7891}

    SearchResult{"doc2.txt", 0.6543}

### Search Phase (Runtime)```



```## 🔐 Class Relationships

User Query (Arabic text)

        ↓1. **Main** orchestrates **TfIdfBuilder** and **SearchEngine**

Preprocessor.preprocessText()2. **TfIdfBuilder** depends on **Preprocessor**

        ↓3. **SearchEngine** depends on **Preprocessor** and **CosineSimilarity**

Stemmed Query Terms4. **Preprocessor** depends on **SAFAR** (external library)

  List<String>5. **SearchEngineDemo** uses **SearchEngine** for testing

        ↓

SearchEngine.buildQueryVector()## 💾 File Dependencies

        ↓

Query TF-IDF Vector```

  Map<String, Double>Preprocessor.java

        ↓    ├─ reads: stopwords.txt

CosineSimilarity.rankDocuments()    ├─ reads: Corpus/**/*.txt

        ↓    └─ uses: SAFAR_v2.jar

Similarity Scores

  Map<String, Double>TfIdfBuilder.java

        ↓    ├─ uses: Preprocessor.java

Sorted Results (top-K)    └─ writes: output/matrix.csv

  List<SearchResult>

        ↓SearchEngine.java

Display to User    ├─ uses: Preprocessor.java

```    ├─ uses: CosineSimilarity.java

    └─ reads: output/matrix.csv

## Key Data Structures

CosineSimilarity.java

### Document Vector (Sparse)    └─ (no external dependencies)

```java```

Map<String, Double>

  "اقتصاد" → 0.234## 🎨 Design Patterns Used

  "مغرب"   → 0.567

  "سياسة"  → 0.1231. **Singleton-like**: SearchEngine loads matrix once

```2. **Strategy**: CosineSimilarity implements ranking algorithm

3. **Factory**: Preprocessor creates SAFAR stemmer

### Document Collection4. **Builder**: TfIdfBuilder constructs matrix incrementally

```java5. **DTO**: SearchResult encapsulates result data

Map<String, Map<String, Double>>

  "doc1.txt" → {"اقتصاد": 0.234, "مغرب": 0.567}---

  "doc2.txt" → {"اقتصاد": 0.123, "سياسة": 0.789}

```**This architecture ensures modularity, testability, and maintainability.**


### IDF Scores
```java
Map<String, Double>
  "اقتصاد" → 2.345
  "مغرب"   → 3.456
  "سياسة"  → 1.234
```

### Search Results
```java
List<SearchResult>
  [0] → SearchResult("doc1.txt", 0.8234)
  [1] → SearchResult("doc3.txt", 0.7891)
  [2] → SearchResult("doc2.txt", 0.6543)
```

## Design Patterns

### 1. Builder Pattern
**TfIdfBuilder** constructs the TF-IDF matrix incrementally through multiple stages (TF → IDF → TF-IDF).

### 2. Strategy Pattern
**CosineSimilarity** implements a pluggable ranking algorithm. Alternative algorithms (BM25, language models) could be swapped in.

### 3. Facade Pattern
**SearchEngine** provides a simple interface hiding the complexity of preprocessing, vectorization, and ranking.

### 4. Data Transfer Object
**SearchResult** encapsulates result data (document name, score) for clean transfer between layers.

### 5. Singleton-like Loading
**SearchEngine** loads the matrix once at initialization and reuses it for all searches.

## Dependencies

### External Libraries
- **SAFAR v2** (SAFAR_v2.jar): Arabic morphological analysis
- **Jython 2.7.3** (jython-standalone-2.7.3.jar): Python runtime for SAFAR
- **Apache Commons IO 2.15**: File utilities
- **JavaFX 17**: Desktop GUI framework

### Internal Dependencies
```
Main.java
  └─→ TfIdfBuilder
  └─→ SearchEngine

TfIdfBuilder
  └─→ Preprocessor

SearchEngine
  └─→ Preprocessor
  └─→ CosineSimilarity

SearchEngineGUI
  └─→ SearchEngine

Preprocessor
  └─→ SAFAR (external)
  └─→ stopwords.txt
```

## File System Structure

```
src/main/resources/
  ├── Corpus/           # Input: Document collection
  │   ├── agadir24/
  │   ├── Hespres/
  │   ├── Medi1TV/
  │   └── Voice of morocco/
  ├── stopwords.txt     # Input: Arabic stopwords (800 terms)
  └── output/
      └── matrix.csv    # Output: TF-IDF matrix (generated)
```

## Performance Characteristics

### Time Complexity
- **Indexing**: O(N × M) where N = documents, M = avg terms/doc
- **Query Processing**: O(V) where V = vocabulary size
- **Similarity**: O(V × N) for all documents
- **Top-K Selection**: O(N log K) with priority queue

### Space Complexity
- **Matrix Storage**: O(V × N) sparse representation
- **Runtime Memory**: O(V + N) for maps and sets
- **Query Vector**: O(Q) where Q = query terms

### Optimization Strategies
1. **Sparse Vectors**: Store only non-zero TF-IDF values
2. **Matrix Caching**: Load from disk once, reuse in memory
3. **Early Termination**: Stop processing if similarity too low
4. **Top-K Heap**: Efficient result selection

## Scalability Considerations

### Current Capacity
- Documents: Hundreds to low thousands
- Vocabulary: Tens of thousands of terms
- Query Response: < 1 second typical

### Bottlenecks
1. **SAFAR Stemming**: ~100-200ms per query
2. **Matrix Loading**: ~1-2s on startup
3. **All-document Similarity**: O(N) per query

### Future Improvements
- Inverted index for faster retrieval
- Approximate nearest neighbors (ANN)
- Distributed processing for large corpora
- Caching of frequent queries

## Error Handling

### Preprocessor
- SAFAR failures return unstemmed word
- Missing stopwords file: empty set
- Empty documents: skip processing

### SearchEngine
- Missing matrix file: throw exception
- Malformed CSV: skip row, log warning
- Empty query: return empty results

### GUI
- Search errors: display error message
- Matrix not found: show rebuild instructions
- Empty results: friendly "no results" message

## Security Considerations

- Input validation on queries (prevent injection)
- File path sanitization (prevent directory traversal)
- Resource limits (prevent DoS via huge queries)
- UTF-8 encoding validation

## Testing Strategy

### Unit Tests
- Preprocessor: normalization, stemming
- CosineSimilarity: various vector combinations
- TfIdfBuilder: TF/IDF calculations

### Integration Tests
- End-to-end search pipeline
- Matrix generation and loading
- GUI interaction flows

### Test Data
- Sample Arabic documents
- Known stemming examples
- Ground truth relevance judgments

---

This architecture ensures maintainability, testability, and extensibility for future enhancements to the Arabic Search Engine.
