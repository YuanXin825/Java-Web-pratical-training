<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>报修投诉列表</title>
</head>
<body>
<h2>报修投诉管理</h2>
<a href="repair-add.jsp">新增报修</a>
<table border="1" cellpadding="5" cellspacing="0">
  <tr>
    <th>ID</th>
    <th>住户ID</th>
    <th>标题</th>
    <th>类型</th>
    <th>状态</th>
    <th>提交时间</th>
    <th>操作</th>
  </tr>
  <c:forEach items="${repairList}" var="r">
    <tr>
      <td>${r.id}</td>
      <td>${r.residentId}</td>
      <td>${r.title}</td>
      <td>${r.type==1?'报修':'投诉'}</td>
      <td>
        <c:if test="${r.status==0}">待处理</c:if>
        <c:if test="${r.status==1}">处理中</c:if>
        <c:if test="${r.status==2}">已完成</c:if>
      </td>
      <td>${r.createTime}</td>
      <td>
        <a href="repair?action=toDeal&id=${r.id}">处理</a>
        <a href="repair?action=delete&id=${r.id}" onclick="return confirm('确定删除？')">删除</a>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>