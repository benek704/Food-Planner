package pl.coderslab.web.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetails;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@WebServlet(name = "PlanDetailServlet", value = "/app/plan/details")
public class PlanDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        PlanDao pd = new PlanDao();
        request.setAttribute("Plan", pd.read(id).get());
        request.setAttribute("DaysMap", pd.getPlanDetails(id).get().getMealPlan());
        request.setAttribute("id", id);
        request.getRequestDispatcher("/app/plan/details.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
