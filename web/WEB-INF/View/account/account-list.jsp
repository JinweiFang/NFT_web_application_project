<%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 8/25/22
  Time: 10:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../Templates/Header.jsp" %>
<main class="container-fluid">
    <div class="container-sm">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">First</th>
                <th scope="col">Last</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Balance</th>
                <th scope="col">isAdmin</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th scope="row">1</th>
                <td>Sut</td>
                <td>Tuang</td>
                <td>suttuang</td>
                <td>suttuang@uwm.edu</td>
                <td>$1000</td>
                <td>Yes</td>
            </tr>
            </tbody>
        </table>
    </div>
</main>
<%@ include file="../../Templates/Footer.jsp" %>