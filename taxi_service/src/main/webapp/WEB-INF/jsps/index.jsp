<%--
  Created by IntelliJ IDEA.
  User: al1
  Date: 30.03.15
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Operator Authentication</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<c:if test="${message!=null}">
    <c:out value="${message}"/>
    <c:if test="${userTries.tries>0}">
        <br>You have
        <c:out value="${userTries.tries}"/>
         more tries
    </c:if>
</c:if>

  <form action="/dashboard.html" method="POST">
      <table>
          <tr>
              <td align="left">login:</td>
              <td>
                  <input type="text" name="login"/>
              </td>
          </tr>
          <tr>
              <td align="left">password:</td>
              <td>
                  <input type="password" name="password"/>
              </td>
          </tr>
          <tr>
              <td colspan="2" align="center">
                  <input class="button" type="submit" value="send"/>
              </td>
          </tr>
      </table>
  </form>
  <div>
      <a href="/register.html">register</a>
  </div>
</body>
</html>
