package ua.nure.plotnykova.usermanagement.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class User implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public User(String firstName, String lastName, Date now) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = now;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public User() {

    }

    public User(long id, String firstName, String lastName, Date dateOfBirth) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return new StringBuilder(firstName).append(", ").append(lastName).toString();
    }

    public int getAge(Date currentDate) {
        return (int) (TimeUnit.MILLISECONDS
                        .toDays(currentDate.getTime() - dateOfBirth.getTime()) / 365);
    }

}
