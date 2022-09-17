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
        <form action="/authenticate/security-questions" method="post" class="login flex-fill">
            <fieldset>
                <legend class="py-2">Reset Password via Security Questions</legend>
                <a href="./reset.jsp" class="px-2">Or, reset password via email</a>
                <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("1")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Error finding data for user
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("2")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Incorrect answer, or other error
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("3")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Answers were correct but encountered error while attempting to reset password
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (request.getParameterMap().containsKey("succreset") && request.getParameter("succreset").equals("1")) { %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Successfully reset password!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (!request.getParameterMap().containsKey("status") || request.getParameter("status").equals("-1")) { %>
                <div class="form-row form-group">
                    <label for="inputUsername">Username</label>
                    <input type="text" name="username" class="form-control" id="inputUsername" placeholder="Username"
                           required>
                </div>
                <div class="form-row">
                    <button type="submit" class="btn btn-primary">Receive security questions</button>
                </div>
                <% } %>

                <% if (request.getParameterMap().containsKey("status") && request.getParameter("status").equals("0")) { %>
                <legend class="py-2">What city were you born in?</legend>
                <% } %>
                <% if (request.getParameterMap().containsKey("status") && request.getParameter("status").equals("1")) { %>
                <legend class="py-2">What was your favorite food as a child?</legend>
                <% } %>
                <% if (request.getParameterMap().containsKey("status") && request.getParameter("status").equals("2")) { %>
                <legend class="py-2">What was the name of your elementary school?</legend>
                <% } %>

                <% if (request.getParameterMap().containsKey("status") && (request.getParameter("status").equals("0") || request.getParameter("status").equals("1") || request.getParameter("status").equals("2"))) { %>
                <div class="form-row form-group">
                    <label for="inputAnswer">Answer</label>
                    <input type="text" name="answer" class="form-control" id="inputAnswer" placeholder="Answer"
                           required>
                </div>
                <div class="form-row">
                    <button type="submit" class="btn btn-primary">Submit answer</button>
                </div>
                <% } %>

                <% if (request.getParameterMap().containsKey("status") && request.getParameter("status").equals("3")) { %>
                <div class="form-row form-group">
                    <label for="inputPassword">Password</label>
                    <input type="text" name="password" class="form-control" id="inputPassword" placeholder="New Password"
                           required>
                </div>
                <div class="form-row">
                    <button type="submit" class="btn btn-primary">Confirm new password</button>
                </div>
                <% } %>

            </fieldset>
        </form>
    </div>
</main>

<%@ include file="../WEB-INF/Templates/Footer.jsp" %>
