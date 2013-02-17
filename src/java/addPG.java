
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "addPG", urlPatterns = {"/addPG"})
public class addPG extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String title = request.getParameter("title");

        String description = request.getParameter("description");

        Statement stmt;
        ResultSet rs;
        Connection con;
        HttpSession session = request.getSession();

        out.println("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\" />\n"
                + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"> \n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \n"
                + "        <title>Add Project Group</title>\n"
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
                + "            <form class=\"form-1\" action=\"createSG\" method=\"post\">\n"
                + "                <p class=\"field\">");


        try {

            System.out.println("User logged:" + session.getAttribute("user").toString());

        } catch (NullPointerException e) {

            out.println("<a>You must <a href=\"logIn.html\" style=\"font-weight:bold\">Log in</a>!</a><br>");
            return;

        }



        try {

            if ((title.equals("")) || (title.equals("null"))) {

                out.println("<a>Invalid Title</a><br>");
                out.println("<a href=\"addPG.jsp\" style=\"font-weight:bold\">Try Again</a>");
                return;

            }

            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/studyGroups?"
                    + "user=root&password=123456";
            con = DriverManager.getConnection(connectionUrl);

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM projectGroups WHERE Title='" + title + "'");

            if (rs.next()) {

                out.println("<a>Title exists</a><br>");
                out.println("<a href=\"addPG.jsp\" style=\"font-weight:bold\">Try Again</a>");

                con.close();
                stmt.close();
                rs.close();
                return;

            }


            rs = stmt.executeQuery("SELECT * FROM users WHERE Username='" + session.getAttribute("user").toString() + "'");

            String msg = "Congratulations \"" + session.getAttribute("user").toString()
                    + "\",you have created \"" + title + "\" PG!";

            if (rs.next()) {
                SMTPAuthenticator.sendMail(rs.getString(3), "\"" + title + "\" is now a new PG!", msg);
            }

            stmt.execute("INSERT INTO projectGroups VALUES('" + title + "', '" + description + "','" + rs.getString(3) + "')");

            out.println("<a>\"" + title + "\" is now a new Project Group</a><br>");
            out.println("<a href=\"mainPage.jsp\" style=\"font-weight:bold\">Home</a>");



            con.close();
            stmt.close();
            rs.close();

        } catch (NullPointerException e) {
            out.println("<a>You must fill the <a href=\"addPG.jsp\" style=\"font-weight:bold\">form</a>!</a><br>");
        } catch (SQLException e) {
            out.println("<a>Problem with mysql.</a><br>");
            out.println("<a href=\"addPG.jsp\" style=\"font-weight:bold\">Try again</a>");
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
