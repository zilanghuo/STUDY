<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //设置页面全局变量
    String path = request.getContextPath();
    session.setAttribute("path",path);
%>
<html>
<script src="<%=path %>/js/jquery-1.3.2.min.js" ></script>
<script type="text/javascript">
    $(function(){

    });

</script>
<head>
    <title>health</title>
</head>
<body>
health<%=path%>,图片：<img src="${path}/images/ico.ico" />
</body>
</html>
