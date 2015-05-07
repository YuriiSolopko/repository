<%--
  Created by IntelliJ IDEA.
  User: Jura
  Date: 01.04.2015
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dashboard</title>
    <style>
        * {
            margin: 0;
            padding: 0
        }
        body {
            text-align: center;
        }
        table {
            margin: 0 auto;
        }
        table td.whitesmoke {
            background-color: whitesmoke;
            padding: 3px 6px;
        }
        table td.lightgrey {
            background-color: lightgrey;
            padding: 3px 6px;
        }
        table td.menu {
            background-color: darkslategrey;
            color: white;
            font-weight: bold;
            padding: 3px 6px;
        }
        #field {
            width: 50px;
        }
        div {
            background-color: aliceblue;
            margin: 0 auto;
            font: normal 14pt/14pt sans-serif;
            width: 400px;
        }
    </style>
</head>
<body>
Operator: <c:out value="${operator.login}"/>
<div>
    <a href="/registerClient.html">Register new client</a>
    <br/>
    <a href="/dashboard.html/showAll?firstResult=0">Show all clients</a>
    <br/>
    <a href="/dashboard.html/showLastMonth">Show all clients for last month</a>
    <br/>
    <form action="/dashboard.html/showGtSum" method="GET">
        <table>
            <tr>
                <td>
                    Show clients with sum >
                </td>
                <td>
                    <input type="text" name="clientSum" id="field"/>
                </td>
                <td>
                    <input type="submit" value="show"/>
                </td>
            </tr>
        </table>
    </form>
    <br/>
    <a href="/order.html">Create order</a>
    <br/>
    <a href="/orders.html?firstResult=0">Show orders</a>
    <br/>
    <form action="/orders.html/bySum" method="GET">
        <table>
            <tr>
                <td>
                    Show orders in sum region
                </td>
                <td>
                    <input type="text" name="sumFrom" id="field"/>
                </td>
                <td>
                    <input type="text" name="sumTo" id="field"/>
                </td>
                <td>
                    <input type="submit" value="show" id="field"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<c:if test="${clientList!=null}">
    <table>
        <tr>
            <td class="menu">ID</td>
            <td class="menu">Name</td>
            <td class="menu">Surname</td>
            <td class="menu">Phone no.</td>
            <td class="menu">Address</td>
            <td class="menu">Total sum</td>
            <td class="menu">Last order date</td>
        </tr>
        <c:forEach var="el" items="${clientList}">
            <tr>
                <td class="lightgrey"><c:out value="${el.clientId}"/></td>
                <td class="whitesmoke"><c:out value="${el.firstName}"/></td>
                <td class="lightgrey"><c:out value="${el.lastName}"/></td>
                <td class="whitesmoke"><c:out value="${el.phoneNumber}"/></td>
                <td class="lightgrey"><c:out value="${el.address}"/></td>
                <td class="whitesmoke"><c:out value="${el.sum}"/></td>
                <td class="lightgrey"><c:out value="${el.lastOrderDate}"/></td>
            </tr>
        </c:forEach>
        <c:if test="${firstIndex!=-1}">
            <tr>
                <td colspan="3"></td>
                <td>
                    <c:if test="${firstIndex>0}">
                        <a href="/dashboard.html/showAll?firstResult=${firstIndex-10}"/> <<
                  </c:if>
                </td>
                <td>
                    <c:if test="${(clientSize-firstIndex) > 10}">
                        <a href="/dashboard.html/showAll?firstResult=${firstIndex+10}"/> >>
                    </c:if>
              </td>
            </tr>
        </c:if>
    </table>
</c:if>
</body>
</html>
