<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    session.setAttribute("path", path);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>首页</title>

    <script type="text/javascript" src="js/jquery-1.3.2.min.js"/>

    <script type="text/javascript">
       $(function(){
          alert("hello");
       });
    </script>
</head>
<body>
hello world!
</body>
</html>
