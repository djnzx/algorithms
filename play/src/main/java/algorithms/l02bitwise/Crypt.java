package algorithms.l02bitwise;

public class Crypt {
  static String encryptDecrypt(String origin, char key) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < origin.length(); i++) {
      sb.append((char) (origin.charAt(i) ^ key));
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    String origin = "IBA Tech Academy";

    String encrypted = encryptDecrypt(origin, '5');
    encrypted = encryptDecrypt(encrypted, 'X');
    encrypted = encryptDecrypt(encrypted, 'Z');

    System.out.printf("Encrypted String: %s\n", encrypted);

    String decrypted = encryptDecrypt(encrypted, '5');
    decrypted = encryptDecrypt(decrypted, 'Z');
    decrypted = encryptDecrypt(decrypted, 'X');
    System.out.printf("Decrypted String: %s\n", decrypted);
  }

}
