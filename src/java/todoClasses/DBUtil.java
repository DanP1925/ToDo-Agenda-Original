package todoClasses;

import java.sql.*;
import java.util.ArrayList;

public class DBUtil {

    private String url = "jdbc:mysql://localhost:3306/todoagenda";
    private String driver = "com.mysql.jdbc.Driver";
    //private String dbUname = "root";
    //private String dbPass = "1234";
    private String dbUname = "daniel";
    private String dbPass = "daniel";

    public void createUser(String firstName, String lastName, String username, String password, String gender) throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, dbUname, dbPass);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO `todoagenda`.`usersdata` (`username`, `password`, `First Name`, `Last Name`, `Gender`)"
                + "VALUES ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', '" + gender + "');");
        connection.close();
    }

    public boolean isUserInDB(String username, String password) throws ClassNotFoundException, SQLException {

        boolean isInDB;
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, dbUname, dbPass);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todoagenda.usersdata\n"
                + "           WHERE username='" + username + "'\n"
                + "           AND password='" + password + "';");
        isInDB = resultSet.next();

        connection.close();

        return isInDB;
    }

    public UserData getUser(String username, String password) throws ClassNotFoundException, SQLException {
        UserData userObject = new UserData();
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, dbUname, dbPass);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todoagenda.usersdata\n"
                + "           WHERE username='" + username + "'\n"
                + "           AND password='" + password + "';");

        ResultSetMetaData result = resultSet.getMetaData();

        while (resultSet.next()) {
            userObject.setPrimaryKey(resultSet.getInt("idUsersData"));
            userObject.setFirstName(resultSet.getString("First Name"));
            userObject.setLastName(resultSet.getString("Last Name"));
            userObject.setUsername(resultSet.getString("username"));
            userObject.setPassword(resultSet.getString("password"));
            userObject.setGender(resultSet.getString("Gender"));
        }

        connection.close();
        return userObject;
    }

    public TaskList getTaskList(int userPK, java.util.Date currentDate) throws ClassNotFoundException, SQLException {
        TaskList taskList = new TaskList();

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, dbUname, dbPass);
        Statement statement = connection.createStatement();

        java.text.SimpleDateFormat formateerYear = new java.text.SimpleDateFormat("yyyy");
        java.text.SimpleDateFormat formateerMonth = new java.text.SimpleDateFormat("MM");
        java.text.SimpleDateFormat formateerDay = new java.text.SimpleDateFormat("dd");
        String outputYear = formateerYear.format(currentDate);
        String outputMonth = formateerMonth.format(currentDate);
        String outputDay = formateerDay.format(currentDate);

        ResultSet resultSet = statement.executeQuery("SELECT * FROM todoagenda.taskdata\n"
                + "WHERE idusersdata=" + userPK + "\n"
                + "AND createdDate<='" + outputYear + "-" + outputMonth + "-" + outputDay + "'\n"
                + "AND (deadline>='" + outputYear + "-" + outputMonth + "-" + outputDay + "' OR isnull(deadline))\n"
                + "AND (completeIndicator='NotComplete' OR (completeIndicator='Complete' AND completeDate='" + outputYear + "-" + outputMonth + "-" + outputDay + "'))\n"
                + "ORDER BY priority DESC;");

        ResultSetMetaData result = resultSet.getMetaData();
        int cn = result.getColumnCount();

        ArrayList<Integer> primaryKey = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Integer> priority = new ArrayList<>();
        ArrayList<java.util.Date> deadline = new ArrayList<>();
        while (resultSet.next()) {
            primaryKey.add(resultSet.getInt("idtaskdata"));
            title.add(resultSet.getString("title"));
            priority.add(resultSet.getInt("priority"));
            deadline.add(resultSet.getDate("deadline"));
        }

        taskList.setPrimaryKey(primaryKey);
        taskList.setTitle(title);
        taskList.setPriority(priority);
        taskList.setDeadline(deadline);

        connection.close();
        return taskList;
    }

    public void createTask(int userPK, String title, String description, String createdDate, String deadline, String priority) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, dbUname, dbPass);
        Statement statement = connection.createStatement();

        if (deadline.equals("")) {
            statement.executeUpdate("INSERT INTO `todoagenda`.`taskdata` (`idusersdata`, `title`, `description`, `createdDate`, `priority`, `completeIndicator`) "
                    + "VALUES ('" + userPK + "', '" + title + "', '" + description + "', '" + createdDate + "', '" + priority + "', 'NotComplete');");
        } else {
            statement.executeUpdate("INSERT INTO `todoagenda`.`taskdata` (`idusersdata`, `title`, `description`, `createdDate`, `deadline`, `priority`, `completeIndicator`) "
                    + "VALUES ('" + userPK + "', '" + title + "', '" + description + "', '" + createdDate + "', '" + deadline + "', '" + priority + "', 'NotComplete');");
        }
        connection.close();
    }

    public void completeTask(String taskPK) throws ClassNotFoundException, SQLException {
        java.util.Date currentJavaDate = new java.util.Date();
        Date currentSQLDate = new Date(currentJavaDate.getTime());
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, dbUname, dbPass);
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE `todoagenda`.`taskdata` SET "
                + "`priority`='-1', `completeIndicator`='Complete', `completeDate`='" + currentSQLDate + "' WHERE `idtaskdata`='" + taskPK + "';");
        connection.close();
    }

    public void deleteTask(String taskPK) throws ClassNotFoundException, SQLException {
        java.util.Date currentJavaDate = new java.util.Date();
        Date currentSQLDate = new Date(currentJavaDate.getTime());
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, dbUname, dbPass);
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM `todoagenda`.`taskdata` WHERE `idtaskdata`='" + taskPK + "';");
        connection.close();
    }

    public Task getTask(String taskPK) throws ClassNotFoundException, SQLException {
        Task newTask = new Task();
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, dbUname, dbPass);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todoagenda.taskdata WHERE idtaskdata=" + taskPK + ";");

        ResultSetMetaData result = resultSet.getMetaData();

        while (resultSet.next()) {
            newTask.setTitle(resultSet.getString("title"));
            newTask.setDescription(resultSet.getString("description"));
            newTask.setCreatedDate(resultSet.getDate("createdDate"));
            newTask.setDeadline(resultSet.getDate("deadline"));
            newTask.setPriority(resultSet.getInt("priority"));
        }

        connection.close();
        return newTask;
    }

    public Task getPriorityTask(int taskPK, java.util.Date currentDate) throws ClassNotFoundException, SQLException {
        Task newTask = new Task();
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, dbUname, dbPass);
        Statement statement = connection.createStatement();
        Date sqlcurrentDate = new Date(currentDate.getTime());
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todoagenda.taskdata WHERE "
                + "idusersdata="+ taskPK + " AND createdDate<='" + sqlcurrentDate.toString() + "' AND (deadline>='" + sqlcurrentDate.toString() + "' OR isnull(deadline)) "
                + "AND completeIndicator='NotComplete' "
                + "order by priority DESC "
                + "limit 1;");

        ResultSetMetaData result = resultSet.getMetaData();

        while (resultSet.next()) {
            newTask.setTitle(resultSet.getString("title"));
            newTask.setDescription(resultSet.getString("description"));
            newTask.setCreatedDate(resultSet.getDate("createdDate"));
            newTask.setDeadline(resultSet.getDate("deadline"));
            newTask.setPriority(resultSet.getInt("priority"));
        }
        
        connection.close();
        return newTask;
    }
}
