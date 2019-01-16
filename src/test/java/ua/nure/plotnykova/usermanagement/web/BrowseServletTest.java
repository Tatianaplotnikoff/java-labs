package ua.nure.plotnykova.usermanagement.web;

import ua.nure.plotnykova.usermanagement.domain.User;

import java.util.*;

public class BrowseServletTest extends MockServletTestCase {


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    public void testBrowse() {
        User user = new User(1000, "Harry", "Potter", new Date());
        List<User> userList = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAll", userList);
        doGet();
        List<User> users = (List<User>) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull("Could not find list of users in session", users);
        assertSame(userList, users);
    }

    public void testEdit() {
        User user = new User(1000, "Harry", "Potter", new Date());
        getMockUserDao().expectAndReturn("find", 1000L, Optional.of(user));
        addRequestParameter("editButton", "Edit");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull("Could not find user", user);
        assertSame(user, userInSession);
    }
}
