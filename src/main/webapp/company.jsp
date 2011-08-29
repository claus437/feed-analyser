<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.Date" %>
<%@ page import="org.wooddog.servlets.PageAction" %>
<%@ page import="org.wooddog.servlets.PageActionFactory" %>
<%@ page import="org.wooddog.servlets.model.CompanyModel2" %>
<%@ page import="org.wooddog.domain.History" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.wooddog.servlets.model.CompanyPageController" %>

<%!
    static final String SHARE_COLOR = "#995050";
    static final String SCORE_COLOR = "#509950";
    static final String RECOMMENDATION_COLOR = "#505099";
    static final String SHARE = History.Field.SHARE.name();
    static final String SCORE = History.Field.SCORE.name();
    static final String RECOMMENDATION = History.Field.RECOMMENDATION.name();
%>

<%
    PageAction action;
    CompanyPageController controller;
    int companyId;

    if (request.getParameter("companyId") != null) {
        companyId = Integer.parseInt(request.getParameter("companyId"));
    } else {
        companyId = 6;
    }


    controller = (CompanyPageController) session.getAttribute(getClass().getName());
    if (controller == null) {
        System.out.println("creating controller");
        controller = new CompanyPageController();
        controller.setDate(new Date());
        session.setAttribute(getClass().getName(), controller);
    }

    controller.setSummaryCount(10);
    controller.setGraphCount(50);
    controller.setGraphHeight(140);
    controller.setGraphWidth(550);
    controller.setGraphColor(SCORE, SCORE_COLOR);
    controller.setGraphColor(SHARE, SHARE_COLOR);
    controller.setGraphColor(RECOMMENDATION, RECOMMENDATION_COLOR);


    controller.setCompanyId(companyId);


    pageContext.setAttribute("model", controller);


    action = PageActionFactory.getInstance().getAction(request.getParameter("action"));

    if (action != null) {
        try {
            action.setParameters(request.getParameterMap());
            controller.execute(action);
        } catch (Throwable x) {
            session.setAttribute("error", x.getMessage());
        }
        response.sendRedirect("company.jsp");
        return;
    }



%>

