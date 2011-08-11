<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page import="org.wooddog.dao.CompanyService" %>
<%@ page import="org.wooddog.dao.ScoringService" %>
<%@ page import="org.wooddog.domain.Company" %>
<%@ page import="org.wooddog.domain.Scoring" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>

<%!
    Date getLowerDate(int dayOffset) {
        Calendar lower;

        lower = Calendar.getInstance();
        lower.add(Calendar.DAY_OF_MONTH, dayOffset);
        lower.set(Calendar.HOUR_OF_DAY, 0);
        lower.set(Calendar.MINUTE, 0);
        lower.set(Calendar.SECOND, 0);
        lower.set(Calendar.MILLISECOND, 0);

        return lower.getTime();
    }

    Date getUpperDate(int dayOffset) {
        Calendar upper;

        upper = Calendar.getInstance();
        upper.add(Calendar.DAY_OF_MONTH, dayOffset);
        upper.set(Calendar.HOUR_OF_DAY, 23);
        upper.set(Calendar.MINUTE, 59);
        upper.set(Calendar.SECOND, 59);
        upper.set(Calendar.MILLISECOND, 999);

        return upper.getTime();
    }

    int getScore(int companyId, int dayOffset) {
        List<Scoring> scoreList;
        Date from;
        Date to;
        int score;

        from = getLowerDate(dayOffset);
        to = getUpperDate(dayOffset);
        score = 0;

        scoreList = ScoringService.getInstance().getScoringsInPeriodForCompany(companyId, from, to);
        for (Scoring scoring : scoreList) {
            score += scoring.getScore();
        }

        return score;
    }

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

%>

<%
    List<Company> companyList;

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

            <% for (Company company : companyList) { %>
                <table style="margin-top: 40px; border-left: 2px solid #DDDDDD">
                    <tr class="header">
                        <td width="100px" style="vertical-align: top" colspan="49"><b><%=company.getName()%></b></td>
                    </tr>

                    <tr>
                        <td></td>
                        <%
                            Calendar date = Calendar.getInstance();
                            for (int i = 0; i > -24; i--) {
                                out.println("<td colspan=\"2\" style=\"border-right: 1px solid #DDDDDD;\">" + date.get(Calendar.DAY_OF_MONTH) + ".</td>");
                                date.add(Calendar.DAY_OF_MONTH, -1);
                            }
                        %>
                    </tr>
                    <tr>
                        <td>Score</td>
                        <% for (int i = 0; i > -24; i--) { %>
                            <td><%=getScore(company.getId(), i)%></td>
                            <td style="border-right: 1px solid #DDDDDD;"><%=getMentioned(company.getId(), i)%></td>
                        <% } %>
                    </tr>

                    <tr>
                        <td>Share</td>
                        <% for (int i = 0; i > -24; i--) { %>
                            <td style="border-right: 1px solid #DDDDDD;" colspan="2">n/a</td>
                        <% } %>
                    </tr>

                </table>
            <% }%>
         </div>
    </body>
</html>