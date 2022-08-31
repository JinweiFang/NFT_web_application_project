<%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/25/22
  Time: 9:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../Templates/Header.jsp" %>
<main class="container-fluid">
    <div class="container-sm">
        <% if (request.getParameterMap().containsKey("msg") && !request.getParameter("msg").isBlank()) { %>
        <h5 class="text-center"><%= request.getParameter("msg")%></h5>
        <% } %>
    </div>
</main>
<%@ include file="../Templates/Footer.jsp" %>