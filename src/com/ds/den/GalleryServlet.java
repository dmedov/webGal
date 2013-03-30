package com.ds.den;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class GalleryServlet extends HttpServlet {
    String resultMessage = "its works";
    ArrayList<String> fileNames = new ArrayList<String>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uploadPath =  this.getServletContext().getInitParameter("uploadPath");
        if (uploadPath == null) {
            throw new ServletException("'uploadPath' is not configured.");
        }
        File file = new File(uploadPath);

        // Reading directory contents
        File[] files = file.listFiles();

        fileNames.clear();
        for (int i = 0; i < files.length; i++) {
            fileNames.add(files[i].getName());
        }


        //throw new ServletException(fileNames.get(0));
        goToView(request, response);
    }


    private void goToView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("testVar", "hahaha");

        request.setAttribute("fileNames", fileNames);
        this.getServletContext().setAttribute("testVar2", "BIG!");

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/gallery.jsp");
        rd.forward(request, response);
    }
}
