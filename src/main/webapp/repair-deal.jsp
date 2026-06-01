<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>处理报修</title>
</head>
<body>
<h2>处理报修</h2>
<form action="repair?action=deal" method="post">
  <input type="hidden" name="id" value="${repair.id}">
  <table>
    <tr>
      <td>标题：</td>
      <td>${repair.title}</td>
    </tr>
    <tr>
      <td>内容：</td>
      <td>${repair.content}</td>
    </tr>
    <tr>
      <td>状态：</td>
      <td>
        <select name="status">
          <option value="1" <c:if test="${repair.status==1}">selected</c:if>>处理中</option>
          <option value="2" <c:if test="${repair.status==2}">selected</c:if>>已完成</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>处理备注：</td>
      <td><textarea name="remark" rows="3" cols="30">${repair.remark}</textarea></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="保存"></td>
    </tr>
  </table>
</form>
</body>
</html>