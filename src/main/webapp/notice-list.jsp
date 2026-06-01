<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>公告通知</title>
  <style>
    table {
      border-collapse: collapse;
      width: 100%;
      max-width: 1200px;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 8px;
      text-align: left;
    }
    th {
      background-color: #4CAF50;
      color: white;
    }
    tr:nth-child(even) {
      background-color: #f2f2f2;
    }
    .content-cell {
      max-width: 400px;
      white-space: pre-wrap;
      word-wrap: break-word;
    }
  </style>
</head>
<body>
<h2>公告通知管理</h2>
<a href="notice-add.jsp">新增公告</a>
<table>
  <thead>
  <tr>
    <th width="50">ID</th>
    <th width="150">标题</th>
    <th width="100">发布人</th>
    <th width="400">内容</th>
    <th width="150">发布时间</th>
    <th width="150">操作</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${noticeList}" var="n">
    <tr>
      <td>${n.id}</td>
      <td>${n.title}</td>
      <td>${n.publisher}</td>
      <td class="content-cell">${n.content}</td>
      <td>${n.createTime}</td>
      <td>
        <a href="notice?action=toEdit&id=${n.id}">编辑</a> |
        <a href="notice?action=delete&id=${n.id}" onclick="return confirm('确定删除？')">删除</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
