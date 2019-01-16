package ua.nure.plotnykova.usermanagement.web;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;
import ua.nure.plotnykova.usermanagement.db.DaoFactory;
import ua.nure.plotnykova.usermanagement.db.MockDaoFactory;
import ua.nure.plotnykova.usermanagement.domain.User;

import java.util.ArrayList;
import java.util.Properties;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {

    private Mock mockUserDao;

    public void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDaoFactory.class.getName());
        DaoFactory.init(properties);
        setMockUserDao(((MockDaoFactory)DaoFactory.getInstance()).getMockUserDao());
    }

    @Override
    protected void tearDown() throws Exception {
        mockUserDao.verify();
        super.tearDown();
    }
}
