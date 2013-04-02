package com.ds.models;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ImageModel {
    private static ImageModel instance;
    private String path;

    int lastId;

    private ImageModel(String path) {
        lastId = getLastId();
        this.path = path;
    }


    public static synchronized ImageModel getInstance(String imagesPath) {
        if (instance == null) {
            instance = new ImageModel(imagesPath);
        }
        return instance;
    }

    private int getLastId() {
        if (path != null) {
            ArrayList<Integer> arrId = new ArrayList<Integer>();

            File file = new File(path);
            File[] files = file.listFiles();

            // add all id in array
            for (int i = 0; i < files.length; i++)
                arrId.add(Integer.parseInt(files[i].getName()));
            return Collections.max(arrId);
        }
        return 0;
    }

    public String uploadFile(OutputStream out) throws IOException {
        String fileName = getNextFileName();
        File file = new File(path + fileName);

        // send file


        return null;
    }

    public void downloadFile(String fileId, OutputStream out) throws IOException {
        File file = new File(path + fileId);
        if (!file.exists()) {
            throw new IOException("file not found");
        }

        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        out.flush();
        in.close();
    }

    public boolean validId(String fileId) {
        int id = Integer.parseInt(fileId);
        if (id > 0 && id <= lastId) {
            return true;
        }
        return false;
    }

    public String getFullName(String fileId) {
        File file = new File(path + fileId);
        String mimeType = getMimeType(fileId);
        String ext = ".ext";
        if (mimeType.equals("image/jpeg")) {
            ext = "jpg";
        }
        if (mimeType.equals("image/png")) {
            ext = "png";
        }
        if (mimeType.equals("image/gif")) {
            ext = "gif";
        }
        return fileId + "." + ext;
    }

    public String getMimeType(String fileId) {
        File file = new File(path + fileId);
        return  new MimetypesFileTypeMap().getContentType(file);
    }

    private String getNextFileName() {
        lastId++;
        return  Integer.toString(lastId);
    }

    private void checkMimeType() {

    }
}
