package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.StringUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminDao admin = new AdminDao();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!StringUtil.isAnyNullOrBlank(email, password) && admin.readLogin(email, password) == 1) {
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null){
                oldSession.invalidate();
            }
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("logged", admin.userId(email));
            newSession.setMaxInactiveInterval(5*60);

            Cookie message = new Cookie("massage", "Witaj");
            response.addCookie(message);

            response.sendRedirect("/app/dashboard");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
