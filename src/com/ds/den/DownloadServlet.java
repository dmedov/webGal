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
        String attach = request.getParameter("attach");

        response.setContentType(getContentType(fileName));

        // download to browser option
        if (attach != null && attach.equals("true")) {
            response.setHeader("Content-disposition","attachment; filename=" + fileName);
        }

        String uploadPath =  this.getServletContext().getInitParameter("uploadPath");
        if (uploadPath == null) {
            throw new ServletException("'uploadPath' is not configured.");
        }
        File file = new File(uploadPath + fileName);

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

    private String getContentType(String fileName) throws ServletException {
        final String[] correctSuffixs = {"png", "jpg", "gif"};
        if (fileName.endsWith(".jpg")) {
            return "image/jpeg";
        }
        for (String correctSuffix : correctSuffixs)
            if (fileName.endsWith("." + correctSuffix)) {
                return "image/" + correctSuffix;
            }
        throw new ServletException("file with this filetype cant be downloaded.");
    }
}
