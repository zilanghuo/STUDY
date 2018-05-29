<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String path = request.getContextPath();
    session.setAttribute("path", path);
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>支付页</title>
</head>
<body>
<form id="wapHiddenForm" method="post" action="http://www-1.fuiou.com:18670/mobile_pay/h5pay/payAction.pay">
    <input type="hidden" name="VERSION" value="${VERSION}"/>
    <input type="hidden" name="ENCTP" value="${ENCTP}" />
    <input type="hidden" name="MCHNTCD" value="${MCHNTCD}" />
    <input type="hidden" name="FM" value='<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ORDER><AMT>1</AMT><BACKURL>www.baidu.com</BACKURL><BANKCARD>6222001001127676964</BANKCARD><HOMEURL>www.baidu.com</HOMEURL><IDNO>350300199111163692</IDNO><IDTYPE>0</IDTYPE><LOGOTP>0</LOGOTP><MCHNTCD>0002900F0096235</MCHNTCD><MCHNTORDERID>2018052900000001</MCHNTORDERID><NAME>张三</NAME><REURL>www.baidu.com</REURL><SIGN>081a72e320ed39bd1d0ddc6ee92d8bcc</SIGN><SIGNTP>md5</SIGNTP><TYPE>10</TYPE><USERID>20180529</USERID><VERSION>2.0</VERSION></ORDER>"/>'/>
</form>

</body>
<script type="text/javascript">
   $(function(){
        $("#wapHiddenForm").submit();
    });
</script>

</html>
