# How to Run - Arabic Search Engine# 🚀 Quick Start Guide - Arabic Search Engine



Complete instructions for running the Arabic Search Engine on Windows.## How to Run the Interfaces



## Prerequisites### 1️⃣ **JavaFX Desktop Application** (Recommended)



### Required Software```powershell

1. **Java Development Kit (JDK) 17 or higher**cd "c:\Users\hp\Desktop\s3 master\NLP & Text Mining\Projects\ArabicSearchEngine"

   - Download: https://adoptium.net/mvn javafx:run

   - Verify: `java -version````



2. **Apache Maven 3.6 or higher****Features:**

   - Download: https://maven.apache.org/download.cgi- ✅ Real search with SAFAR stemming

   - Verify: `mvn -version`- ✅ Shows file path for each result

- ✅ Shows content preview (first 500 characters)

3. **Windows Terminal or PowerShell** (recommended)- ✅ Visual relevance score bars

- ✅ Top 10 results

### Hardware Requirements- ✅ Clear button to reset

- **RAM**: At least 2GB available

- **Disk Space**: 500MB for project and dependencies**Screenshot of Results:**

- **CPU**: Any modern processor```

1. 📄 txt2.txt

## Quick Start   Relevance Score: 0.8523

   [██████████████████████████████████████████░░░░░░░░]

### Step 1: Navigate to Project Directory   📁 Path: C:\Users\hp\Desktop\...\Corpus\Hespres\مجتمع\txt2.txt

   ─────────────────────────────────────────

```powershell   📖 Content Preview:

cd "c:\Users\hp\Desktop\s3 master\NLP & Text Mining\Projects\ArabicSearchEngine"   النص العربي هنا... (first 500 characters)

```   ─────────────────────────────────────────

```

### Step 2: Build the Project

---

```powershell

mvn clean compile### 2️⃣ **HTML Demo Page** (Preview Only)

```

```powershell

**Expected Output**:start "c:\Users\hp\Desktop\s3 master\NLP & Text Mining\Projects\ArabicSearchEngine\search-demo.html"

``````

[INFO] BUILD SUCCESS

[INFO] Total time: 15.432 s**Note:** This is a front-end demo with sample data. To make it work with real search:

```- Option A: Create a Spring Boot REST API

- Option B: Use the JavaFX GUI instead (recommended)

### Step 3: Run the Desktop Application

---

```powershell

mvn javafx:run## 🔍 What the GUI Now Shows

```

For each search result, you'll see:

**Expected**: JavaFX window opens with search interface

1. **Rank** - Position in results (1, 2, 3...)

## Running Options2. **Document Name** - e.g., txt2.txt

3. **Relevance Score** - e.g., 0.8523 (0-1 scale)

### Option 1: Desktop GUI (Recommended)4. **Visual Score Bar** - █████████░░░░░

5. **📁 Full File Path** - Complete path to the .txt file

**Command**:6. **📖 Content Preview** - First 500 characters of the document

```powershell

mvn javafx:run---

```

## 📝 Example Queries to Try

**Features**:

- Modern card-based interfaceTry these Arabic queries in the GUI:

- Real-time search results

- Interactive result cards1. **الاقتصاد المغربي** - Moroccan economy

- Full document content viewer2. **كرة القدم** - Football/soccer

- Relevance score visualization3. **السياسة** - Politics

4. **الثقافة والفنون** - Culture and arts

**Usage**:5. **الأخبار** - News

1. Wait for "Ready to search" status6. **الرياضة** - Sports

2. Enter Arabic query in text field7. **التعليم** - Education

3. Click "Search" or press Enter8. **الصحة** - Health

4. Click any result card to view full content

5. Use "Clear" button to reset---



### Option 2: Command Line Interface## 🎯 How the Search Works



**Command**:```

```powershellYour Query: "الاقتصاد المغربي"

mvn exec:java -Dexec.mainClass=com.myorg.search.Main    ↓

```Preprocessing (SAFAR Light10 Stemmer)

    ↓

**Features**:Stemmed Terms: ["اقتصاد", "مغرب"]

- Interactive terminal-based search    ↓

- Enter queries at promptTF-IDF Vectorization

- View results in terminal    ↓

- Type 'exit' to quitCosine Similarity with all 105 documents

    ↓

**Example Session**:Top 10 Results with Paths & Content

``````

=== Arabic Search Engine ===

Using existing matrix from: src/main/resources/output/matrix.csv---



Enter query (or 'exit' to quit): الاقتصاد## 🛠️ Rebuild Matrix (if corpus changes)

Query terms after preprocessing: [اقتصاد]

