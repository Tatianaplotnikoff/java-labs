package ua.nure.plotnykova.usermanagement.web;

import ua.nure.plotnykova.usermanagement.db.DaoFactory;
import ua.nure.plotnykova.usermanagement.db.UserDao;
import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BrowseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("addButton") != null) {
            add(req, resp);
        } else if (req.getParameter("editButton") != null) {
            edit(req, resp);
        } else if (req.getParameter("deleteButton") != null) {
            delete(req, resp);
        } else if (req.getParameter("detailsButton") != null) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        if (Objects.isNull(userId) || userId.trim().length() == 0) {
            req.setAttribute("error", "You must select user");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }

        try {
            Optional<User> userOptional = DaoFactory.getInstance().getUserDao().find(Long.valueOf(userId));
            User user = userOptional.get();
            req.getSession().setAttribute("user", user);
        } catch (DatabaseException e) {
            req.setAttribute("error", "ERROR: " + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/details").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        if (Objects.isNull(userId) || userId.trim().length() == 0) {
            req.setAttribute("error", "You must select user");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }

        try {
            DaoFactory.getInstance().getUserDao().delete(Long.parseLong(userId));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        browse(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        if (Objects.isNull(userId) || userId.trim().length() == 0) {
            req.setAttribute("error", "You must select user");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }

        try {
            Optional<User> userOptional = DaoFactory.getInstance().getUserDao().find(Long.valueOf(userId));
            User user = userOptional.get();
            req.getSession().setAttribute("user", user);
        } catch (DatabaseException e) {
            req.setAttribute("error", "ERROR: " + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/edit").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add").forward(req, resp);
    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users;
        try {
            users = DaoFactory.getInstance().getUserDao().findAll();
            req.getSession().setAttribute("users", users);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            throw new ServletException(e);
        }
    }


}
