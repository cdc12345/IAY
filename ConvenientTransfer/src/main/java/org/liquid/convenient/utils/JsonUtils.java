package org.liquid.convenient.utils;

import com.google.gson.JsonObject;
import org.liquid.convenient.TransferMain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class JsonUtils {

    private static final int BYTE_LEN = 256;
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    public static boolean isRegularPack(JsonObject jsonObject){
        return jsonObject.has("name");
    }

    public static JsonObject map(JsonObject jsonObject){
        var input = JsonUtils.class.getResourceAsStream("/dictionary/dic.txt");
        Properties dic = new Properties();
		try {
            var original = new Properties();
            original.load(input);
            //create map
            for (Map.Entry<Object,Object> entry:original.entrySet()){
                dic.put(entry.getValue(),entry.getKey());
            }

            if (jsonObject.has("type")){
                var type = jsonObject.get("type").getAsString();
                jsonObject.addProperty("type",dic.getProperty(type,type).replace("types.",""));
            }
            if (jsonObject.has("_type")){
                var type = jsonObject.get("_type").getAsString();
                jsonObject.addProperty("_type",dic.getProperty(type,type).replace("types.",""));
            }
		} catch (IOException ignored) {
		}
        System.out.println(jsonObject.toString());
        return jsonObject;
	}

    public static JsonObject unmap(JsonObject jsonObject){
        var input = JsonUtils.class.getResourceAsStream("/dictionary/dic.txt");
        Properties dic = new Properties();
        try {
            dic.load(input);

            if (jsonObject.has("type")){
                var type = jsonObject.get("type").getAsString();
                jsonObject.addProperty("type",dic.getProperty("types."+type,type));
            }
            if (jsonObject.has("_type")){
                var type = jsonObject.get("_type").getAsString();
                jsonObject.addProperty("_type",dic.getProperty("types."+type,type));
            }
        } catch (IOException ignored) {
        }
        System.out.println(jsonObject.toString());
        return jsonObject;
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
