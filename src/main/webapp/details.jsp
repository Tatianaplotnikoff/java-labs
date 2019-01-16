<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" class="ua.nure.plotnykova.usermanagement.domain.User" scope="session"/>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>
<html>
<head>
    <title>User management. Edit</title>
</head>
<body>
    FirstName: ${user.firstName}<br>
    LastName: ${user.lastName}<br>
    Date of birth: <fmt:formatDate value="${user.dateOfBirth}" type="date" dateStyle="medium"/>"<br>
    Age: ${user.getAge(now)}<br>
    <input type="submit" name="cancelButton" value="Cancel">
</body>
</html>
