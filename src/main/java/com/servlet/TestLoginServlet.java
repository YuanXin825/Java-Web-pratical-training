package com.servlet;

import com.bean.User;
import com.constant.SessionConstants;
import com.dao.UserDao;
import com.util.DBUtil;
import com.util.RoleUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * 临时测试登录，A 同学正式登录上线后删除。
 * 用法：/testLogin?userId=3  （userId 必须是 user 表中真实存在的 id）
 */
@WebServlet("/testLogin")
public class TestLoginServlet extends HttpServlet {

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String userIdStr = request.getParameter("userId");
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            writeError(response, "请指定真实用户 ID，例如：/testLogin?userId=3", null);
            return;
        }

        Integer userId;
        try {
            userId = Integer.valueOf(userIdStr.trim());
        } catch (NumberFormatException e) {
            writeError(response, "userId 必须是数字", null);
            return;
        }

        User user;
        try {
            user = userDao.findById(userId);
        } catch (SQLException e) {
            writeError(response, "数据库异常：" + e.getMessage(), e);
            return;
        }

        if (user == null) {
            writeError(response,
                    "user 表中不存在 id=" + userId + " 的用户。"
                            + "若 Navicat 能查到，说明 Java 程序连接的数据库与 Navicat 不是同一个，请核对 DBUtil 配置。",
                    null);
            return;
        }

        RoleUtil.ensureRole(user);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstants.LOGIN_USER, user);

        String redirect = RoleUtil.isAdmin(user) ? "notice?action=list" : "repair?action=list";
        response.sendRedirect(redirect);
    }

    private void writeError(HttpServletResponse response, String message, SQLException e) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<h3>测试登录失败</h3>");
        out.println("<p>" + message + "</p>");
        out.println("<p>Java 当前连接：<code>" + DBUtil.getDisplayUrl() + "</code></p>");
        out.println("<p>请在 Navicat 中确认「qi」连接的<strong>主机、端口、数据库名</strong>是否与上面一致。</p>");
        out.println("<p>校验 SQL：<code>SELECT id, username, role_id FROM `user` WHERE id = ?;</code></p>");
        if (e != null) {
            out.println("<p>详细错误：<code>" + e.getMessage() + "</code></p>");
        }
    }
}
