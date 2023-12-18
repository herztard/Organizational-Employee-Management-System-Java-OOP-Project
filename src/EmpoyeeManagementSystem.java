import java.sql.*;
import java.text.NumberFormat;
import java.util.Scanner;

/*  ВНИМАНИЕ!
 *  Перед тем как запускать проект, убедитесь, что у вас подключена библиотека sqlite-jdbc.
 *  Скачать её можно по ссылке здесь: https://javadoc.io/doc/org.xerial/sqlite-jdbc/latest/index.html
 *
 *  А также, поменяйте содержимое всех переменных String url на путь к файлу в вашем компьютере.
 *
 *  Вместо создания .txt файла на каждого человека, вопреки инструкции проекта, я решил использовать базу данных.
 *  Во-первых, это практичнее. Никто в реальных проектах не держит данные своих юзеров в отдельно созданных .txt файлов.
 *  Во-вторых, это удобнее. Вместо навигации по папке, где ты видишь только ID, в таблице базы данных всё собрано в одном
 *  месте и читаются все поля данных (к примеру, если поиск человека идёт не по ID, а по имени или почте).
 */

public class EmpoyeeManagementSystem {
    static String tempString;
    static int choice;


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        MainMenu start = new MainMenu();
        start.homePage();

        Class.forName("org.sqlite.JDBC");
        DataBaseCommands.connect();

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("Please, Enter choice : ");
            tempString = scanner.nextLine();
            try{
                choice = Integer.parseInt(tempString);
                if (choice >= 1 && choice <= 5) {
                    switch (choice){
                        case 1:
                            EmployeeAdd.addToTable();

                            System.out.println("\nPress ENTER to continue...");
                            scanner.nextLine();

                            start.homePage();
                            break;
                        case 2:
                            int id;
                            while (true) {
                                System.out.print("Enter Employee's ID : ");
                                tempString = scanner.nextLine();
                                try{
                                    id = Integer.parseInt(tempString);
                                    break;
                                } catch (Exception e){
                                    System.out.println("Incorrect input. Please, try again. ");
                                }
                            }

                            EmployeeShow.viewProfile(id);

                            System.out.println("\nPress ENTER to continue...");
                            scanner.nextLine();

                            start.homePage();
                            break;
                        case 3:
                            int id1;
                            while (true) {
                                System.out.print("Enter Employee's ID : ");
                                tempString = scanner.nextLine();
                                try{
                                    id1 = Integer.parseInt(tempString);
                                    break;
                                } catch (Exception e){
                                    System.out.println("Incorrect input. Please, try again. ");
                                }
                            }

                            EmployeeRemove employeeRemove = new EmployeeRemove();
                            employeeRemove.removeEmployee(id1);

                            System.out.println("\nPress ENTER to continue...");
                            scanner.nextLine();

                            start.homePage();
                            break;
                        case 4:
                            int id2;
                            while (true) {
                                System.out.print("Enter Employee's ID : ");
                                tempString = scanner.nextLine();
                                try{
                                    id2 = Integer.parseInt(tempString);
                                    break;
                                } catch (Exception e){
                                    System.out.println("Incorrect input. Please, try again. ");
                                }
                            }

                            EmployeeUpdate.update(id2);

                            System.out.println("\nPress ENTER to continue...");
                            scanner.nextLine();

                            start.homePage();
                            break;
                        case 5:
                            CodeExit.out();
                    }
                }
                else System.out.print("You can ONLY enter numbers from 1 to 5 inclusive. ");
            } catch (Exception e){
                System.out.print("You can ONLY enter numbers from 1 to 5 inclusive. ");
            }
        }
    }
}

