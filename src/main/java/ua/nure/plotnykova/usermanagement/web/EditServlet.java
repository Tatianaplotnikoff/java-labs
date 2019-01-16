package ua.nure.plotnykova.usermanagement.web;

import ua.nure.plotnykova.usermanagement.db.DaoFactory;
import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Objects;

public class EditServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("okButton") != null) {
            doOk(req, resp);
        } else if (req.getParameter("cancelButton") != null) {
            doCancel(req, resp);
        } else {
            showPage(req, resp);
        }
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    protected void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = null;
        try {
            user = getUser(req);
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            showPage(req, resp);
            return;
        }
        try {
            processUser(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().update(user);
    }

    protected User getUser(HttpServletRequest req) throws ValidationException {
        User user = new User();
        String id = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String date = req.getParameter("date");
        if (Objects.isNull(firstName)) {
            throw new ValidationException("First name is empty");
        }
        if (Objects.isNull(lastName)) {
            throw new ValidationException("Last name is empty");
        }
        if (Objects.isNull(date)) {
            throw new ValidationException("Date is empty");
        }
        if (Objects.nonNull(id)) {
            user.setId(Long.valueOf(id));
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setDateOfBirth(DateFormat.getDateInstance().parse(date));
        } catch (ParseException e) {
            throw new ValidationException("DateFormat is incorrect");
        }

        return user;
    }
}
