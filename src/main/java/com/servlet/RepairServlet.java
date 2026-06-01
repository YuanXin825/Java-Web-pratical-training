package com.servlet;

import com.entity.Repair;
import com.service.RepairService;
import com.impl.RepairServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/repair")
public class RepairServlet extends HttpServlet {

    private RepairService repairService = new RepairServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        // 1. 业主提交报修
        if ("add".equals(action)) {
            Integer residentId = Integer.valueOf(request.getParameter("residentId"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            Integer type = Integer.valueOf(request.getParameter("type"));

            Repair repair = new Repair();
            repair.setResidentId(residentId);
            repair.setTitle(title);
            repair.setContent(content);
            repair.setType(type);

            repairService.addRepair(repair);
            response.sendRedirect("repair?action=list");
        }

        // 2. 报修列表
        else if ("list".equals(action)) {
            List<Repair> list = repairService.getAllRepair();
            request.setAttribute("repairList", list);
            request.getRequestDispatcher("repair-list.jsp").forward(request, response);
        }

        // 3. 跳转到处理页面
        else if ("toDeal".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            Repair repair = repairService.getRepairById(id);
            request.setAttribute("repair", repair);
            request.getRequestDispatcher("repair-deal.jsp").forward(request, response);
        }

        // 4. 物业处理报修
        else if ("deal".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            Integer status = Integer.valueOf(request.getParameter("status"));
            String remark = request.getParameter("remark");

            Repair repair = new Repair();
            repair.setId(id);
            repair.setStatus(status);
            repair.setRemark(remark);

            repairService.updateRepair(repair);
            response.sendRedirect("repair?action=list");
        }

        // 5. 删除报修
        else if ("delete".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            repairService.deleteRepair(id);
            response.sendRedirect("repair?action=list");
        }
    }
}