If you add/remove documents in the corpus:

Top 10 Results:

1. txt2.txt (Score: 0.8523)```powershell

2. f3.txt (Score: 0.7841)cd "c:\Users\hp\Desktop\s3 master\NLP & Text Mining\Projects\ArabicSearchEngine"

3. file5.txt (Score: 0.6234)Remove-Item "src\main\resources\output\matrix.csv" -Force

...mvn exec:java "-Dexec.mainClass=com.myorg.search.BuildMatrixOnly"

```

Enter query (or 'exit' to quit): exit

```Then restart the GUI.



### Option 3: Web Demo---



**Command**:## 📊 Current Status

```powershell

start search-demo.html✅ **SAFAR_v2.jar** - Light10 stemmer working perfectly

```✅ **Preprocessing** - Normalization + stemming + stopwords

✅ **TF-IDF Matrix** - 2837 terms × 17 documents

**Note**: This opens a browser-based demo with sample data. It is not connected to the actual search engine backend.✅ **Search Engine** - Cosine similarity ranking

✅ **JavaFX GUI** - Desktop application with file details

**Features**:✅ **Query Preprocessing** - Same pipeline as corpus

- Responsive web interface

- Sample Arabic documents---

- Interactive result cards

- Modal content viewer## 🎨 GUI Controls



## First-Time Setup- **Search Box** - Enter Arabic text

- **🔍 Search Button** - Click or press Enter

### Generate TF-IDF Matrix- **✖ Clear Button** - Reset search

- **Results Area** - Scrollable, shows all details

**Only needed once** (or when corpus changes):- **Status Bar** - Shows search progress



```powershell---

mvn exec:java -Dexec.mainClass=com.myorg.search.BuildMatrixOnly

```## 🔧 Troubleshooting



**Expected Output**:### GUI doesn't open

``````powershell

Building TF-IDF matrix...# Install JavaFX if needed

Processing documents...mvn clean compile

Calculating term frequencies...mvn javafx:run

Calculating IDF scores...```

Generating TF-IDF matrix...

Matrix saved to: src/main/resources/output/matrix.csv### No results found

Total documents: 105- Try simpler queries (single words)

Vocabulary size: 2837 terms- Check matrix.csv exists

Time taken: 8.4 seconds- Verify corpus has documents

```

### Content not showing

**When to Regenerate**:- Check file paths in Corpus directory

- First time running the project- Ensure files are .txt format

- After adding/removing documents in Corpus- Verify UTF-8 encoding

- After modifying stopwords.txt

- If matrix.csv is deleted or corrupted---



## Advanced Usage## 📌 File Structure



### Build Executable JAR```

ArabicSearchEngine/

```powershell├── src/main/java/com/myorg/search/

mvn clean package│   ├── SearchEngineGUI.java      ← JavaFX Interface

```│   ├── SearchEngine.java          ← Core search logic

│   ├── Preprocessor.java          ← SAFAR stemming

**Output**: `target/ArabicSearchEngine-1.0-SNAPSHOT.jar`│   └── ...

├── src/main/resources/

**Run JAR** (CLI mode):│   ├── Corpus/                    ← Your documents

```powershell│   └── output/matrix.csv          ← TF-IDF matrix

java -cp "target\ArabicSearchEngine-1.0-SNAPSHOT.jar;lib\*" com.myorg.search.Main├── search-demo.html               ← HTML demo

```└── pom.xml

```

### Run Tests

---

```powershell

mvn test## 🚀 Next Steps

```

You can now:

### Clean Build1. ✅ Search through Arabic documents

2. ✅ See file paths and content

```powershell3. ✅ Use the beautiful GUI

mvn clean4. 🔜 Create Spring Boot REST API (optional)

