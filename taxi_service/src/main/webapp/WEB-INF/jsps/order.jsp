<%--
  Created by IntelliJ IDEA.
  User: Jura
  Date: 05.04.2015
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create order</title>
    <style>
        table {background-color: whitesmoke; font-weight: bold}
    </style>
</head>
<body>
Operator: <c:out value="${operator.login}"/>
<br/>
<c:out value="${orderMessage}"/>
<form action="/createOrder.html" method="post">
    <table>
        <tr>
            <td align="left">Enter unique ID</td>
            <td><input type="text" name = "orderId"/></td>
        </tr>
        <tr>
            <td align="left">Enter client's phone nmbr</td>
            <td><input type="text" name = "clientPhoneNo"/></td>
        </tr>
        <tr>
            <td align="left">Enter sum</td>
            <td><input type="text" name = "orderSum"/></td>
        </tr>
        <tr>
            <td align="left">Enter 'from' address</td>
            <td><input type="text" name = "orderAddressFrom"/></td>
        </tr>
        <tr>
            <td align="left">Enter destination address</td>
            <td><input type="text" name = "orderAddressTo"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="create"/>
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
