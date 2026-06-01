<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑公告</title>
</head>
<body>
<h2>编辑公告</h2>
<form action="notice?action=update" method="post">
    <input type="hidden" name="id" value="${notice.id}">
    <table>
        <tr>
            <td>标题：</td>
            <td><input type="text" name="title" value="${notice.title}" required></td>
        </tr>
        <tr>
            <td>内容：</td>
            <td><textarea name="content" rows="5" cols="30" required>${notice.content}</textarea></td>
        </tr>
        <tr>
            <td>发布人：</td>
            <td><input type="text" name="publisher" value="${notice.publisher}" required></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="保存">
                <a href="notice?action=list">返回列表</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
