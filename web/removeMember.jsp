<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <title>Remove Member</title>
        <meta name="description" content="Custom Login Form Styling with CSS3" />
        <meta name="keywords" content="css3, login, form, custom, input, submit, button, html5, placeholder" />
        <meta name="author" content="Codrops" />
        <link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <script src="js/modernizr.custom.63321.js"></script>

    </head>
    <body>
        <div>


            <form class="form-1" action="removeMember?type=<%

                try {
                    String type = request.getParameter("type").toString();
                    if (type.equals("null")) {
                        response.sendRedirect("/finalProject/mainPage.jsp");
                    } else {
                        out.print(type);
                    }
                } catch (NullPointerException e) {
                    response.sendRedirect("/finalProject/mainPage.jsp");

                }

                  %>" method="post">
                <p class="field">
                    <input type="text" name="username" maxlength="25" placeholder="Username">
                    <i class="icon-user icon-large"></i>
                </p>
                <p class="field">
                    <input type="text" name="title" maxlength="25" placeholder="Title">
                    <i class="icon-book icon-large"></i>
                </p>
                <p class="submit"> 
                    <button type="submit" name="submit"><i class="icon-arrow-right icon-large"></i></button>
                </p>
                <p class="field">
                    <a href="mainPage.jsp" style="font-weight:bold">Home</a>
                </p>    
            </form>


        </div>
    </body>
</html>