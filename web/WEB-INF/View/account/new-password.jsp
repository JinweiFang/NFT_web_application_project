<%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/15/22
  Time: 11:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../Templates/Header.jsp" %>
<main class="container-fluid">
  <div class="container-sm login-container d-flex flex-row justify-content-center">
    <form action="/authenticate/new-password" method="post" class="login flex-fill">
      <fieldset>
        <legend class="py-2">Reset Password</legend>
        <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("1")) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
          An unexpected error occurred!
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% } %>
        <div class="form-row form-group">
          <input type="hidden" name="uname" value="<%=request.getParameter("uname")%>">
          <input type="hidden" name="token" value="<%=request.getParameter("token")%>">
        </div>
        <div class="form-row form-group">
          <label for="inputPassword">Password</label>
          <input type="password" name="password" class="form-control" id="inputPassword"
                 placeholder="Password"
                 required>
        </div>
        <div class="form-row form-group">
          <label for="inputpassword2">Retype password</label>
          <input type="password" name="password2" class="form-control" id="inputpassword2"
                 placeholder="Retype Password"
                 required>
        </div>
        <div class="form-row py-2">
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </fieldset>
    </form>
  </div>
</main>
<%@ include file="../../Templates/Footer.jsp" %>

