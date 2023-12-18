<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale"/>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Your Anime List</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
  <style>
    .element-container {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
    }

    .element {
      width: calc(25% - 10px);
      margin-bottom: 20px;
    }

    #markValue {
        display: block;
        text-align: center;
        font-size: 24px;
        margin-top: 10px;
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
<div class="container">
    <div class="row text-center">
        <h1>${anime.getName()}</h1>
    </div>
    <div class="d-flex justify-content-space-between">
        <div class="col d-flex justify-content-center">
            <img src="images/${anime.getImagePath()}" alt="Anime Image" class="img-fluid" style="height: 400px">
        </div>
        <div class="col d-flex flex-column justify-content-center">
            <p><fmt:message key="anime.author" /> : ${anime.getAuthorName()}</p>
            <p><fmt:message key="anime.rating" /> : ${anime.getRating()}</p>
            <p><fmt:message key="anime.year" /> : ${anime.getYear()}</p>
            <p><fmt:message key="anime.description" /> : ${anime.getDescription()}</p>
        </div>
    </div>
</div>

<div class="d-flex flex-column align-items-center">
    <div style="width: 66%;">
        <c:if test="${requestScope.user != null}">
            <c:choose>
                <c:when test="${user_review == null}">
                    <c:choose>
                        <c:when test="${user.isBanned()}">
                            <h3 class="alert alert-warning"><fmt:message key="review.banned_label"/></h3>
                        </c:when>
                        <c:otherwise>
                            <%@include file="review_form.html"%>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <div class="container">
                        <h2><fmt:message key="review.your_review"/>:</h2>
                        <div class="row">
                            <div class="card mb-3">
                                <div class="card-body">
                                    <a href="/your_anime_list/controller?command=profile&user_id=${user_review.userId()}">
                                        <h5 class="card-title">${user_review.userLogin()}</h5>
                                    </a>
                                    <h6><fmt:message key="review.rate"/> ${user_review.rate()}</h6>
                                    <p class="card-text">${user_review.comment()}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>

        <div class="container">
            <h2><fmt:message key="anime.reviews"/>:</h2>
            <div class="row">
                <c:forEach var="animeReview" items="${anime_reviews}">
                    <c:if test="${user == null || (animeReview.userId() != user.getId())}">
                        <div class="card mb-3">
                            <div class="card-body">
                                <a href="/your_anime_list/controller?command=profile&user_id=${animeReview.userId()}">
                                    <h5 class="card-title">${animeReview.userLogin()}</h5>
                                </a>
                                <h6><fmt:message key="review.rate" />: ${animeReview.rate()}</h6>
                                <p class="card-text">${animeReview.comment()}</p>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
