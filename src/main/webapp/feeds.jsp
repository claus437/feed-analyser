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

            <table width="100%" style="margin-top: 40px;">
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

            <form action="?action=AddChannel" method="POST">
                <table style="margin-top: 10px; background-color: #FEFEFE; width: 100%">
                    <tr><td style="background-color: #DDDDDD;width: 100%; text-align: right"><label for="url">ADD CHANNEL</label> <input type="text" name="url" id="url"  style="border: 1px solid #DDDDDD; width: 560px"/> <input type="submit" value="ADD" style="float:right; border: 1px solid #DDDDDD"/></td></tr>
                </table>
            </form>

        </div>
    </body>
</html>