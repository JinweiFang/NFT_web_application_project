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
                    Not all answers were correct!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("3")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Answers were correct but encountered error while attempting to reset password
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("4")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    User not found!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (request.getParameterMap().containsKey("succreset") && request.getParameter("succreset").equals("1")) { %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Successfully reset password!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (request.getParameterMap().containsKey("badAns1") && request.getParameter("badAns1").equals("1")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    First answer is incorrect!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (request.getParameterMap().containsKey("badAns2") && request.getParameter("badAns2").equals("1")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Second answer is incorrect!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (request.getParameterMap().containsKey("badAns3") && request.getParameter("badAns3").equals("1")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Third answer is incorrect!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>

                <div class="form-row form-group">
                    <label for="inputUsername">Username</label>
                    <input type="text" name="username" class="form-control" id="inputUsername" placeholder="Username"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputSecurityAnswer1">What city were you born in?</label>
                    <input type="text" name="secAns1" class="form-control" id="inputSecurityAnswer1"
                           placeholder="Answer"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputSecurityAnswer2">What was your favorite food as a child?</label>
                    <input type="text" name="secAns2" class="form-control" id="inputSecurityAnswer2"
                           placeholder="Answer"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputSecurityAnswer3">What was the name of your elementary school?</label>
                    <input type="text" name="secAns3" class="form-control" id="inputSecurityAnswer3"
                           placeholder="Answer"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputPassword">New password</label>
                    <input type="password" name="password" class="form-control" id="inputPassword"
                           placeholder="Password"
                           required>
                </div>

                <div class="form-row">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>

            </fieldset>
        </form>
    </div>
</main>

<%@ include file="../WEB-INF/Templates/Footer.jsp" %>
