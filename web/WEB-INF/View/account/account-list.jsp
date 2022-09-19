<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/25/22
  Time: 10:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../Templates/Header.jsp" %>
<% List<User> users = (List<User>) request.getAttribute("users"); %>
<main class="container-fluid">
    <div class="container-fluid p-4">
        <div class="row">
            <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("1")) { %>
            <div class="alert alert-danger alert-dismissible fade show text-center" role="alert">
                An unexpected error occurred while adding a new account!
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% } %>
            <% if (request.getParameterMap().containsKey("succsignup") && request.getParameter("succsignup").equals("1")) { %>
            <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
                Successfully registered!
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% } %>
        </div>
        <div class="row px-5 pb-4">
            <div class="text-end">
                <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#addAccountFormContainer">
                    Add Account
                </button>
            </div>
        </div>
        <div class="row table-responsive">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">First</th>
                    <th scope="col">Last</th>
                    <th scope="col">Username</th>
                    <th scope="col">Email</th>
                    <th scope="col">Balance</th>
                    <th scope="col">isAdmin</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <% for (User usr : users) {%>
                <tr>
                    <th scope="row"><%=usr.getId()%></th>
                    <td><%=usr.getfName()%></td>
                    <td><%=usr.getlName()%></td>
                    <td><%=usr.getUsername()%></td>
                    <td><%=usr.getEmail()%></td>
                    <td>$<%=usr.getBalance()%></td>
                    <td><%=usr.isAdmin() ? "Yes" : "No"%></td>
                    <td><a href="/u/@<%=usr.getUsername()%>">View</a></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</main>
<!-- Modal -->
<div class="modal fade" id="addAccountFormContainer" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Add Account</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="addAccountForm" action="/authenticate/register@admin" method="post" class="general flex-fill">
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
                        <label for="inputBalance">Balance</label>
                        <input type="number" name="balance" class="form-control" id="inputBalance" placeholder="Balance"
                               required>
                    </div>
                    <div class="form-row form-group">
                        <label for="inputAccountType">Account Type</label>
                        <select id="inputAccountType" name="accountType" class="form-control">
                            <option value="0">Default</option>
                            <option value="1">Admin</option>
                        </select>
                    </div>
                    <div class="form-row form-group">
                        <label for="inputUsername">Username</label>
                        <input type="text" name="username" class="form-control" id="inputUsername"
                               placeholder="Username"
                               required>
                    </div>
                    <div class="form-row form-group">
                        <label for="inputPassword">Password</label>
                        <input type="password" name="password" class="form-control" id="inputPassword"
                               placeholder="Password"
                               required>
                    </div>
                </form>
            </div>
            <div class="modal-footer border-top-0 p-0 justify-content-center">
                <button type="submit" form="addAccountForm" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../Templates/Footer.jsp" %>