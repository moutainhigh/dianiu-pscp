package com.edianniu.pscp.message.web.util;

import org.apache.commons.io.IOUtils;
import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HttpClient {
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.67 Safari/537.31";
    private static final int socketReadTimeout = 30000;

    public static String getUrlAsString(String url) throws URISyntaxException, IOException {
        HttpURLConnection conn = openConnection(url);
        String contentType = conn.getContentType();
        String encoding = "utf-8";
        if (contentType != null && contentType.indexOf("charset=") > 0) {
            encoding = contentType.split("charset=")[1];
        }
        String html = IOUtils.toString(conn.getInputStream(), encoding);
        conn.disconnect();
        return html;
    }
    public static String post(String url, byte[] body, String contentType) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URI(url).toURL().openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("uuid", UUID.randomUUID().toString());
            conn.getOutputStream().write(body);

            contentType = conn.getContentType();
            String encoding = "utf-8";
            if (contentType != null && contentType.indexOf("charset=") > 0) {
                encoding = contentType.split("charset=")[1];
            }
            Map<String, List<String>> headers = conn.getHeaderFields();
            for (String key : headers.keySet()) {
                System.out.println(key + ":" + headers.get(key));
            }

            InputStream is = conn.getInputStream();
            byte[] resp = ByteStreams.toByteArray(is);
            conn.disconnect();
            return new String(resp, encoding);
        } catch (URISyntaxException | IOException e) {
        	System.out.println(e);
            return "";
        }
    }
    public static String get(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URI(url).toURL().openConnection();

            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setRequestProperty("uuid", UUID.randomUUID().toString());
            String encoding = "utf-8";
            Map<String, List<String>> headers = conn.getHeaderFields();
            for (String key : headers.keySet()) {
                System.out.println(key + ":" + headers.get(key));
            }

            InputStream is = conn.getInputStream();
            byte[] resp = ByteStreams.toByteArray(is);
            conn.disconnect();
            return new String(resp, encoding);
        } catch (URISyntaxException | IOException e) {
        	System.out.println(e);
            return "";
        }
    }
    public static byte[] get1(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URI(url).toURL().openConnection();

            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setRequestProperty("uuid", UUID.randomUUID().toString());
            String encoding = "utf-8";
            Map<String, List<String>> headers = conn.getHeaderFields();
            for (String key : headers.keySet()) {
                System.out.println(key + ":" + headers.get(key));
            }

            InputStream is = conn.getInputStream();
            byte[] resp = ByteStreams.toByteArray(is);
            conn.disconnect();
            return resp;
        } catch (URISyntaxException | IOException e) {
        	System.out.println(e);
            return new byte[0];
        }
    }
    public static byte[] post(String url, byte[] body) throws URISyntaxException, IOException {
        HttpURLConnection conn = openConnection(url);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write(body);
        byte[] ret = IOUtils.toByteArray(conn.getInputStream());
        conn.disconnect();
        return ret;
    }
   

    public static String getRedirectUrl(String url) throws URISyntaxException, IOException {
        HttpURLConnection conn = openConnection(url);
        conn.setInstanceFollowRedirects(false);
        if (conn.getResponseCode() != 302) {
            conn.disconnect();
            return null;
        }
        String result = conn.getHeaderField("Location");
        conn.disconnect();
        return result;
    }

    public static int getContentLength(String httpUrl) throws IOException, URISyntaxException {
        HttpURLConnection conn = openConnection(httpUrl);
        conn.setRequestMethod("HEAD");
        int length = conn.getContentLength();
        conn.disconnect();
        return length;
    }

    public static int downloadFile(String fileUrl, File saveTo) throws IOException, URISyntaxException {
        HttpURLConnection conn = openConnection(fileUrl);
        FileOutputStream out = new FileOutputStream(saveTo);
        int fileSize = IOUtils.copy(conn.getInputStream(), out);
        out.close();
        conn.disconnect();
        return fileSize;
    }

    public static HttpURLConnection openConnection(String url) throws URISyntaxException, IOException {
        URLConnection conn = new URI(url).toURL().openConnection();
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setReadTimeout(socketReadTimeout);
        return (HttpURLConnection) conn;
    }
}
