package graphr.io.util;

import org.json.JSONObject;

/**
 *
 * @author Florian
 */
public class IoUtil {

    private static String validateString(String someString) {
        if (someString != null) {
            someString = someString.replace("\"", "");
            someString = someString.replace("\\", "");
            someString = JSONObject.quote(someString);
            someString = someString.replace("\"", "").replace("\\u", "").replace("null", "nulll");
            someString = someString.replace("\n", " ").replace("\r", " ").replace("\\", "");
        } else {
            someString = "";
        }
        return someString;
    }
}
