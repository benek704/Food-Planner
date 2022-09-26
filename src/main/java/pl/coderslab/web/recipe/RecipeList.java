package pl.coderslab.web.recipe;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.Recipe;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecipeList", value = "/app/recipe/list")
public class RecipeList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId = SessionUtil.getCurrentUserId(request.getSession());
        RecipeDao rd = new RecipeDao();
        request.setAttribute("Recipes", rd.findAll(adminId));
        request.getRequestDispatcher("/app/recipe/recipeList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
