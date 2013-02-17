
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "studyGroupsList", urlPatterns = {"/studyGroupsList"})
public class studyGroupsList extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String stars;
        Statement stmt, stmt1;
        ResultSet rs, rs1;
        ResultSetMetaData rsMD;
        Connection con;
        HttpSession session = request.getSession();
        int counter, i, mean, countVotes;

        out.println("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\" />\n"
                + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"> \n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \n"
                + "        <title>Existing " + type + " Groups</title>\n"
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

            out.println("            <form class=\"form-1\" action=\"studyGroupsList\" method=\"post\">\n");

            out.println("           <p class=\"field\">\n"
                    + "                 <a>You must <a href=\"logIn.html\" style=\"font-weight:bold\">Log In</a>!</a><br>"
                    + "             </p>"
                    + "            </form>\n");

            return;

        }

        out.println("            <form class=\"form-table\" action=\"studyGroupsList\" method=\"post\">\n");

        try {

            session.setAttribute("title", title);

            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/studyGroups?"
                    + "user=root&password=123456";
            con = DriverManager.getConnection(connectionUrl);

            stmt = con.createStatement();
            stmt1 = con.createStatement();


            if (title.equals("")) {
                rs = stmt.executeQuery("SELECT * FROM " + type + "Groups");
            } else {
                rs = stmt.executeQuery("SELECT * FROM " + type + "Groups WHERE Title='" + title + "'");
            }


            rsMD = rs.getMetaData();
            counter = 1;

            out.println("<p class=\"field\">\n"
                    + "     <a>" + type + " Groups<a>"
                    + "</p>");


            while (rs.next()) {

                //Prints column names
                if (counter <= 1) {

                    out.println("<table id=\"one-column-emphasis\" >");
                    out.println("<tbody>");
                    out.println("<thead>");

                    for (; counter <= rsMD.getColumnCount(); counter++) {

                        out.println("<th>" + rsMD.getColumnName(counter) + "</th>");

                    }

                    out.println("<th>Rating</th>");

                    out.println("</thead>");
                }

                counter = 1;

                out.println("<tr>");

                // Prints row data
                for (; counter <= rsMD.getColumnCount(); counter++) {

                    out.println("<td>" + rs.getString(counter) + "</td>");
                }

                countVotes = 0;
                mean = 0;

                rs1 = stmt1.executeQuery("SELECT * FROM " + type + "GroupsVotes WHERE Title='" + rs.getString(1) + "'");

                while (rs1.next()) {
                    mean += rs1.getInt(3);
                    countVotes++;
                }
                if (countVotes != 0) {
                    mean = mean / countVotes;
                }

                stars = "";

                for (i = 0; i < 5; i++) {
                    if (i < mean) {
                        stars += "<a href=\"voteSG?title=" + rs.getString(1) + "&vote=" + (i + 1) + "&type=" + type + "\"><img src=/finalProject/images/yellow_star.gif></a>";
                    } else {
                        stars += "<a href=\"voteSG?title=" + rs.getString(1) + "&vote=" + (i + 1) + "&type=" + type + "\"><img src=/finalProject/images/white_star.gif></a>";
                    }
                }
                out.println("<td>" + stars + "</td>");

                out.println("</tr>");

            }

            out.println("</tbody>");
            out.println("</table>");


            if (title.equals("")) {
                rs = stmt.executeQuery("SELECT * FROM " + type + "GroupsMembers");
            } else {
                rs = stmt.executeQuery("SELECT * FROM " + type + "GroupsMembers WHERE Title='" + title + "'");
            }

            rsMD = rs.getMetaData();
            counter = 1;

            out.println("<p class=\"field\">\n"
                    + "     <a>" + type + " Groups Members<a>"
                    + "</p>");

            while (rs.next()) {

                //Prints column names
                if (counter <= 1) {

                    out.println("<table id=\"one-column-emphasis\" >");
                    out.println("<tbody>");
                    out.println("<thead>");

                    for (; counter <= rsMD.getColumnCount(); counter++) {

                        out.println("<th>" + rsMD.getColumnName(counter) + "</th>");

                    }

                    out.println("</thead>");
                }

                counter = 1;

                out.println("<tr>");

                // Prints row data
                for (; counter <= rsMD.getColumnCount(); counter++) {

                    out.println("<td>" + rs.getString(counter) + "</td>");
                }

                out.println("</tr>");

            }

            out.println("</tbody>");
            out.println("</table>");

            stmt.close();
            rs.close();
            con.close();

            out.println("<p class=\"field\">\n"
                    + "     <a href=\"studyGroupsList.jsp?type=" + type + "\" style=\"font-weight:bold\">Try another search</a><br>"
                    + "     <a href=\"mainPage.jsp\" style=\"font-weight:bold\">Home</a>"
                    + "</p>");


        } catch (SQLException e) {
            out.println("<a>Problem with mysql.</a><br>");
            out.println("<a href=\"studyGroupsList.jsp\" style=\"font-weight:bold\">Try again</a>");
            throw new ServletException("Servlet Could not display records.", e);
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        } catch (NullPointerException e) {

            out.println("<p class=\"field\">\n"
                    + "<a>You must fill the <a href=\"studyGroupsList.jsp\" style=\"font-weight:bold\">form</a>!</a><br>"
                    + "</p>");

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