class DataBaseCommands{
    public static void connect() throws SQLException {
        Connection connection = null;

        try{
            String url = "jdbc:sqlite:D:\\CSS108\\Slyamgazy_Adilzhan_CCS108_project1\\src\\Employee.db";
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to database has been established.");
        } catch (SQLException e) {
            System.out.println("Connection to database has NOT been established.");
        }


        //==============================


        String sql = "CREATE TABLE EMPLOYEES " +
                "(employID             INT," +
                " name           TEXT, " +
                " age            INT, " +
                " email          TEXT, " +
                " position       TEXT, " +
                " employContact  LONG," +
                " employSalary   FLOAT)";

        try{
            assert connection != null;
            Statement statement = connection.createStatement();
            statement.execute(sql);

            System.out.println("A new table has been created.");

        } catch (SQLException e) {
            System.out.println("Table has already created.");
        }
        connection.close();
    }
}

class MainMenu{
    void homePage(){
        System.out.println(
                """
                        \t\t******************************************
                        \t\t\t\tEmployee Management System
                        \t\t******************************************
                        
                        
                        Press 1 : Add an Employee Details
                        Press 2 : See an Employee Details
                        Press 3 : Remove an Employee
                        Press 4 : Update Employee Details
                        Press 5 : Exit the EMS Portal
                        """);
    }
}

abstract class EmployDetail{
    private int employID;
    private String name;
    private int age;
    private String email;
    private String position;
    private long employContact;
    private float employSalary;

    boolean isValid = false;
    String tempString;

    void employeeDetail(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Employee's name --------: ");
        name = scanner.nextLine();
        while (true) {
            System.out.print("Enter Employee's age ---------: ");
            tempString = scanner.nextLine();
            try{
                age = Integer.parseInt(tempString);
                break;
            } catch (Exception e){
                System.out.println("Incorrect input. Please, try again. ");
            }
        }

        while (true) {
            System.out.print("Enter Employee's ID ----------: ");
            tempString = scanner.nextLine();
            try{
                employID = Integer.parseInt(tempString);
                break;
            } catch (Exception e){
                System.out.println("Incorrect input. Please, try again. ");
            }
        }

        while (true) {
            System.out.print("Enter Employee's email -------: ");
            email = scanner.nextLine();

            String[] domainList = {"@gmail.com", "@sdu.edu.kz", "@mail.ru",
                    "@icloud.com", "@yahoo.com", "@yandex.ru",
                    "@outlook.com", "@protonmail.com"};


            for (String d : domainList) {
                if (email.contains(d)) {
                    isValid = true;
                    break;
                }
            }

            if (isValid) break;
            else System.out.println("Incorrect input. Please, try again. ");
        }

        System.out.print("Enter Employee's Position ----: ");
        position = scanner.nextLine();

        while(true){
            System.out.print("Enter Employee's Contact -----: ");
            tempString = scanner.nextLine();
            try{
                employContact = Long.parseLong(tempString);
                break;
            } catch (Exception e) {
                System.out.println("Incorrect input. Please, try again. ");
            }
        }

        while(true){
            System.out.print("Enter Employee's Salary ------: ");
            tempString = scanner.nextLine();
            try {
                employSalary = Float.parseFloat(tempString);
                break;
            } catch (Exception e){
                System.out.println("Incorrect input. Please, try again. ");
            }
        }
    }

    public int getEmployID() {
        return employID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPosition() {
        return position;
    }

    public long getEmployContact() {
        return employContact;
    }

    public float getEmploySalary() {
        return employSalary;
    }
}

class EmployeeAdd extends EmployDetail{
    private static void insert(int employID, String name, int age, String email,
                               String position, long employContact, float employSalary) throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:D:\\CSS108\\Slyamgazy_Adilzhan_CCS108_project1\\src\\Employee.db";
        Connection connection = DriverManager.getConnection(url);


        String sql = "INSERT INTO EMPLOYEES(employID,name,age,email,position,employContact,employSalary) " +
                "VALUES(?,?,?,?,?,?,?)";

        PreparedStatement prepStatement = connection.prepareStatement(sql);
        prepStatement.setInt(1, employID);
        prepStatement.setString(2, name);
        prepStatement.setInt(3, age);
        prepStatement.setString(4, email);
        prepStatement.setString(5, position);
        prepStatement.setLong(6, employContact);
        prepStatement.setFloat(7, employSalary);
        prepStatement.executeUpdate();
        connection.close();
    }

