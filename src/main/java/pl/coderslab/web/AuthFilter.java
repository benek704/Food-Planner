package pl.coderslab.web;

import pl.coderslab.exception.SessionNotFound;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/** !Require testing! */

@WebFilter(filterName = "AuthFilter", value="/app/*")
public class AuthFilter implements Filter {
    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session == null) {   //checking whether the session exists
            this.context.log("Unauthorized access request");
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            try{
                chain.doFilter(request, response);
            }catch (SessionNotFound e){
                e.getMessage();
                ((HttpServletResponse) response).sendRedirect("/login");
            }
        }
    }

    public void destroy() {
        //close any resources here
    }
}
