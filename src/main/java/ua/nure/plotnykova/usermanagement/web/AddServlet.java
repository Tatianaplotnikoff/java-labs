package ua.nure.plotnykova.usermanagement.web;

import ua.nure.plotnykova.usermanagement.db.DaoFactory;
import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends EditServlet{

    @Override
    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().create(user);
    }

    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req,resp);
    }
}