    public static void addToTable() throws SQLException, ClassNotFoundException {

        EmployeeAdd details = new EmployeeAdd();
        details.employeeDetail();

        insert(details.getEmployID(), details.getName(), details.getAge(), details.getEmail(),
                details.getPosition(), details.getEmployContact(), details.getEmploySalary());

        System.out.println("\nThe employee was successfully added.");


    }
}

class EmployeeShow {
    public static void viewProfile(int ID) throws SQLException {

        String url = "jdbc:sqlite:D:\\CSS108\\Slyamgazy_Adilzhan_CCS108_project1\\src\\Employee.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Connection to database has NOT been established.");
        }

        String sql = "SELECT employID, name, age, email, position, employContact, employSalary " +
                "FROM EMPLOYEES WHERE employID LIKE " + ID;

        assert connection != null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        if (resultSet.next()) {
            System.out.println(
                    "\n1-ID ---------: " + resultSet.getInt("employID") +
                    "\n2-Name -------: " + resultSet.getString("name") +
                    "\n3-Age --------: " + resultSet.getInt("age") +
                    "\n4-Email ------: " + resultSet.getString("email") +
                    "\n5-Position ---: " + resultSet.getString("position") +
                    "\n6-Contact ----: " + resultSet.getLong("employContact") +
                    "\n7-Salary -----: " + numberFormat.format(resultSet.getFloat("employSalary")));
        } else {
            System.out.println("\nERROR: There is NO employee with such ID");
        }
        connection.close();
    }
}

interface Remove{
    void removeEmployee(int ID);
}

class EmployeeRemove implements Remove{

