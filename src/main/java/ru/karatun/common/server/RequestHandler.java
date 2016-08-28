package ru.karatun.common.server;

import java.io.*;
import java.net.URLConnection;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class RequestHandler {

    private static final RequestHandler INSTANCE = new RequestHandler();

    public static RequestHandler getInstance() {
        return INSTANCE;
    }

    public void handleRequest(InputStream inputStream, OutputStream outputStream) {
        try {
            String file = getClass().getClassLoader().getResource("hello_world.html").getFile();
            File requestedFile = new File(file);

            int fileLen = (int) requestedFile.length();

            BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(requestedFile));

            String contentType = URLConnection.guessContentTypeFromStream(fileIn);

            byte[] headerBytes = composeHeaderBytes("HTTP/1.0 200 OK", fileLen, contentType);

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

            bufferedOutputStream.write(headerBytes);

            byte[] buffer = new byte[2048];
            int blockLen;
            while ((blockLen = fileIn.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, blockLen);
            }

            fileIn.close();
            bufferedOutputStream.flush();
            outputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] composeHeaderBytes(String responseString, int contentLen, String contentType) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(baos));
        writer.write(responseString + "\r\n");
        if (contentLen != -1) {
            writer.write(
                    "Content-Length: " + contentLen + "\r\n");
        }
        if (contentType != null) {
            writer.write(
                    "Content-Type: " + contentType + "\r\n");
        }

        writer.write("\r\n");
        writer.flush();

        byte[] headerBytes = baos.toByteArray();
        writer.close();

        return headerBytes;
    }

}
