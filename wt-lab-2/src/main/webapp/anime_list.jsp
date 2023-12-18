<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale"/>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Your Anime List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <style>
        .element-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
            width: 100%;
        }

        .element {
            width: calc(25% - 10px);
            margin-bottom: 20px;
        }

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
<div>
    <div class="element-container">
        <c:forEach var="anime" items="${anime_list}">
            <div class="element d-flex align-items-center justify-content-center">
                <a href="?command=anime&id=${anime.getId()}" class="card-element">
                    <div class="card element" style="width: 13rem;">
                        <img src="images/${anime.getImagePath()}" class="card-img-top" alt="Anime Image">
                        <div class="card-body">
                            <h5 class="card-title">${anime.getName()}</h5>
                        </div>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
    <div class="d-flex justify-content-center">
        <form method="POST" action="/your_anime_list/controller">
            <input type="hidden" name="page_num" value="${page_num}" />
            <c:if test="${page_num != 1}">
                <button type="submit" name="command" value="prev_anime" class="btn btn-primary">Prev</button>
            </c:if>
            <c:if test="${page_num != max_page_num}">
                <button type="submit" name="command" value="next_anime" class="btn btn-primary">Next</button>
            </c:if>
        </form>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
