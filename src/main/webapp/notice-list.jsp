<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>公告通知</title>
  <style>
    table { border-collapse: collapse; width: 100%; max-width: 1200px; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background-color: #4CAF50; color: white; }
    tr:nth-child(even) { background-color: #f2f2f2; }
    .top-badge { color: #e65100; font-weight: bold; }
    .unread-badge { color: #d32f2f; font-weight: bold; }
    .toolbar { margin-bottom: 12px; }
    .toolbar a { margin-right: 12px; }
  </style>
</head>
<body>
<h2>公告通知</h2>

<div class="toolbar">
  <c:if test="${isAdmin}">
    <a href="notice?action=toAdd">发布公告</a>
    <a href="notice?action=categoryList">分类管理</a>
  </c:if>
  <c:if test="${role == 'owner' && unreadCount > 0}">
    <span class="unread-badge">您有 ${unreadCount} 条未读公告</span>
  </c:if>
</div>

<table>
  <thead>
  <tr>
    <th width="50">ID</th>
    <th width="150">标题</th>
    <th width="100">分类</th>
    <th width="80">置顶</th>
    <th width="100">发布人</th>
    <th width="150">发布时间</th>
    <c:if test="${role == 'owner'}">
      <th width="80">状态</th>
    </c:if>
    <th width="200">操作</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${noticeList}" var="n">
    <tr>
      <td>${n.id}</td>
      <td>${n.title}</td>
      <td>${empty n.categoryName ? '未分类' : n.categoryName}</td>
      <td>
        <c:if test="${n.isTop == 1}">
          <span class="top-badge">置顶</span>
        </c:if>
      </td>
      <td>${n.publisher}</td>
      <td>${n.createTime}</td>
      <c:if test="${role == 'owner'}">
        <td>
          <c:if test="${n.unread}">
            <span class="unread-badge">未读</span>
          </c:if>
          <c:if test="${!n.unread}">
            已读
          </c:if>
        </td>
      </c:if>
      <td>
        <a href="notice?action=detail&id=${n.id}">查看</a>
        <c:if test="${role == 'admin' || isAdmin}">
          | <a href="notice?action=toEdit&id=${n.id}">编辑</a>
          | <a href="notice?action=delete&id=${n.id}" onclick="return confirm('确定删除？')">删除</a>
          <c:if test="${n.isTop == 1}">
            | <a href="notice?action=top&id=${n.id}&isTop=0">取消置顶</a>
          </c:if>
          <c:if test="${n.isTop != 1}">
            | <a href="notice?action=top&id=${n.id}&isTop=1">置顶</a>
          </c:if>
        </c:if>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<c:if test="${empty noticeList}">
  <p>暂无公告</p>
</c:if>
</body>
</html>
