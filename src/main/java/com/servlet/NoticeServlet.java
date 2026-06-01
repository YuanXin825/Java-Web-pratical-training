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

        if ("add".equals(action)) {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String publisher = request.getParameter("publisher");

            Notice notice = new Notice();
            notice.setTitle(title);
            notice.setContent(content);
            notice.setPublisher(publisher);

            noticeService.addNotice(notice);
            response.sendRedirect("notice?action=list");
        }

        else if ("list".equals(action)) {
            List<Notice> list = noticeService.getAllNotice();
            request.setAttribute("noticeList", list);
            request.getRequestDispatcher("notice-list.jsp").forward(request, response);
        }

        else if ("toEdit".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            Notice notice = noticeService.getNoticeById(id);
            request.setAttribute("notice", notice);
            request.getRequestDispatcher("notice-edit.jsp").forward(request, response);
        }

        else if ("update".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String publisher = request.getParameter("publisher");

            Notice notice = new Notice();
            notice.setId(id);
            notice.setTitle(title);
            notice.setContent(content);
            notice.setPublisher(publisher);

            noticeService.updateNotice(notice);
            response.sendRedirect("notice?action=list");
        }

        else if ("delete".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            noticeService.deleteNotice(id);
            response.sendRedirect("notice?action=list");
        }
    }
}
