package com.ds.models;

import java.io.*;

public class ImageModel {
    private static ImageModel instance;
    private static String path;

    int lastId;

    private ImageModel() {
        lastId = 0;
        if (path != null) {

        }
    }

    public static synchronized ImageModel getInstance() {
        if (instance == null) {
            instance = new ImageModel();
        }
        return instance;
    }

    public static void setPath(String filesPath) {
        path = filesPath;
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
        in.close();
    }

    private String getNextFileName() {
        lastId++;
        return  Integer.toString(lastId);
    }
}
