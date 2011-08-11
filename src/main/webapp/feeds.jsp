<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page import="org.wooddog.dao.ChannelService" %>
<%@ page import="org.wooddog.domain.Channel" %>
<%@ page import="org.wooddog.servlets.JspTool" %>
<%@ page import="org.wooddog.servlets.PageAction" %>
<%@ page import="org.wooddog.servlets.PageActionFactory" %>
<%@ page import="java.util.List" %>
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

    channels = ChannelService.getInstance().getChannels();
%>
<html>
    <head>
        <title>feeds</title>
        <link rel="stylesheet" href="css/default.css" media="screen" type="text/css"/>
    </head>

    <body>
        <div class="content">
            <jsp:include page="header.jsp">
                <jsp:param name="title" value="CHANNELS"/>
            </jsp:include>

            <jsp:include page="error.jsp"/>

            <form action="?action=AddChannel" method="POST" style="margin-top: 30px;">
                <div style="float: right; background-color: #505050; color: white; border: 1px solid #505050">
                    <input type="text" name="url" style="border: 0; height: 20px"/>
                    <input type="submit" value="ADD" style="border: 0px; background-color: #505050; color: #FFFFFF; height: 20px;">
                </div>
            </form>

            <table style="margin-top: 40px; width: 100%">
                <tr>
                    <th>URL</th>
                    <th>FETCHED</th>
                    <th></th>
                </tr>
                <% for (Channel channel : channels) { %>
                    <tr>
                        <td><%=channel.getUrl()%></td>
                        <td><nobr><%=JspTool.formatFullDate(channel.getFetched())%></nobr></td>
                        <td><a href="?action=DeleteChannel&id=<%=channel.getId()%>">delete</a></td>
                    </tr>
                <% } %>
            </table>
        </div>
    </body>
</html>