<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>公告详情</title>
  <style>
    .meta { color: #666; margin-bottom: 16px; }
    .content { white-space: pre-wrap; line-height: 1.6; padding: 12px; border: 1px solid #ddd; max-width: 800px; }
    .top-badge { color: #e65100; font-weight: bold; }
  </style>
</head>
<body>
<h2>${notice.title}
  <c:if test="${notice.isTop == 1}">
    <span class="top-badge">[置顶]</span>
  </c:if>
</h2>

<div class="meta">
  分类：${empty notice.categoryName ? '未分类' : notice.categoryName}
  &nbsp;|&nbsp; 发布人：${notice.publisher}
  &nbsp;|&nbsp; 发布时间：${notice.createTime}
  <c:if test="${not empty notice.updateTime}">
    &nbsp;|&nbsp; 更新时间：${notice.updateTime}
  </c:if>
</div>

<div class="content">${notice.content}</div>

<p style="margin-top: 20px;">
  <a href="notice?action=list">返回列表</a>
  <c:if test="${isAdmin}">
    &nbsp;|&nbsp; <a href="notice?action=toEdit&id=${notice.id}">编辑</a>
  </c:if>
</p>
</body>
</html>
