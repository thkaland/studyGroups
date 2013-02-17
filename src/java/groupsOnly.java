
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "groupsOnly", urlPatterns = {"/groupsOnly"})
public class groupsOnly extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String type = request.getParameter("type");
        Statement stmt;
        ResultSet rs;
        ResultSetMetaData rsMD;
        Connection con;
        int counter;

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
                + "\n"
                + "            <form class=\"form-table\" action=\"groupsOnly\" method=\"post\">\n");

        try {


            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/studyGroups?"
                    + "user=root&password=123456";
            con = DriverManager.getConnection(connectionUrl);

            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT * FROM " + type + "Groups");

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
                    + "     <a href=\"index.jsp\" style=\"font-weight:bold\">Home</a>"
                    + "</p>");


        } catch (SQLException e) {
            out.println("<a>Problem with mysql.</a><br>");
            out.println("<a href=\"index.jsp\" style=\"font-weight:bold\">Try again</a>");
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
