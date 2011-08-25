<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page import="org.wooddog.dao.service.CompanyServiceDao" %>
<%@ page import="org.wooddog.domain.Company" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="org.wooddog.servlets.PageAction" %>
<%@ page import="org.wooddog.servlets.PageActionFactory" %>
<%@ page import="org.wooddog.servlets.model.TrentModel" %>
<%@ page import="org.wooddog.servlets.model.CompanyModel" %>
<%@ page import="java.awt.*" %>

<%!
    static final int HISTORY_COUNT = 10;
    static final String SHARE_COLOR = "#995050";
    static final String SCORE_COLOR = "#509950";
    static final String RECOMMENDATION_COLOR = "#505099";
%>

<%
    PageAction action;
    CompanyModel model;
    int companyId;


    action = PageActionFactory.getInstance().getAction(request.getParameter("action"));
    if (action != null) {
        try {
            action.execute(request.getParameterMap());
        } catch (Throwable x) {
            session.setAttribute("error", "failed adding company, " + x.getMessage());
        }
        response.sendRedirect("trends.jsp");
        return;
    }

    if (request.getParameter("companyId") == null) {
        companyId = 6;
    } else {
        companyId = Integer.parseInt(request.getParameter("companyId"));
    }

    model = new CompanyModel();
    model.setColor(CompanyModel.GraphElement.SHARE, SHARE_COLOR);
    model.setColor(CompanyModel.GraphElement.SCORE, SCORE_COLOR);
    model.setColor(CompanyModel.GraphElement.RECOMMENDATION, RECOMMENDATION_COLOR);
    model.setDate(new Date());
    model.setWidth(550);
    model.setHeight(200);
    model.setGraphCount(50);
    model.setSummaryCount(10);
    model.setCompanyId(companyId);

    model.load();
