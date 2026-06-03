<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>公告分类管理</title>
  <style>
    table { border-collapse: collapse; width: 100%; max-width: 900px; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background-color: #4CAF50; color: white; }
    .section { margin-bottom: 24px; }
  </style>
</head>
<body>
<h2>公告分类管理</h2>
<p><a href="notice?action=list">返回公告列表</a></p>

<div class="section">
  <h3>新增分类</h3>
  <form action="notice?action=categoryAdd" method="post">
    名称：<input type="text" name="name" required maxlength="50">
    排序：<input type="number" name="sortOrder" value="0" style="width:60px">
    备注：<input type="text" name="remark" maxlength="200">
    <input type="submit" value="添加">
  </form>
</div>

<div class="section">
  <h3>分类列表</h3>
  <table>
    <tr>
      <th>ID</th>
      <th>名称</th>
      <th>排序</th>
      <th>备注</th>
      <th>操作</th>
    </tr>
    <c:forEach items="${categoryList}" var="c">
      <tr>
        <td>${c.id}</td>
        <td>
          <form action="notice?action=categoryUpdate" method="post" style="display:inline">
            <input type="hidden" name="id" value="${c.id}">
            <input type="text" name="name" value="${c.name}" required maxlength="50">
        </td>
        <td><input type="number" name="sortOrder" value="${c.sortOrder}" style="width:60px"></td>
        <td><input type="text" name="remark" value="${c.remark}" maxlength="200"></td>
        <td>
            <input type="submit" value="保存">
          </form>
          <a href="notice?action=categoryDelete&id=${c.id}" onclick="return confirm('确定删除该分类？')">删除</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>
