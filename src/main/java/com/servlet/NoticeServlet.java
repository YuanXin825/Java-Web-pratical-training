package com.servlet;

import com.bean.Notice;
import com.bean.NoticeCategory;
import com.bean.User;
import com.constant.SessionConstants;
import com.service.NoticeService;
import com.service.impl.NoticeServiceImpl;
import com.util.RoleUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/notice")
public class NoticeServlet extends HttpServlet {

    private NoticeService noticeService = new NoticeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        User loginUser = getLoginUser(request);
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        RoleUtil.ensureRole(loginUser);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstants.LOGIN_USER, loginUser);

        boolean isAdmin = RoleUtil.isAdmin(loginUser);
        boolean isOwner = RoleUtil.isOwner(loginUser);
        if (!isAdmin && !isOwner) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "无效角色(role=" + loginUser.getRole() + ", roleId=" + loginUser.getRoleId()
                            + ")，请用 /testLogin?userId=1 登录管理员");
            return;
        }

        String role = loginUser.getRole();

        if ("list".equals(action)) {
            handleList(request, response, loginUser, isAdmin, isOwner, role);
        } else if ("detail".equals(action)) {
            handleDetail(request, response, loginUser, isOwner, role, isAdmin);
        } else if ("toAdd".equals(action)) {
            requireAdmin(response, loginUser, isAdmin);
            if (!isAdmin) return;
            loadCategories(request);
            setCommonAttributes(request, role, isAdmin);
            request.getRequestDispatcher("notice-add.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            handleAdd(request, response, loginUser, isAdmin);
        } else if ("toEdit".equals(action)) {
            handleToEdit(request, response, isAdmin);
        } else if ("update".equals(action)) {
            handleUpdate(request, response, loginUser, isAdmin);
        } else if ("delete".equals(action)) {
            handleDelete(request, response, isAdmin);
        } else if ("top".equals(action)) {
            handleTop(request, response, isAdmin);
        } else if ("categoryList".equals(action)) {
            handleCategoryList(request, response, isAdmin, role);
        } else if ("categoryAdd".equals(action)) {
            handleCategoryAdd(request, response, isAdmin);
        } else if ("categoryUpdate".equals(action)) {
            handleCategoryUpdate(request, response, isAdmin);
        } else if ("categoryDelete".equals(action)) {
            handleCategoryDelete(request, response, isAdmin);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response,
                            User loginUser, boolean isAdmin, boolean isOwner, String role)
            throws ServletException, IOException {
        List<Notice> list;
        int unreadCount = 0;
        if (isAdmin) {
            list = noticeService.getAllNotice();
        } else if (isOwner) {
            list = noticeService.getAllNoticeForOwner(loginUser.getId());
            unreadCount = noticeService.countUnread(loginUser.getId());
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        request.setAttribute("noticeList", list);
        request.setAttribute("role", role);
        request.setAttribute("isAdmin", isAdmin);
        request.setAttribute("unreadCount", unreadCount);
        request.getRequestDispatcher("notice-list.jsp").forward(request, response);
    }

    private void handleDetail(HttpServletRequest request, HttpServletResponse response,
                              User loginUser, boolean isOwner, String role, boolean isAdmin)
            throws ServletException, IOException {
        Integer id = parseId(request, response);
        if (id == null) return;

        Notice notice = noticeService.getNoticeById(id);
        if (notice == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (isOwner) {
            noticeService.markRead(loginUser.getId(), id);
        }

        request.setAttribute("notice", notice);
        request.setAttribute("role", role);
        request.setAttribute("isAdmin", isAdmin);
        request.getRequestDispatcher("notice-detail.jsp").forward(request, response);
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response,
                           User loginUser, boolean isAdmin) throws IOException {
        if (!isAdmin) {
            requireAdmin(response, loginUser, false);
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        if (isBlank(title) || isBlank(content)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Notice notice = buildNoticeFromRequest(request, loginUser);
        notice.setTitle(title.trim());
        notice.setContent(content.trim());
        noticeService.addNotice(notice);
        response.sendRedirect("notice?action=list");
    }

    private void handleToEdit(HttpServletRequest request, HttpServletResponse response,
                              boolean isAdmin) throws ServletException, IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Integer id = parseId(request, response);
        if (id == null) return;

        Notice notice = noticeService.getNoticeById(id);
        if (notice == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        loadCategories(request);
        request.setAttribute("notice", notice);
        request.getRequestDispatcher("notice-edit.jsp").forward(request, response);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response,
                              User loginUser, boolean isAdmin) throws IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Integer id = parseId(request, response);
        if (id == null) return;

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        if (isBlank(title) || isBlank(content)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Notice notice = buildNoticeFromRequest(request, loginUser);
        notice.setId(id);
        notice.setTitle(title.trim());
        notice.setContent(content.trim());
        noticeService.updateNotice(notice);
        response.sendRedirect("notice?action=list");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response,
                              boolean isAdmin) throws IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Integer id = parseId(request, response);
        if (id == null) return;
        noticeService.deleteNotice(id);
        response.sendRedirect("notice?action=list");
    }

    private void handleTop(HttpServletRequest request, HttpServletResponse response,
                           boolean isAdmin) throws IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Integer id = parseId(request, response);
        if (id == null) return;

        String topStr = request.getParameter("isTop");
        int isTop = "1".equals(topStr) ? 1 : 0;
        noticeService.updateTop(id, isTop);
        response.sendRedirect("notice?action=list");
    }

    private void handleCategoryList(HttpServletRequest request, HttpServletResponse response,
                                    boolean isAdmin, String role) throws ServletException, IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        request.setAttribute("categoryList", noticeService.getAllCategories());
        request.setAttribute("role", role);
        request.setAttribute("isAdmin", isAdmin);
        request.getRequestDispatcher("notice-category-list.jsp").forward(request, response);
    }

    private void handleCategoryAdd(HttpServletRequest request, HttpServletResponse response,
                                   boolean isAdmin) throws IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String name = request.getParameter("name");
        if (isBlank(name)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        NoticeCategory category = new NoticeCategory();
        category.setName(name.trim());
        category.setSortOrder(parseIntParam(request.getParameter("sortOrder"), 0));
        category.setRemark(trimToNull(request.getParameter("remark")));
        noticeService.addCategory(category);
        response.sendRedirect("notice?action=categoryList");
    }

    private void handleCategoryUpdate(HttpServletRequest request, HttpServletResponse response,
                                      boolean isAdmin) throws IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Integer id = parseId(request, response);
        if (id == null) return;

        String name = request.getParameter("name");
        if (isBlank(name)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        NoticeCategory category = new NoticeCategory();
        category.setId(id);
        category.setName(name.trim());
        category.setSortOrder(parseIntParam(request.getParameter("sortOrder"), 0));
        category.setRemark(trimToNull(request.getParameter("remark")));
        noticeService.updateCategory(category);
        response.sendRedirect("notice?action=categoryList");
    }

    private void handleCategoryDelete(HttpServletRequest request, HttpServletResponse response,
                                      boolean isAdmin) throws IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Integer id = parseId(request, response);
        if (id == null) return;

        try {
            noticeService.deleteCategory(id);
        } catch (IllegalStateException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }
        response.sendRedirect("notice?action=categoryList");
    }

    private void requireAdmin(HttpServletResponse response, User loginUser, boolean isAdmin) throws IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "需要管理员权限。当前 role=" + loginUser.getRole()
                            + ", roleId=" + loginUser.getRoleId()
                            + "。请访问 /testLogin?userId=1 重新登录管理员账号");
        }
    }

    private void setCommonAttributes(HttpServletRequest request, String role, boolean isAdmin) {
        request.setAttribute("role", role);
        request.setAttribute("isAdmin", isAdmin);
    }

    private Notice buildNoticeFromRequest(HttpServletRequest request, User loginUser) {
        Notice notice = new Notice();
        notice.setCategoryId(parseIntParam(request.getParameter("categoryId"), null));
        notice.setIsTop("1".equals(request.getParameter("isTop")) ? 1 : 0);
        notice.setPublisherId(loginUser.getId());
        String publisherName = loginUser.getName();
        if (publisherName == null || publisherName.trim().isEmpty()) {
            publisherName = loginUser.getUsername();
        }
        notice.setPublisher(publisherName);
        return notice;
    }

    private void loadCategories(HttpServletRequest request) {
        request.setAttribute("categoryList", noticeService.getAllCategories());
    }

    private User getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object obj = session.getAttribute(SessionConstants.LOGIN_USER);
        if (!(obj instanceof User)) return null;
        return (User) obj;
    }

    private Integer parseId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        try {
            return Integer.valueOf(idStr.trim());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    private Integer parseIntParam(String value, Integer defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
