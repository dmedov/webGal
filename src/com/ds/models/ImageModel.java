package com.ds.models;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

public class ImageModel {
    private static ImageModel instance;
    private String path;

    int lastId;

    private ImageModel(String path) {
        this.path = path;
        lastId = initLastId();
    }


    public static synchronized ImageModel getInstance(String imagesPath) {
        if (instance == null) {
            instance = new ImageModel(imagesPath);
        }
        return instance;
    }

    private int initLastId() {
            ArrayList<Integer> arrId = new ArrayList<Integer>();

            File file = new File(path);
            File[] files = file.listFiles();

            if (files.length == 0) {
                return -1;
            }

            // add all id in array
            for (int i = 0; i < files.length; i++)
                arrId.add(Integer.parseInt(files[i].getName()));
            return Collections.max(arrId);
    }

    public void uploadFile(InputStream inp) throws IOException {
        String fileName = getNextFileName();
        File file = new File(path + fileName);
        OutputStream out = new FileOutputStream(new File(path + fileName));

        int read = 0;
        final byte[] bytes = new byte[1024];

        // upload the file
        while ((read = inp.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        if (out != null) {
            out.close();
        }
        if (inp != null) {
            inp.close();
        }
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
        if (id >= 0 && id <= lastId) {
            return true;
        }
        return false;
    }

    public String getFullName(String fileId) throws IOException {
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

    public String getMimeType(String fileId) throws IOException {
        URL u = new URL("file:" + path + fileId);
        URLConnection uc = u.openConnection();
        String type = uc.getContentType();
        return  type;
    }

    private String getNextFileName() {
        lastId++;
        return  Integer.toString(lastId);
    }

    //todo
    private void checkMimeType() {

    }

    public int getLastId() {
        return lastId;
    }
}
