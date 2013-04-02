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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // get upload path param from web.xml
        String uploadPath =  this.getServletContext().getInitParameter("uploadPath");
        if (uploadPath == null) {
            throw new ServletException("'uploadPath' is not configured.");
        }
        imageModel = ImageModel.getInstance(uploadPath);

        String fileId = request.getParameter("id");
        String attach = request.getParameter("attach");
        response.setContentType(imageModel.getMimeType(fileId));

        if (fileId == null || !imageModel.validId(fileId)) {
            throw new ServletException("invalid id");
        }

        // download to browser option
        if (attach != null && attach.equals("true")) {
            response.setHeader("Content-disposition","attachment; filename=" + imageModel.getFullName(fileId));
        }

        imageModel.downloadFile(fileId, response.getOutputStream());
    }
}
