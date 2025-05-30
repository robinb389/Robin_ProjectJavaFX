package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Noted;
import model.Rating;
import model.Song;
import java.sql.*;
import java.util.Optional;

/**
 * Controller class for handling user interaction in the Noted JavaFX application.
 * It manages login, signup, song search, and song rating features.
 * 
 * Author: Robinbir Singh  
 * Version: 1.0
 */
public class Controller {
    @FXML private TextField loginUsernameField;
    @FXML private PasswordField loginPasswordField;
    @FXML private TextField signupUsernameField;
    @FXML private PasswordField signupPasswordField;
    @FXML private TextField signupEmailField;
    @FXML private Label loginStatusLabel;
    @FXML private Label signupStatusLabel;
    @FXML private TableView<Song> songTable;
    @FXML private TableColumn<Song, String> titleColumn;
    @FXML private TableColumn<Song, String> artistColumn;
    @FXML private TableColumn<Song, String> albumColumn;
    @FXML private TextField searchField;
    @FXML private ListView<String> artistList;
    @FXML private ListView<String> genreList;
    @FXML private VBox loginPane;
    @FXML private VBox signUpPane;

    private int currentUserId;
    private Song selectedSong;

    /**
     * Initializes the controller. Sets up table columns, loads songs/artists/genres, 
     * and defines event handlers for user interaction like double-clicking a song.
     */
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));

        loadSongs();
        loadArtists();
        loadGenres();

        // Filter songs by selected artist
        artistList.setOnMouseClicked(event -> {
            String selectedArtist = artistList.getSelectionModel().getSelectedItem();
            if (selectedArtist != null) {
                loadSongsByArtist(selectedArtist);
            }
        });

        // Filter songs by selected genre
        genreList.setOnMouseClicked(event -> {
            String selectedGenre = genreList.getSelectionModel().getSelectedItem();
            if (selectedGenre != null) {
                loadSongsByGenre(selectedGenre);
            }
        });

        // Show song details and ratings on double-click
        songTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && songTable.getSelectionModel().getSelectedItem() != null) {
                Song selected = songTable.getSelectionModel().getSelectedItem();

                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:noted.db")) {
                    String ratingsQuery = "SELECT r.rating, r.review_text " + 
                            "FROM ratings r " + 
                            "WHERE r.song_id = ?";

                    PreparedStatement ratingStmt = conn.prepareStatement(ratingsQuery);
                    ratingStmt.setInt(1, selected.getSong_id());
                    ResultSet ratingRs = ratingStmt.executeQuery();

                    StringBuilder info = new StringBuilder();
                    info.append("Title: ").append(selected.getTitle()).append("\n");
                    info.append("Artist: ").append(selected.getArtist()).append("\n");
                    info.append("Album: ").append(selected.getAlbum()).append("\n\n");
                    info.append("All Ratings & Reviews:\n");

                    boolean hasRatings = false;
                    while (ratingRs.next()) {
                        hasRatings = true;
                        int rating = ratingRs.getInt("rating");
                        String review = ratingRs.getString("review_text");

                        info.append("Rating: ").append(rating).append("\n")
                            .append("Review: ").append((review == null || review.isEmpty()) ? "No review" : review)
                            .append("\n----------------------\n");
                    }

                    if (!hasRatings) {
                        info.append("No ratings available.");
                    }

                    TextArea textArea = new TextArea(info.toString());
                    textArea.setWrapText(true);
                    textArea.setEditable(false);
                    textArea.setPrefSize(400, 300); // Ensure the size is large enough
                    textArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);  // Allow resizing if needed

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Song Info & Ratings");
                    alert.setHeaderText("Details for: " + selected.getTitle());
                    alert.getDialogPane().setContent(textArea);
                    alert.showAndWait();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Searches songs in the database by title or artist using the text in searchField.
     * Populates the song table with matching results.
     */
    public void handleSearch() {
        String keyword = searchField.getText();
        ObservableList<Song> results = FXCollections.observableArrayList();
        String query = "SELECT song_id, title, artist, album FROM song WHERE title LIKE ? OR artist LIKE ?";

        try (Connection conn = Noted.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(new Song(
                    rs.getInt("song_id"),
                    rs.getString("title"),
                    rs.getString("artist"),
                    rs.getString("album"),
                    0,
                    null
                ));
            }
            songTable.setItems(results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all songs from the database and displays them in the song table.
     */
    private void loadSongs() {
        ObservableList<Song> songs = FXCollections.observableArrayList();
        String query = "SELECT song_id, title, artist, album FROM song";

        try (Connection conn = Noted.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                songs.add(new Song(
                    rs.getInt("song_id"),
                    rs.getString("title"),
                    rs.getString("artist"),
                    rs.getString("album"),
                    0,
                    null
                ));
            }
            songTable.setItems(songs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads songs by a specific artist and displays them in the song table.
     * 
     * @param artist The name of the artist to filter songs by.
     */
    
    private void loadSongsByArtist(String artist) {
        ObservableList<Song> songs = FXCollections.observableArrayList();
        String query = "SELECT song_id, title, artist, album FROM song WHERE artist = ?";

        try (Connection conn = Noted.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, artist);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                songs.add(new Song(
                    rs.getInt("song_id"),
                    rs.getString("title"),
                    rs.getString("artist"),
                    rs.getString("album"),
                    0,
                    null
                ));
            }
            songTable.setItems(songs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads songs by a specific genre and displays them in the song table.
     * 
     * @param genre The name of the genre to filter songs by.
     */
    
    private void loadSongsByGenre(String genre) {
        ObservableList<Song> songs = FXCollections.observableArrayList();
        String query = "SELECT s.song_id, s.title, s.artist, s.album FROM song s JOIN genre g ON s.genre_id = g.genre_id WHERE g.name = ?";

        try (Connection conn = Noted.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                songs.add(new Song(
                    rs.getInt("song_id"),
                    rs.getString("title"),
                    rs.getString("artist"),
                    rs.getString("album"),
                    0,
                    null
                ));
            }
            songTable.setItems(songs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads all unique artists from the database and populates the artist list.
     */
    
    private void loadArtists() {
        ObservableList<String> artists = FXCollections.observableArrayList();
        String query = "SELECT DISTINCT artist FROM song ORDER BY artist";

        try (Connection conn = Noted.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                artists.add(rs.getString("artist"));
            }
            artistList.setItems(artists);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads all genres from the database and populates the genre list.
     */
    
    private void loadGenres() {
        ObservableList<String> genres = FXCollections.observableArrayList();
        String query = "SELECT name FROM genre ORDER BY name";

        try (Connection conn = Noted.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                genres.add(rs.getString("name"));
            }
            genreList.setItems(genres);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets the selected song, used in various operations like rating and updating.
     * 
     * @param song The selected song.
     */
    
    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
    }
    
    
    
    public void setSelectedSong(Song song) {
        this.selectedSong = song;
    }
    
    /**
     * Handles the action for rating a selected song by showing a dialog for input.
     */
    
    public void handleRateSong() {
        selectedSong = songTable.getSelectionModel().getSelectedItem();

        if (selectedSong == null) {
            showAlert("No Song Selected", "Please select a song to rate.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Rate Song");
        dialog.setHeaderText("Rate the song: " + selectedSong.getTitle());

        Label ratingLabel = new Label("Rating (1–5):");
        TextField ratingField = new TextField();
        Label reviewLabel = new Label("Review:");
        TextArea reviewArea = new TextArea();
        reviewArea.setPrefRowCount(3);
        reviewArea.setWrapText(true);

        VBox vbox = new VBox(10, ratingLabel, ratingField, reviewLabel, reviewArea);
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                int ratingValue = Integer.parseInt(ratingField.getText());
                if (ratingValue < 1 || ratingValue > 5) {
                    showAlert("Invalid Rating", "Please enter a rating between 1 and 5.");
                    return;
                }

                String reviewText = reviewArea.getText().trim();
                insertRating(selectedSong, ratingValue, reviewText);

            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number for rating.");
            }
        }
    }

    /**
     * Inserts a new rating and optional review text for a song into the database.
     * 
     * @param song       The song being rated.
     * @param rating     The numeric rating (1–5).
     * @param reviewText The textual review provided by the user.
     */
    private void insertRating(Song song, int rating, String reviewText) {
        String insertQuery = "INSERT INTO ratings (user_id, song_id, rating, review_text) VALUES (?, ?, ?, ?)";

        try (Connection conn = Noted.connect(); PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setInt(1, currentUserId);
            stmt.setInt(2, song.getSong_id());
            stmt.setInt(3, rating);
            stmt.setString(4, reviewText);
            stmt.executeUpdate();

            showAlert("Success", "Your rating and review have been submitted!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save rating.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Displays all ratings and reviews associated with a song.
     * 
     * @param song The song to display ratings for.
     */
    
    private void showSongRatings(Song song) {
        String query = "SELECT r.rating, r.review_text FROM ratings r";

        StringBuilder ratingsInfo = new StringBuilder();

        try (Connection conn = Noted.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, song.getSong_id());
            ResultSet rs = stmt.executeQuery();

            boolean hasRatings = false;
            while (rs.next()) {
                hasRatings = true;
                int rating = rs.getInt("rating");
                String review = rs.getString("review_text");
                ratingsInfo.append("User: ").append("Rating: ").append(rating).append("\n")
                           .append("Review: ").append(review).append("\n")
                           .append("----------------------\n");
            }

            if (!hasRatings) {
                ratingsInfo.append("No ratings found for this song.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            ratingsInfo.append("Failed to load ratings.");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ratings for: " + song.getTitle());
        alert.setHeaderText(null);
        alert.setContentText(ratingsInfo.toString());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // Resize dialog
        alert.showAndWait();
    }

    
    /**
     * Handles the navigation to the home state of the application.
     * 
     * @param event The action event triggered by the UI.
     */
    
    public void handleHome(ActionEvent event) {
        System.out.println("Home button clicked!");
        
        
        initialize();
    }
    

	/**
	 * Handles the user login by prompting for username and password.
	 */
    public void handleLogin() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Please log in to continue.");

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        VBox vbox = new VBox(10,
            new Label("Username:"), usernameField,
            new Label("Password:"), passwordField
        );

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Username and password cannot be empty.");
                return;
            }

            try (Connection conn = Noted.connect();
                 PreparedStatement stmt = conn.prepareStatement("SELECT user_id FROM users WHERE username = ? AND password = ?")) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    currentUserId = rs.getInt("user_id");
                    showAlert("Login Successful", "Welcome, " + username + "!");
                } else {
                    showAlert("Login Failed", "Invalid credentials.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Database error during login.");
            }
        }
    }
    
    /**
     * Handles updating an existing rating for a selected song.
     * Allows the user to select and modify one of their previous ratings.
     */
    
    public void handleUpdateRating() {
        selectedSong = songTable.getSelectionModel().getSelectedItem();

        if (selectedSong == null) {
            showAlert("No Song Selected", "Please select a song to update a rating.");
            return;
        }

        String query = "SELECT * FROM ratings WHERE song_id = ? AND user_id = ?";
        ObservableList<Rating> ratings = FXCollections.observableArrayList();

        try (Connection conn = Noted.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, selectedSong.getSong_id());
            stmt.setInt(2, currentUserId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int ratingId = rs.getInt("rating_id");
                int rating = rs.getInt("rating");
                String reviewText = rs.getString("review_text");
                int userId = rs.getInt("user_id");
                int songId = rs.getInt("song_id");

                ratings.add(new Rating(ratingId, rating, reviewText, userId, songId));
            }

            if (ratings.isEmpty()) {
                showAlert("No Ratings Found", "You have no ratings for this song.");
                return;
            }

           
            Dialog<ButtonType> selectDialog = new Dialog<>();
            selectDialog.setTitle("Update Rating");
            selectDialog.setHeaderText("Select a rating to update for: " + selectedSong.getTitle());

            ComboBox<Rating> ratingComboBox = new ComboBox<>(ratings);
            ratingComboBox.setPromptText("Select a rating");
            ratingComboBox.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(Rating item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null :
                            "Rating: " + item.getRating() + " | Review: " + item.getReview_text());
                }
            });
            ratingComboBox.setButtonCell(ratingComboBox.getCellFactory().call(null));

            VBox selectBox = new VBox(10, new Label("Choose a rating to update:"), ratingComboBox);
            selectDialog.getDialogPane().setContent(selectBox);
            selectDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> selectionResult = selectDialog.showAndWait();
            if (selectionResult.isEmpty() || selectionResult.get() != ButtonType.OK) return;

            Rating selectedRating = ratingComboBox.getValue();
            if (selectedRating == null) {
                showAlert("No Selection", "You must select a rating to update.");
                return;
            }

            
            Dialog<ButtonType> updateDialog = new Dialog<>();
            updateDialog.setTitle("Update Rating");
            updateDialog.setHeaderText("Edit your rating for: " + selectedSong.getTitle());

            TextField ratingField = new TextField(String.valueOf(selectedRating.getRating()));
            TextArea reviewArea = new TextArea(selectedRating.getReview_text());
            reviewArea.setPrefRowCount(3);
            reviewArea.setWrapText(true);

            VBox updateBox = new VBox(10,
                new Label("New Rating (1–5):"), ratingField,
                new Label("Updated Review:"), reviewArea
            );
            updateDialog.getDialogPane().setContent(updateBox);
            updateDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> updateResult = updateDialog.showAndWait();
            if (updateResult.isPresent() && updateResult.get() == ButtonType.OK) {
                try {
                    int newRating = Integer.parseInt(ratingField.getText().trim());
                    if (newRating < 1 || newRating > 5) {
                        showAlert("Invalid Rating", "Rating must be between 1 and 5.");
                        return;
                    }

                    String newReview = reviewArea.getText().trim();
                    updateRating(selectedRating.getRating_id(), newRating, newReview);
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid number for rating.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load ratings.");
        }
    }

    private void updateRating(int ratingId, int newRating, String newReviewText) {
        String updateQuery = "UPDATE ratings SET rating = ?, review_text = ? WHERE rating_id = ?";

        try (Connection conn = Noted.connect(); PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setInt(1, newRating);
            stmt.setString(2, newReviewText);
            stmt.setInt(3, ratingId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                showAlert("Rating Updated", "Your rating has been successfully updated.");
                loadSongs(); 
            } else {
                showAlert("Update Failed", "No rows were updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Could not update the rating.");
        }
    }
    
    /**
     * Handles deleting an existing rating for a selected song.
     * Allows the user to select and delete one of their previous ratings.
     */
    public void handleDeleteRating() {
        selectedSong = songTable.getSelectionModel().getSelectedItem();

        if (selectedSong == null) {
            showAlert("No Song Selected", "Please select a song to delete a rating.");
            return;
        }

        
        String query = "SELECT * FROM ratings WHERE song_id = ? AND user_id = ?";
        ObservableList<Rating> ratings = FXCollections.observableArrayList();

        try (Connection conn = Noted.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, selectedSong.getSong_id());
            stmt.setInt(2, currentUserId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int ratingId = rs.getInt("rating_id");
                int rating = rs.getInt("rating");
                String reviewText = rs.getString("review_text");
                int userId = rs.getInt("user_id");
                int songId = rs.getInt("song_id");

                ratings.add(new Rating(ratingId, rating, reviewText, userId, songId));
            }

            if (ratings.isEmpty()) {
                showAlert("No Ratings Found", "You have no ratings for this song.");
                return;
            }

            
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Delete Rating");
            dialog.setHeaderText("Delete a rating for: " + selectedSong.getTitle());

            ComboBox<Rating> ratingComboBox = new ComboBox<>(ratings);
            ratingComboBox.setPromptText("Select a rating to delete");
            ratingComboBox.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(Rating item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null :
                            "Rating: " + item.getRating() + " | Review: " + item.getReview_text());
                }
            });
            ratingComboBox.setButtonCell(ratingComboBox.getCellFactory().call(null));

            VBox content = new VBox(10, new Label("Choose a rating to delete:"), ratingComboBox);
            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Rating selectedRating = ratingComboBox.getValue();
                if (selectedRating != null) {
                    deleteRating(selectedRating.getRating_id());
                } else {
                    showAlert("No Selection", "You must select a rating to delete.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load ratings for this song.");
        }
    }
    
    private void deleteRating(int ratingId) {
        String deleteQuery = "DELETE FROM ratings WHERE rating_id = ?";

        try (Connection conn = Noted.connect(); PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, ratingId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Rating Deleted", "The rating has been deleted successfully.");
                loadSongs(); 
            } else {
                showAlert("Error", "Failed to delete the rating.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete rating.");
        }
    }
    
}
