
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "logIn", urlPatterns = {"/logIn"})
public class logIn extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        /*
         Statement stmt;
         */

        PreparedStatement stmt;
        ResultSet rs;
        Connection con;

        out.println("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\" />\n"
                + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"> \n"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \n"
                + "<title>Log In</title>\n"
                + "<meta name=\"description\" content=\"Custom Login Form Styling with CSS3\" />\n"
                + "<meta name=\"keywords\" content=\"css3, login, form, custom, input, submit, button, html5, placeholder\" />\n"
                + "<meta name=\"author\" content=\"Codrops\" />\n"
                + "<link rel=\"shortcut icon\" href=\"../favicon.ico\"> \n"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />\n"
                + "<script src=\"js/modernizr.custom.63321.js\"></script>\n"
                + "\n"
                + "</head>\n"
                + "<body>\n"
                + "<div>\n"
                + "\n"
                + "\n"
                + "<form class=\"form-1\" action=\"logIn\" method=\"post\">\n"
                + "<p class=\"field\">");

        try {

            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/studyGroups?"
                    + "user=root&password=123456";
            con = DriverManager.getConnection(connectionUrl);

            stmt = con.prepareStatement("SELECT * FROM users WHERE Username = ? AND Password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            /*
             stmt = con.createStatement();

             rs = stmt.executeQuery("SELECT * FROM users WHERE Username='" + username + "' AND Password='" + password + "'");
             */

            if (rs.next()) {

                session.setAttribute("user", username);
                response.sendRedirect("/finalProject/mainPage.jsp");

                stmt.close();
                rs.close();
                con.close();
                return;

            }

            out.println("<a>Login failed</a><br>");
            out.println("<a href=\"logIn.html\" style=\"font-weight:bold\">Try Again</a>");

            stmt.close();
            rs.close();
            con.close();

        } catch (SQLException e) {
            out.println("<a>Problem with mysql.</a><br>");
            out.println("<a href=\"logIn.html\" style=\"font-weight:bold\">Try again</a>");
            throw new ServletException("Servlet Could not display records.", e);
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        } finally {
            out.close();
        }

        out.println("</p>\n"
                + "</form>\n"
                + "\n"
                + "\n"
                + "</div>\n"
                + "</body>\n"
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
