<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>

<h3>User list</h3>

<h5>Id текущего пользователя: ${userId}</h5>

<form action="users" method="post">
    <p>
        <label>
            <select name="id">
                <option>--Select User--</option>
                <c:forEach items="${users}" var="user">
                    <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
                    <option value="${user.id}">${user.name}</option>
                </c:forEach>
            </select>
        </label>
        <input type="submit" value="Select">
    </p>
</form>
</body>
</html>
