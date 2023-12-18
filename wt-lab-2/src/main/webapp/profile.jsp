<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale"/>

<html>
<head>
    <title>Hello, World!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <style>
        html {
            height: 100%;
        }
        body {
            min-height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
    </style>
</head>
<body>
<%@include file="navbar.jsp"%>
<div class="d-flex flex-column align-items-center">
    <h2>${current_user.getLogin()}</h2>
    <h3><fmt:message key="profile.status"/>: ${current_user.getStatusValue()}</h3>
    <c:if test="${user != null}">
        <c:if test="${user.getUserPrivilegeRole().getName() == 'Administrator' && user.getId() != current_user.getId()}">
            <form method="POST" action="/your_anime_list/controller">
                <input type="hidden" name="user_id" value="${current_user.getId()}" />
                <c:choose>
                    <c:when test="${!current_user.isBanned()}">
                        <input type="hidden" name="command" value="ban" />
                        <button type="submit" class="btn btn-danger">
                            <fmt:message key="profile.ban"/>
                        </button>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="command" value="unban" />
                        <button type="submit" class="btn btn-success">
                            <fmt:message key="profile.unban"/>
                        </button>
                    </c:otherwise>
                </c:choose>
            </form>
        </c:if>
    </c:if>
</div>

<%@include file="footer.jsp"%>
</body>
</html>
