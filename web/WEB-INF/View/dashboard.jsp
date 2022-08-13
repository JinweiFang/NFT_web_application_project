<%@ page import="Model.User" %><%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/12/22
  Time: 9:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../Templates/Admin-Header.jsp" %>
<% User currentUser = (User) session.getAttribute("user"); %>
<main class="container-fluid">
  <h3>Welcome, <%= currentUser.getfName()%>!</h3>
</main>
<%@ include file="../Templates/Footer.jsp" %>