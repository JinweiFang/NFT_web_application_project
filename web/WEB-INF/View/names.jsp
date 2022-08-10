<%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/8/22
  Time: 8:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Test" %>
<% Test tests[] = (Test[]) request.getAttribute("data"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NFT Web Project</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css">

    <!-- Custom Import -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

<header class="p-3 bg-dark text-white">
    <nav class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                    <use xlink:href="#bootstrap"/>
                </svg>
            </a>

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="#" class="nav-link px-2 text-secondary">Home</a></li>
                <li><a href="#" class="nav-link px-2 text-white">FAQs</a></li>
                <li><a href="#" class="nav-link px-2 text-white">About</a></li>
            </ul>

            <div class="text-end">
                <button type="button" class="btn btn-outline-light me-2">Login</button>
                <button type="button" class="btn btn-warning">Sign-up</button>
            </div>
        </div>
    </nav>
</header>

<main class="container-fluid">
    <div class="container-sm">
        <% if (tests != null && tests[0] != null) {%>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Name</th>
                <th scope="col">Created On</th>
            </tr>
            </thead>
            <tbody>
            <% for(Test t : tests) { %>
            <tr>
                <th scope="row"><%= t.getID() %></th>
                <td><%= t.getName() %></td>
                <td><%= Utils.DateUtils.formateDateTime(t.getCreatedOn()) %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } else { %>
        <div class="alert alert-danger" role="alert">
            Record(s) not found!
        </div>
        <% } %>
    </div>
</main>
<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
