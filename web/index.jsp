<%--
  Created by IntelliJ IDEA.
  User: 26524
  Date: 2022/8/3
  Time: 23:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>NFT Web Project</title>
    <style>
      form {
        text-align: center;
        padding: 10px;
        max-width: 350px;
        margin: 0 auto;
      }
    </style>
  </head>
  <body>
    <form action="/names" method="">
      <input type="text" name="name" placeholder="enter a name">
<%--      <input type="submit" value="add">--%>
      <button formaction="/names" formmethod="post">add</button>
      <button formaction="/names" formmethod="get">search</button>
    </form>
  </body>
</html>
