
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "voteSG", urlPatterns = {"/voteSG"})
public class voteSG extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String title = request.getParameter("title");
        String vote = request.getParameter("vote");
        String type = request.getParameter("type");

        Statement stmt;
        ResultSet rs;
        Connection con;
        HttpSession session = request.getSession();

        int v;
        boolean exists = false;

        out.println("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\" />\n"
                + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"> \n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \n"
                + "        <title>Vote</title>\n"
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
                + "            <form class=\"form-1\" action=\"voteSG\" method=\"post\">\n"
                + "                <p class=\"field\">");


        try {

            System.out.println("User logged:" + session.getAttribute("user").toString());

            v = Integer.parseInt(vote);

        } catch (NullPointerException e) {

            out.println("<a>You must <a href=\"logIn.html\" style=\"font-weight:bold\">Log in</a>!</a><br>");
            return;

        } catch (NumberFormatException nfe) {

            out.println("<a>You have to vote first!</a><br>");
            out.println("<a href=\"mainPage.jsp\" style=\"font-weight:bold\">Home</a>");
            return;

        }
        try {

            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/studyGroups?"
                    + "user=root&password=123456";
            con = DriverManager.getConnection(connectionUrl);

            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT * FROM " + type + "GroupsMembers WHERE Username='"
                    + session.getAttribute("user").toString() + "' AND Title='" + title + "' AND Status='active'");

            if (rs.next()) {
                exists = true;
            }

            if (exists) {

                rs = stmt.executeQuery("SELECT * FROM " + type + "GroupsVotes WHERE Username='"
                        + session.getAttribute("user").toString() + "' AND Title='" + title + "'");

                if (rs.next()) {

                    out.println("<a>You have already voted for this SG!</a><br>");
                    out.println("<a href=\"javascript:history.go(-1);\" style=\"font-weight:bold\">Back</a>");
                    return;

                }

                stmt.execute("INSERT INTO " + type + "GroupsVotes VALUES('" + session.getAttribute("user").toString() + "', '"
                        + title + "', '" + vote + "')");
                out.println("<a>Thank you for voting!</a><br>");
                out.println("<a href=\"/finalProject/studyGroupsList?title=" + session.getAttribute("title").toString() + "&type=" + type + "\" style=\"font-weight:bold\">Back</a>");

            } else {
                out.println("<a>You cannot vote for this SG,because you are not an authorized member!</a><br>");
                out.println("<a href=\"javascript:history.go(-1);\" style=\"font-weight:bold\">Back</a>");
            }


        } catch (SQLException e) {
            out.println("<a>Problem with mysql.</a><br>");
            out.println("<a href=\"studyGroupsList.jsp\" style=\"font-weight:bold\">Try again</a>");
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
    }// </editor-fold>
}
