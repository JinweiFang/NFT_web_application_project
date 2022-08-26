<%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/12/22
  Time: 9:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../Templates/Header.jsp" %>
<main class="container-fluid">
    <div class="container-sm">
        <h3>Welcome, <%= currentUser.getfName()%>!</h3>
        <% if (currentUser.getIsAdmin() == 1) {%>
        <h5>I am an admin!</h5>
        <% } %>
    </div>
</main>
<%@ include file="../Templates/Footer.jsp" %>