<html>
    <head>
        <title>company</title>
        <link rel="stylesheet" href="css/default.css" media="screen" type="text/css"/>
        <script src="js/Graph.js" type="text/javascript"></script>

        <script type="text/javascript">
            var graph;


            function init() {
                var minValues = document.getElementById("minValues");
                var midValues = document.getElementById("midValues");
                var maxValues = document.getElementById("maxValues");
                var data;

                graph = new Graph(minValues, midValues, maxValues);

                data = new GrapData();
                data.setImage(document.getElementById("shareImg"));
                data.setColor("<%=SHARE_COLOR%>");
                data.setMinValue(${model.graphs['SHARE'].minValue});
                data.setMidValue(${model.graphs['SHARE'].midValue});
                data.setMaxValue(${model.graphs['SHARE'].maxValue});
                graph.add("share", data);

                data = new GrapData();
                data.setImage(document.getElementById("scoreImg"));
                data.setColor("<%=SCORE_COLOR%>");
                data.setMinValue(${model.graphs['SCORE'].minValue});
                data.setMidValue(${model.graphs['SCORE'].midValue});
                data.setMaxValue(${model.graphs['SCORE'].maxValue});
                graph.add("score", data);

                data = new GrapData();
                data.setImage(document.getElementById("recommendationImg"));
                data.setColor("<%=RECOMMENDATION_COLOR%>");
                data.setMinValue(${model.graphs['RECOMMENDATION'].minValue});
                data.setMidValue(${model.graphs['RECOMMENDATION'].midValue});
                data.setMaxValue(${model.graphs['RECOMMENDATION'].maxValue});
                graph.add("recommendation", data);

                graph.refresh();
            }
        </script>

        <style type="text/css">
            tr.header td {
                vertical-align: bottom;
            }

            tr.rate-header td {
                font-weight: bold;
                width: 20px;
                vertical-align: top;
            }

        </style>

    </head>

    <body onload="init()">
        <div class="content">
            <jsp:include page="header.jsp">
                <jsp:param name="title" value="COMPANY"/>
            </jsp:include>

            <jsp:include page="error.jsp"/>

            <form action="?action=FindCompany" method="POST" style="margin-top: 30px;">
                <div style="float: right; background-color: #505050; color: white; border: 1px solid #505050">
                    <input type="text" name="name" style="border: 0; height: 20px"/>
                    <input type="submit" value="FIND" style="border: 0; background-color: #505050; color: #FFFFFF; height: 20px;">
                </div>
            </form>

            <table class="company">
                <tbody>
                <tr class="header">
                    <td colspan="4">${model.company.name}</td>
                    <td colspan="7"></td>
                </tr>
                <tr class="month">
                    <td></td>
                    <c:forEach items="${model.summaryList}" var="history"><td><fmt:formatDate value="${history.date}" pattern="dd MMM"/></td></c:forEach>
                </tr>
                <tr class="score">
                    <td>POINTS</td>
                    <c:forEach items="${model.summaryList}" var="history"><td><fmt:formatNumber value="${history.score}" pattern="#.##" /></td></c:forEach>
                </tr>
                <tr class="score">
                    <td></td>
                    <c:forEach items="${model.summaryList}" var="history"><td><fmt:formatNumber value="${history.scoreChanged}" pattern="#.##"/></td></c:forEach>
                </tr>
                <tr class="stock">
                    <td>SHARE</td>
                    <c:forEach items="${model.summaryList}" var="history"><td><fmt:formatNumber value="${history.share}" pattern="#,##0.###"/></td></c:forEach>
                </tr>
                <tr class="stock">
                    <td></td>
                    <c:forEach items="${model.summaryList}" var="history"><td><fmt:formatNumber value="${history.shareChanged}" pattern="#,##0.###"/></td></c:forEach>
                </tr>
                <tr class="recommendation border-top">
                    <td>REC</td>
                    <c:forEach items="${model.summaryList}" var="history"><td>${history.recommendation}</td></c:forEach>
                </tr>
                <tr class="recommendation border-bottom">
                    <td></td>
                    <c:forEach items="${model.summaryList}" var="history"><td>${history.recommendationChanged}</td></c:forEach>
                </tr>
                </tbody>
            </table>

            <table class="graph">
                <tr>
                    <td valign="top" rowspan="4" style="background-color: #999999; color: white; font-weight: bold;">
                        <input type="checkbox" onclick="graph.show('share', this.checked)" checked="checked"/> POINTS<br/>
                        <input type="checkbox" onclick="graph.show('score', this.checked)" checked="checked"/> SHARE<br/>
                        <input type="checkbox" onclick="graph.show('recommendation', this.checked)" checked="checked"/> REC.<br/>
                    </td>
                    <td class="graphMax"><div id="maxValues"></div></td>
                    <td  valign="top" rowspan="3" align="right" style="width: 0">
                        <div style="position: relative; width: 550px; height: 100px">
                            <img alt="" id="shareImg" style="position: absolute; top: 0; left:0; margin: 0; padding: 0; width: 550px; height: 100px;" src="img/${model.graphs['SHARE'].image}"/>
                            <img alt="" id="scoreImg" style="position: absolute; top: 0; left:0; margin: 0; padding: 0; width: 550px; height: 100px;" src="img/${model.graphs['SCORE'].image}"/>
                            <img alt="" id="recommendationImg" style="position: absolute; top: 0; left:0; margin: 0; padding: 0; width: 550px; height: 100px;" src="img/${model.graphs['RECOMMENDATION'].image}"/>
                        </div>
                    </td>
                </tr>
                <tr><td class="graphMid"><div id="midValues"></div></td></tr>
                <tr><td class="graphLow"><div id="minValues"></div></td></tr>
                <tr><td colspan="2" align="right"><br/>SUB</td></tr>
            </table>


            <style>
                table.articles td {
                    vertical-align: top;
                }

                table.articles tr : first-child {
                    white-space: nowrap;
                }
            </style>

            <form action="?action=ArticleList" method="POST" style="margin-top: 30px;">
                <div style="float: right; background-color: #505050; color: white; border: 1px solid #505050">
                    <input type="text" name="name" style="border: 0; height: 20px"/>
                    <input type="submit" value="JUMP" style="border: 0; background-color: #505050; color: #FFFFFF; height: 20px;">
                </div>
            </form>
            <br/>


            <table style="margin-top: 20px; border-bottom: 1px solid #999999; border-collapse: collapse; width: 100%">
                <tr>
                    <td valign="top" style="padding-bottom: 10px;">
                        <table class="articles">
                            <c:forEach items="${model.articleList}" var="article" begin="0" end="10">
                                <c:choose>
                                    <c:when test="${article != null}">
                                        <tr>
                                            <td style="white-space: nowrap;"><fmt:formatDate value="${article.published}" pattern="MMM dd yyyy"/></td>
                                            <td><a href="article.jsp?scoreId=${model.articleScore[article.id].id}&articleId=${article.id}">${article.title}</a></td>
                                            <td>${model.articleScore[article.id].score}</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise><td colspan="3">&nbsp;</td></c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </table>
                    </td>

                    <td>&nbsp;</td><td style="border-left: 1px solid #999999">&nbsp;</td>

                    <td valign="top" style="padding-bottom: 10px;">
                        <table class="articles">
                            <c:forEach items="${model.articleList}" var="article" begin="0" end="10">
                                <c:choose>
                                    <c:when test="${article != null}">
                                        <tr>
                                            <td style="white-space: nowrap;"><fmt:formatDate value="${article.published}" pattern="MMM dd yyyy"/></td>
                                            <td><a href="article.jsp?scoreId=${model.articleScore[article.id].id}&articleId=${article.id}">${article.title}</a></td>
                                            <td>${model.articleScore[article.id].score}</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise><td colspan="3">&nbsp;</td></c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </table>
                    </td>
                </tr>

                <tr><td colspan="4" align="right">PREV | NEXT</td></tr>
            </table>
         </div>
    </body>
</html>