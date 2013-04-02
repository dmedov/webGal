package com.ds.controllers;


import com.ds.models.ImageModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class GalleryServlet extends HttpServlet {
    private int lastId;
    private ImageModel imageModel;

    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // get upload path param from web.xml
        String uploadPath =  this.getServletContext().getInitParameter("uploadPath");
        if (uploadPath == null) {
            throw new ServletException("'uploadPath' is not configured.");
        }
        imageModel = ImageModel.getInstance(uploadPath);

        lastId = imageModel.getLastId();
        goToView(request, response);
    }

    private void goToView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("lastId", String.valueOf(lastId));
        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/gallery.jsp");
        rd.forward(request, response);
    }
}
