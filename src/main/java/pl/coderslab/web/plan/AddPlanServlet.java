package pl.coderslab.web.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.SessionUtil;
import pl.coderslab.utils.StringUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "AddPlanServlet", value = "/app/plan/add")
public class AddPlanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/app/plan/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("planName");
        String desc = request.getParameter("description");
        if (StringUtil.isAnyNullOrBlank(name, desc)) {
            response.sendRedirect(this.getServletContext().getContextPath() + "/app/plan/add");
            return;
        }
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        System.out.println(currentTime);
        int adminId = SessionUtil.getCurrentUserId(request.getSession());
        Plan plan = new Plan(name, desc, currentTime, adminId);
        new PlanDao().create(plan);
        response.sendRedirect(this.getServletContext().getContextPath() + "/app/plan/list");
    }
}
