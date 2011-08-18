<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page import="org.wooddog.dao.service.CompanyServiceDao" %>
<%@ page import="org.wooddog.domain.Company" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="org.wooddog.servlets.PageAction" %>
<%@ page import="org.wooddog.servlets.PageActionFactory" %>
<%@ page import="org.wooddog.servlets.model.TrentModel" %>

<%!
    static final int HISTORY_COUNT = 10;
%>

<%
    List<Company> companyList;
    TrentModel model;
    PageAction action;

    model = new TrentModel(new Date(), 10);

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

    companyList = CompanyServiceDao.getInstance().getCompanies();
%>
<html>
    <head>
        <title>trends</title>
        <link rel="stylesheet" href="css/default.css" media="screen" type="text/css"/>
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

    <body>
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
                    border-top: 1px solid #999999;
                    border-bottom: 1px solid #999999;
                    font-weight: bold;
                }

            </style>

            <% for (Company company : companyList) { %>
                <table class="company">
                    <tr class="header">
                        <td colspan="4"><%=company.getName()%></td>
                        <td colspan="<%=HISTORY_COUNT - 3%>"></td>
                    </tr>
                    <tr class="month">
                        <td></td>
                        <% for (String month : model.getMonths()) { %>
                            <td><%=month%></td>
                        <% } %>
                    </tr>
                    <tr class="score">
                        <td>P</td>
                        <% for (String score : model.getScores(company.getId())) { %>
                            <td><%=score%></td>
                        <% } %>
                    </tr>
                    <tr class="stock">
                        <td>S</td>
                        <% for (String stock : model.getStocks(company.getName())) { %>
                            <td><%=stock%></td>
                        <% } %>
                    </tr>
                    <tr class="recommendation">
                        <td>R</td>
                        <% for (String recommendation : model.getRecommendations(company.getId())) { %>
                            <td><%=recommendation%></td>
                        <% } %>
                    </tr>
                </table>
            <% } %>
         </div>
    </body>
</html>