package pl.coderslab.web.recipe;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.Recipe;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddToPlan", value = "/app/recipe/plan/add")
public class AddToPlan extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId = SessionUtil.getCurrentUserId(request.getSession());
        List<Plan> userPlans = new PlanDao().findAll(adminId);
        List<DayName> days = new DayNameDao().findAll();
        List<Recipe> recipes = new RecipeDao().findAll(adminId);
        request.setAttribute("plans", userPlans);
        request.setAttribute("days", days);
        request.setAttribute("recipes", recipes);
        request.getRequestDispatcher("/app/recipe/plan/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int plan = Integer.parseInt(request.getParameter("plan"));
        int recipe = Integer.parseInt(request.getParameter("recipe"));
        int day = Integer.parseInt(request.getParameter("day"));
        String mealName = request.getParameter("mealName");
        int mealNumber = Integer.parseInt(request.getParameter("mealNumber"));
        PlanDao planDao = new PlanDao();
        planDao.addMeal(plan,recipe,day,mealName,mealNumber);
        response.sendRedirect("/app/recipe/plan/add");



    }
}
