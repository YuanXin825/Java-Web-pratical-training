package com.servlet;

import com.entity.Notice;
import com.service.NoticeService;
import com.impl.NoticeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/notice")
public class NoticeServlet extends HttpServlet {

    private NoticeService noticeService = new NoticeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        // 1. 新增公告
        if ("add".equals(action)) {
            Integer adminId = Integer.valueOf(request.getParameter("adminId"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            Integer isTop = Integer.valueOf(request.getParameter("isTop"));

            Notice notice = new Notice();
            notice.setAdminId(adminId);
            notice.setTitle(title);
            notice.setContent(content);
            notice.setIsTop(isTop);

            noticeService.addNotice(notice);
            response.sendRedirect("notice?action=list");
        }

        // 2. 公告列表
        else if ("list".equals(action)) {
            List<Notice> list = noticeService.getAllNotice();
            request.setAttribute("noticeList", list);
            request.getRequestDispatcher("notice-list.jsp").forward(request, response);
        }

        // 3. 跳转到编辑
        else if ("toEdit".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            Notice notice = noticeService.getNoticeById(id);
            request.setAttribute("notice", notice);
            request.getRequestDispatcher("notice-edit.jsp").forward(request, response);
        }

        // 4. 更新公告
        else if ("update".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            Integer isTop = Integer.valueOf(request.getParameter("isTop"));

            Notice notice = new Notice();
            notice.setId(id);
            notice.setTitle(title);
            notice.setContent(content);
            notice.setIsTop(isTop);

            noticeService.updateNotice(notice);
            response.sendRedirect("notice?action=list");
        }

        // 5. 删除公告
        else if ("delete".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            noticeService.deleteNotice(id);
            response.sendRedirect("notice?action=list");
        }
    }
}