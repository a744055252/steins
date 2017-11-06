<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
</head>
<body>
    <form action="../User/user" method="POST">
        用户名:<input placeholder="用户名" id="userName" name="userName"/>
        账号:<input type="text" placeholder="账号" id="account" name="account"/>
        密码:<input type="password" placeholder="密码" id="password" name="password"/>
        性别:<input type="text" placeholder="性别" id="sex" name="sex"/>
        年龄:<input type="text" placeholder="年龄" id="age" name="age"/>
        email:<input type="text" placeholder="email" id="email" name="email"/>
        手机:<input type="text" placeholder="手机" id="phone" name="phone"/>
        <button type="submit" id="login">登　录</button>
    </form>
</body>
</html>
