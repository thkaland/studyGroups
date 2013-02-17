<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <title>Reschedule Group</title>
        <meta name="description" content="Custom Login Form Styling with CSS3" />
        <meta name="keywords" content="css3, login, form, custom, input, submit, button, html5, placeholder" />
        <meta name="author" content="Codrops" />
        <link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <script src="js/modernizr.custom.63321.js"></script>

    </head>
    <body>
        <div>


            <form class="form-1" action="reScheduleSG?type=<%

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
                    <input type="text" name="title" maxlength="25" placeholder="Title">
                    <i class="icon-book icon-large"></i>
                </p>
                <p class="field">
                    <label>Monday </label><input type="checkbox" name="monday" value="monday"> 
                    <label>at </label><input type="tel" style="width:25px;height:20px;margin-bottom:5px;" name="time1h" maxlength="2">:
                    <input type="tel" style="width:25px;height:20px" name="time1m" maxlength="2"> <br>
                </p>
                <p class="field">
                    <label>Tuesday </label><input type="checkbox" name="tuesday" value="tuesday">
                    <label>at </label><input type="tel" style="width:25px;height:20px;margin-bottom:5px;" name="time2h" maxlength="2">:
                    <input type="tel" style="width:25px;height:20px" name="time2m" maxlength="2"> <br>
                </p>
                <p class="field">
                    <label>Wednesday </label><input type="checkbox" name="wednesday" value="wednesday">
                    <label>at </label><input type="tel" style="width:25px;height:20px;margin-bottom:5px;" name="time3h" maxlength="2">:
                    <input type="tel" style="width:25px;height:20px" name="time3m" maxlength="2"> <br>
                </p>
                <p class="field">
                    <label>Thursday </label><input type="checkbox" name="thursday" value="thursday">
                    <label>at </label><input type="tel" style="width:25px;height:20px;margin-bottom:5px;" name="time4h" maxlength="2">:
                    <input type="tel" style="width:25px;height:20px" name="time4m" maxlength="2"> <br>
                </p>
                <p class="field">
                    <label>Friday </label><input type="checkbox" name="friday" value="friday">
                    <label>at </label><input type="tel" style="width:25px;height:20px;margin-bottom:5px;" name="time5h" maxlength="2">:
                    <input type="tel" style="width:25px;height:20px" name="time5m" maxlength="2"> <br>
                </p>
                <p class="field">
                    <label>Saturday </label><input type="checkbox" name="saturday" value="saturday">
                    <label>at </label><input type="tel" style="width:25px;height:20px;margin-bottom:5px;" name="time6h" maxlength="2">:
                    <input type="tel" style="width:25px;height:20px" name="time6m" maxlength="2"> <br>
                </p>
                <p class="field">
                    <label>Sunday </label><input type="checkbox" name="sunday" value="sunday"> 
                    <label>at </label><input type="tel" style="width:25px;height:20px;margin-bottom:10px;" name="time7h" maxlength="2">:
                    <input type="tel" style="width:25px;height:20px" name="time7m" maxlength="2"> <br>
                </p>
                <p class="submit"> 
                    <button type="submit" name="submit"><i class="icon-arrow-right icon-large"></i></button>
                </p>
                <p class="field">
                    <a href="mainPage.jsp" style="font-weight:bold">Home</a> <br>
                </p>    
            </form>


        </div>
    </body>
</html>