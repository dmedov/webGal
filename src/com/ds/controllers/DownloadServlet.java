package com.ds.controllers;


import com.ds.models.ImageModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadServlet extends HttpServlet  {
    private ImageModel imageModel;

    DownloadServlet() throws ServletException {
        // get upload path param from web.xml
        String uploadPath =  this.getServletContext().getInitParameter("uploadPath");
        if (uploadPath == null) {
            throw new ServletException("'uploadPath' is not configured.");
        }
        // prepare model
        imageModel = ImageModel.getInstance();
        ImageModel.setPath(uploadPath);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String fileName = request.getParameter("name");
        String attach = request.getParameter("attach");

        response.setContentType(getContentType(fileName));

        // download to browser option
        if (attach != null && attach.equals("true")) {
            response.setHeader("Content-disposition","attachment; filename=" + fileName);
        }

        OutputStream out = response.getOutputStream();
        imageModel.uploadFile(out);
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
