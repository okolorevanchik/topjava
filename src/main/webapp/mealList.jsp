<%--@elvariable id="number" type="java.lang.Integer"--%>
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
                <%--@elvariable id="description" type="java.lang.String"--%>
                <%--@elvariable id="date" type="java.time.LocalDateTime"--%>
                <%--@elvariable id="calories" type="java.lang.Integer"--%>
                    <form method="POST" action="${pageContext.request.contextPath}/meals?number=${number}">
                        <table>
                            <tbody>
                            <tr>
                                <td>Date:</td>
                                <td><label><input type="datetime-local" name="local_date" value="${date}"/></label></td>
                            </tr>
                            <tr>
                                <td>Description:</td>
                                <td><label><input type="text" name="description" value="${description}"/></label></td>
                            </tr>
                            <tr>
                                <td>Calories:</td>
                                <td><label><input type="number" name="calories" value="${calories}"/></label></td>
                            </tr>
                            <tr>
                                <td>Exceed:</td>
                                <c:choose>
                                    <%--@elvariable id="exceed" type="java.lang.Boolean"--%>
                                    <c:when test="${exceed == 'true'}">
                                        <td><label><input type="checkbox" name="exceed" checked /></label></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><label><input type="checkbox" name="exceed" /></label></td>
                                    </c:otherwise>
                                </c:choose>
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
                    <form method="POST" action="${pageContext.request.contextPath}/meals?new=true">
                        <table>
                            <tbody>
                            <tr>
                                <td>Date:</td>
                                <td><label><input type="datetime-local" name="local_date_new" /></label></td>
                            </tr>
                            <tr>
                                <td>Description:</td>
                                <td><label><input type="text" name="description_new"/></label></td>
                            </tr>
                            <tr>
                                <td>Calories:</td>
                                <td><label><input type="number" name="calories_new"/></label></td>
                            </tr>
                            <tr>
                                <td>Exceed:</td>
                                <td><label><input type="checkbox" name="exceed_new"/></label></td>
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
    <%--@elvariable id="list" type="java.util.List"--%>
    <c:forEach var="meal" items="${list}">
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
                        <td><a href="${pageContext.request.contextPath}/meals?edit_number=${list.indexOf(meal)}">Edit</a></td>
                        <td><a href="${pageContext.request.contextPath}/meals?del_number=${list.indexOf(meal)}">Delete</a></td>
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
