<%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/15/22
  Time: 7:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../WEB-INF/Templates/Header.jsp" %>

<main class="container-fluid">
    <div class="container-sm login-container d-flex flex-row justify-content-center">
        <form action="/authenticate/reset" method="post" class="login flex-fill">
            <fieldset>
                <legend class="py-2">Reset Password</legend>
                <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("1")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    An error occurred during authentication!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <div class="form-row form-group">
                    <label for="inputUsername">Username</label>
                    <input type="text" name="username" class="form-control" id="inputUsername" placeholder="Username"
                           required>
                </div>
                <div class="form-row">
                    <button type="submit" class="btn btn-primary">Reset</button>
                </div>
            </fieldset>
        </form>
    </div>
</main>

<%@ include file="../WEB-INF/Templates/Footer.jsp" %>
