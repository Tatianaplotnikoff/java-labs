package ua.nure.plotnykova.usermanagement.web;

import ua.nure.plotnykova.usermanagement.domain.User;

import java.text.DateFormat;
import java.util.Date;

public class EditServletTest extends MockServletTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    public void testEdit() {
        Date date = new Date();
        User user = new User(1000, "Harry", "Potter", date);
        getMockUserDao().expect("update", user);

        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "Harry");
        addRequestParameter("lastName", "Potter");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton","Ok");
        doPost();
    }

    public void testEditEmptyFirstName() {
        Date date = new Date();

        addRequestParameter("id", "1000");
        addRequestParameter("lastName", "Potter");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton","Ok");
        doPost();
        String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message", error);
    }

    public void testEditEmptyLastName() {
        Date date = new Date();

        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "Harry");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton","Ok");
        doPost();
        String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message", error);
    }

    public void testEditEmptyDateOfBirth() {

        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "Harry");
        addRequestParameter("lastName", "Potter");
        addRequestParameter("okButton","Ok");
        doPost();
        String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message", error);
    }

    public void testEditIncorrectDateOfBirth() {
        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "Harry");
        addRequestParameter("lastName", "Potter");
        addRequestParameter("date", "adsadsadasd");
        addRequestParameter("okButton","Ok");
        doPost();
        String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message", error);
    }
}
