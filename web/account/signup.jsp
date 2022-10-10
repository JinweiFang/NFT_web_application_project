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
    <div class="container-sm signup-container d-flex flex-row justify-content-center">
        <form action="/authenticate/register" method="post" class="signup flex-fill">
            <fieldset>
                <legend class="py-2">Sign up</legend>
                <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("1")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    An unexpected error occurred while registering!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <% if (request.getParameterMap().containsKey("succsignup") && request.getParameter("succsignup").equals("1")) { %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Successfully registered!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <div class="form-row form-group">
                    <label for="inputfName">First Name</label>
                    <input type="text" name="fName" class="form-control" id="inputfName" placeholder="First Name"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputlName">Last Name</label>
                    <input type="text" name="lName" class="form-control" id="inputlName" placeholder="Last Name"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputEmail">Email</label>
                    <input type="text" name="email" class="form-control" id="inputEmail" placeholder="Email"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputUsername">Username</label>
                    <input type="text" name="username" class="form-control" id="inputUsername" placeholder="Username"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputPassword">Password</label>
                    <input type="password" name="password" class="form-control" id="inputPassword"
                           placeholder="Password"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputSecurityAnswer1">Security question 1: What city were you born in?</label>
                    <input type="text" name="secAns1" class="form-control" id="inputSecurityAnswer1"
                           placeholder="Answer"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputSecurityAnswer2">Security question 2: What was your favorite food as a child?</label>
                    <input type="text" name="secAns2" class="form-control" id="inputSecurityAnswer2"
                           placeholder="Answer"
                           required>
                </div>
                <div class="form-row form-group">
                    <label for="inputSecurityAnswer3">Security question 3: What was the name of your elementary school?</label>
                    <input type="text" name="secAns3" class="form-control" id="inputSecurityAnswer3"
                           placeholder="Answer"
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