<%--
  Created by IntelliJ IDEA.
  User: 26524
  Date: 2022/8/3
  Time: 23:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../WEB-INF/Templates/Header.jsp" %>
<%@ page import="Controller.transactionServlet" %>
<%@ page import="java.util.ArrayList" %>
<% ArrayList<Integer> cart = (ArrayList<Integer>) session.getAttribute("cart"); %>

<main class="container-fluid">
    <div class="container-sm login-container d-flex flex-row justify-content-center">
        <form action="/transaction/cart" method="post" class="login flex-fill">
            <fieldset>
                <legend class="py-2">Cart</legend>
                <% if (cart != null) {
                    for(int i = 0; i < cart.size(); i++){
                %>
                <h3>NFT: <%= cart.get(i)%></h3>
                <button type="submit" name="remove-button" class="btn-close" value="<%=i%>">REMOVE</button>
                <%
                    }
                } %>

                <h4>Your current balance is $130.12.</h4>
                <h4>The total cost of the items in your cart is $110.00.</h4>
                <div class="form-row">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </fieldset>
        </form>
    </div>
</main>

<%@ include file="../WEB-INF/Templates/Footer.jsp" %>
