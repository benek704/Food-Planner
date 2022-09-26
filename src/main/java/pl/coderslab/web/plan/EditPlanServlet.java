package pl.coderslab.web.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.exception.IdNotFoundException;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.ServletUtil;
import pl.coderslab.utils.SessionUtil;
import pl.coderslab.utils.StringUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

@WebServlet(name = "EditPlanServlet", value = "/app/plan/edit")
public class EditPlanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletUtil.searchIdRequest(new PlanDao(), "plan", request);
            request.getRequestDispatcher("edit.jsp").forward(request, response);
        } catch (IdNotFoundException e) {
            response.sendRedirect(this.getServletContext().getContextPath() + "/app/plan/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newPlanName = request.getParameter("planName");
        String newDesc = request.getParameter("description");
        String idParam = request.getParameter("id");

        if (!StringUtil.isAnyNullOrBlank(newPlanName, newDesc, idParam)) {
            int planId = Integer.parseInt(idParam);
            Plan plan = new Plan(planId,
                    newPlanName,
                    newDesc,
                    new Timestamp(System.currentTimeMillis()),
                    SessionUtil.getCurrentUserId(request.getSession()));
            new PlanDao().update(plan);
        }
        response.sendRedirect(this.getServletContext().getContextPath() + "/app/plan/list");
    }
}
