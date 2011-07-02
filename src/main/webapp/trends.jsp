<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page import="org.wooddog.domain.Channel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.wooddog.dao.Service" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.wooddog.servlets.PageActionFactory" %>
<%@ page import="org.wooddog.servlets.PageAction" %>
<%@ page import="org.wooddog.servlets.JspTool" %>
<%@ page import="org.wooddog.dao.ChannelService" %>
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

            <table style="margin-top: 40px; border-left: 2px solid #DDDDDD">

                <tr class="header">
                    <td width="100px" style="vertical-align: top"><b>FUJITSU</b></td>
                    <td></td>
                    <td colspan="14">LAST 7 DAYS</td>
                    <td colspan="2">7 days</td>
                    <td colspan="2">30 days</td>
                    <td colspan="2">90 days</td>
                    <td colspan="2">180 days</td>
                    <td colspan="2">360 days</td>

                </tr>
                <tr class="rate-header">
                    <td></td>
                    <td></td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                </tr>
                <tr>
                    <td></td>
                    <td>TREND</td>
                    <td>0</td><td class="split">2</td>
                    <td>0</td><td class="split">3</td>
                    <td>0</td><td class="split">4</td>
                    <td>0</td><td class="split">3</td>
                    <td>0</td><td class="split">4</td>
                    <td>0</td><td class="split">3</td>
                    <td>0</td><td class="hard-split">3</td>
                    <td>0</td><td class="hard-split">1</td>
                    <td>0</td><td class="hard-split">6</td>
                    <td>0</td><td class="hard-split">8</td>
                    <td>0</td><td class="hard-split">5</td>
                    <td>0</td><td class="hard-split">5</td>
                </tr>

                <tr>
                    <td></td>
                    <td>SHARE</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                </tr>

            </table>

            <table style="margin-top: 40px; border-left: 2px solid #DDDDDD">

                <tr class="header">
                    <td width="100px" style="vertical-align: top"><b>TELIA SONERA</b></td>
                    <td></td>
                    <td colspan="14">LAST 7 DAYS</td>
                    <td colspan="2">7 days</td>
                    <td colspan="2">30 days</td>
                    <td colspan="2">90 days</td>
                    <td colspan="2">180 days</td>
                    <td colspan="2">360 days</td>

                </tr>
                <tr class="rate-header">
                    <td></td>
                    <td></td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                    <td>S</td><td class="hard-split">M</td>
                </tr>
                <tr>
                    <td></td>
                    <td>TREND</td>
                    <td>0</td><td class="split">2</td>
                    <td>0</td><td class="split">3</td>
                    <td>0</td><td class="split">4</td>
                    <td>0</td><td class="split">3</td>
                    <td>0</td><td class="split">4</td>
                    <td>0</td><td class="split">3</td>
                    <td>0</td><td class="hard-split">3</td>
                    <td>0</td><td class="hard-split">1</td>
                    <td>0</td><td class="hard-split">6</td>
                    <td>0</td><td class="hard-split">8</td>
                    <td>0</td><td class="hard-split">5</td>
                    <td>0</td><td class="hard-split">5</td>
                </tr>

                <tr>
                    <td></td>
                    <td>SHARE</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                    <td colspan="2" class="hard-split">+1</td>
                </tr>

            </table>



            <!--
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
            -->
        </div>
    </body>
</html>