<%--
  Created by IntelliJ IDEA.
  User: 26524
  Date: 2022/8/3
  Time: 23:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

            <form action="/names" method="get" class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                <input type="search" name="name" class="form-control form-control-dark" placeholder="Search names..." aria-label="Search">
            </form>

            <div class="text-end">
                <button type="button" class="btn btn-outline-light me-2">Login</button>
                <button type="button" class="btn btn-warning">Sign-up</button>
            </div>
        </div>
    </nav>
</header>
<%--<form action="" method="">--%>
<%--    <input type="text" name="name" placeholder="enter a name" required>--%>
<%--    <button formaction="/names" formmethod="post">add</button>--%>
<%--    <button formaction="/names" formmethod="get">search</button>--%>
<%--</form>--%>
<main class="container-fluid">
    <div class="container-sm">
        <form action="/names" method="post" class="">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="enter a name" aria-label="name" aria-describedby="button-addon2">
                <button formaction="/names" formmethod="post" class="btn btn-light btn-outline-secondary" type="button" id="button-addon2">Add</button>
            </div>
        </form>
    </div>
</main>
<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
