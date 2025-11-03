package com.myorg.search;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.File;
import java.util.List;

/**
 * Professional Card-Based GUI for Arabic Search Engine
 */
public class SearchEngineGUI extends Application {

    private SearchEngine searchEngine;
    private TextField searchField;
    private VBox resultsContainer;
    private Label statusLabel;
    private ScrollPane scrollPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Arabic Search Engine");

        // Status label
        statusLabel = new Label("Loading search engine...");
        statusLabel.setStyle("-fx-text-fill: #5c6bc0; -fx-font-size: 13px;");

        // Create UI
        VBox root = createUI();
        
        // Check if matrix exists
        File matrixFile = new File("src/main/resources/output/matrix.csv");
        if (!matrixFile.exists()) {
            showMatrixWarning();
        }
        
        // Load search engine asynchronously
        new Thread(() -> {
            try {
                searchEngine = new SearchEngine();
                javafx.application.Platform.runLater(() -> {
                    statusLabel.setText("Ready to search");
                    statusLabel.setStyle("-fx-text-fill: #43a047; -fx-font-size: 13px;");
                });
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    statusLabel.setText("Error loading engine: " + e.getMessage());
                    statusLabel.setStyle("-fx-text-fill: #e53935; -fx-font-size: 13px;");
                });
                e.printStackTrace();
            }
        }).start();

        Scene scene = new Scene(root, 1100, 750);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    private VBox createUI() {
        VBox root = new VBox(18);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #fafafa;");

        // Header
        VBox header = createHeader();
        
        // Search box
        HBox searchBox = createSearchBox();

        // Status
        HBox statusBox = new HBox(statusLabel);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        statusBox.setPadding(new Insets(8, 0, 12, 0));

        // Results container with scroll
        resultsContainer = new VBox(16);
        resultsContainer.setPadding(new Insets(5));
        resultsContainer.setStyle("-fx-background-color: transparent;");
        
        scrollPane = new ScrollPane(resultsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #fafafa; -fx-background-color: transparent; " +
                           "-fx-border-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(header, searchBox, statusBox, scrollPane);
        return root;
    }

    private VBox createHeader() {
        VBox header = new VBox(8);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 10, 0));

        Label title = new Label("Arabic Search Engine");
        title.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 28));
        title.setStyle("-fx-text-fill: #2c3e50;");

        Label subtitle = new Label("Powered by TF-IDF and SAFAR stemming technology");
        subtitle.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13px;");

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    private HBox createSearchBox() {
        HBox searchBox = new HBox(12);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(16, 18, 16, 18));
        searchBox.setStyle("-fx-background-color: white; -fx-background-radius: 6; " +
                          "-fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 6;");

        // Search field
        searchField = new TextField();
        searchField.setPromptText("Enter your search query in Arabic...");
        searchField.setFont(Font.font("Segoe UI", 15));
        searchField.setStyle("-fx-border-color: transparent; -fx-background-color: transparent; " +
                            "-fx-text-fill: #2c3e50;");
        searchField.setPrefWidth(500);
        searchField.setOnAction(e -> performSearch());

        // Search button
        Button searchButton = new Button("Search");
        searchButton.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        searchButton.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; " +
                             "-fx-padding: 10 24; -fx-background-radius: 4; -fx-cursor: hand; " +
                             "-fx-border-color: transparent;");
        searchButton.setOnMouseEntered(e -> searchButton.setStyle(
            "-fx-background-color: #3f51b5; -fx-text-fill: white; " +
            "-fx-padding: 10 24; -fx-background-radius: 4; -fx-cursor: hand; -fx-border-color: transparent;"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle(
            "-fx-background-color: #5c6bc0; -fx-text-fill: white; " +
            "-fx-padding: 10 24; -fx-background-radius: 4; -fx-cursor: hand; -fx-border-color: transparent;"));
        searchButton.setOnAction(e -> performSearch());

        // Clear button
        Button clearButton = new Button("Clear");
        clearButton.setFont(Font.font("Segoe UI", 14));
        clearButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #7f8c8d; " +
                            "-fx-padding: 10 20; -fx-border-color: #bdc3c7; -fx-border-width: 1; " +
                            "-fx-border-radius: 4; -fx-background-radius: 4; -fx-cursor: hand;");
        clearButton.setOnMouseEntered(e -> clearButton.setStyle(
            "-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50; " +
            "-fx-padding: 10 20; -fx-border-color: #95a5a6; -fx-border-width: 1; " +
            "-fx-border-radius: 4; -fx-background-radius: 4; -fx-cursor: hand;"));
        clearButton.setOnMouseExited(e -> clearButton.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #7f8c8d; " +
            "-fx-padding: 10 20; -fx-border-color: #bdc3c7; -fx-border-width: 1; " +
            "-fx-border-radius: 4; -fx-background-radius: 4; -fx-cursor: hand;"));
        clearButton.setOnAction(e -> clearSearch());

        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchBox.getChildren().addAll(searchField, searchButton, clearButton);
        
        return searchBox;
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        
        if (query.isEmpty()) {
            showAlert("Empty Query", "Please enter a search query.");
            return;
        }

        if (searchEngine == null) {
            showAlert("Not Ready", "Search engine is still loading. Please wait a moment.");
            return;
        }

        statusLabel.setText("Searching for \"" + query + "\"...");
        statusLabel.setStyle("-fx-text-fill: #5c6bc0; -fx-font-size: 13px;");
        resultsContainer.getChildren().clear();

        // Perform search in background
        new Thread(() -> {
            try {
                List<SearchEngine.SearchResult> results = searchEngine.search(query, 10);
                
                javafx.application.Platform.runLater(() -> {
                    displayResultCards(query, results);
                });
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    showAlert("❌ Search Error", "Error: " + e.getMessage());
                    statusLabel.setText("❌ Search failed");
                    statusLabel.setStyle("-fx-text-fill: #f44336; -fx-font-size: 14px; -fx-font-weight: bold;");
                });
                e.printStackTrace();
            }
        }).start();
    }

    private void displayResultCards(String query, List<SearchEngine.SearchResult> results) {
        resultsContainer.getChildren().clear();

        if (results.isEmpty()) {
            VBox noResults = new VBox(15);
            noResults.setAlignment(Pos.CENTER);
            noResults.setPadding(new Insets(60));
            
            Label icon = new Label("🔍");
            icon.setStyle("-fx-font-size: 48px;");
            
            Label message = new Label("No results found");
            message.setStyle("-fx-font-size: 18px; -fx-text-fill: #7f8c8d; -fx-font-weight: 500;");
            
            Label suggestion = new Label("Try different keywords or check your spelling");
            suggestion.setStyle("-fx-font-size: 13px; -fx-text-fill: #95a5a6;");
            
            noResults.getChildren().addAll(icon, message, suggestion);
            resultsContainer.getChildren().add(noResults);
            
            statusLabel.setText("No documents matched your query");
            statusLabel.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 13px;");
            return;
        }

        // Create cards for each result
        int rank = 1;
        for (SearchEngine.SearchResult result : results) {
            VBox card = createResultCard(rank, result);
            resultsContainer.getChildren().add(card);
            rank++;
        }

        statusLabel.setText(String.format("Found %d result%s", results.size(), results.size() > 1 ? "s" : ""));
        statusLabel.setStyle("-fx-text-fill: #43a047; -fx-font-size: 13px;");
    }

    private VBox createResultCard(int rank, SearchEngine.SearchResult result) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18, 20, 18, 20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 6; " +
                     "-fx-border-color: #e8eaed; -fx-border-width: 1; -fx-border-radius: 6; " +
                     "-fx-cursor: hand;");

        // Hover effect
        card.setOnMouseEntered(e -> card.setStyle(
            "-fx-background-color: #f8f9fa; -fx-background-radius: 6; " +
            "-fx-border-color: #d0d3d6; -fx-border-width: 1; -fx-border-radius: 6; " +
            "-fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle(
            "-fx-background-color: white; -fx-background-radius: 6; " +
            "-fx-border-color: #e8eaed; -fx-border-width: 1; -fx-border-radius: 6; " +
            "-fx-cursor: hand;"));

        // Header row
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        // Rank indicator
        Label rankLabel = new Label(String.valueOf(rank));
        rankLabel.setStyle("-fx-background-color: #5c6bc0; " +
                          "-fx-text-fill: white; -fx-font-weight: 600; -fx-font-size: 14px; " +
                          "-fx-padding: 4 10; -fx-background-radius: 3;");

        // Document name
        Label docName = new Label(result.getDocumentName());
        docName.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        docName.setStyle("-fx-text-fill: #2c3e50;");

        // Score badge
        Label scoreLabel = new Label(String.format("%.1f%% match", result.getScore() * 100));
        scoreLabel.setStyle("-fx-background-color: #e8f5e9; -fx-text-fill: #2e7d32; " +
                           "-fx-font-weight: 500; -fx-padding: 4 10; -fx-background-radius: 3; " +
                           "-fx-font-size: 12px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(rankLabel, docName, spacer, scoreLabel);

        // Score bar
        ProgressBar scoreBar = new ProgressBar(result.getScore());
        scoreBar.setPrefHeight(4);
        scoreBar.setPrefWidth(Double.MAX_VALUE);
        scoreBar.setStyle("-fx-accent: #5c6bc0;");

        // File path
        Label pathLabel = new Label((result.getFilePath() != null ? result.getFilePath() : "Path unavailable"));
        pathLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 11px;");
        pathLabel.setWrapText(true);

        // Content preview
        String preview = result.getContent();
        if (preview != null && preview.length() > 180) {
            preview = preview.substring(0, 180) + "...";
        }
        Label contentPreview = new Label(preview != null ? preview : "No preview available");
        contentPreview.setStyle("-fx-text-fill: #34495e; -fx-font-size: 13px; -fx-line-spacing: 1.4;");
        contentPreview.setWrapText(true);
        contentPreview.setMaxHeight(60);

        // View details link
        Label viewLink = new Label("View full document →");
        viewLink.setStyle("-fx-text-fill: #5c6bc0; -fx-font-size: 13px; -fx-cursor: hand; " +
                         "-fx-underline: false;");
        viewLink.setOnMouseEntered(e -> viewLink.setStyle(
            "-fx-text-fill: #3f51b5; -fx-font-size: 13px; -fx-cursor: hand; -fx-underline: true;"));
        viewLink.setOnMouseExited(e -> viewLink.setStyle(
            "-fx-text-fill: #5c6bc0; -fx-font-size: 13px; -fx-cursor: hand; -fx-underline: false;"));
        viewLink.setOnMouseClicked(e -> showFullContent(result));

        card.getChildren().addAll(header, scoreBar, pathLabel, contentPreview, viewLink);

        // Click anywhere on card to view details
        card.setOnMouseClicked(e -> showFullContent(result));

        return card;
    }

    private void showFullContent(SearchEngine.SearchResult result) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(result.getDocumentName());

        VBox content = new VBox(18);
        content.setPadding(new Insets(28));
        content.setStyle("-fx-background-color: white;");

        // Title
        Label title = new Label(result.getDocumentName());
        title.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 20));
        title.setStyle("-fx-text-fill: #2c3e50;");

        // Score
        HBox scoreBox = new HBox(10);
        scoreBox.setAlignment(Pos.CENTER_LEFT);
        Label scoreLabel = new Label("Relevance:");
        scoreLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");
        Label scoreValue = new Label(String.format("%.1f%%", result.getScore() * 100));
        scoreValue.setStyle("-fx-font-size: 13px; -fx-text-fill: #2e7d32; -fx-font-weight: 600;");
        scoreBox.getChildren().addAll(scoreLabel, scoreValue);

        // Separator
        Separator separator1 = new Separator();
        separator1.setStyle("-fx-background-color: #e0e0e0;");

        // File path section
        Label pathLabel = new Label("Location");
        pathLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        pathLabel.setStyle("-fx-text-fill: #2c3e50;");

        TextArea pathArea = new TextArea(result.getFilePath() != null ? result.getFilePath() : "Path not available");
        pathArea.setEditable(false);
        pathArea.setWrapText(true);
        pathArea.setPrefRowCount(2);
        pathArea.setStyle("-fx-control-inner-background: #f8f9fa; -fx-font-family: 'Consolas', 'Courier New'; " +
                         "-fx-font-size: 11px; -fx-text-fill: #34495e; -fx-border-color: #e0e0e0; " +
                         "-fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4;");

        // Separator
        Separator separator2 = new Separator();
        separator2.setStyle("-fx-background-color: #e0e0e0;");

        // Content section
        Label contentLabel = new Label("Document Content");
        contentLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        contentLabel.setStyle("-fx-text-fill: #2c3e50;");

        TextArea contentArea = new TextArea(result.getContent() != null ? result.getContent() : "Content not available");
        contentArea.setEditable(false);
        contentArea.setWrapText(true);
        contentArea.setStyle("-fx-control-inner-background: white; -fx-font-size: 13px; " +
                            "-fx-text-fill: #2c3e50; -fx-border-color: #e0e0e0; " +
                            "-fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; " +
                            "-fx-font-family: 'Segoe UI';");
        VBox.setVgrow(contentArea, Priority.ALWAYS);

        // Close button
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; " +
                            "-fx-padding: 10 28; -fx-background-radius: 4; -fx-cursor: hand; " +
                            "-fx-font-size: 13px;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle(
            "-fx-background-color: #3f51b5; -fx-text-fill: white; " +
            "-fx-padding: 10 28; -fx-background-radius: 4; -fx-cursor: hand; -fx-font-size: 13px;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle(
            "-fx-background-color: #5c6bc0; -fx-text-fill: white; " +
            "-fx-padding: 10 28; -fx-background-radius: 4; -fx-cursor: hand; -fx-font-size: 13px;"));
        closeButton.setOnAction(e -> dialog.close());
        buttonBox.getChildren().add(closeButton);

        content.getChildren().addAll(title, scoreBox, separator1, pathLabel, pathArea, 
                                     separator2, contentLabel, contentArea, buttonBox);

        Scene scene = new Scene(content, 750, 650);
        dialog.setScene(scene);
        dialog.show();
    }

    private void clearSearch() {
        searchField.clear();
        resultsContainer.getChildren().clear();
        statusLabel.setText("Ready to search");
        statusLabel.setStyle("-fx-text-fill: #43a047; -fx-font-size: 13px;");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showMatrixWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Matrix Not Found");
        alert.setHeaderText("TF-IDF Matrix Missing");
        alert.setContentText("The TF-IDF matrix file is missing. Please build it first by running:\n\n" +
                            "mvn exec:java -Dexec.mainClass=com.myorg.search.BuildMatrixOnly");
        alert.showAndWait();
    }
}
