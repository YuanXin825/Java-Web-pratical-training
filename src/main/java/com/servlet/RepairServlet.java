package com.servlet;

import com.bean.Repair;
import com.bean.User;
import com.constant.SessionConstants;
import com.dao.UserDao;
import com.service.RepairService;
import com.service.impl.RepairServiceImpl;
import com.util.RoleUtil;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/repair")
public class RepairServlet extends HttpServlet {

    private RepairService repairService = new RepairServiceImpl();
    private UserDao userDao = new UserDao();

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

        User loginUser = refreshLoginUser(request);
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        boolean isAdmin = RoleUtil.isAdmin(loginUser);
        boolean isOwner = RoleUtil.isOwner(loginUser);
        if (!isAdmin && !isOwner) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "无效角色，请重新登录。role=" + loginUser.getRole() + ", roleId=" + loginUser.getRoleId());
            return;
        }
        String role = loginUser.getRole();

        if ("toAdd".equals(action)) {
            if (!isOwner) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            request.getRequestDispatcher("repair-add.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            handleAdd(request, response, loginUser, isOwner);
        } else if ("list".equals(action)) {
            handleList(request, response, loginUser, isAdmin, isOwner, role);
        } else if ("detail".equals(action)) {
            handleDetail(request, response, loginUser, isAdmin, isOwner, role);
        } else if ("toDeal".equals(action)) {
            handleToDeal(request, response, isAdmin);
        } else if ("deal".equals(action)) {
            handleDeal(request, response, isAdmin);
        } else if ("evaluate".equals(action)) {
            handleEvaluate(request, response, loginUser, isOwner);
        } else if ("delete".equals(action)) {
            handleDelete(request, response, loginUser, isAdmin, isOwner);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response,
                           User loginUser, boolean isOwner) throws IOException {
        if (!isOwner) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        if (isBlank(title) || isBlank(content)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!isValidType(type)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "类型只能是报修或投诉");
            return;
        }

        Repair repair = new Repair();
        repair.setTitle(title.trim());
        repair.setContent(content.trim());
        repair.setType(type.trim());
        repair.setUserId(loginUser.getId());

        try {
            repairService.addRepair(repair);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }
        response.sendRedirect("repair?action=list");
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response,
                            User loginUser, boolean isAdmin, boolean isOwner, String role)
            throws ServletException, IOException {
        List<Repair> list;
        if (isAdmin) {
            list = repairService.getAllRepair();
        } else if (isOwner) {
            list = repairService.getRepairByUserId(loginUser.getId());
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        request.setAttribute("repairList", list);
        request.setAttribute("role", role);
        request.setAttribute("isAdmin", isAdmin);
        request.getRequestDispatcher("repair-list.jsp").forward(request, response);
    }

    private void handleDetail(HttpServletRequest request, HttpServletResponse response,
                              User loginUser, boolean isAdmin, boolean isOwner, String role)
            throws ServletException, IOException {
        Integer id = parseId(request, response);
        if (id == null) return;

        Repair repair = repairService.getRepairById(id);
        if (repair == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (isOwner && !repair.getUserId().equals(loginUser.getId())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        boolean canEvaluate = isOwner
                && "已完成".equals(repair.getStatus())
                && repair.getRating() == null;

        request.setAttribute("repair", repair);
        request.setAttribute("role", role);
        request.setAttribute("isAdmin", isAdmin);
        request.setAttribute("isOwner", isOwner);
        request.setAttribute("canEvaluate", canEvaluate);
        request.getRequestDispatcher("repair-detail.jsp").forward(request, response);
    }

    private void handleToDeal(HttpServletRequest request, HttpServletResponse response,
                              boolean isAdmin) throws ServletException, IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Integer id = parseId(request, response);
        if (id == null) return;

        Repair repair = repairService.getRepairById(id);
        if (repair == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("repair", repair);
        request.getRequestDispatcher("repair-deal.jsp").forward(request, response);
    }

    private void handleDeal(HttpServletRequest request, HttpServletResponse response,
                            boolean isAdmin) throws IOException {
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "需要管理员权限才能处理工单");
            return;
        }
        Integer id = parseId(request, response);
        if (id == null) return;

        String status = request.getParameter("status");
        if (!isValidStatus(status)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String reply = request.getParameter("reply");
        Repair repair = new Repair();
        repair.setId(id);
        repair.setStatus(status);
        repair.setReply(trimToNull(reply));

        repairService.updateRepair(repair);
        response.sendRedirect("repair?action=list");
    }

    private void handleEvaluate(HttpServletRequest request, HttpServletResponse response,
                                User loginUser, boolean isOwner) throws IOException {
        if (!isOwner) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Integer id = parseId(request, response);
        if (id == null) return;

        Integer rating = parseRating(request.getParameter("rating"));
        if (rating == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "请选择1-5星评价");
            return;
        }

        String evaluation = trimToNull(request.getParameter("evaluation"));
        boolean ok = repairService.evaluateRepair(id, loginUser.getId(), rating, evaluation);
        if (!ok) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "仅已完成且未评价的工单可评价");
            return;
        }
        response.sendRedirect("repair?action=detail&id=" + id);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response,
                              User loginUser, boolean isAdmin, boolean isOwner) throws IOException {
        Integer id = parseId(request, response);
        if (id == null) return;

        if (isAdmin) {
            repairService.deleteRepair(id);
        } else if (isOwner) {
            if (!repairService.deleteRepairByOwner(id, loginUser.getId())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        response.sendRedirect("repair?action=list");
    }

    /** 从 Session 取用户，并刷新数据库中的角色信息，避免 Session 角色过期导致 403 */
    private User refreshLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object obj = session.getAttribute(SessionConstants.LOGIN_USER);
        if (!(obj instanceof User)) return null;

        User sessionUser = (User) obj;
        if (sessionUser.getId() != null) {
            try {
                User dbUser = userDao.findById(sessionUser.getId());
                if (dbUser != null) {
                    RoleUtil.ensureRole(dbUser);
                    session.setAttribute(SessionConstants.LOGIN_USER, dbUser);
                    return dbUser;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        RoleUtil.ensureRole(sessionUser);
        session.setAttribute(SessionConstants.LOGIN_USER, sessionUser);
        return sessionUser;
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

    private Integer parseRating(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            int r = Integer.parseInt(value.trim());
            if (r >= 1 && r <= 5) return r;
        } catch (NumberFormatException ignored) {
        }
        return null;
    }

    private boolean isValidStatus(String status) {
        return "待处理".equals(status) || "处理中".equals(status) || "已完成".equals(status);
    }

    private boolean isValidType(String type) {
        return "报修".equals(type) || "投诉".equals(type);
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