    public void removeEmployee(int ID) {

        String url = "jdbc:sqlite:D:\\CSS108\\Slyamgazy_Adilzhan_CCS108_project1\\src\\Employee.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Connection to database has NOT been established.");
        }

        String sql = "DELETE FROM EMPLOYEES WHERE employID LIKE " + ID;
        String sqlIsExists = "SELECT employID FROM EMPLOYEES WHERE " +
                "EXISTS(SELECT employID FROM EMPLOYEES WHERE employID LIKE + " + ID + ")";
        try{
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlIsExists);

            if (resultSet.next()) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
                System.out.println("\nThe employee was successfully removed.");
            } else {
                System.out.println("\nERROR: There is NO employee with such ID.");
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

class EmployeeUpdate {
    private static int employID;
    private static String name;
    private static int age;
    private static String email;
    private static String newPosition;
    private static long employContact;
    private static float newEmploySalary;

    private static boolean isValid = false;
    private static String tempString;

    public static void update(int ID) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:D:\\CSS108\\Slyamgazy_Adilzhan_CCS108_project1\\src\\Employee.db";
        Connection connection = DriverManager.getConnection(url);
        connection.setAutoCommit(false);


        String sql = "SELECT employID, name, age, email, position, employContact, employSalary " +
                "FROM EMPLOYEES WHERE employID LIKE " + ID;


        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (!resultSet.next()){
            System.out.println("\nERROR: There is NO employee with such ID.");
            return;
        } else {
            EmployeeShow.viewProfile(ID);
            System.out.println();
        }



        int choice;
        while(true) {
            System.out.print("Please enter corresponsible number of the detail you want to update: ");
            tempString = scanner.nextLine();
            try {
                choice = Integer.parseInt(tempString);
                if (choice >= 1 && choice <= 7) {
                    break;
                } else System.out.print("You can ONLY enter numbers from 1 to 7 inclusive. ");
            } catch (Exception e) {
                System.out.print("You can ONLY enter numbers from 1 to 7 inclusive. ");
            }
        }

        PreparedStatement prepStatement;

        try{
            switch (choice) {
                case 1:
                    while (true) {
                        System.out.print("Enter new employee's ID : ");
                        tempString = scanner.nextLine();
                        try {
                            employID = Integer.parseInt(tempString);
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect input. Please, try again. ");
                        }
                    }
                    sql = "UPDATE employees SET employID=? WHERE employID LIKE "+ ID;
                    connection.close();
                    connection = DriverManager.getConnection(url);

                    prepStatement = connection.prepareStatement(sql);
                    prepStatement.setInt(1, employID);
                    prepStatement.executeUpdate();
                    break;
                case 2:
                    System.out.print("Enter new employee's name : ");
                    name = scanner.nextLine();

                    sql = "UPDATE EMPLOYEES SET name=? WHERE employID LIKE " + ID;
                    connection.close();
                    connection = DriverManager.getConnection(url);

                    prepStatement = connection.prepareStatement(sql);
                    prepStatement.setString(1, name);
                    prepStatement.executeUpdate();

                    break;
                case 3:
                    while (true) {
                        System.out.print("Enter new employee's age ---------: ");
                        tempString = scanner.nextLine();
                        try {
                            age = Integer.parseInt(tempString);
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect input. Please, try again. ");
                        }
                    }
                    sql = "UPDATE EMPLOYEES SET age=? WHERE employID LIKE " + ID;
                    connection.close();
                    connection = DriverManager.getConnection(url);

                    prepStatement = connection.prepareStatement(sql);
                    prepStatement.setInt(1, age);
                    prepStatement.executeUpdate();

                    break;
                case 4:
                    while (true) {
                        System.out.print("Enter new employee's email -------: ");
                        email = scanner.nextLine();

                        String[] domainList = {"@gmail.com", "@sdu.edu.kz", "@mail.ru",
                                "@icloud.com", "@yahoo.com", "@yandex.ru",
                                "@outlook.com", "@protonmail.com"};


                        for (String d : domainList) {
                            if (email.contains(d)) {
                                isValid = true;
                                break;
                            }
                        }

                        if (isValid) break;
                        else System.out.println("Incorrect input. Please, try again. ");
                    }
                    sql = "UPDATE EMPLOYEES SET email=? WHERE employID LIKE " + ID;
                    connection.close();
                    connection = DriverManager.getConnection(url);

                    prepStatement = connection.prepareStatement(sql);
                    prepStatement.setString(1, email);
                    prepStatement.executeUpdate();
                    break;
                case 5:
                    System.out.print("Enter new employee's Position ----: ");
                    String newPosition = scanner.nextLine();
                    sql = "UPDATE EMPLOYEES SET position = ? WHERE employID = " + ID;
                    connection.close();
                    connection = DriverManager.getConnection(url);

                    prepStatement = connection.prepareStatement(sql);

                    prepStatement.setString(1, newPosition);
                    prepStatement.executeUpdate();

                    connection.close();
                    break;
                case 6:
                    connection.close();
                    connection = DriverManager.getConnection(url);

                    while (true) {
                        System.out.print("Enter new employee's Contact -----: ");
                        tempString = scanner.nextLine();
                        try {
                            employContact = Long.parseLong(tempString);
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect input. Please, try again. ");
                        }
                    }
                    sql = "UPDATE EMPLOYEES SET employContact=? WHERE employID LIKE " + ID;

                    prepStatement = connection.prepareStatement(sql);
                    prepStatement.setLong(1, employContact);
                    prepStatement.executeUpdate();
                    break;
                case 7:
                    while (true) {
                        System.out.print("Enter new employee's Salary ------: ");
                        tempString = scanner.nextLine();
                        try {
                            newEmploySalary = Float.parseFloat(tempString);
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect input. Please, try again. ");
                        }
                    }
                    sql = "UPDATE EMPLOYEES SET employSalary=? WHERE employID LIKE " + ID;
                    connection.close();
                    connection = DriverManager.getConnection(url);

                    prepStatement = connection.prepareStatement(sql);
                    prepStatement.setFloat(1, newEmploySalary);
                    prepStatement.executeUpdate();
                    break;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }

        System.out.println("\nDetails successfully have been updated.");
    }
}

class CodeExit{
    static void out(){
        System.out.println(
                """
                        
                        \t\t******************************************
                        \t\t  Thanks You For Sharing your details :)
                        \t\t******************************************""");
        System.exit(0);
    }
}
