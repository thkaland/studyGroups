
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "destroySG", urlPatterns = {"/destroy"})
public class destroySG extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String type = request.getParameter("type");
        String title = request.getParameter("title");
        Statement stmt, stmt1;
        ResultSet rs, rs1;
        Connection con;
        HttpSession session = request.getSession();

        out.println("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\" />\n"
                + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"> \n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \n"
                + "        <title>Destroy " + type + " Group</title>\n"
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
                + "            <form class=\"form-1\" action=\"destroySG\" method=\"post\">\n"
                + "                <p class=\"field\">");

        try {

            System.out.println("User logged:" + session.getAttribute("user").toString());

        } catch (NullPointerException e) {

            out.println("<a>You must <a href=\"logIn.html\" style=\"font-weight:bold\">Log in</a>!</a><br>");
            return;

        }


        try {

            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/studyGroups?"
                    + "user=root&password=123456";
            con = DriverManager.getConnection(connectionUrl);

            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT * FROM " + type + "Groups WHERE Title='" + title
                    + "' AND Creator='" + session.getAttribute("user").toString() + "'");

            if (rs.next()) {

                stmt.execute("DELETE FROM " + type + "Groups WHERE Title='" + title
                        + "' AND Creator='" + session.getAttribute("user").toString() + "'");


                rs = stmt.executeQuery("SELECT * FROM " + type + "GroupsMembers WHERE Title='" + title + "' AND Status='active'");

                String msg = "\"" + title + "\" " + type + "G no longer exists!";

                while (rs.next()) {

                    stmt1 = con.createStatement();
                    rs1 = stmt1.executeQuery("SELECT * FROM users WHERE Username='" + rs.getString(1) + "'");

                    if (rs1.next()) {
                        SMTPAuthenticator.sendMail(rs1.getString(3), "\"" + title + "\" " + type + "G terminated!", msg);
                    }

                    stmt1.close();
                    rs1.close();

                }

                stmt.execute("DELETE FROM " + type + "GroupsMembers WHERE Title='" + title + "'");

                stmt.execute("DELETE FROM " + type + "GroupsDates WHERE Title='" + title + "'");
                
                stmt.execute("DELETE FROM " + type + "GroupsVotes WHERE Title='" + title + "'");


                out.println("<a>" + type + " Group \"" + title + "\" has been terminated</a><br>");
                out.println("<a href=\"mainPage.jsp\" style=\"font-weight:bold\">Home</a>");

            } else {

                out.println("<a>No " + type + " groups named \"" + title + "\" created by \""
                        + session.getAttribute("user").toString() + "\"</a><br>");
                out.println("<a href=\"destroySG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");

            }

            stmt.close();
            rs.close();
            con.close();

        } catch (SQLException e) {
            out.println("<a>Problem with mysql.</a><br>");
            out.println("<a href=\"destroySG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
            throw new ServletException("Servlet Could not display records.", e);
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        } finally {
            out.close();
        }

        out.println("</p>    \n"
                + "            </form>\n"
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
