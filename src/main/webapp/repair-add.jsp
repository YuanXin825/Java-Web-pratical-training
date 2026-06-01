<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增报修/投诉</title>
</head>
<body>
<h2>提交报修/投诉</h2>
<form action="repair?action=add" method="post">
    <table>
        <tr>
            <td>标题：</td>
            <td><input type="text" name="title" required></td>
        </tr>
        <tr>
            <td>详细内容：</td>
            <td><textarea name="content" rows="5" cols="30" required></textarea></td>
        </tr>
        <tr>
            <td>用户ID：</td>
            <td><input type="number" name="userId" required></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>
</body>
</html>
