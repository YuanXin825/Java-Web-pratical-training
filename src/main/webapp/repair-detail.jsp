<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>报修详情</title>
  <style>
    .meta { color: #666; margin-bottom: 16px; }
    .section { margin: 16px 0; padding: 12px; border: 1px solid #ddd; max-width: 800px; }
    .label { font-weight: bold; color: #333; }
    .content { white-space: pre-wrap; line-height: 1.6; }
    .status-待处理 { color: #f57c00; }
    .status-处理中 { color: #1976d2; }
    .status-已完成 { color: #388e3c; }
    .toolbar { margin-top: 20px; }
    .toolbar a { margin-right: 12px; }
  </style>
</head>
<body>

<%-- 业主端：进度查询 + 结果查看 + 评价 --%>
<c:if test="${role == 'owner'}">
  <h2>${empty repair.type ? '报修' : repair.type}详情</h2>
  <p style="color:#666;font-size:14px;">进度查询 · 结果查看</p>
</c:if>

<%-- 管理员端：工单查看 --%>
<c:if test="${isAdmin}">
  <h2>${empty repair.type ? '报修' : repair.type}工单详情</h2>
  <p style="color:#666;font-size:14px;">工单查看 · 处理参考</p>
</c:if>

<div class="meta">
  工单号：${repair.id}
  <c:if test="${isAdmin}">
    &nbsp;|&nbsp; 提交业主ID：${repair.userId}
  </c:if>
  &nbsp;|&nbsp; 状态：<span class="status-${repair.status}">${repair.status}</span>
  &nbsp;|&nbsp; 提交时间：${repair.createTime}
  <c:if test="${not empty repair.updateTime}">
    &nbsp;|&nbsp; 更新时间：${repair.updateTime}
  </c:if>
</div>

<div class="section">
  <p class="label">标题</p>
  <p>${repair.title}</p>
</div>

<div class="section">
  <p class="label">问题描述</p>
  <p class="content">${repair.content}</p>
</div>

<div class="section">
  <p class="label">处理进度</p>
  <p>
    待处理 → 处理中 → 已完成<br>
    当前：<strong>${repair.status}</strong>
  </p>
</div>

<div class="section">
  <p class="label">管理员回复</p>
  <c:if test="${not empty repair.reply}">
    <p class="content">${repair.reply}</p>
  </c:if>
  <c:if test="${empty repair.reply}">
    <c:if test="${role == 'owner'}">
      <p style="color:#999;">暂无回复，请耐心等待物业处理</p>
    </c:if>
    <c:if test="${isAdmin}">
      <p style="color:#999;">暂未填写回复，可在「处理」页面补充</p>
    </c:if>
  </c:if>
</div>

<%-- 已有评价：业主看「我的评价」，管理员看「业主评价」 --%>
<c:if test="${not empty repair.rating}">
  <div class="section">
    <p class="label">
      <c:choose>
        <c:when test="${isAdmin}">业主评价</c:when>
        <c:otherwise>我的评价</c:otherwise>
      </c:choose>
    </p>
    <p>评分：${repair.rating} 星</p>
    <c:if test="${not empty repair.evaluation}">
      <p class="content">${repair.evaluation}</p>
    </c:if>
    <c:if test="${empty repair.evaluation}">
      <p style="color:#999;">未填写文字评价</p>
    </c:if>
  </div>
</c:if>

<%-- 仅业主可提交评价 --%>
<c:if test="${canEvaluate}">
  <div class="section">
    <p class="label">评价反馈</p>
    <form action="repair" method="post">
      <input type="hidden" name="action" value="evaluate">
      <input type="hidden" name="id" value="${repair.id}">
      <p>
        满意度：
        <select name="rating" required>
          <option value="">请选择</option>
          <option value="5">5星 - 非常满意</option>
          <option value="4">4星 - 满意</option>
          <option value="3">3星 - 一般</option>
          <option value="2">2星 - 不满意</option>
          <option value="1">1星 - 非常不满意</option>
        </select>
      </p>
      <p>
        评价内容：<br>
        <textarea name="evaluation" rows="3" cols="40" placeholder="选填"></textarea>
      </p>
      <input type="submit" value="提交评价">
    </form>
  </div>
</c:if>

<c:if test="${isAdmin && empty repair.rating && repair.status == '已完成'}">
  <div class="section">
    <p class="label">业主评价</p>
    <p style="color:#999;">业主尚未评价</p>
  </div>
</c:if>

<div class="toolbar">
  <a href="repair?action=list">返回列表</a>
  <c:if test="${isAdmin}">
    <a href="repair?action=toDeal&id=${repair.id}">去处理</a>
  </c:if>
</div>
</body>
</html>
