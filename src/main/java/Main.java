import java.sql.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        String dbPath = "src/main/resources/";
        String dbName = "newDB.db";

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath+dbName);
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS contacts(firstName TEXT, lastName TEXT, age INTEGER)");

            menu(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void menu(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Menu!");
        System.out.println("Press 1 to add data to the database");
        System.out.println("Press 2 to view the database");
        System.out.println("Or press any other key to exit");

        int num = scanner.nextInt();

        if(num == 1) {
            prepareDataToAddToTheDatabase(connection, scanner);
        } if(num == 2) {
            viewDatabase(connection);
        } else {
            System.out.println("Goodbye");
        }
    }

    public static void viewDatabase(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet rs =   statement.executeQuery("SELECT * FROM contacts");

        while (rs.next()) {
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
        }

    }

    public static void prepareDataToAddToTheDatabase(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Please enter the person's first name: ");
        String firstname = scanner.next();
        System.out.println("Please enter the person's last name: ");
        String lastName = scanner.next();
        System.out.println("Please enter the person's age: ");
        int age = scanner.nextInt();
        insertDataIntoDatabase(connection,firstname,lastName,age);

    }

    public static void insertDataIntoDatabase(Connection connection, String firstName, String lastName, Integer age) throws SQLException {

        String sqlStatement = "INSERT INTO contacts " +
                "(firstName, lastName, age)" +
                " VALUES (?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1,firstName);
        preparedStatement.setString(2,lastName);
        preparedStatement.setInt(3,age);

        preparedStatement.execute();
        System.out.println("Successfully added new person");
        menu(connection);
    }

}
