<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

${resultMessage}

<form action="/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" id="file"><br>
    <input type="submit" name="submit" value="Submit">
</form>

<%
    // show images with links
    int lastId = Integer.parseInt((String)request.getAttribute("lastId"));
    for (int i =0; i <= lastId; i++) {
        String id = Integer.toString(i);
        out.println("<div>");
        out.println("<img src=\"/download?id=" + Integer.toString(i) + "\"/>");
        out.println("<a href=\"/download?id=" + id + "\"> [full version] </a>");
        out.println("<a href=\"/download?id=" + id + "&attach=true\"> [download] </a>");
        out.println("</div>");
    }
%>

</body>
</html>