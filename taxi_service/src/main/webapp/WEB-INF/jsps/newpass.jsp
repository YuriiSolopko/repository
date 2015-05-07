<%--
  Created by IntelliJ IDEA.
  User: Jura
  Date: 03.04.2015
  Time: 12:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Enter new password</title>
</head>
<body>
<c:if test="${newPassMessage!=null}">
    <c:out value="${newPassMessage}"/>
</c:if>
<form action="/newpass.html" method="POST">
    <table>
        <tr>
            <td>Change pass for:</td>
            <td>
                <input type="text" name="operatorLogin" contenteditable="false" value="${operatorLog}"/>
            </td>
        </tr>
        <tr>
            <td align="left">old password:</td>
            <td>
                <input type="password" name="oldPassword"/>
            </td>
        </tr>
        <tr>
            <td align="left">new password:</td>
            <td>
                <input type="password" name="newPassword"/>
            </td>
        </tr>
        <tr>
            <td align="left">confirm password:</td>
            <td>
                <input type="password" name="confirmNewPassword"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="save"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
