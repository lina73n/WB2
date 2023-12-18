<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Anime List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <style>
        .error-caption {
            font-size: 36px;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .error-image {
            max-width: 100%;
            height: auto;
        }

        html {
            height: 100%;
        }

        body {
            min-height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            align-content: center;
        }
    </style>
</head>
<body>

<%@include file="navbar.jsp"%>
<div class="container text-center">
    <div class="error-caption"><fmt:message key="error.error" /></div>
    <img src="images/404.jpg" alt="Error Image" class="error-image">
</div>
<%@include file="footer.jsp"%>
</body>
</html>
