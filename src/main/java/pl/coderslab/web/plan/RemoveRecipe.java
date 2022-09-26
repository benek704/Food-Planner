package pl.coderslab.web.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.NoSuchElementException;

@WebServlet(name = "ConfirmRemoveRecipeFromPlan", value = "/add/plan/remove")
public class RemoveRecipe extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int removeId = Integer.parseInt(request.getParameter("id"));
        int planId = Integer.parseInt(request.getParameter("planId"));
        PlanDao planDao = new PlanDao();
        planDao.removeMeal(removeId);

        response.sendRedirect(this.getServletContext().getContextPath() + "/app/plan/details?id=" + planId);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
