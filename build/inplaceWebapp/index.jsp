<%--
  Created by IntelliJ IDEA.
  User: superyoungy
  Date: 2020/3/18
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
  <!--test StudetLoginServlet-->
  <form action="StudentLoginServlet" method="post">
    学号<input type="text" name="stuId"><br>
    姓名<input type="text" name="stuName"><br>
    年龄<input type="text" name="stuAge"><br>
    <input type="submit" value="提交"> <br>
  </form>
  <!--
      1、jsp中的'/'代表服务器根路径（locallost：8080/)
      2、java中的'/'代表项目部署后的项目根路径(localhost:8080//JavaWebProject),
      输出this.getServletContext().getRealPath("/StudentLoginServlet")（或去掉斜杠）语句，得：
      D:\myapps\my-ideaworksapce\JavaWebProject\out\artifacts\testServlet_war_exploded\StudentLoginServlet

  -->
  <form action="download" method="post">
    文件名<input type="text" name="filename"><br>
    下载<input type="submit">
  </form>
  </body>
</html>
