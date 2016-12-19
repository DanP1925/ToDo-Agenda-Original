<%-- 
    Document   : newUser
    Created on : 27/02/2016, 08:47:39 PM
    Author     : Daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New User</title>
        <link rel="stylesheet" href="css/task.css">
    </head>
    <body>
        <h2 id="informationLabel"> Insert your information here:</h2>
        <form id="newData" action="Controller" method="GET" enctype="text/plain">

            <div id ="newUserInput">
                <label for="firstName">First Name:</label>
                <input type="text" name="firstName" id="firstName" size="20" /><br />
                <label for="lastName">Last Name:</label>
                <input type="text" name="lastName" id="lastName" size="20" /><br />
                <label for="username">Username:</label>
                <input type="text" name="username" id="username" size="20" /><br />
                <label for="password">Password:</label>
                <input type="text" name="password" id="password" size="20" /><br />
                <label>Gender:</label>
                <input type="radio" name="gender" value="male" checked> Male
                <input type="radio" name="gender" value="female"> Female<br>
            </div>

            <div id ="newUserOptions">
                <input class="myButton" type="submit" name="action" value="Submit" />
                <input class="myButton" type="submit" name="action" value="Back"/>
                <input type="hidden" name="lastPage" value="newUser">
            </div>

        </form>

        <%String noFirstName = (String) request.getAttribute("noFirstName");
            if (noFirstName != null && noFirstName.equals("true")) {
        %> <h3> First Name should have a value </h3>
        <%}%>

        <%String noLastName = (String) request.getAttribute("noLastName");
            if (noLastName != null && noLastName.equals("true")) {
        %> <h3> Last Name should have a value </h3>
        <%}%>

        <% String noUsername = (String) request.getAttribute("noUsername");
            if (noUsername != null && noUsername.equals("true")) {
        %> <h3> Username should have a value </h3>
        <%}%>

        <% String noPassword = (String) request.getAttribute("noPassword");
            if (noPassword != null && noPassword.equals("true")) {
        %> <h3> Password should have a value </h3>
        <%}%>
    </body>
</html>
