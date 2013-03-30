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
    ArrayList<String> imageNames = (ArrayList<String>)request.getAttribute("fileNames");
    for (int i =0; i < imageNames.size(); i++) {
        String fileName = imageNames.get(i);
        out.println("<div>");
        out.println("<img src=\"/download?name=" + fileName + "\"/>");
        out.println("<a href=\"/download?name=" + fileName + "\"> [full version] </a>");
        out.println("<a href=\"/download?name=" + fileName + "&attach=true\"> [download] </a>");
        out.println("</div>");
    }
%>

</body>
</html>