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

        if ("add".equals(action)) {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            Integer userId = Integer.valueOf(request.getParameter("userId"));

            Repair repair = new Repair();
            repair.setTitle(title);
            repair.setContent(content);
            repair.setUserId(userId);

            repairService.addRepair(repair);
            response.sendRedirect("repair?action=list");
        }

        else if ("list".equals(action)) {
            List<Repair> list = repairService.getAllRepair();
            request.setAttribute("repairList", list);
            request.getRequestDispatcher("repair-list.jsp").forward(request, response);
        }

        else if ("toDeal".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            Repair repair = repairService.getRepairById(id);
            request.setAttribute("repair", repair);
            request.getRequestDispatcher("repair-deal.jsp").forward(request, response);
        }

        else if ("deal".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            String status = request.getParameter("status");

            Repair repair = new Repair();
            repair.setId(id);
            repair.setStatus(status);

            repairService.updateRepair(repair);
            response.sendRedirect("repair?action=list");
        }

        else if ("delete".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            repairService.deleteRepair(id);
            response.sendRedirect("repair?action=list");
        }
    }
}
