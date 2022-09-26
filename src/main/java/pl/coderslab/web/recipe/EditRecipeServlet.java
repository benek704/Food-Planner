package pl.coderslab.web.recipe;

import pl.coderslab.dao.Recipe;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.exception.IdNotFoundException;
import pl.coderslab.utils.ServletUtil;
import pl.coderslab.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "EditRecipeServlet", value = "/app/recipe/edit")
public class EditRecipeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletUtil.searchIdRequest(new RecipeDao(), "recipe", request);
            request.getRequestDispatcher("editRecipe.jsp").forward(request, response);
        } catch (IdNotFoundException e) {
            response.sendRedirect(this.getServletContext().getContextPath() + "/app/recipe/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId = SessionUtil.getCurrentUserId(request.getSession());
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
        String preparation = request.getParameter("preparation");
        String ingredients= request.getParameter("ingredients");
        String created = request.getParameter("created");

        Date today = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String updated = df.format(today);
        RecipeDao recipeDao = new RecipeDao();

        recipeDao.update(new Recipe
                (id,name,ingredients,description,created,updated,preparationTime,preparation,adminId));
        response.sendRedirect("/app/recipe/list");
    }
}
