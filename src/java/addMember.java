
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "addMember", urlPatterns = {"/addMember"})
public class addMember extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String type = request.getParameter("type");
        String username = request.getParameter("username");
        String title = request.getParameter("title");
        Statement stmt;
        ResultSet rs;
        Connection con;
        HttpSession session = request.getSession();
        boolean exists = false;

        out.println("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\" />\n"
                + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"> \n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \n"
                + "        <title>Add new Member</title>\n"
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
                + "            <form class=\"form-1\" action=\"addMember\" method=\"post\">\n"
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
            rs = stmt.executeQuery("SELECT * FROM users WHERE Username='" + username + "'");

            if (rs.next()) {
                exists = true;
            }

            if (!exists) {

                out.println("<a>User \"" + username + "\" does not exist</a><br>");
                out.println("<a href=\"addMember.jsp?type=" + type + "\" style=\"font-weight:bold\">Try again</a>");

                con.close();
                stmt.close();
                rs.close();
                return;

            }


            rs = stmt.executeQuery("SELECT * FROM " + type + "GroupsMembers WHERE Username='" + username
                    + "' AND Title='" + title + "'");

            if (rs.next()) {

                out.println("<a>\"" + username + "\" is already a member of \"" + title + "\"</a><br>");
                out.println("<a href=\"addMember.jsp?type=" + type + "\" style=\"font-weight:bold\">Try again</a>");

                con.close();
                stmt.close();
                rs.close();
                return;

            }


            rs = stmt.executeQuery("SELECT * FROM " + type + "Groups WHERE Title='" + title
                    + "' AND Empty='0'");

            if (rs.next()) {

                out.println("<a>\"" + title + "\" is full!</a><br>");
                out.println("<a href=\"addMember.jsp?type=" + type + "\" style=\"font-weight:bold\">Try again</a>");

                con.close();
                stmt.close();
                rs.close();
                return;

            }


            rs = stmt.executeQuery("SELECT * FROM " + type + "Groups WHERE Title='" + title
                    + "' AND Creator='" + session.getAttribute("user").toString() + "'");

            if (rs.next()) {

                int em = Integer.parseInt(rs.getString(3));
                em--;
                String empty = Integer.toString(em);


                stmt.execute("INSERT INTO " + type + "GroupsMembers VALUES('" + username + "', '"
                        + title + "', 'active')");

                stmt.execute("UPDATE " + type + "Groups SET Empty='" + empty + "' WHERE Title='" + title + "'");

                rs = stmt.executeQuery("SELECT * FROM users WHERE Username='" + username + "'");

                String msg = "I am \"" + session.getAttribute("user").toString()
                        + "\", creator of \"" + title + "\" and you are now a new member of this " + type + "G!";

                if (rs.next()) {
                    SMTPAuthenticator.sendMail(rs.getString(3), "Welcome to \"" + title + "\" " + type + "G!", msg);
                }

                out.println("<a>\"" + username + "\" is now a new member of \"" + title + "\"</a><br>");
                out.println("<a href=\"mainPage.jsp\" style=\"font-weight:bold\">Home</a>");

            } else {

                out.println("<a>No " + type + " groups named \"" + title + "\" created by \""
                        + session.getAttribute("user").toString() + "\"</a><br>");
                out.println("<a href=\"addMember.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");

            }

            stmt.close();
            rs.close();
            con.close();

        } catch (SQLException e) {
            out.println("<a>Problem with mysql.</a><br>");
            out.println("<a href=\"addMember.jsp?type=" + type + "\" style=\"font-weight:bold\">Try again</a>");
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
                + "</html>\n"
                + "");

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
