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
    <div class="col-lg-12 col-md-8 col-12">
        <!-- Error -->
        <div class="col-lg-12 col-md-8 col-12">
            <div class="alert-danger">
                <%--error message : could not update the db --%>
                <% if (request.getParameterMap().containsKey("errmsg")) { %>
                <%if(request.getParameter("errmsg").equals("1")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    An unexpected error occurred, please try again!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% }%>

                <%if(request.getParameter("errmsg").equals("2")) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Wrong password!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% }%>
                <% } %>
            </div>
        </div>

        <!-- Success -->
        <div class="col-lg-12 col-md-8 col-12">
            <div class="alert-success">
                <%--error message : could not update the db --%>
                <% if (request.getParameterMap().containsKey("errmsg")) { %>
                <%if(request.getParameter("errmsg").equals("3")) { %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Personal information changed!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% }%>

                <%if(request.getParameter("errmsg").equals("4")) { %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Password changed!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% }%>
                <% } %>
            </div>
        </div>
        <!-- Card -->
        <div class="card">
            <!-- Card header -->
            <div class="card-header">
                <h3 class="mb-0">My Account</h3>
            </div>
            <!-- Welcome Message -->
            <div class="d-lg-flex align-items-center justify-content-between">
                <hr class="my-5" />
                <div class="d-flex align-items-center mb-4 mb-lg-0">
<%--                    for when you feel like doing this--%>
<%--                    <img src="./assets/avatar/code lines.jpg" id="img-uploaded" height="800px" width="800px"--%>
<%--                         class="avatar-xl rounded-circle" alt="" />--%>
                    <div class="ms-3">
                        <h4 class="mb-0">Your avatar</h4>
                        <p class="mb-0">
                            PNG or JPG no bigger than 800px wide and tall.
                        </p>
                    </div>
                </div>
                <div>
                    <a href="#" class="btn btn-outline-white btn-sm px-3 pl-10">Update</a>
                    <a href="#" class="btn btn-outline-danger btn-sm px-6">Delete</a>
                </div>
            </div>

            <!-- Personal Information -->
            <div class="card-body">
                <hr class="my-3" />
                <div>
                    <h4 class="mb-0">Personal Details</h4>
                    <p class="mb-5">
                        Edit your personal information and address.
                    </p>
                    <!-- Form -->
                    <form action="/profile/changePersonalInfo" method="post">
                        <fieldset>
                            <!-- First name -->
                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label" for="fname">First Name</label>
                                <input type=text name="fName" class="form-control" id="fName"
                                       value="<%= currentUser.getfName()%>"
                                       required />
                            </div>
                            <!-- Last name -->
                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label" for="lname">Last Name</label>
                                <input type=text name="lName" class="form-control" id="lName"
                                       value="<%= currentUser.getlName()%>"
                                       required />
                            </div>
                            <!-- Username -->
                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label" for="username">Username</label>
                                <input type=text name="username" class="form-control" id="username"
                                       value="<%= currentUser.getUsername()%>"
                                       required />
                            </div>
                            <!-- Email -->
                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label" for="email">Email</label>
                                <input type=text name = "email" class="form-control" id="email"
                                       value="<%= currentUser.getEmail()%>" required />
                            </div>
                            <div>
                                <input type="hidden" name="passwordValidate" class="form-control" id="passwordValidate"
                                       value="<%= currentUser.getPassword()%>" required>
                            </div>
                            <div>
                                <input type="hidden" name="usernameValidate" class="form-control" id="usernameValidate"
                                       value="<%= currentUser.getUsername()%>" required>
                            </div>
                            <div class="col-12">
                                <!-- Button -->
                                <button class="btn btn-primary" type="submit">Update Profile</button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>

            <!-- Password Change -->
            <div class="card-body">
                <hr class="my-3" />
                <div>
                    <h4 class="mb-0">Security</h4>
                    <p class="mb-4">
                        Edit your password.
                    </p>
                    <!-- Form -->
                    <form action="/profile/changePassword" method="post">
                        <fieldset>
                            <%--username for login check--%>
                            <div class="mb-3 col-12 col-md-6">
                                <input type="hidden" name="username" class="form-control" id="usernameCheck"
                                       value="<%=currentUser.getUsername()%>"><br>
                            </div>

                            <%--old password--%>
                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label" for="oldPassword">Old Password</label>
                                <input type="password" name="oldPassword" class="form-control" id="oldPassword"
                                       placeholder="Password"
                                       required/>
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label" for="newPassword">New Password</label>
                                <input type="password" name="newPassword" class="form-control" id="newPassword"
                                       placeholder="Password"
                                       required/>
                            </div>
                            <div class="col-12">
                                <!-- Button -->
                                <button class="btn btn-primary" type="submit">Update Password</button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>

            <!-- delete account -->
            <div class="card-body">
                <hr class="my-3" />
                <div>
                    <h4 class="mb-0">Delete</h4>
                    <p class="mb-4">
                        Delete your account permanently.
                    </p>
                    <!-- Form -->
                    <form action="/profile/deleteAccount" onSubmit="return confirm('Are you sure you want to delete your account?')" method="post">
                        <fieldset>
                            <!--authenticate-->
                            <div>
                                <input type="hidden" name="usernameDelete" class="form-control" id="usernameDelete"
                                       value="<%= currentUser.getUsername()%>" required>
                                <input type="hidden" name="passwordDelete" class="form-control" id="passwordDelete"
                                       value="<%= currentUser.getPassword()%>" required>
                            </div>
                            <div class="col-12">
                                <!-- Button -->
                                <button class="btn btn-danger" type="submit">Delete</button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>

        </div>
    </div>
</main>
<%@ include file="../../Templates/Footer.jsp" %>