package todoClasses;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniel
 */
public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setHeader("Cache-Control", "no-cache");
        
        DBUtil dbTool = new DBUtil();
        Util Toolkit = new Util();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(300);

        boolean test = false;
        String address = "/Login.html";
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        if (action.equals("Login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (dbTool.isUserInDB(username, password)) {
                String remember = request.getParameter("remember");
                if (remember == null) {
                    remember = "";
                }
                if (remember.equals("yes")) {
                    Toolkit.setCookie(response, "username", username, 60 * 60 * 24 * 30);
                    Toolkit.setCookie(response, "password", password, 60 * 60 * 24 * 30);
                }

                UserData userObject = dbTool.getUser(username, password);
                session.setAttribute("userObj", userObject);
                TaskList userTaskList = dbTool.getTaskList(userObject.getPrimaryKey(), new Date());
                request.setAttribute("userTaskList", userTaskList);
                session.setAttribute("currentDate", new Date());

                address = "/DailyView.jsp";
            }
        } else if (action.equals("NewUser")) {
            address = "/NewUser.jsp";
        } else if (action.equals("Submit")) {
            String lastPage = request.getParameter("lastPage");
            if (lastPage.equals("newUser")) {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String gender = request.getParameter("gender");

                boolean valuesAreGood = true;
                if (!Toolkit.isGoodFormat(firstName)) {
                    valuesAreGood = false;
                    request.setAttribute("noFirstName", "true");
                }
                if (!Toolkit.isGoodFormat(lastName)) {
                    valuesAreGood = false;
                    request.setAttribute("noLastName", "true");
                }
                if (!Toolkit.isGoodFormat(username)) {
                    valuesAreGood = false;
                    request.setAttribute("noUsername", "true");
                }
                if (!Toolkit.isGoodFormat(password)) {
                    valuesAreGood = false;
                    request.setAttribute("noPassword", "true");
                }
                if (valuesAreGood) {
                    dbTool.createUser(firstName, lastName, username, password, gender);
                    address = "/Login.html";
                } else {
                    address = "/NewUser.jsp";
                }
            } else if (lastPage.equals("newTask")) {
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String createdDate = request.getParameter("createdDate");
                String deadline = request.getParameter("deadline");
                String priority = request.getParameter("priority");
                boolean valuesAreGood = true;
                if (!Toolkit.isGoodFormat(title)) {
                    valuesAreGood = false;
                    request.setAttribute("noTitle", "true");
                }
                if (!Toolkit.isGoodFormat(createdDate)) {
                    valuesAreGood = false;
                    request.setAttribute("noCreatedDate", "true");
                }

                if (valuesAreGood) {
                    Date newDate = (Date) session.getAttribute("currentDate");
                    UserData userObject = (UserData) session.getAttribute("userObj");
                    dbTool.createTask(userObject.getPrimaryKey(),title, description, createdDate, deadline, priority);
                    TaskList userTaskList = dbTool.getTaskList(userObject.getPrimaryKey(), newDate);
                    request.setAttribute("userTaskList", userTaskList);
                    address = "/DailyView.jsp";
                } else {
                    address = "/NewTask.jsp";
                }
            }
        } else if (action.equals("Back")) {
            String lastPage = request.getParameter("lastPage");
            if (lastPage.equals("newUser")) {
                address = "/Login.html";
            } else if (lastPage.equals("newTask")||lastPage.equals("taskDescription")) {
                Date newDate = (Date) session.getAttribute("currentDate");
                UserData userObject = (UserData) session.getAttribute("userObj");
                TaskList userTaskList = dbTool.getTaskList(userObject.getPrimaryKey(), newDate);
                request.setAttribute("userTaskList", userTaskList);

                address = "/DailyView.jsp";
            }
        } else if (action.equals("LogOut")) {
            Toolkit.setCookie(response, "username", "", 0);
            Toolkit.setCookie(response, "password", "", 0);
            session.invalidate();
            address = "/Login.html";
        } else if (action.equals("Add Task")) {
            address = "/NewTask.jsp";
        } else if (action.equals("Complete Task")){
            String taskPK = request.getParameter("taskKey");
            if (!taskPK.equals("noKey")){
                dbTool.completeTask(taskPK);
            }
            Date newDate = (Date) session.getAttribute("currentDate");
            UserData userObject = (UserData) session.getAttribute("userObj");
            TaskList userTaskList = dbTool.getTaskList(userObject.getPrimaryKey(), newDate);
            request.setAttribute("userTaskList", userTaskList);
            address = "/DailyView.jsp";
        } else if (action.equals("Delete Task")){
            String taskPK = request.getParameter("taskKey");
            if (!taskPK.equals("noKey")){
                dbTool.deleteTask(taskPK);
            }
            Date newDate = (Date) session.getAttribute("currentDate");
            UserData userObject = (UserData) session.getAttribute("userObj");
            TaskList userTaskList = dbTool.getTaskList(userObject.getPrimaryKey(), newDate);
            request.setAttribute("userTaskList", userTaskList);
            address = "/DailyView.jsp";
        } else if (action.equals("Yesterday")) {
            Date newDate = (Date) session.getAttribute("currentDate");
            newDate.setTime(newDate.getTime() - 24 * 60 * 60 * 1000); //Substract a day in milliseconds 

            UserData userObject = (UserData) session.getAttribute("userObj");
            TaskList userTaskList = dbTool.getTaskList(userObject.getPrimaryKey(), newDate);
            request.setAttribute("userTaskList", userTaskList);
            session.setAttribute("currentDate", newDate);

            address = "/DailyView.jsp";
        } else if (action.equals("Tomorrow")) {
            Date newDate = (Date) session.getAttribute("currentDate");
            newDate.setTime(newDate.getTime() + 24 * 60 * 60 * 1000); //Add a day in milliseconds 

            UserData userObject = (UserData) session.getAttribute("userObj");
            TaskList userTaskList = dbTool.getTaskList(userObject.getPrimaryKey(), newDate);
            request.setAttribute("userTaskList", userTaskList);
            session.setAttribute("currentDate", newDate);

            address = "/DailyView.jsp";
        } else if (action.equals("taskDescription")){
            String taskPK = request.getParameter("taskKey");
            Task taskObject = dbTool.getTask(taskPK);
            request.setAttribute("taskObj", taskObject);
            address = "/TaskDescription.jsp";
        } else if (action.equals("Monthly View")){
            UserData userObject = (UserData) session.getAttribute("userObj");
            Task currentTask = dbTool.getPriorityTask(userObject.getPrimaryKey(), new Date());
            request.setAttribute("currentTask", currentTask);
            session.setAttribute("currentDate", new Date());
            address = "/MonthlyView.jsp";
        } else if (action.equals("taskMonthly")){
            int currentDay = Integer.parseInt(request.getParameter("currentDay"));
            Date currentDate = new Date();
            UserData userObject = (UserData) session.getAttribute("userObj");
            Task currentTask = dbTool.getPriorityTask(userObject.getPrimaryKey(),new Date(currentDate.getYear(), currentDate.getMonth(), currentDay));
            request.setAttribute("currentTask", currentTask);
            session.setAttribute("currentDate", new Date(currentDate.getYear(), currentDate.getMonth(), currentDay));
            address = "/MonthlyView.jsp";
        } else if (action.equals("Daily View")){
            UserData userObject = (UserData) session.getAttribute("userObj");
            TaskList userTaskList = dbTool.getTaskList(userObject.getPrimaryKey(), new Date());
            request.setAttribute("userTaskList", userTaskList);
            session.setAttribute("currentDate", new Date());

            address = "/DailyView.jsp";
        } else if (action.equals("")) {
            if (Toolkit.isCookie(request, "username")) {
                UserData userObject = dbTool.getUser(Toolkit.getCookieValue(request, "username"), Toolkit.getCookieValue(request, "password"));
                session.setAttribute("userObj", userObject);
                TaskList userTaskList = dbTool.getTaskList(userObject.getPrimaryKey(), new Date());
                request.setAttribute("userTaskList", userTaskList);
                session.setAttribute("currentDate", new Date());

                address = "/DailyView.jsp";
            }
        }

        if (!test) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>TEST CONTROLLER</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>" + request.getParameter("taskKey") + "</h1>");
                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
