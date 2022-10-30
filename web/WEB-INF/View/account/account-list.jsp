<%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/25/22
  Time: 10:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../Templates/Header.jsp" %>
<% List<User> users = (List<User>) request.getAttribute("users"); %>

<main class="container-fluid">
    <div class="container-fluid p-4">
        <div class="row">
            <% if (request.getParameterMap().containsKey("errmsg") && request.getParameter("errmsg").equals("1")) { %>
            <div class="alert alert-danger alert-dismissible fade show text-center" role="alert">
                An unexpected error occurred!
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% } %>
            <% if (request.getParameterMap().containsKey("successmsg") && request.getParameter("successmsg").equals("1")) { %>
            <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
                Successfully executed action!
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
                <tr id="user-<%=usr.getId()%>">
                    <th id="user-id" scope="row" class="align-middle"><%=usr.getId()%></th>
                    <td id="user-fname" class="align-middle"><%=usr.getfName()%></td>
                    <td id="user-lname" class="align-middle"><%=usr.getlName()%></td>
                    <td id="user-username" class="align-middle"><%=usr.getUsername()%></td>
                    <td id="user-email" class="align-middle"><%=usr.getEmail()%></td>
                    <td id="user-balance" class="align-middle">$<%=usr.getBalance()%></td>
                    <td id="user-isadmin" class="align-middle"><%=usr.isAdmin() ? "Yes" : "No"%></td>
                    <td class="align-middle">
                        <button id="adminEditButton" type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#editAccountFormContainer" data-bs-uid="<%=usr.getId()%>">Edit</button>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</main>

<!-- Modal -->
<!-- add account -->
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
            <div class="modal-footer border-top-0 p-0 pb-3 justify-content-center">
                <button type="submit" form="addAccountForm" class="btn btn-primary">Add</button>
            </div>
        </div>
    </div>
</div>

<!-- edit account -->
<div class="modal fade" id="editAccountFormContainer" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel2">Edit Account</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editAccountForm" action="/authenticate/update@admin" method="post" class="general flex-fill">
                    <div class="d-none">
                        <input type="hidden" id="inputId2" name="id"  value="">
                    </div>
                    <div class="form-row form-group">
                        <label for="inputfName2">First Name</label>
                        <input type="text" name="fName" value="" class="form-control" id="inputfName2" placeholder="First Name"
                               required>
                    </div>
                    <div class="form-row form-group">
                        <label for="inputlName2">Last Name</label>
                        <input type="text" name="lName" class="form-control" id="inputlName2" placeholder="Last Name"
                               required>
                    </div>
                    <div class="form-row form-group">
                        <label for="inputEmail2">Email</label>
                        <input type="text" name="email" class="form-control" id="inputEmail2" placeholder="Email"
                               required>
                    </div>
                    <div class="form-row form-group">
                        <label for="inputAccountType2">Account Type</label>
                        <select id="inputAccountType2" name="accountType" class="form-control">
                            <option value="0">Default</option>
                            <option value="1">Admin</option>
                        </select>
                    </div>
                    <div class="form-row form-group">
                        <label for="inputUsername2">Username</label>
                        <input type="text" name="username" class="form-control" id="inputUsername2"
                               placeholder="Username"
                               required>
                    </div>
                    <div class="form-row form-group">
                        <label for="inputPassword2">Password</label>
                        <input type="password" name="password" class="form-control" id="inputPassword2"
                               placeholder="Password">
                    </div>
                </form>
            </div>
            <div class="modal-footer border-top-0 p-0 pb-3 justify-content-center">
                <button type="submit" form="editAccountForm" class="btn btn-primary">Update</button>
                <button type="submit" form="editAccountForm" formaction="/authenticate/delete@admin" class="btn btn-danger">Remove</button>
            </div>
        </div>
    </div>
</div>
<!-- End Modal -->

<%@ include file="../../Templates/Footer.jsp" %>