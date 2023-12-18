<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Anime List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <style>
        html {
            height: 100%;
        }
        body {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            min-height: 100%;
        }
    </style>
</head>
<body>
<%@include file="navbar.jsp"%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h2><fmt:message key="auth.login" /></h2>
            <form method="POST" action="/your_anime_list/controller">
                <input type="hidden" name="command" value="do_login" />
                <div class="form-group">
                    <label for="login"><fmt:message key="auth.nickname" /></label>
                    <input name="login" class="form-control" id="login"
                           placeholder="<fmt:message key="auth.nickname_placeholder" />"
                           pattern="[a-zA-Z][a-zA-Z0-9]{5,25}" required>
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="auth.password" /></label>
                    <input name="password" type="password" class="form-control"
                           id="password" placeholder="<fmt:message key="auth.password_placeholder" />"
                           pattern="[a-zA-Z0-9]{6,26}" required>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="auth.login" /></button>
            </form>
        </div>
   </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
