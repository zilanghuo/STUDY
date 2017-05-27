<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>首页</title>
    <script type="text/javascript" src="js/jquery-1.3.2.js" />

    <script type="text/javascript">
        $(document).ready(function() {
            alert("ok");
        });
    </script>
</head>
<body>
<div id="wrap">
    <div id="logo">
        <a href="http://www.mycodes.net/" target="_blank"><img class="png" src="images/logo.png" border="0"
                                                               alt="返回奇迹秀"/></a>
    </div>
    <div id="container">
        <ul id="lsn">
            <li id="uctext"><h2>4243<img src="<%=path%>/b3.jpg">
            </h2></li>
            <li id="uccounter">
                <div id="defaultCountdown"></div>
                <div id="overlay"></div>
                <div id="overlay_2">
                    <img class="png" src="images/ks.png" alt="这里记录奇迹秀从开始到现在"/>
                </div>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
