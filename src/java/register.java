
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "register", urlPatterns = {"/register"})
public class register extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
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
                + "<title>Sign Up</title>\n"
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
                + "<form class=\"form-1\" action=\"register\" method=\"post\">\n"
                + "<p class=\"field\">");


        try {

            if ((username.equals("")) || (username.equals("null"))) {
                out.println("<a>Invalid username</a><br>");
                out.println("<a href=\"register.html\" style=\"font-weight:bold\">Try Again</a>");
            } else if (email.equals("")) {
                out.println("<a>Invalid email</a><br>");
                out.println("<a href=\"register.html\" style=\"font-weight:bold\">Try Again</a>");
            } else {

                try {

                    Class.forName("com.mysql.jdbc.Driver");
                    String connectionUrl = "jdbc:mysql://localhost/studyGroups?"
                            + "user=root&password=123456";
                    con = DriverManager.getConnection(connectionUrl);

                    stmt = con.prepareStatement("SELECT * FROM users WHERE Username = ?");
                    stmt.setString(1, username);
                    rs = stmt.executeQuery();

                    /*
                     stmt = con.createStatement();

                     rs = stmt.executeQuery("SELECT * FROM users WHERE Username='" + username + "'");
                     */

                    if (rs.next()) {

                        out.println("<a>Username exists</a><br>");
                        out.println("<a href=\"register.html\" style=\"font-weight:bold\">Try Again</a>");

                        con.close();
                        stmt.close();
                        rs.close();
                        return;

                    }

                    stmt = con.prepareStatement("SELECT * FROM users WHERE Email = ?");
                    stmt.setString(1, email);

                    rs = stmt.executeQuery();

                    /*
                     rs = stmt.executeQuery("SELECT * FROM users WHERE Email='" + email + "'");
                     */

                    if (rs.next()) {

                        out.println("<a>Email exists</a><br>");
                        out.println("<a href=\"register.html\" style=\"font-weight:bold\">Try Again</a>");

                        con.close();
                        stmt.close();
                        rs.close();
                        return;

                    }

                    stmt = con.prepareStatement("INSERT INTO users (Username, Password, Email) values (?, ?, ?)");
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    stmt.setString(3, email);

                    /*
                     if (!stmt.execute("INSERT INTO users VALUES('" + username + "', '" + password + "','" + email + "')")) {
                     */

                    if (stmt.executeUpdate() == 1) {

                        String msg = "Hello \"" + username + "\",welcome to studyGroups!\nUsername:\"" + username
                                + "\"\nPassword:\"" + password + "\"";

                        SMTPAuthenticator.sendMail(email, "Welcome to studyGroups!", msg);

                        out.println("<a>You are now registered \"" + username + "\"</a><br>");
                        out.println("<a href=\"logIn.html\" style=\"font-weight:bold\">Log In</a>");

                    } else {

                        out.println("<h1>An error occured with mysql</h1>");
                        out.println("<a href=\"register.html\" style=\"font-weight:bold\">Try Again</a>");

                    }

                    con.close();
                    stmt.close();
                    rs.close();

                } catch (SQLException e) {
                    out.println("<a>Problem with mysql.</a><br>");
                    out.println("<a href=\"register.html\" style=\"font-weight:bold\">Try again</a>");
                    throw new ServletException("Servlet Could not display records.", e);
                } catch (ClassNotFoundException cE) {
                    System.out.println("Class Not Found Exception: " + cE.toString());
                } finally {
                    out.close();
                }
            }

        } catch (NullPointerException e) {

            out.println("<a>You must fill the <a href=\"register.html\" style=\"font-weight:bold\">form</a>!</a><br>");

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
