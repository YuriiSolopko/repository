<%--
  Created by IntelliJ IDEA.
  User: Jura
  Date: 03.04.2015
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Register Client</title>
    <style>
        table {background-color: whitesmoke; font-weight: bold}
    </style>
</head>
<body>
Operator: <c:out value="${operator.login}"/>
<c:out value="${clientMessage}"/>
<form action="/registrationClient.html" method="post">
    <table>
        <tr>
            <td align="left">Enter name</td>
            <td>
                <input type="text" name="clientName"/>
            </td>
        </tr>
        <tr>
            <td align="left">Enter surname</td>
            <td>
                <input type="text" name="clientSurname"/>
            </td>
        </tr>
        <tr>
            <td align="left">Enter phone nmbr</td>
            <td>
                <input type="text" name="clientPhone"/>
            </td>
        </tr>
        <tr>
            <td align="left">Enter address</td>
            <td>
                <input type="text" name="clientAddress"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="submit"/>
            </td>
        </tr>
    </table>
</form>
<form action="/dashboard.html" method="post">
    <input type="hidden" name="login" value="${operator.login}">
    <input type="hidden" name="password" value="${operator.password}">
    <input type="submit" value="back">
</form>
</body>
</html>
