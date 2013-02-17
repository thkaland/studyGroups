<!DOCTYPE html>
<%@ page language="java" import="java.util.*" session="true"%>

<html lang="en">
    <head>
        <link href='http://fonts.googleapis.com/css?family=Droid+Sans' rel='stylesheet' type='text/css'>
        <meta charset="utf-8">
        <title>Study Groups</title>
        <link href="css/stylemenu.css" media="screen" rel="stylesheet" type="text/css" />
        <link href="css/iconic.css" media="screen" rel="stylesheet" type="text/css" />
        <script src="js/prefix-free.js"></script>
    </head>

    <body>

        <div class="wrap">

            <nav>
                <ul class="menu">
                    <li><a href="mainPage.jsp"><span class="iconic home"></span> Home</a></li>
                    <li><a href="studyGroupsList.jsp?type=study"><span class="iconic plus-alt"></span> Study Groups</a>
                        <ul>
                            <li><a href="signUp.jsp?type=study">Sign Up</a></li>


                            <li><a href="addMember.jsp?type=study">Add Member</a></li>
                            <li><a href="removeMember.jsp?type=study">Remove Member</a></li>
                            <li><a href="authorize.jsp?type=study">Authorize Member</a></li>


                            <li><a href="createSG.jsp?type=study">Create SG</a></li>
                            <li><a href="destroySG.jsp?type=study">Destroy SG</a></li>
                            <li><a href="reScheduleSG.jsp?type=study">Reschedule SG</a></li>

                        </ul>   
                    </li>
                    <li><a href="studyGroupsList.jsp?type=tutor"><span class="iconic magnifying-glass"></span> Tutor Groups</a>
                        <ul>
                            <li><a href="signUp.jsp?type=tutor">Sign Up</a></li>


                            <li><a href="addMember.jsp?type=tutor">Add Member</a></li>
                            <li><a href="removeMember.jsp?type=tutor">Remove Member</a></li>
                            <li><a href="authorize.jsp?type=tutor">Authorize Member</a></li>


                            <li><a href="createSG.jsp?type=tutor">Create TG</a></li>
                            <li><a href="destroySG.jsp?type=tutor">Destroy TG</a></li>
                            <li><a href="reScheduleSG.jsp?type=tutor">Reschedule TG</a></li>
                            <li><a href="http://83.212.101.47:8080/final_project/" target="_new">Live Lectures</a></li>

                        </ul>   
                    </li>
                    <li><a href="/finalProject/projectGroupsList"><span class="iconic map-pin"></span> Project Groups</a>
                        <ul>
                            <li><a href="addPG.jsp">Add PG</a></li>
                            <li><a href="removePG.jsp">Remove PG</a></li>
                        </ul>  
                    </li>
                    <li><a href="/finalProject/myTimetable"><span class="iconic clock"></span> My Timetable</a></li>
                    <li><a href="/finalProject/logOut"><span class="iconic user"></span> Log Out,
                            <%

                                try {
                                    String user = session.getAttribute("user").toString();
                                    out.print(user);
                                } catch (NullPointerException e) {
                                    response.sendRedirect("/finalProject/logIn.html");

                                }

                            %>
                        </a></li>
                </ul>
                <div class="clearfix"></div>
            </nav>

            <noscript>Enable Javascript to get full functionality of this <a href="http://www.freeshoutbox.net/">Chat</a><br /></noscript>

            <iframe src="http://godgiven21.freeshoutbox.net/" height="500" width="400" frameborder="0" align="right"></iframe>

        </div>

    </body>

</html>