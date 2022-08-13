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
            <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                    <use xlink:href="#bootstrap"/>
                </svg>
            </a>
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="/" class="nav-link px-2 text-warning">Home</a></li>
                <li><a href="#about" class="nav-link px-2 text-white">About</a></li>
            </ul>
            <div class="text-end">
                <%--                <button type="button" class="btn btn-outline-light me-2">Login</button>--%>
                <%--                <button type="button" class="btn btn-warning">Sign-up</button>--%>
                <a href="/login.jsp" class="btn btn-outline-light me-2">Login</a>
                <a href="/signup.jsp" class="btn btn-outline-warning">Sign up</a>
            </div>
        </div>
    </nav>
</header>
