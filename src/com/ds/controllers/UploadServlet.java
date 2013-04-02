package com.ds.controllers;

import com.ds.models.ImageModel;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;


@MultipartConfig(maxFileSize = 10485760L) // 10MB.
public class UploadServlet extends HttpServlet {
    private ImageModel imageModel;

    // config supported file types
    final private String[] supportTypes = {"png", "jpeg", "gif"};

    private String resultMessage;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // get upload path param from web.xml
        String uploadPath =  this.getServletContext().getInitParameter("uploadPath");
        if (uploadPath == null) {
            throw new ServletException("'uploadPath' is not configured.");
        }
        imageModel = ImageModel.getInstance(uploadPath);

        response.setContentType("text/html;charset=UTF-8");
        final Part filePart = request.getPart("file");

        if (filePart == null) {
            resultMessage = "sorry, but your image didn't upload";
            goToView(request, response);
            return;
        }

        if (!checkFileType(filePart)) {
            resultMessage = "sorry, you can't upload file with this filetype";
            goToView(request, response);
            return;
        }

        imageModel.uploadFile(filePart.getInputStream());
        resultMessage ="your file upload";
        goToView(request, response);
    }

    private boolean checkFileType(final Part part) {
        // content-type for image file looks like
        // Content-Type: image/jpg
        String[] types = part.getHeader("content-type").split("/");
        if (types[0].equals("image")) {
            for (String goodType : supportTypes) {
                if (goodType.equals(types[1])) {
                    return true;
                }
            }
        }
        return false;
    }

    private void goToView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("resultMessage", resultMessage);

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/gallery");
        //response.sendRedirect();
        rd.forward(request, response);
    }
}