```5. 🔜 Deploy as web service (optional)



Removes all compiled files and generated resources.---



## Configuration**Enjoy your Arabic Search Engine! 🎉**


### Corpus Location

Place your Arabic text documents here:
```
src/main/resources/Corpus/
```

The engine will recursively scan all subdirectories for `.txt` files.

### Stopwords

Edit the stopword list:
```
src/main/resources/stopwords.txt
```

Format: One word per line (Arabic text)

### Output Directory

TF-IDF matrix is saved here:
```
src/main/resources/output/matrix.csv
```

## Troubleshooting

### Issue: "No module 'javafx.controls'"

**Solution**: Ensure JavaFX is in your PATH or use Maven command:
```powershell
mvn javafx:run
```

### Issue: "Matrix file not found"

**Solution**: Generate the matrix:
```powershell
mvn exec:java -Dexec.mainClass=com.myorg.search.BuildMatrixOnly
```

### Issue: "SAFAR stemmer error"

**Symptoms**: Errors mentioning Python or Jython

**Solution**: Verify SAFAR library exists:
```powershell
dir lib\SAFAR_v2.jar
dir lib\jython-standalone-2.7.3.jar
```

If missing, ensure libraries are in the `lib/` directory.

### Issue: "OutOfMemoryError"

**Solution**: Increase heap size:
```powershell
$env:MAVEN_OPTS="-Xmx2g"
mvn javafx:run
```

### Issue: GUI doesn't open

**Check JavaFX installation**:
```powershell
mvn javafx:run -X
```

Look for errors in detailed output.

### Issue: No search results

**Possible Causes**:
1. Matrix not generated → Run BuildMatrixOnly
2. Query too specific → Try simpler queries
3. Corpus empty → Verify documents in Corpus/

**Debug**:
```powershell
# Check matrix exists
dir src\main\resources\output\matrix.csv

# Check corpus documents
dir src\main\resources\Corpus -Recurse -Filter *.txt | Measure-Object
```

### Issue: Encoding problems (garbled Arabic text)

**Solution**: Ensure UTF-8 encoding:
- Windows Terminal: Settings → Profiles → Defaults → Font (use "Cascadia Code")
- PowerShell: `chcp 65001` (set UTF-8 code page)

## Example Queries

Try these Arabic queries:

| Query | Meaning | Expected Results |
|-------|---------|------------------|
| الاقتصاد | Economy | Economic articles |
| كرة القدم | Football | Sports articles |
| السياسة | Politics | Political news |
| الثقافة والفنون | Culture and arts | Cultural content |
| التعليم | Education | Educational topics |
| الصحة | Health | Health articles |
| التكنولوجيا | Technology | Tech news |
| البيئة | Environment | Environmental topics |

## Performance Tips

### Speed Up Matrix Generation
- Use SSD for project directory
- Close unnecessary applications
- Increase heap size: `-Xmx4g`

### Speed Up Searches
- Keep matrix file on fast storage
- Use simple queries (2-3 words)
- Close other Java applications

## Development Workflow

### Typical Session

1. **Open terminal** in project directory
2. **Compile** if code changed: `mvn clean compile`
3. **Regenerate matrix** if corpus changed: `mvn exec:java -Dexec.mainClass=com.myorg.search.BuildMatrixOnly`
4. **Run GUI**: `mvn javafx:run`
5. **Test searches** with various queries
6. **Close** when done

### Adding New Documents

1. Add `.txt` files to `src/main/resources/Corpus/`
2. Delete old matrix: `Remove-Item src\main\resources\output\matrix.csv`
3. Regenerate: `mvn exec:java -Dexec.mainClass=com.myorg.search.BuildMatrixOnly`
4. Restart application

## System Information

### Project Structure
```
ArabicSearchEngine/
├── src/main/
│   ├── java/com/myorg/search/     # Java source files
│   └── resources/
│       ├── Corpus/                 # Input: Documents
│       ├── stopwords.txt           # Input: Stopwords
│       └── output/matrix.csv       # Generated: TF-IDF
├── lib/                            # External JARs (SAFAR)
├── pom.xml                         # Maven configuration
└── search-demo.html                # Web demo
```

### Maven Goals
- `mvn clean`: Remove compiled files
- `mvn compile`: Compile Java sources
- `mvn package`: Create JAR file
- `mvn test`: Run unit tests
- `mvn javafx:run`: Launch JavaFX GUI
- `mvn exec:java`: Run specific main class

## Support

### Getting Help

1. **Check documentation**:
   - README.md - Project overview
   - ARCHITECTURE.md - System design
   - IMPLEMENTATION.md - Technical details

2. **Check logs**:
   - Look for error messages in terminal
   - Check Maven output for build issues

3. **Verify setup**:
   - Java version: `java -version`
   - Maven version: `mvn -version`
   - File existence: `dir lib\*.jar`

### Common Commands Reference

```powershell
# Navigate to project
cd "c:\Users\hp\Desktop\s3 master\NLP & Text Mining\Projects\ArabicSearchEngine"

# Build project
mvn clean compile

# Generate matrix
mvn exec:java -Dexec.mainClass=com.myorg.search.BuildMatrixOnly

# Run GUI
mvn javafx:run

# Run CLI
mvn exec:java -Dexec.mainClass=com.myorg.search.Main

# Run tests
mvn test

# Package JAR
mvn clean package

# Open web demo
start search-demo.html
```

---

You are now ready to use the Arabic Search Engine! Start with the GUI for the best experience.
