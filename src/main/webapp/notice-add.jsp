<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>新增公告</title>
</head>
<body>
<h2>新增公告</h2>
<form action="notice?action=add" method="post">
  <input type="hidden" name="adminId" value="1"> <%-- 管理员ID，从session取 --%>
  <table>
    <tr>
      <td>标题：</td>
      <td><input type="text" name="title" required></td>
    </tr>
    <tr>
      <td>内容：</td>
      <td><textarea name="content" rows="5" cols="30" required></textarea></td>
    </tr>
    <tr>
      <td>是否置顶：</td>
      <td>
        <select name="isTop">
          <option value="0">否</option>
          <option value="1">是</option>
        </select>
      </td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="发布"></td>
    </tr>
  </table>
</form>
</body>
</html>