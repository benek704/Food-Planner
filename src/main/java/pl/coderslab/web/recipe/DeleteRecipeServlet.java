package pl.coderslab.web.recipe;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.utils.ServletUtil;
import pl.coderslab.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteRecipeServlet", value = "/app/recipe/delete")
public class DeleteRecipeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtil.deleteObjectWithRequestedId(new RecipeDao(), request);
        response.sendRedirect(this.getServletContext().getContextPath() + "/app/recipe/list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
