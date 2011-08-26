<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page import="org.wooddog.servlets.actions.ReadArticleAction" %>
<%
    ReadArticleAction action;

    action = new ReadArticleAction();
    action.execute(request.getParameterMap());

    pageContext.setAttribute("article", action.getArticle());
%>

<html>
    <head>
        <title>article</title>
        <link rel="stylesheet" href="css/default.css" media="screen" type="text/css"/>
    </head>

    <body>
        <div class="content">
            <jsp:include page="header.jsp">
                <jsp:param name="title" value="ARTICLE"/>
            </jsp:include>

            <jsp:include page="error.jsp"/>

            <form action="?action=SetScore" method="POST" style="margin-top: 30px;">
                <div style="float: right; background-color: #505050; color: white; border: 1px solid #505050">
                    <input type="text" name="name" style="border: 0; height: 20px"/>
                    <input type="submit" value="RESCORE" style="border: 0px; background-color: #505050; color: #FFFFFF; height: 20px;">
                </div>
            </form>

            <br/>

            <h2>${article.title}</h2>
            <h3><fmt:formatDate value="${article.published}" pattern="dd. MMM yyyy HH:mm"/> (${article.source})</h3>
            <p>${article.description}</p>
        </div>
    </body>
</html>