package warmup;

public class ChangeCase {
  public String invert(String origin) {
    byte[] bytes = origin.getBytes();
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] ^= 0x20;
    }
    return new String(bytes);
  }
}
