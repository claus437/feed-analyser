<%@ page import="org.wooddog.dao.Service" %>
<%@ page import="org.wooddog.dao.ServiceInMemory" %>
<%@ page import="java.util.List" %>
<%@ page import="org.wooddog.servlets.PageActionFactory" %>
<%@ page import="org.wooddog.servlets.PageAction" %>

<%
    PageActionFactory actionFactory;
    PageAction action;

    actionFactory = new PageActionFactory();
    action = actionFactory.createAction(request.getParameter("action"));
    if (action != null) {
        action.execute(request);
    }


%>

<html>
    <head>
        <style>
            td {
                font-family: arial;
                font-size: 10px;
                vertical-align: top;
            }

            th {
                font-family: arial;
                font-size: 10;
                font-weight: bold;
                align: left;
            }
        </style>

    </head>
    <body>
        <h1>FEED TABLE ENTRIES</h1>
        count: <%=entries.size()%>

        <table>
            <tr><th>source</th><th>published</th><th>title</th><th>description</th></tr>
            <%
                for (ServiceInMemory.FeedTableEntry entry : entries) {
                    out.print("<tr><td>");
                    out.print(entry.source);
                    out.print("</td><td>");
                    out.print(entry.published);
                    out.print("</td><td>");
                    out.print(entry.title);
                    out.print("</td><td>");
                    out.print(entry.description);
                    out.print("</td></tr>");
                }
            %>
        </table>
    </body>
</html>

