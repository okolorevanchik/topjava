<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>

    <h3>Meal list</h3>
    <a href="meals?action=create">Add Meal</a>
    <hr>

    <form action="meals" method="get">
        <input type="hidden" name="action" value="filter">
        <table style="text-align: left; width: 25%">
            <tr>
                <th>Start date</th>
                <th></th>
                <th>Start time</th>
            </tr>
            <tr>
                <td><label><input type="date" name="startDate"/></label></td>
                <td></td>
                <td><label><input type="time" name="startTime"/></label></td>
            </tr>
            <tr></tr>
            <tr>
                <th>End date</th>
                <th></th>
                <th>End time</th>
            </tr>
            <tr>
                <td><label><input type="date" name="endDate"/></label></td>
                <td></td>
                <td><label><input type="time" name="endTime"/></label></td>
            </tr>
        </table>
        <button type="submit">Filter</button>
    </form>
    <br>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <%--@elvariable id="mealList" type="java.util.List"--%>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--<fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate"/>--%>
                        <%--<fmt:formatDate value="${parsedDate}" pattern="yyyy.MM.dd HH:mm" />--%>
                    <%=TimeUtil.toString(meal.getDateTime())%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>