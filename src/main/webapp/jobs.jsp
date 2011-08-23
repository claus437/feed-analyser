<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page import="org.wooddog.dao.service.ChannelServiceDao" %>
<%@ page import="org.wooddog.domain.Channel" %>
<%@ page import="org.wooddog.servlets.JspTool" %>
<%@ page import="org.wooddog.servlets.PageAction" %>
<%@ page import="org.wooddog.servlets.PageActionFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="org.wooddog.config.BackgroundJobs" %>
<%@ page import="org.wooddog.job.JobThread" %>
<%
    List<Channel> channels;
    PageAction action;

    action = PageActionFactory.getInstance().getAction(request.getParameter("action"));
    if (action != null) {
        try {
            action.execute(request.getParameterMap());
        } catch (Throwable x) {
            session.setAttribute("error", x.getMessage());
        }
        response.sendRedirect("feeds.jsp");
        return;
    }

    channels = ChannelServiceDao.getInstance().getChannels();

    List<JobThread> jobs;
    BackgroundJobs backgroundJobs;

    try {
        backgroundJobs = BackgroundJobs.getInstance();
    } catch (Throwable x) {
        x.printStackTrace();
        return;
    }

    jobs = backgroundJobs.getManager().getJobList();


%>
<html>
    <head>
        <title>jobs</title>
        <link rel="stylesheet" href="css/default.css" media="screen" type="text/css"/>
    </head>

    <body>
        <div class="content">
            <jsp:include page="header.jsp">
                <jsp:param name="title" value="JOBS"/>
            </jsp:include>

            <jsp:include page="error.jsp"/>

            <style>
                td {
                    white-space: nowrap;
                }

                th {
                    white-space: nowrap;
                }
            </style>

            <table style="margin-top: 40px; width: 100%">
                <tr>
                    <th>JOB</th>
                    <th>LAST RUN</th>
                    <th>NEXT RUN</th>
                    <th colspan="2">STATUS</th>
                    <th>EXECUTION TIME</th>
                </tr>

                <% for (JobThread job : jobs) { %>
                    <tr>
                        <td><%=job.getJob().getName()%></td>
                        <td><%=job.getLastRun()%></td>
                        <td><%=job.getNextRun()%></td>
                        <td><%=job.getStatus()%></td>
                        <td>(<%=job.getJob().progress()%>%)</td>
                        <td><%=job.getExecutingTime()%></td>
                    </tr>
                <% } %>
            </table>
        </div>
    </body>
</html>