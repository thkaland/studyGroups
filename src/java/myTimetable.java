
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "myTimetable", urlPatterns = {"/myTimetable"})
public class myTimetable extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String title;
        Statement stmt, stmt1;
        ResultSet rs, rs1;
        ResultSetMetaData rsMD;
        Connection con;
        int counter;
        boolean created = false;
        HttpSession session = request.getSession();

        out.println("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\" />\n"
                + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"> \n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \n"
                + "        <title>My Timetable</title>\n"
                + "        <meta name=\"description\" content=\"Custom Login Form Styling with CSS3\" />\n"
                + "        <meta name=\"keywords\" content=\"css3, login, form, custom, input, submit, button, html5, placeholder\" />\n"
                + "        <meta name=\"author\" content=\"Codrops\" />\n"
                + "        <link rel=\"shortcut icon\" href=\"../favicon.ico\"> \n"
                + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />\n"
                + "        <script src=\"js/modernizr.custom.63321.js\"></script>\n"
                + "\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <div>\n"
                + "\n"
                + "\n");

        try {

            System.out.println("User logged:" + session.getAttribute("user").toString());

        } catch (NullPointerException e) {

            out.println("            <form class=\"form-1\" action=\"myTimetable\" method=\"post\">\n");

            out.println("           <p class=\"field\">\n"
                    + "                 <a>You must <a href=\"logIn.html\" style=\"font-weight:bold\">Log In</a>!</a><br>"
                    + "             </p>"
                    + "            </form>\n");

            return;

        }

        out.println("            <form class=\"form-table\" action=\"myTimetable\" method=\"post\">\n");

        try {

            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/studyGroups?"
                    + "user=root&password=123456";
            con = DriverManager.getConnection(connectionUrl);


            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT * FROM studyGroupsMembers WHERE Username='"
                    + session.getAttribute("user").toString() + "' AND Status='active'");

            out.println("<p class=\"field\">\n"
                    + "<a>\"" + session.getAttribute("user").toString() + "\"'s Study Group Timetable<a>"
                    + "</p>");

            while (rs.next()) {

                title = rs.getString(2);

                stmt1 = con.createStatement();

                rs1 = stmt1.executeQuery("SELECT * FROM studyGroupsDates WHERE Title='" + title + "'");

                rsMD = rs1.getMetaData();
                counter = 1;

                while (rs1.next()) {

                    //Prints column names
                    if ((counter <= 1) && (!created)) {

                        out.println("<table id=\"one-column-emphasis\" >");
                        out.println("<tbody>");
                        out.println("<thead>");

                        for (; counter <= rsMD.getColumnCount(); counter++) {

                            out.println("<th>" + rsMD.getColumnName(counter) + "</th>");

                        }

                        out.println("</thead>");
                        created = true;

                    }

                    counter = 1;

                    out.println("<tr>");

                    // Prints row data
                    for (; counter <= rsMD.getColumnCount(); counter++) {

                        out.println("<td>" + rs1.getString(counter) + "</td>");
                    }

                    out.println("</tr>");

                }

                stmt1.close();
                rs1.close();

            }
            out.println("</tbody>");
            out.println("</table>");
            
            created = false;
            
            rs = stmt.executeQuery("SELECT * FROM tutorGroupsMembers WHERE Username='"
                    + session.getAttribute("user").toString() + "' AND Status='active'");

            out.println("<p class=\"field\">\n"
                    + "<a>\"" + session.getAttribute("user").toString() + "\"'s Tutor Group Timetable<a>"
                    + "</p>");

            while (rs.next()) {

                title = rs.getString(2);

                stmt1 = con.createStatement();

                rs1 = stmt1.executeQuery("SELECT * FROM tutorGroupsDates WHERE Title='" + title + "'");

                rsMD = rs1.getMetaData();
                
                counter = 1;

                while (rs1.next()) {

                    //Prints column names
                    if ((counter <= 1) && (!created)) {

                        out.println("<table id=\"one-column-emphasis\" >");
                        out.println("<tbody>");
                        out.println("<thead>");

                        for (; counter <= rsMD.getColumnCount(); counter++) {

                            out.println("<th>" + rsMD.getColumnName(counter) + "</th>");

                        }

                        out.println("</thead>");
                        created = true;

                    }

                    counter = 1;

                    out.println("<tr>");

                    // Prints row data
                    for (; counter <= rsMD.getColumnCount(); counter++) {

                        out.println("<td>" + rs1.getString(counter) + "</td>");
                    }

                    out.println("</tr>");

                }

                stmt1.close();
                rs1.close();

            }
            out.println("</tbody>");
            out.println("</table>");

            stmt.close();
            rs.close();
            con.close();

            out.println("<p class=\"field\">\n"
                    + "<a href=\"mainPage.jsp\" style=\"font-weight:bold\">Home</a>"
                    + "</p>");

        } catch (SQLException e) {
            out.println("<a>Problem with mysql.</a><br>");
            out.println("<a href=\"/finalProject/myTimetable\" style=\"font-weight:bold\">Try again</a>");
            throw new ServletException("Servlet Could not display records.", e);
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        } finally {
            out.close();
        }

        out.println("            </form>\n"
                + "\n"
                + "\n"
                + "        </div>\n"
                + "    </body>\n"
                + "</html>");


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
