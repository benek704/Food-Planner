package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.Recipe;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetails;
import pl.coderslab.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@WebServlet("/app/dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int adminId = SessionUtil.getCurrentUserId(request.getSession());

        //This code will count how many recipes user has
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> listRecipe = recipeDao.findAll(adminId);
        request.setAttribute("recipeCount", listRecipe.size());

        //This code will count how many plans user has
        PlanDao planDao = new PlanDao();
        List<Plan> listPlan = planDao.findAll(adminId);
        request.setAttribute("recipePlan", listPlan.size());

        //This code will pass parameter with latest plan of user to JSP
        request.setAttribute("LatestPlanMap", planDao.getLatestPlanForUser(adminId)
                .orElse(new PlanDetails()).getMealPlan());
        request.setAttribute("Plan", planDao.readForAdmin(adminId)
                .orElse(new Plan()).getName());
        request.getRequestDispatcher("/app/dashboard/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
