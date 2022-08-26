<%@ page import="Domain.User" %>
<% User currentUser = (User) session.getAttribute("user"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NFT Web Project - Login</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css">

    <!-- Custom Import -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

<header class="p-3 bg-primary text-white">
    <nav class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="${pageContext.request.contextPath}/"
               class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                    <use xlink:href="#bootstrap"/>
                </svg>
            </a>
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 text-warning">Home</a></li>
                <li><a href="#about" class="nav-link px-2 text-white">About</a></li>
            </ul>
            <%-- If logged in then only show logout button --%>
            <% if (currentUser != null && currentUser.getId() > 0) {%>
            <div class="flex-shrink-0 dropdown">
                <a href="#" class="d-block link-light text-decoration-none dropdown-toggle" id="dropdownUser2" data-bs-toggle="dropdown" aria-expanded="false">
<%--                    <img src="https://johnpiapian.github.io/imgs/me.jpg" alt="mdo" width="32" height="32" class="rounded-circle">--%>
                    <span><%=currentUser.getfName()%></span>
                </a>
                <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser2" style="">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
                    <% if (currentUser.getIsAdmin() == 1) {%>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard/account-list">Account List</a></li>
                    <% } %>
                    <li><a class="dropdown-item" href="#">Profile</a></li>
                    <li><a class="dropdown-item" href="#">Settings</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/authenticate?logout=1">Log Out</a></li>
                </ul>
            </div>
            <% } else { %>
            <div class="text-end">
                <a href="${pageContext.request.contextPath}/account/login.jsp"
                   class="btn btn-outline-light me-2">Login</a>
                <a href="${pageContext.request.contextPath}/account/signup.jsp" class="btn btn-outline-warning">Sign
                    up</a>
            </div>
            <% } %>
        </div>
    </nav>
</header>
