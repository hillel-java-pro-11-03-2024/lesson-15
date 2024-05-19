package utils;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

public class StringUtils {

  public static String utf8ToIso(String msg) {
    return new String(msg.getBytes(UTF_8), ISO_8859_1);
  }

  public static String isoToUtf8(String msg) {
    return new String(msg.getBytes(ISO_8859_1), UTF_8);
  }

}
