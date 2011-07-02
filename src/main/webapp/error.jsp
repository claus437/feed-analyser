<%
    if (session.getAttribute("error") != null) {
        response.getWriter().println("<div class=\"error\">" + session.getAttribute("error") + "</div>");
    }

    session.removeAttribute("error");
%>
