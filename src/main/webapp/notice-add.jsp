<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>发布公告</title>
</head>
<body>
<h2>发布公告</h2>
<form action="notice?action=add" method="post">
  <table>
    <tr>
      <td>标题：</td>
      <td><input type="text" name="title" required maxlength="100"></td>
    </tr>
    <tr>
      <td>分类：</td>
      <td>
        <select name="categoryId">
          <option value="">未分类</option>
          <c:forEach items="${categoryList}" var="c">
            <option value="${c.id}">${c.name}</option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr>
      <td>内容：</td>
      <td><textarea name="content" rows="8" cols="50" required></textarea></td>
    </tr>
    <tr>
      <td>置顶：</td>
      <td><input type="checkbox" name="isTop" value="1"> 置顶显示</td>
    </tr>
    <tr>
      <td colspan="2">
        <input type="submit" value="发布">
        <a href="notice?action=list">返回列表</a>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
