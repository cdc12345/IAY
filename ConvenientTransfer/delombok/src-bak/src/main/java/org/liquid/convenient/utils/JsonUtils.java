package org.liquid.convenient.utils;

import com.google.gson.JsonObject;
import org.liquid.convenient.TransferMain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class JsonUtils {

    private static final int BYTE_LEN = 256;
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    public static boolean isRegularPack(JsonObject jsonObject){
        return jsonObject.has("name") && jsonObject.has("type");
    }

    public static byte[] uncompress(byte[] bytes) throws IOException{
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);

        GZIPInputStream ungzip;
        try {
            ungzip = new GZIPInputStream(in);
        } catch (IOException e) {
            TransferMain.LOG.error("not a gzip");
            return bytes;
        }
        byte[] buffer = new byte[BYTE_LEN];
        int n;
        while ((n = ungzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }

        return out.toByteArray();
    }

    public static byte[] compress(String str, String encoding) throws IOException {
        if (str == null || str.isEmpty()) {
            return null;
        }
        if (encoding == null){
            encoding = GZIP_ENCODE_UTF_8;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes(encoding));
        gzip.close();
        return out.toByteArray();
    }
}
