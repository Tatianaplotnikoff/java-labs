<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>User management. Add</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/add" method="post">
    <input type="hidden" name="id" , value="${user.id}">
    FirstName <input type="text" name="firstName" value=""/><br>
    LastName <input type="text" name="lastName" value=""/><br>
    Date of birth <input type="text" name="date"
                         value=""/><br>
    <input type="submit" name="okButton" value="Ok"><br>
    <input type="submit" name="cancelButton" value="Cancel">
</form>

</body>
</html>
