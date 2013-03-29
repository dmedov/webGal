package com.ds.den;

import java.io.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
    private String responseTemplate =
            "<html>\n" +
                    "<body>\n" +
                    "<h2>Hello from Simple Servlet</h2>\n" +
                    "</body>\n" +
                    "</html>";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.getWriter().write(responseTemplate);
    }

    //protected void doGet( HttpServletRequest request,
    //                      HttpServletResponse response)
    //        throws ServletException, IOException {
    //
    //    response.getWriter().write("<html><body>GET response</body></html>");
    //}
}