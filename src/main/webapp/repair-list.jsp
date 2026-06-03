<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/date-fmt.jspf" %>
<html>
<head>
  <title>报修投诉列表</title>
  <style>
    table { border-collapse: collapse; width: 100%; max-width: 1100px; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background-color: #2196F3; color: white; }
  </style>
</head>
<body>
<h2>报修投诉管理</h2>
<c:if test="${role == 'owner'}">
  <a href="repair?action=toAdd">提交报修/投诉</a>
</c:if>
<table>
  <tr>
    <th>ID</th>
    <th>类型</th>
    <th>标题</th>
    <c:if test="${isAdmin}">
      <th>用户ID</th>
    </c:if>
    <th>状态</th>
    <th>提交时间</th>
    <th>操作</th>
  </tr>
  <c:forEach items="${repairList}" var="r">
    <tr>
      <td>${r.id}</td>
      <td>${empty r.type ? '报修' : r.type}</td>
      <td>${r.title}</td>
      <c:if test="${isAdmin}">
        <td>${r.userId}</td>
      </c:if>
      <td>${r.status}</td>
      <td><fmt:formatDate value="${r.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
      <td>
        <a href="repair?action=detail&id=${r.id}">查看</a>
        <c:if test="${isAdmin}">
          | <a href="repair?action=toDeal&id=${r.id}">处理</a>
          | <a href="repair?action=delete&id=${r.id}" onclick="return confirm('确定删除？')">删除</a>
        </c:if>
        <c:if test="${role == 'owner' && r.status == '待处理'}">
          | <a href="repair?action=delete&id=${r.id}" onclick="return confirm('确定删除？')">删除</a>
        </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
<c:if test="${empty repairList}">
  <p>暂无报修/投诉记录</p>
</c:if>
</body>
</html>
