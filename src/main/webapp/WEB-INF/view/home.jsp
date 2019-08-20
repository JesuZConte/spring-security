<%--
  Created by IntelliJ IDEA.
  User: nisum
  Date: 2019-08-19
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>luv2code Company Home Page</title>
</head>
<hr>
    <h2>luv2code Company Home Page</h2>
    <br/>
    <p>Welcome to the luv2code Company Home Page!</p>

    <p> User: <security:authentication property="principal.username" />
<br/><br/>
        Role(s): <security:authentication property="principal.authorities" />
    </p>

<!-- Add a logout button -->
    <form:form action="${pageContext.request.contextPath}/logout" method="POST">
        <input type="submit" value="Logout" />
    </form:form>

</body>
</html>
