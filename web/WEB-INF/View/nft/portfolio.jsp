<%--
  Created by IntelliJ IDEA.
  User: johnpiapian
  Date: 1/20/23
  Time: 6:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Domain.Nft" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../Templates/Header.jsp" %>
<% List<Nft> nfts = (List<Nft>) request.getAttribute("nfts"); %>

<main class="container-fluid">
    <div class="container-sm">
        <div class="container">
            <div class="row border rounded-2 border-2 p-3 my-2">
                <h3 class="py-3 text-center border-bottom">My NFTs</h3>
                <div class="row row-cols-1 row-cols-md-3 g-4">
                    <% for (Nft nft : nfts) {%>
                    <div class="col">
                        <div class="card h-100">
                            <img src="<%= nft.getPicture() %>" class="card-img-top nft-card-img" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">This should be something</h5>
                                <p class="card-text">This is another one</p>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</main>
<%@ include file="../../Templates/Footer.jsp" %>
