import java.nio.charset.StandardCharsets;
import java.util.*;

public class QRMain {
    public  static List<String> stringList = new ArrayList<>();
    public  static List<String> encodedText = new ArrayList<>();

    static void encodeIt() {
        for (String a:stringList) {
            String encrypted = encodedReturn(a);
            encodedText.add(encrypted);
        }
    }

    public static List<String> getEncodedText(){
        return encodedText;
    }
    private static String encodedReturn(String a) {
        return Base62.base62Encode(a.getBytes(StandardCharsets.UTF_8));
    }

    static void addToList(long a) {
        stringList.add(String.valueOf(a));
    }
}
