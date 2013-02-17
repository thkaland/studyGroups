
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "signUp", urlPatterns = {"/signUp"})
public class signUp extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String type = request.getParameter("type");
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
                + "        <title>Become a Member</title>\n"
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
                + "            <form class=\"form-1\" action=\"signUp\" method=\"post\">\n"
                + "                <p class=\"field\">");

        try {

            if (type.equals("null")) {
                out.println("<p class=\"field\">\n"
                        + "<a>You must fill the <a href=\"signUp.jsp\" style=\"font-weight:bold\">form</a>!</a><br>"
                        + "</p>");
                return;
            }

        } catch (NullPointerException e) {

            out.println("<p class=\"field\">\n"
                    + "<a>You must fill the <a href=\"signUp.jsp\" style=\"font-weight:bold\">form</a>!</a><br>"
                    + "</p>");
            return;

        }

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
            rs = stmt.executeQuery("SELECT * FROM " + type + "Groups WHERE Title='" + title + "'");

            if (rs.next()) {
                exists = true;
            }

            if (exists) {

                rs = stmt.executeQuery("SELECT * FROM " + type + "GroupsMembers WHERE Username='" + session.getAttribute("user").toString()
                        + "' AND Title='" + title + "'");

                if (rs.next()) {

                    out.println("<a>You are already a member of \"" + title + "\"</a><br>");
                    out.println("<a href=\"signUp.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");

                    con.close();
                    stmt.close();
                    rs.close();
                    return;

                }


                stmt.execute("INSERT INTO " + type + "GroupsMembers VALUES('"
                        + session.getAttribute("user").toString() + "', '" + title + "', 'inactive')");


                rs = stmt.executeQuery("SELECT * FROM " + type + "Groups WHERE Title='" + title + "'");

                if (rs.next()) {

                    String creator = rs.getString(4);

                    rs = stmt.executeQuery("SELECT * FROM users WHERE Username='" + creator + "'");

                    String msg = "I am \"" + session.getAttribute("user").toString()
                            + "\",please authorise my membership in \"" + title + "\" " + type + "G!";

                    if (rs.next()) {
                        SMTPAuthenticator.sendMail(rs.getString(3), "\"" + title + "\" membership!", msg);
                    }


                }


                out.println("<a>You are now waiting for confirmation on becoming a new member of \""
                        + title + "\"</a><br>");
                out.println("<a href=\"mainPage.jsp\" style=\"font-weight:bold\">Home</a>");



            } else {

                out.println("<a>There is no " + type + " Group named \"" + title + "\"</a><br>");
                out.println("<a href=\"signUp.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");

            }


            stmt.close();
            rs.close();
            con.close();

        } catch (SQLException e) {
            out.println("<a>Problem with mysql.</a><br>");
            out.println("<a href=\"signUp.jsp?type=" + type + "\" style=\"font-weight:bold\">Try again</a>");
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
