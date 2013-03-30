package com.ds.den;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;


@MultipartConfig(maxFileSize = 10485760L) // 10MB.
public class UploadServlet extends HttpServlet {

    // config supported file types
    final private String[] supportTypes = {"png", "jpeg", "gif"};


    private String resultMessage;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");



        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);
        //final PrintWriter writer = response.getWriter();

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

       resultMessage =  uploadFile(filePart, fileName);
       goToView(request, response);
    }

    private String uploadFile(Part filePart, String fileName) throws IOException, ServletException {
        OutputStream out = null;
        InputStream filecontent = null;
        try {
            String uploadPath =  getServletConfig().getInitParameter("uploadPath");
            if (uploadPath == null) {
                throw new ServletException("'uploadPath' is not configured.");
            }

            out = new FileOutputStream(new File(uploadPath + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            // upload the file
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            return "File " + fileName + " uploaded";
            //LOGGER.log(Level.INFO, "File{0}being uploaded to {1}",
            //        new Object[]{fileName, path});
        } catch (FileNotFoundException fne) {
            return "You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location."
                    + "<br/> ERROR: " + fne.getMessage();

            //LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
            //        new Object[]{fne.getMessage()});
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
    }

    private String getFileName(final Part part) {
        //final String partHeader = part.getHeader("content-disposition");
        //LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
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
                .getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }
}