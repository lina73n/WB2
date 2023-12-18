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
    <h1><fmt:message key="add_anime.add_new_anime" /></h1>
    <form method="POST" action="${pageContext.request.contextPath}/your_anime_list/controller" enctype="multipart/form-data" >
        <input type="hidden" name="command" value="do_add_anime" />
        <div class="form-group">
            <label for="animeName"><fmt:message key="add_anime.anime_name"/></label>
            <input type="text" class="form-control" id="animeName" placeholder="<fmt:message key="add_anime.name_placeholder"/>" name="anime_name">
        </div>
        <div class="form-group">
            <label for="authorName"><fmt:message key="anime.author" /></label>
            <input type="text" class="form-control" id="authorName" placeholder="<fmt:message key="add_anime.author_placeholder"/>" name="author_name">
        </div>
        <div class="form-group">
            <label for="animeYear"><fmt:message key="anime.year" /></label>
            <input type="number" class="form-control" id="animeYear" placeholder="<fmt:message key="add_anime.year_placeholder"/>" name="anime_year">
        </div>
        <div class="form-group">
            <label for="animeDescription"><fmt:message key="anime.description"/></label>
            <textarea name="anime_description" class="form-control" id="animeDescription"
                      rows="5" placeholder="<fmt:message key="add_anime.description_placeholder"/>"></textarea>
        </div>
        <div class="form-group">
            <div>
                <label for="animeImage"><fmt:message key="add_anime.anime_image" /></label>
            </div>
            <input type="file" class="form-control-file" id="animeImage" name="anime_image">
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="add_anime.submit" /></button>
    </form>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
