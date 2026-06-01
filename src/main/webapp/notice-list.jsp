<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>公告通知</title>
</head>
<body>
<h2>公告通知管理</h2>
<a href="notice-add.jsp">新增公告</a>
<table border="1" cellpadding="5" cellspacing="0">
  <tr>
    <th>ID</th>
    <th>标题</th>
    <th>发布人ID</th>
    <th>是否置顶</th>
    <th>发布时间</th>
    <th>操作</th>
  </tr>
  <c:forEach items="${noticeList}" var="n">
    <tr>
      <td>${n.id}</td>
      <td>${n.title}</td>
      <td>${n.adminId}</td>
      <td>${n.isTop==1?'置顶':'普通'}</td>
      <td>${n.createTime}</td>
      <td>
        <a href="notice?action=toEdit&id=${n.id}">编辑</a>
        <a href="notice?action=delete&id=${n.id}" onclick="return confirm('确定删除？')">删除</a>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>