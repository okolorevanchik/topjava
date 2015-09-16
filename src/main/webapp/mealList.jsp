<jsp:useBean id="userMealWithExceeds" scope="request" type="java.util.List"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2>Meal list</h2>
<table>
    <tr>
        <td>
            <c:choose>
                <%--@elvariable id="flag_editor" type="java.lang.Boolean"--%>
                <c:when test="${flag_editor == 'true'}">
                    <jsp:useBean id="userMeal" scope="request" type="ru.javawebinar.topjava.model.UserMeal"/>
                    <form method="POST" action="${pageContext.request.contextPath}/meals?action=edit&id=${userMeal.id}">
                        <table>
                            <tbody>
                            <tr>
                                <td>Date:</td>
                                <td><label><input type="datetime-local" name="local_date" value="${userMeal.dateTime}"/></label></td>
                            </tr>
                            <tr>
                                <td>Description:</td>
                                <td><label><input type="text" name="description" value="${userMeal.description}"/></label></td>
                            </tr>
                            <tr>
                                <td>Calories:</td>
                                <td><label><input type="number" name="calories" value="${userMeal.calories}"/></label></td>
                            </tr>
                            <tr>
                                <td><input type="submit" value="Update" /></td>
                                <td><a href="${pageContext.request.contextPath}/meals">Cancel</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </c:when>
                <c:otherwise>
                    <form method="POST" action="${pageContext.request.contextPath}/meals?action=add">
                        <table>
                            <tbody>
                            <tr>
                                <td>Date:</td>
                                <td><label><input type="datetime-local" name="local_date" /></label></td>
                            </tr>
                            <tr>
                                <td>Description:</td>
                                <td><label><input type="text" name="description"/></label></td>
                            </tr>
                            <tr>
                                <td>Calories:</td>
                                <td><label><input type="number" name="calories"/></label></td>
                            </tr>
                            <tr>
                                <td><input type="submit" value="Add" /></td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>

<table style="text-align: center">
    <thead>
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${userMealWithExceeds}">
        <tr>
            <c:choose>
                <c:when test="${meal.exceed == 'true'}">
                    <td style="color: red">${meal.dateTime}</td>
                    <td style="color: red">${meal.description}</td>
                    <td style="color: red">${meal.calories}</td>
                </c:when>
                <c:otherwise>
                    <td style="color: green">${meal.dateTime}</td>
                    <td style="color: green">${meal.description}</td>
                    <td style="color: green">${meal.calories}</td>
                </c:otherwise>
            </c:choose>
            <td>
                <table>
                    <tbody>
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/meals?action=edit&id=${meal.id}">Edit</a></td>
                        <td><a href="${pageContext.request.contextPath}/meals?action=delete&id=${meal.id}">Delete</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<p><a href="${pageContext.request.contextPath}/">go to index</a></p>

<p><a href="${pageContext.request.contextPath}/users">go to users</a></p>
</body>
</html>
