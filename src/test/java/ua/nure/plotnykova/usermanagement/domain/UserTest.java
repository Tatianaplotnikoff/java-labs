package ua.nure.plotnykova.usermanagement.domain;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

public class UserTest {

    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User(1L, "Ivan", "Sokolov", new SimpleDateFormat(DATE_FORMAT)
                .parse("18.12.1995"));
    }

    @Test
    public void getFullNameTest() {
        assertEquals("Ivan, Sokolov", user.getFullName());
    }

    @Test
    public void getAgeTest() {
        assertEquals(22, user.getAge(Calendar.getInstance().getTime()));
    }
}