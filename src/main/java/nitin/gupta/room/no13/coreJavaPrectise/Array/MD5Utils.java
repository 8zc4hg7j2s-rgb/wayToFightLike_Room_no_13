package nitin.gupta.room.no13.coreJavaPrectise.Array;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static int SHORT_URL_CHAR_SIZE = 7;

    public static String convert(String longURL) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(longURL.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandomShortUrl(String longURL) {
        String hash = MD5Utils.convert(longURL);
        return hash;
    }

    public static void main(String[] args) {
        String hash = generateRandomShortUrl("system-design-scalable-url-shortener-service-like-tinyurl-106f30f23a82");
        System.out.println(hash);
        int numberOfCharsInHash = hash.length();
        int counter = 0;
        String sss = "";
        while (counter < numberOfCharsInHash - SHORT_URL_CHAR_SIZE) {
            sss = hash.substring(counter, counter + SHORT_URL_CHAR_SIZE);
            counter++;
        }
        System.out.println(sss);
    }

}