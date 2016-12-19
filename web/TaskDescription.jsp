<%-- 
    Document   : TaskDescription
    Created on : 29/02/2016, 09:23:55 PM
    Author     : Daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Task Description</title>
        <link rel="stylesheet" href="css/task.css">
    </head>
    <body id ="descriptionBody">
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
                    <h2><%= welcomeMessage%> Task Description</h2>
                    <% java.util.Date currentDate = (java.util.Date) session.getAttribute("currentDate");
                        java.text.SimpleDateFormat formateer = new java.text.SimpleDateFormat("EEEEE dd MMMMMMM yyyy");
                        String output = formateer.format(currentDate);
                    %>
                    <h3><%= output%></h3>
                </div>
            </div>
        </div>
        <br />

        <div id="taskData">
            <jsp:useBean id="taskObj" type="todoClasses.Task" scope="request" />
            <label>Title:</label>
            <h5><jsp:getProperty name="taskObj" property="title"/></h5>
            <label>Description:</label>
            <h5><jsp:getProperty name="taskObj" property="description"/></h5>
            <label>Created on:</label>
            <h5><jsp:getProperty name="taskObj" property="createdDate"/></h5>
            <label>Deadline:</label>
            <% if (taskObj.getDeadline() != null) { %>
            <h5><jsp:getProperty name="taskObj" property="deadline"/></h5>
            <%} else {%>
            <h5> The task doesn't have a deadline</h5>
            <%}%>
            <label>Priority: </label>
            <% if (taskObj.getPriority() != -1) { %>
            <h5><jsp:getProperty name="taskObj" property="priority"/></h5>
            <%} else {%>
            <h5>The task is already completed c:</h5>
            <%}%>
        </div>

        <br />
        <form id="backButton" action="Controller" method="GET" enctype="text/plain">
            <input class="myButton" type="submit" name="action" value="Back" />
            <input type="hidden" name="lastPage" value="taskDescription">
        </form>
    </body>
</html>
