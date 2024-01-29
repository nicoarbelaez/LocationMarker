package lm.nicoarbelaez.util;

public class OtherUtils {

    public OtherUtils() {
    }

    public static void printDebug(boolean debug, Object objectClass, String... messages) {
        if (!debug) {
            return;
        }

        String className = objectClass.getClass().getSimpleName();
        String[] messageSplit = String.join(" ", messages).split("\\r?\\n");

        for (String message : messageSplit) {
            System.out.print("[" + className + "] " + message);
        }
    }
}