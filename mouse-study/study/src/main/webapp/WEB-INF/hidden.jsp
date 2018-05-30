<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String path = request.getContextPath();
    session.setAttribute("path", path);
%>
<head>
    <script type="text/javascript" src="${path}/js/jquery-1.3.2.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>支付页</title>
</head>
<body>
<form id="wapHiddenForm" method="post" action='http://www-1.fuiou.com:18670/mobile_pay/h5pay/payAction.pay'>
    <input type="hidden" name="VERSION" value='${VERSION}'/>
    <input type="hidden" name="ENCTP" value='${ENCTP}' />
    <input type="hidden" name="MCHNTCD" value='${MCHNTCD}' />
    <input type="hidden" name="FM" value='${FM}'/>
</form>

</body>
<script type="text/javascript">
   $(function(){
        $("#wapHiddenForm").submit();
    });
</script>

</html>
