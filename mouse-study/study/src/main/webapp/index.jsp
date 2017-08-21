<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String path = request.getContextPath();
    session.setAttribute("path", path);
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>首页</title>
    <link rel="shortcut icon" href="${path}/images/ico.ico" type="images/x-icon" />
    <link rel="stylesheet" type="text/css" media="all" href="${path}/css/style.css" />
    <script type="text/javascript" src="${path}/js/jquery-1.3.2.js"></script>
    <script type="text/javascript" src="${path}/js/jquery.countdown.js"></script>
    <script type="text/javascript" src="${path}/js/subscribe.js"></script>
    <script type="text/javascript" src="${path}/js/DD_belated.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#defaultCountdown').countdown({since: new Date(2014, 9-1, 20, 0, 0, 0 ),// change this date to match yours, format is year, month-1, day, hour(24), min, sec
                format: 'ODHMS'});
        });
    </script>
</head>
<body>
<%--<div id="wrap">
    <div id="logo">
        <a href="#" target="_blank"><img class="png" src="${path}/images/logo.png" border="0" /></a>
    </div>
    <div id="container">
        <ul id="lsn">
            <li id="uctext"><h2></h2></li>
            <li id="uccounter">
                <div id="defaultCountdown"></div>
                <div id="overlay"></div>
                <div id="overlay_2">
                    <img class="png" src="${path}/images/ks.png"  />
                </div> </li>
        </ul>
    </div>
</div>--%>
index

    <form action="https://www.growingio.com/auth/token" method="get" >

        <input type="text" name="project" value="1P6VAXPd" />
        <input type="text" name="ai" id="b93a6913b8d3edb9" />
        <input type="text" name="tm" id="1503049938050" />
        <input type="text" name="auth" id="17bde4522a25ff50b95050cffb81ecf5a8587913c4efc3da1eb8d9b3508e0681" />
        <input type="submit" value="发送" />
    </form>

</body>
</html>
