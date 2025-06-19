import java.sql.*;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n=== MovieRater Menu ===");
            System.out.println("1. Add a user");
            System.out.println("2. Show viewing habits of a user");
            System.out.println("3. Change movie title");
            System.out.println("4. Delete a viewing record");
            System.out.println("5. Show mean user age");
            System.out.println("6. Show number of users who watched a specific movie");
            System.out.println("7. Show total minutes watched");
            System.out.println("8. Show number of users who watched more than one movie");
            System.out.println("9. Add email column (if not added yet)");
            System.out.println("0. Exit");
            System.out.print("> ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> addUser();
                case "2" -> showViewingHabits();
                case "3" -> changeMovieTitle();
                case "4" -> deleteViewingRecord();
                case "5" -> showMeanAge();
                case "6" -> countUsersWatchedMovie();
                case "7" -> showTotalMinutesWatched();
                case "8" -> showUsersWatchedMultipleMovies();
                case "9" -> addEmailColumn();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addUser() {
        try (Connection conn = DatabaseManager.connect()) {
            System.out.print("Enter user age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            String sql = "INSERT INTO User (Age, Email) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, age);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("User added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    private void showViewingHabits() {
        try (Connection conn = DatabaseManager.connect()) {
            System.out.print("Enter user ID: ");
            int userID = Integer.parseInt(scanner.nextLine());

            String sql = """
                SELECT m.Title, vh.MinutesWatched
                FROM ViewingHabit vh
                JOIN Movie m ON vh.MovieID = m.MovieID
                WHERE vh.UserID = ?""";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userID);

            ResultSet rs = pstmt.executeQuery();
            System.out.println("Viewing habits:");
            while (rs.next()) {
                System.out.printf("- %s: %d minutes\n", rs.getString("Title"), rs.getInt("MinutesWatched"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching viewing habits: " + e.getMessage());
        }
    }

    private void changeMovieTitle() {
        try (Connection conn = DatabaseManager.connect()) {
            System.out.print("Enter movie ID: ");
            int movieID = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter new title: ");
            String newTitle = scanner.nextLine();

            String sql = "UPDATE Movie SET Title = ? WHERE MovieID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, movieID);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Movie title updated.");
            } else {
                System.out.println("Movie ID not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating movie title: " + e.getMessage());
        }
    }

    private void deleteViewingRecord() {
        try (Connection conn = DatabaseManager.connect()) {
            System.out.print("Enter user ID: ");
            int userID = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter movie ID: ");
            int movieID = Integer.parseInt(scanner.nextLine());

            String sql = "DELETE FROM ViewingHabit WHERE UserID = ? AND MovieID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, movieID);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Viewing record deleted.");
            } else {
                System.out.println("Record not found.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting viewing record: " + e.getMessage());
        }
    }

    private void showMeanAge() {
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT AVG(Age) AS MeanAge FROM User";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                System.out.println("Mean age: " + rs.getDouble("MeanAge"));
            }
        } catch (Exception e) {
            System.out.println("Error calculating mean age: " + e.getMessage());
        }
    }

    private void countUsersWatchedMovie() {
        try (Connection conn = DatabaseManager.connect()) {
            System.out.print("Enter movie ID: ");
            int movieID = Integer.parseInt(scanner.nextLine());

            String sql = """
                SELECT COUNT(DISTINCT UserID) AS UserCount
                FROM ViewingHabit
                WHERE MovieID = ?""";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, movieID);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Users watched this movie: " + rs.getInt("UserCount"));
        } catch (Exception e) {
            System.out.println("Error counting users: " + e.getMessage());
        }
    }

    private void showTotalMinutesWatched() {
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT SUM(MinutesWatched) AS Total FROM ViewingHabit";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Total minutes watched: " + rs.getInt("Total"));
        } catch (Exception e) {
            System.out.println("Error calculating total minutes: " + e.getMessage());
        }
    }

    private void showUsersWatchedMultipleMovies() {
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {

            String sql = """
                SELECT COUNT(*) AS Count
                FROM (
                    SELECT UserID
                    FROM ViewingHabit
                    GROUP BY UserID
                    HAVING COUNT(DISTINCT MovieID) > 1
                )""";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Users who watched more than one movie: " + rs.getInt("Count"));
        } catch (Exception e) {
            System.out.println("Error fetching user count: " + e.getMessage());
        }
    }

    private void addEmailColumn() {
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {

            String checkSql = "PRAGMA table_info(User)";
            ResultSet rs = stmt.executeQuery(checkSql);

            boolean emailExists = false;
            while (rs.next()) {
                if ("Email".equalsIgnoreCase(rs.getString("name"))) {
                    emailExists = true;
                    break;
                }
            }

            if (!emailExists) {
                stmt.execute("ALTER TABLE User ADD COLUMN Email TEXT");
                System.out.println("Email column added.");
            } else {
                System.out.println("Email column already exists.");
            }

        } catch (Exception e) {
            System.out.println("Error modifying User table: " + e.getMessage());
        }
    }
}
