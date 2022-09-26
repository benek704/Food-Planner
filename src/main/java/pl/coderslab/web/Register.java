package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/registration.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO
        // Necessary: check if email is correct via regex : ^(.+)@(.+)$ ...
        // Pattern pattern = Pattern.complile(regex);
        // Matcher matcher = pattern.matcher(email);
        // Optional: More information on successful / failed attempt to create account?

        Admin admin = new Admin();
        String password = request.getParameter("password");
        String rePassword = request.getParameter("repassword");
        if(password.equals(rePassword)){
            admin.setFirstName(request.getParameter("name"));
            admin.setLastName(request.getParameter("surname"));
            admin.setEmail(request.getParameter("email"));
            admin.setPassword(password);

            AdminDao adminDao = new AdminDao();
            adminDao.create(admin);
            response.sendRedirect("/login");
        }else{
            request.getRequestDispatcher("/registration.jsp").forward(request,response);
        }
    }
}
