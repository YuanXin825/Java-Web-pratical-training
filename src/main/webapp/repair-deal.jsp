<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>处理报修</title>
</head>
<body>
<h2>处理报修/投诉（派单 · 进度 · 回复）</h2>
<form action="repair" method="post">
  <input type="hidden" name="action" value="deal">
  <input type="hidden" name="id" value="${repair.id}">
  <table cellpadding="6">
    <tr>
      <td>类型：</td>
      <td>${empty repair.type ? '报修' : repair.type}</td>
    </tr>
    <tr>
      <td>标题：</td>
      <td>${repair.title}</td>
    </tr>
    <tr>
      <td>内容：</td>
      <td style="white-space: pre-wrap;">${repair.content}</td>
    </tr>
    <tr>
      <td>提交用户ID：</td>
      <td>${repair.userId}</td>
    </tr>
    <tr>
      <td>提交时间：</td>
      <td>${repair.createTime}</td>
    </tr>
    <tr>
      <td>当前状态：</td>
      <td>${repair.status}</td>
    </tr>
    <tr>
      <td>更新进度为：</td>
      <td>
        <select name="status">
          <option value="待处理" ${repair.status=='待处理'?'selected':''}>待处理（待受理）</option>
          <option value="处理中" ${repair.status=='处理中'?'selected':''}>处理中（已派单）</option>
          <option value="已完成" ${repair.status=='已完成'?'selected':''}>已完成</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>回复反馈：</td>
      <td>
        <textarea name="reply" rows="4" cols="40" placeholder="填写处理说明或回复业主">${repair.reply}</textarea>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <input type="submit" value="保存">
        <a href="repair?action=list">返回列表</a>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
