<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page import="org.wooddog.dao.CompanyService" %>
<%@ page import="org.wooddog.dao.ScoringService" %>
<%@ page import="org.wooddog.domain.Company" %>
<%@ page import="org.wooddog.domain.Scoring" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="org.wooddog.servlets.PageAction" %>
<%@ page import="org.wooddog.servlets.PageActionFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>

<%!
    static final int HISTORY_COUNT = 10;

    Date getLowerDate(Date date) {
        Calendar lower;

        lower = Calendar.getInstance();
        lower.setTime(date);
        lower.set(Calendar.HOUR_OF_DAY, 0);
        lower.set(Calendar.MINUTE, 0);
        lower.set(Calendar.SECOND, 0);
        lower.set(Calendar.MILLISECOND, 0);

        return lower.getTime();
    }

    Date getUpperDate(Date date) {
        Calendar upper;

        upper = Calendar.getInstance();
        upper.setTime(date);
        upper.set(Calendar.HOUR_OF_DAY, 23);
        upper.set(Calendar.MINUTE, 59);
        upper.set(Calendar.SECOND, 59);
        upper.set(Calendar.MILLISECOND, 999);

        return upper.getTime();
    }

    int getScore(int companyId, Date date) {
        List<Scoring> scoreList;
        Date from;
        Date to;
        int score;

        from = getLowerDate(date);
        to = getUpperDate(date);
        score = 0;

        scoreList = ScoringService.getInstance().getScoringsInPeriodForCompany(companyId, from, to);
        for (Scoring scoring : scoreList) {
            score += scoring.getScore();
        }

        return score;
    }

    /*
    int getMentioned(int companyId, int dayOffset) {
        List<Scoring> scoreList;
        Date from;
        Date to;
        int mentioned;

        from = getLowerDate(dayOffset);
        to = getUpperDate(dayOffset);
        mentioned = 0;

        scoreList = ScoringService.getInstance().getScoringsInPeriodForCompany(companyId, from, to);
        for (Scoring scoring : scoreList) {
            if (scoring.getScore() != 0) {
                mentioned ++;
            }

        }

        return mentioned;
    }
    */

    List<String> getMonths(Date now) {
        List<String> months;
        Calendar calendar;
        SimpleDateFormat format;

        format = new SimpleDateFormat("MMM dd", Locale.UK);
        calendar = Calendar.getInstance();
        calendar.setTime(now);

        months = new ArrayList<String>();
        for (int i = 0; i < HISTORY_COUNT; i++) {
            months.add(format.format(calendar.getTime()).toLowerCase());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        return months;
    }

    List<String> getScores(int companyId, Date now) {
        List<String> scores;
        int score;
        Calendar calendar;
        int currentScore;

        scores = new ArrayList<String>();
        calendar = Calendar.getInstance();
        calendar.setTime(now);

        currentScore = 1;

        for (int i = 0; i < HISTORY_COUNT; i++) {
            score = getScore(companyId, calendar.getTime());

            if (currentScore == 0) {
                currentScore = 1;
            }

            scores.add(Integer.toString(((score - currentScore) / currentScore) / 100) + " / " + Integer.toString(score));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            currentScore = score;
        }

        return scores;
    }

    List<String> getStocks(int companyId, Date now) {
        List<String> stocks;

        stocks = new ArrayList<String>();
        for (int i = 0; i < HISTORY_COUNT; i++) {
            stocks.add("n/a");
        }

        return stocks;
    }

    List<String> getRecommendations(int companyId, Date now) {
        List<String> recommendations;

        recommendations = new ArrayList<String>();
        for (int i = 0; i < HISTORY_COUNT; i++) {
            recommendations.add("n/a");
        }

        return recommendations;
    }

%>

<%
    List<Company> companyList;
    PageAction action;
    Date now;

    now = new Date();

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

    companyList = CompanyService.getInstance().getCompanies();
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
                }

                tr.month td {
                    width: 50px;
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
                        <td colspan="2"><%=company.getName()%></td>
                        <td colspan="<%=HISTORY_COUNT - 1%>"></td>
                    </tr>
                    <tr class="month">
                        <td></td>
                        <% for (String month : getMonths(now)) { %>
                            <td><%=month%></td>
                        <% } %>
                    </tr>
                    <tr class="score">
                        <td>SCORE</td>
                        <% for (String score : getScores(company.getId(), now)) { %>
                            <td><%=score%></td>
                        <% } %>
                    </tr>
                    <tr class="stock">
                        <td>STOCK</td>
                        <% for (String stock : getStocks(company.getId(), now)) { %>
                            <td><%=stock%></td>
                        <% } %>
                    </tr>
                    <tr class="recommendation">
                        <td>RECOMMENDATION</td>
                        <% for (String recommendation : getRecommendations(company.getId(), now)) { %>
                            <td><%=recommendation%></td>
                        <% } %>
                    </tr>
                </table>
            <% } %>
         </div>
    </body>
</html>