%>
<html>
    <head>
        <title>trends</title>
        <link rel="stylesheet" href="css/default.css" media="screen" type="text/css"/>
        <script src="js/Graph.js" type="text/javascript"></script>

        <script type="text/javascript">
            var graph;

            function init() {
                var minValues = document.getElementById("minValues");
                var midValues = document.getElementById("midValues");
                var maxValues = document.getElementById("maxValues");

                graph = new Graph(minValues, midValues, maxValues);

                graph.add("share",
                    new GraphData(
                        document.getElementById("shareImg"),
                        "<%=SHARE_COLOR%>",
                        <%=model.getGraphMinValue(CompanyModel.GraphElement.SHARE)%>,
                        <%=model.getGraphMidValue(CompanyModel.GraphElement.SHARE)%>,
                        <%=model.getGraphMaxValue(CompanyModel.GraphElement.SHARE)%>
                    )
                );

                graph.add("score",
                        new GraphData(
                            document.getElementById("scoreImg"),
                            "<%=SCORE_COLOR%>",
                            <%=model.getGraphMinValue(CompanyModel.GraphElement.SCORE)%>,
                            <%=model.getGraphMidValue(CompanyModel.GraphElement.SCORE)%>,
                            <%=model.getGraphMaxValue(CompanyModel.GraphElement.SCORE)%>
                        )
                );

                graph.add("recommendation",
                        new GraphData(
                            document.getElementById("recommendationImg"),
                            "<%=RECOMMENDATION_COLOR%>",
                            <%=model.getGraphMinValue(CompanyModel.GraphElement.RECOMMENDATION)%>,
                            <%=model.getGraphMidValue(CompanyModel.GraphElement.RECOMMENDATION)%>,
                            <%=model.getGraphMaxValue(CompanyModel.GraphElement.RECOMMENDATION)%>
                        )
                );

                graph.refresh();
            }
        </script>

        <style type="text/css">

            td.split {
                border-right: 1px solid #dddddd;
            }

            td.hard-split {
                border-right: 2px solid #dddddd;
            }

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
                <jsp:param name="title" value="TRENDS"/>
            </jsp:include>

            <jsp:include page="error.jsp"/>

            <form action="?action=AddCompany" method="POST" style="margin-top: 30px;">
                <div style="float: right; background-color: #505050; color: white; border: 1px solid #505050">
                    <input type="text" name="name" style="border: 0; height: 20px"/>
                    <input type="submit" value="ADD" style="border: 0px; background-color: #505050; color: #FFFFFF; height: 20px;">
                </div>
            </form>

            <style>
                table.company {
                    border-left: 2px solid #999999;
                    width: 100%;
                    clear: both;
                    border-collapse: collapse;
                    margin-top: 40px;
                }

                tr.header td:first-child {
                    font-weight: bold;
                    color: white;
                    background-color: #999999;
                }

                table.company td {
                    padding: 3px;
                    vertical-align: top;
                }

                tr.month td {
                    width: 70px;
                    border-right: 1px solid #999999;
                    font-weight: bold;
                }

                tr.month td:first-child {
                    width: auto;
                }

                tr.score td {
                    border-right: 1px solid #999999;
                }

                tr.stock td {
                    border-right: 1px solid #999999;
                }

                tr.recommendation td {
                    border-right: 1px solid #999999;
                    font-weight: bold;
                }

                tr.border-top td {
                    border-top: 1px solid #999999;
                }

                tr.border-bottom td {
                    border-bottom: 1px solid #999999;
                }

            </style>
            <table class="company">
                <tbody>
                <tr class="header">
                    <td colspan="4"><%=model.getCompanyName()%></td>
                    <td colspan="7"></td>
                </tr>
                <tr class="month">
                    <td></td>
                    <% for (CompanyModel.History history : model.getSummaryList()) { %>
                        <td><%=history.getDate()%></td>
                    <% } %>
                </tr>
                <tr class="score">
                    <td>POINTS</td>
                    <% for (CompanyModel.History history : model.getSummaryList()) { %>
                        <td><%=history.getScore()%></td>
                    <% } %>
                </tr>
                <tr class="score">
                    <td></td>
                    <% for (CompanyModel.History history : model.getSummaryList()) { %>
                        <td><%=history.getScoreChanged()%></td>
                    <% } %>
                </tr>
                <tr class="stock">
                    <td>SHARE</td>
                    <% for (CompanyModel.History history : model.getSummaryList()) { %>
                        <td><%=history.getShare()%></td>
                    <% } %>
                </tr>
                <tr class="stock">
                    <td></td>
                    <% for (CompanyModel.History history : model.getSummaryList()) { %>
                        <td><%=history.getShareChanged()%></td>
                    <% } %>
                </tr>
                <tr class="recommendation border-top">
                    <td>REC</td>
                    <% for (CompanyModel.History history : model.getSummaryList()) { %>
                        <td><%=history.getRecommendation()%></td>
                    <% } %>
                </tr>
                <tr class="recommendation border-bottom">
                    <td></td>
                    <% for (CompanyModel.History history : model.getSummaryList()) { %>
                        <td><%=history.getRecommendationChanged()%></td>
                    <% } %>
                </tr>
                </tbody>
            </table>

            <style>
                table.graph {
                    border: 1px solid #999999;
                    width: 100%;
                    clear: both;
                    border-collapse: collapse;
                    margin-top: 20px;
                }

                td.graphMax {
                    vertical-align: top;
                    text-align: right;
                    width: 60px;
                }

                td.graphMid {
                    vertical-align: middle;
                    text-align: right;
                }

                td.graphLow {
                    vertical-align: bottom;
                    text-align: right;
                }

            </style>

            <table class="graph">
                <tr>
                    <td valign="top" rowspan="3" style="background-color: #999999; color: white; font-weight: bold;">
                        <input type="checkbox" onclick="graph.show('share', this.checked)" checked="true"/> POINTS<br/>
                        <input type="checkbox" onclick="graph.show('score', this.checked)" checked="true"/> SHARE<br/>
                        <input type="checkbox" onclick="graph.show('recommendation', this.checked)" checked="true"/> REC.<br/>
                    </td>
                    <td class="graphMax"><div id="maxValues"></div></td>
                    <td  valign="top" rowspan="3" align="right" style="width: 0px">
                        <div style="position: relative; width: 550px; height: 200px">
                            <img id="shareImg" style="position: absolute; top: 0px; left:0px; margin: 0; padding: 0; width: 550px; height: 200px;" src="<%=model.getImage(CompanyModel.GraphElement.SHARE)%>"/>
                            <img id="scoreImg" style="position: absolute; top: 0px; left:0px; margin: 0; padding: 0; width: 550px; height: 200px;" src="<%=model.getImage(CompanyModel.GraphElement.SCORE)%>"/>
                            <img id="recommendationImg" style="position: absolute; top: 0px; left:0px; margin: 0; padding: 0; width: 550px; height: 200px;" src="<%=model.getImage(CompanyModel.GraphElement.RECOMMENDATION)%>"/>
                        </div>
                    </td>
                </tr>
                <tr><td class="graphMid"><div id="midValues"></div></td></tr>
                <tr><td class="graphLow"><div id="minValues"></div></td></tr>
            </table>
         </div>
    </body>
</html>