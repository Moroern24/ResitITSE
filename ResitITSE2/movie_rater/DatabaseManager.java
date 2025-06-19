import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:movierater.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            String sql = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("sql/schema.sql")));
            stmt.executeUpdate(sql);
            System.out.println("База данных инициализирована.");
        } catch (Exception e) {
            System.out.println("Ошибка при инициализации БД: " + e.getMessage());
        }
    }
}
