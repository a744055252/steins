<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/14/014
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <c:forEach var="error" items="${errorList}" >
        ${error}<br>
    </c:forEach>
</body>
</html>
