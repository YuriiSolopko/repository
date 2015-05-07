<%--
  Created by IntelliJ IDEA.
  User: Jura
  Date: 02.04.2015
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration form</title>
    <style>
        table {background-color: whitesmoke; font-weight: bold}
    </style>
</head>
<body>
<c:out value="${message}"/>
<form action="/registration.html" method="post">
    <table>
        <tr>
            <td align="left">Enter login</td>
            <td>
                <input type="text" name="login"/>
            </td>
        </tr>
        <tr>
            <td align="left">Enter password</td>
            <td>
                <input type="password" name="password"/>
            </td>
        </tr>
        <tr>
            <td align="left">Confirm password</td>
            <td>
                <input type="password" name="confirmPassword"/>
            </td>
        </tr>
        <tr>
            <td align="left">Enter ID (10 numbers)</td>
            <td>
                <input type="text" name="id"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input class = "button" type="submit" value="submit"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
