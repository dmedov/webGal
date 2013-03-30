package com.ds.den;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadServlet extends HttpServlet  {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String fileName = request.getParameter("name");

        response.setContentType("image/jpeg");

        // Make sure to show the download dialog
        //response.setHeader("Content-disposition","attachment; filename=yourcustomfilename.pdf");

        // Assume file name is retrieved from database
        // For example D:\\file\\test.pdf

        File file =new File("C:\\uploads\\" + fileName);

        // send file
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }
}
