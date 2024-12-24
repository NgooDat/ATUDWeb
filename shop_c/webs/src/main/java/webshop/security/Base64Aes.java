package webshop.security;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Base64Aes {
	private static final char[] CUSTOM_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_@.".toCharArray();
    private static final String FIXED_KEY = "MyFixedSecretKey"; // Khóa cố định 16 ký tự
    private static final SecretKeySpec key;

    static {
        try {
            key = new SecretKeySpec(FIXED_KEY.getBytes("UTF-8"), "AES");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize secret key", e);
        }
    }

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // AES-128
        return keyGenerator.generateKey();
    }

    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return convertToCustomBase(encryptedBytes);
    }

    public static String decrypt(String encryptedText) throws Exception {
        byte[] encryptedBytes = convertFromCustomBase(encryptedText);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, "UTF-8");
    }

    // Chuyển byte[] thành chuỗi ký tự tùy chỉnh
    public static String convertToCustomBase(byte[] data) {
        StringBuilder result = new StringBuilder();
        int value = 0;
        int bits = 0;

        for (byte b : data) {
            value = (value << 8) | (b & 0xFF);
            bits += 8;

            while (bits >= 6) {
                bits -= 6;
                result.append(CUSTOM_CHARSET[(value >> bits) & 0x3F]);
            }
        }

        if (bits > 0) {
            result.append(CUSTOM_CHARSET[(value << (6 - bits)) & 0x3F]);
        }

        return result.toString();
    }

    // Chuyển chuỗi ký tự tùy chỉnh ngược lại thành byte[]
    public static byte[] convertFromCustomBase(String customBase) {
        int value = 0;
        int bits = 0;


        byte[] buffer = new byte[customBase.length() * 6 / 8];
        int bufferIndex = 0;

        for (char c : customBase.toCharArray()) {
            value = (value << 6) | getCharIndex(c);
            bits += 6;

            if (bits >= 8) {
                bits -= 8;
                buffer[bufferIndex++] = (byte) ((value >> bits) & 0xFF);
            }
        }

        if (bufferIndex < buffer.length) {
            byte[] result = new byte[bufferIndex];
            System.arraycopy(buffer, 0, result, 0, bufferIndex);
            return result;
        }

        return buffer;
    }

    // Lấy vị trí của ký tự trong bảng ký tự
    private static int getCharIndex(char c) {
        for (int i = 0; i < CUSTOM_CHARSET.length; i++) {
            if (CUSTOM_CHARSET[i] == c) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid character in encoded text: " + c);
    }

//    // Kiểm tra mã hóa/giải mã
//    public static void main(String[] args) {
//        try {
//            String plainText = "HelloWorld123";
//            System.out.println("Plain Text: " + plainText);
//
//            String encrypted = encrypt(plainText);
//            System.out.println("Encrypted Text: " + encrypted);
//
//            String decrypted = decrypt(encrypted);
//            System.out.println("Decrypted Text: " + decrypted);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}