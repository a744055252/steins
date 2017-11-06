<%--
  Created by IntelliJ IDEA.
  User: caichunl
  Date: 2017/11/6
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="../SpiderUser/user" method="POST">
        平台:<input placeholder="平台" id="terrace" name="terrace"/>
        账号:<input placeholder="账号" id="account" name="account"/>
        密码:<input type="password" placeholder="密码" id="password" name="password"/>
        状态:<input  placeholder="状态" id="status" name="status"/>
        <button id="login">登　录</button>
    </form>
</body>
</html>
