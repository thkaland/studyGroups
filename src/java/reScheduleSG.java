
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "reScheduleSG", urlPatterns = {"/reScheduleSG"})
public class reScheduleSG extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String type = request.getParameter("type");
        String title = request.getParameter("title");

        String monday = request.getParameter("monday");
        String tuesday = request.getParameter("tuesday");
        String wednesday = request.getParameter("wednesday");
        String thursday = request.getParameter("thursday");
        String friday = request.getParameter("friday");
        String saturday = request.getParameter("saturday");
        String sunday = request.getParameter("sunday");
        String temp;

        int h, m;

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
                + "        <title>Reschedule " + type + " Group</title>\n"
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
                + "            <form class=\"form-1\" action=\"reScheduleSG\" method=\"post\">\n"
                + "                <p class=\"field\">");

        try {

            System.out.println("User logged:" + session.getAttribute("user").toString());

        } catch (NullPointerException e) {

            out.println("<a>You must <a href=\"logIn.html\" style=\"font-weight:bold\">Log in</a>!</a><br>");
            return;

        }


        if ((monday == null) && (tuesday == null) && (wednesday == null)
                && (thursday == null) && (friday == null) && (saturday == null)
                && (sunday == null)) {

            out.println("<a>You hane to pick at least one day</a><br>");
            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");

        } else {

            try {

                Class.forName("com.mysql.jdbc.Driver");
                String connectionUrl = "jdbc:mysql://localhost/studyGroups?"
                        + "user=root&password=123456";
                con = DriverManager.getConnection(connectionUrl);

                stmt = con.createStatement();

                rs = stmt.executeQuery("SELECT * FROM " + type + "Groups WHERE Title='" + title
                        + "' AND Creator='" + session.getAttribute("user").toString() + "'");

                if (rs.next()) {

                    if (monday != null) {

                        monday = request.getParameter("time1h");
                        temp = request.getParameter("time1m");
                        try {
                            h = Integer.parseInt(monday);
                            m = Integer.parseInt(temp);
                        } catch (NumberFormatException nfe) {
                            out.println("<a>Hours,Minutes must be integers!</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if ((h < 0) || (h > 23) || (m < 0) || (m > 59)) {
                            out.println("<a>Hours:0-23 Minutes:0-59</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if (h < 10) {
                            monday = "0" + monday + ":";
                        } else {
                            monday = monday + ":";
                        }
                        if (m < 10) {
                            monday += "0" + temp;
                        } else {
                            monday += temp;
                        }

                    } else {
                        monday = "";
                    }
                    if (tuesday != null) {

                        tuesday = request.getParameter("time2h");
                        temp = request.getParameter("time2m");
                        try {
                            h = Integer.parseInt(tuesday);
                            m = Integer.parseInt(temp);
                        } catch (NumberFormatException nfe) {
                            out.println("<a>Hours,Minutes must be integers!</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if ((h < 0) || (h > 23) || (m < 0) || (m > 59)) {
                            out.println("<a>Hours:0-23 Minutes:0-59</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if (h < 10) {
                            tuesday = "0" + tuesday + ":";
                        } else {
                            tuesday = tuesday + ":";
                        }
                        if (m < 10) {
                            tuesday += "0" + temp;
                        } else {
                            tuesday += temp;
                        }

                    } else {
                        tuesday = "";
                    }
                    if (wednesday != null) {

                        wednesday = request.getParameter("time3h");
                        temp = request.getParameter("time3m");
                        try {
                            h = Integer.parseInt(wednesday);
                            m = Integer.parseInt(temp);
                        } catch (NumberFormatException nfe) {
                            out.println("<a>Hours,Minutes must be integers!</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if ((h < 0) || (h > 23) || (m < 0) || (m > 59)) {
                            out.println("<a>Hours:0-23 Minutes:0-59</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if (h < 10) {
                            wednesday = "0" + wednesday + ":";
                        } else {
                            wednesday = wednesday + ":";
                        }
                        if (m < 10) {
                            wednesday += "0" + temp;
                        } else {
                            wednesday += temp;
                        }

                    } else {
                        wednesday = "";
                    }
                    if (thursday != null) {

                        thursday = request.getParameter("time4h");
                        temp = request.getParameter("time4m");
                        try {
                            h = Integer.parseInt(thursday);
                            m = Integer.parseInt(temp);
                        } catch (NumberFormatException nfe) {
                            out.println("<a>Hours,Minutes must be integers!</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if ((h < 0) || (h > 23) || (m < 0) || (m > 59)) {
                            out.println("<a>Hours:0-23 Minutes:0-59</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if (h < 10) {
                            thursday = "0" + thursday + ":";
                        } else {
                            thursday = thursday + ":";
                        }
                        if (m < 10) {
                            thursday += "0" + temp;
                        } else {
                            thursday += temp;
                        }

                    } else {
                        thursday = "";
                    }
                    if (friday != null) {

                        friday = request.getParameter("time5h");
                        temp = request.getParameter("time5m");
                        try {
                            h = Integer.parseInt(friday);
                            m = Integer.parseInt(temp);
                        } catch (NumberFormatException nfe) {
                            out.println("<a>Hours,Minutes must be integers!</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if ((h < 0) || (h > 23) || (m < 0) || (m > 59)) {
                            out.println("<a>Hours:0-23 Minutes:0-59</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if (h < 10) {
                            friday = "0" + friday + ":";
                        } else {
                            friday = friday + ":";
                        }
                        if (m < 10) {
                            friday += "0" + temp;
                        } else {
                            friday += temp;
                        }

                    } else {
                        friday = "";
                    }
                    if (saturday != null) {

                        saturday = request.getParameter("time6h");
                        temp = request.getParameter("time6m");
                        try {
                            h = Integer.parseInt(saturday);
                            m = Integer.parseInt(temp);
                        } catch (NumberFormatException nfe) {
                            out.println("<a>Hours,Minutes must be integers!</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if ((h < 0) || (h > 23) || (m < 0) || (m > 59)) {
                            out.println("<a>Hours:0-23 Minutes:0-59</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if (h < 10) {
                            saturday = "0" + saturday + ":";
                        } else {
                            saturday = saturday + ":";
                        }
                        if (m < 10) {
                            saturday += "0" + temp;
                        } else {
                            saturday += temp;
                        }

                    } else {
                        saturday = "";
                    }
                    if (sunday != null) {

                        sunday = request.getParameter("time7h");
                        temp = request.getParameter("time7m");
                        try {
                            h = Integer.parseInt(sunday);
                            m = Integer.parseInt(temp);
                        } catch (NumberFormatException nfe) {
                            out.println("<a>Hours,Minutes must be integers!</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if ((h < 0) || (h > 23) || (m < 0) || (m > 59)) {
                            out.println("<a>Hours:0-23 Minutes:0-59</a><br>");
                            out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");
                            return;
                        }
                        if (h < 10) {
                            sunday = "0" + sunday + ":";
                        } else {
                            sunday = sunday + ":";
                        }
                        if (m < 10) {
                            sunday += "0" + temp;
                        } else {
                            sunday += temp;
                        }

                    } else {
                        sunday = "";
                    }


                    stmt.execute("UPDATE " + type + "GroupsDates SET Monday='" + monday + "', Tuesday= '" + tuesday + "', Wednesday='"
                            + wednesday + "', Thursday='" + thursday + "', Friday='" + friday + "', Saturday='"
                            + saturday + "', Sunday='" + sunday + "' WHERE Title='" + title + "'");


                    rs = stmt.executeQuery("SELECT * FROM " + type + "GroupsMembers WHERE Title='" + title + "' AND Status='active'");

                    String msg = "\"" + title + "\"  " + type + "G has been rescheduled! Check your Timetable!";

                    while (rs.next()) {

                        stmt1 = con.createStatement();
                        rs1 = stmt1.executeQuery("SELECT * FROM users WHERE Username='" + rs.getString(1) + "'");

                        if (rs1.next()) {
                            SMTPAuthenticator.sendMail(rs1.getString(3), "\"" + title + "\"  " + type + "G rescheduled!", msg);
                        }

                        stmt1.close();
                        rs1.close();

                    }

                    out.println("<a>" + type + " group named \"" + title + "\" has been successfully rescheduled</a><br>");
                    out.println("<a href=\"mainPage.jsp\" style=\"font-weight:bold\">Home</a>");

                } else {

                    out.println("<a>No " + type + " groups named \"" + title + "\" created by \""
                            + session.getAttribute("user").toString() + "\"</a><br>");
                    out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try Again</a>");

                }


                stmt.close();
                rs.close();
                con.close();


            } catch (SQLException e) {
                out.println("<a>Problem with mysql.</a><br>");
                out.println("<a href=\"reScheduleSG.jsp?type=" + type + "\" style=\"font-weight:bold\">Try again</a>");
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
