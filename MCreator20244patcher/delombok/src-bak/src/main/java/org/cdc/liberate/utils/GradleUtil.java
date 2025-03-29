package org.cdc.liberate.utils;

import java.nio.file.Path;
import java.security.MessageDigest;

public class GradleUtil {
    public static String shaEncode(byte[] bytes) throws Exception {
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] md5Bytes = sha.digest(bytes);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static Path getDependPath(Path cacheParent,String group,String artifactId,String version,byte[] fileByte) throws Exception {
        return cacheParent.resolve("caches").resolve("modules-2").resolve("files-2.1").resolve(group).resolve(artifactId)
                .resolve(version).resolve(shaEncode(fileByte));
    }
}
