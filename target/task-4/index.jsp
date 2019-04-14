<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Web application</title>
</head>
<body>
<c:choose>
    <c:when test="${pageContext.request.requestURI.endsWith('/status')}">
        <h2 style="color: red">Application Is Running</h2>
    </c:when>
    <c:when test="${not empty param.name}">
        <h2><c:out value="${'Name is {%s}'.replaceFirst('%s', param.name)}"/></h2>
    </c:when>
    <c:otherwise>
        <h2>Java Web Application</h2>
    </c:otherwise>
</c:choose>


</body>
</html>