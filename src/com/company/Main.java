package com.company;

import java.io.*;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Main {

    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();
            String res = Base64.getEncoder().encodeToString(out.toByteArray());
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    public static String decompress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        try {
            byte[] src = Base64.getDecoder().decode(str);

            ByteArrayInputStream bis = new ByteArrayInputStream(src);
            GZIPInputStream gis = new GZIPInputStream(bis);
            BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            gis.close();
            bis.close();
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String:\n");
        String string = br.readLine();
        System.out.println("Origin length: " + string.length());
        System.out.println("origin string:\n" + string);

/*        String out = Main.compress(string);
        byte[] encodedBytes = Base64.getEncoder().encode(out.getBytes());
        System.out.println("after compress:\n" + new String(encodedBytes));
*/
        String out = Main.compress(string);
        System.out.println("after compress:\n" + out);

        string = Main.decompress(out);
        System.out.println("after decompress:\n" + string);


    }
}