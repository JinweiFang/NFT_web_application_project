<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/25/22
  Time: 10:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../Templates/Header.jsp" %>
<% List<User> users = (List<User>) request.getAttribute("users"); %>
<main class="container-fluid">
    <div class="container-sm">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">First</th>
                <th scope="col">Last</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Balance</th>
                <th scope="col">isAdmin</th>
            </tr>
            </thead>
            <tbody>
            <% for (User usr : users) {%>
            <tr>
                <th scope="row"><%=usr.getId()%></th>
                <td><%=usr.getfName()%></td>
                <td><%=usr.getlName()%></td>
                <td><%=usr.getUsername()%></td>
                <td><%=usr.getEmail()%></td>
                <td>$<%=usr.getBalance()%></td>
                <td><%=usr.isAdmin() ? "Yes" : "No"%></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</main>
<%@ include file="../../Templates/Footer.jsp" %>