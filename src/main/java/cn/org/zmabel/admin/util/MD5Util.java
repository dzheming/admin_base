package cn.org.zmabel.admin.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MD5Util {
    public static String calcMD5(String rawCode)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] encryptCode = md.digest(rawCode.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (Byte b : encryptCode)
            {
                String hex = Integer.toHexString(b&0xff);
                if (hex.length() == 1)
                    hex = "0" + hex;
                sb.append(hex);
            }
            return sb.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
