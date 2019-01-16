package ua.nure.plotnykova.usermanagement.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DetailsServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("cancelButton") != null) {
            doCancel(req,resp);
        } else {
            showPage(req,resp);
        }
    }

    private void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/details.jsp").forward(req, resp);
    }

    private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/browse").forward(req,resp);
    }
}
