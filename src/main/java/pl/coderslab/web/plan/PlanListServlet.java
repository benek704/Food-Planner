package pl.coderslab.web.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PlanListServlet", value = "/app/plan/list")
public class PlanListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId = SessionUtil.getCurrentUserId(request.getSession());
        request.setAttribute("Plans", new PlanDao().findAll(adminId));
        request.getRequestDispatcher("/app/plan/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
