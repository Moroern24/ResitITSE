public class Main {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        UserInterface ui = new UserInterface();
        ui.start();
    }
}
