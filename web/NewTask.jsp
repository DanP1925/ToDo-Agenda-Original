<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Task</title>
        <link rel="stylesheet" href="css/task.css">
    </head>
    <body id ="newTaskBody">

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
                    <h2><%= welcomeMessage%> New Task</h2>
                    <% java.util.Date currentDate = (java.util.Date) session.getAttribute("currentDate");
                        java.text.SimpleDateFormat formateer = new java.text.SimpleDateFormat("EEEEE dd MMMMMMM yyyy");
                        String output = formateer.format(currentDate);
                    %>
                    <h3><%= output%></h3>
                </div>
            </div>
        </div>

        <form id="newTask" action="Controller" method="GET" enctype="text/plain">

            <div id="newTaskHead">
                <div id="newTaskTitle">
                    <label for="title"> Title(*): </label>
                    <input type="text" name="title" id="title" size="20" /><br />
                </div>
                <div id="newTaskDates">
                    <label for="createdDate"> Created Date(*):</label>
                    <input type="date" name="createdDate" id="createdDate"/><br />
                    <label for="deadline"> Deadline:</label>
                    <input type="date" name="deadline" id="deadline"/><br />            
                </div>
            </div>
            <div id="newTaskDescription">
                <label for="description"> Description: </label>
                <input type="text" name="description" id="description" size="20" /><br />
            </div>

            <label style="color: #FFF" for="priority"> Priority(*):</label>
            <select name="priority">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>

            <%String noTitle = (String) request.getAttribute("noTitle");
                if (noTitle != null && noTitle.equals("true")) {
            %> <h3> Title should have a value </h3>
            <%}%>

            <%String noCreatedDate = (String) request.getAttribute("noCreatedDate");
                if (noCreatedDate != null && noCreatedDate.equals("true")) {
            %> <h3> Created Date should have a value </h3>
            <%}%>

            <br /> <br />
            <div id="newTaskButtons">
                <input class="myButton" type="submit" name="action" value="Back"/>
                <input class="myButton" type="submit" name="action" value="Submit" />
                <input type="hidden" name="lastPage" value="newTask"/>
            </div>
        </form>
    </body>
</html>
