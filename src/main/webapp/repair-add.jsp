<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增报修/投诉</title>
</head>
<body>
<h2>提交报修/投诉</h2>
<form action="repair" method="post">
    <input type="hidden" name="action" value="add">
    <table>
        <tr>
            <td>类型：</td>
            <td>
                <select name="type" required>
                    <option value="报修">报修</option>
                    <option value="投诉">投诉</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>标题：</td>
            <td><input type="text" name="title" required maxlength="100"></td>
        </tr>
        <tr>
            <td>详细内容：</td>
            <td><textarea name="content" rows="5" cols="40" required></textarea></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>
<p><a href="repair?action=list">返回列表</a></p>
</body>
</html>
