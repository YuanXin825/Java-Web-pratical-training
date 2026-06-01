<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>新增公告</title>
</head>
<body>
<h2>新增公告</h2>
<form action="notice?action=add" method="post">
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
      <td>发布人：</td>
      <td><input type="text" name="publisher" required></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="发布"></td>
    </tr>
  </table>
</form>
</body>
</html>
