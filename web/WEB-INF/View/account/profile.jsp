<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/25/22
  Time: 10:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../Templates/Header.jsp" %>
<main class="container-fluid">
    <div>
        <form action="/profile/changePersonalInfo" method="post">
            <fieldset>
                <div>
                    <label for="fName">First Name : </label>
                    <input type=text name="fName" class="form-control" id="fName"
                           placeholder="<%= currentUser.getfName()%>"><br>
                </div>
                <div>
                    <label for="lName">Last Name : </label>
                    <input type=text name="lName" class="form-control" id="lName"
                           placeholder="<%= currentUser.getlName()%>"><br>
                </div>
                <div>
                    <label for="username">Username : </label>
                    <input type=text name="username" class="form-control" id="username"
                           placeholder="<%= currentUser.getUsername()%>"><br>
                </div>
                <div>
                    <label for="email">Email : </label>
                    <input type=text name = "email" class="form-control" id="email"
                           placeholder="<%= currentUser.getEmail()%>"><br>
                </div>
                <div>
                    <input type="hidden" name="passwordValidate" class="form-control" id="passwordValidate"
                           value="<%= currentUser.getPassword()%>" required>
                </div>
                <div>
                    <input type="hidden" name="usernameValidate" class="form-control" id="usernameValidate"
                           value="<%= currentUser.getUsername()%>" required>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </fieldset>
        </form>
    </div>
</main>

<main class="container-fluid">
    <div>
        <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("2")) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Wrong password!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% } %>

        <form action="/profile/changePassword" method="post">
            <fieldset>
                <div>
                    <input type="hidden" name="username" class="form-control" id="usernameCheck"
                           value="<%= currentUser.getUsername()%>" required>
                </div>
                <div>
                    <label for="oldPassword">Old Password : </label>
                    <input type="password" name="oldPassword" class="form-control" id="oldPassword"
                           placeholder="Password"
                           required>
                </div>
                <div>
                    <label for="newPassword">New Password : </label>
                    <input type="password" name="newPassword" class="form-control" id="newPassword"
                           placeholder="Password"
                           required>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </fieldset>
        </form>
    </div>
</main>
<%@ include file="../../Templates/Footer.jsp" %>