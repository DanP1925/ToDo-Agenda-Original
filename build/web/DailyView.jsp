<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Daily View</title>
        <link rel="stylesheet" href="css/task.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="js/task.js"></script>
    </head>
    <body id ="dailyBody">

        <div id = "dailyHeader">
            <div id = "dailyLogOutWelcome">
                <form id="logOut" action="Controller" method="GET" enctype="text/plain">
                    <input class="myButton" type="submit" name="action" value="LogOut" />
                </form>

                <jsp:useBean id="userObj" type="todoClasses.UserData" scope="session" />

                <%! String welcomeMessage;%>
                <% if (userObj.getGender().equals("male")) {
                        welcomeMessage = "Mr.";
                    } else if (userObj.getGender().equals("female")) {
                        welcomeMessage = "Ms.";
                    }
                    welcomeMessage += userObj.getLastName();
                %>

                <div id="welcomeMessage">
                    <h2><%= welcomeMessage%> Daily To-do List</h2>
                    <% java.util.Date currentDate = (java.util.Date) session.getAttribute("currentDate");
                        java.text.SimpleDateFormat formateer = new java.text.SimpleDateFormat("EEEEE dd MMMMMMM yyyy");
                        String output = formateer.format(currentDate);
                    %>
                    <h3><%= output%></h3>
                </div>
            </div>

            <div id="taskOptions">
                <form id="taskSubmit" action="Controller" method="GET" enctype="text/plain">
                    <input class="myButton" type="submit" name="action" value="Add Task" />
                    <input class="myButton" type="submit" name="action" value="Complete Task" />
                    <input class="myButton" type="submit" name="action" value="Delete Task" />
                </form>
            </div>
        </div>

        <div id="dailyBodyGroup">
            <div id="monthlyViewButtons">
                <form id="monthlyView" action="Controller" method="GET" enctype="text/plain">
                    <button class="myButton" type="button">Daily View</button>
                    <input class="myButton" type="submit" name="action" value="Monthly View" />
                </form>
            </div>

            <div id="taskTable">
                <jsp:useBean id="userTaskList" type="todoClasses.TaskList" scope="request" />
                <%if (userTaskList.getPrimaryKey().size() == 0) {
                %> <h3>Congratulations, you don't have tasks for this day!</h3>
                <%} else {%>
                <table id="tableTitle">
                    <tr>
                        <th>Title</th>
                        <th>Deadline</th>
                    </tr>
                </table>
                <table id="taskList">
                    <%ArrayList<String> titles = userTaskList.getTitle();
                        ArrayList<java.util.Date> deadlines = userTaskList.getDeadline();
                        ArrayList<Integer> primaryKeys = userTaskList.getPrimaryKey();
                        ArrayList<Integer> priorities = userTaskList.getPriority();
                        for (int i = 0; i < userTaskList.getPrimaryKey().size(); i++) {%>
                    <% String priorityColor;
                        if (priorities.get(i) == 5) {
                            priorityColor = "#C34A2C";
                        } else if (priorities.get(i) == 4) {
                            priorityColor = "orange";
                        } else if (priorities.get(i) == 3) {
                            priorityColor = "#EDE275";
                        } else if (priorities.get(i) == 2) {
                            priorityColor = "#9ACD32";
                        } else if (priorities.get(i) == 1) {
                            priorityColor = "green";
                        } else {
                            priorityColor = "gray";
                        }
                    %>
                    <tr style="background: <%=priorityColor%>">
                        <td style="display:none"><%= primaryKeys.get(i)%></td>
                        <td class="taskData"><%= titles.get(i)%></td>
                        <%if (deadlines.get(i) != null) {%>
                        <td class="taskData"><%=deadlines.get(i)%></td>
                        <%}%>
                    </tr>
                    <%}
                    }%>

                </table>
            </div>
        </div>

        <div id="dateOptions">
            <form action="Controller" method="GET" enctype="text/plain">
                <div id="dateButtons">
                    <input class="myButton" type="submit" name="action" value="Yesterday" />
                    <input class="myButton" type="submit" name="action" value="Tomorrow" />
                </div> 
            </form>
        </div> 

    </body>
</html>
