package pl.coderslab.web.recipe;

import pl.coderslab.dao.Recipe;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "AddRecipe", value = "/app/recipe/add")
public class AddRecipe extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/app/recipe/addRecipe.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId = SessionUtil.getCurrentUserId(request.getSession());
        String recipeName = request.getParameter("name");
        String recipeDesc = request.getParameter("description");
        int recipeTime = Integer.parseInt(request.getParameter("time"));
        String howToPrepare = request.getParameter("preparation");
        String ingredients = request.getParameter("ingredients");

        Date today = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String updated = df.format(today);
        RecipeDao recipeDao = new RecipeDao();

        Recipe recipe = new Recipe(recipeName,ingredients,recipeDesc,updated,updated,recipeTime,howToPrepare,adminId);
        recipeDao.create(recipe);

        response.sendRedirect("/app/recipe/list");
    }
}
