package ua.nure.plotnykova.usermanagement.web;

import ua.nure.plotnykova.usermanagement.domain.User;

import java.text.DateFormat;
import java.util.Date;

public class AddServletTest extends MockServletTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(AddServlet.class);
	}

	public void testAdd() {
		Date date = new Date();
		User newUser = new User("Harry", "Potter", date);
		User user = new User(1000, "Harry", "Potter", date);
		getMockUserDao().expectAndReturn("create", newUser, user);

		addRequestParameter("firstName", "Harry");
		addRequestParameter("lastName", "Potter");
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
	}

	public void testAddEmptyFirstName() {
		Date date = new Date();

		addRequestParameter("id", "1000");
		addRequestParameter("lastName", "Potter");
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message", error);
	}

	public void testAddEmptyLastName() {
		Date date = new Date();

		addRequestParameter("id", "1000");
		addRequestParameter("firstName", "Harry");
		addRequestParameter("date", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message", error);
	}

	public void testAddEmptyDateOfBirth() {

		addRequestParameter("id", "1000");
		addRequestParameter("firstName", "Harry");
		addRequestParameter("lastName", "Potter");
		addRequestParameter("okButton", "Ok");
		doPost();
		String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message", error);
	}

	public void testAddIncorrectDateOfBirth() {
		addRequestParameter("id", "1000");
		addRequestParameter("firstName", "Harry");
		addRequestParameter("lastName", "Potter");
		addRequestParameter("date", "adsadsadasd");
		addRequestParameter("okButton", "Ok");
		doPost();
		String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message", error);
	}
}
