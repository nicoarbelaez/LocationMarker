package lm.nicoarbelaez;

import java.util.Arrays;

public class Test {
    private static final String[] VALID_PREFIXES = { "distance", "coordx", "coordy", "coordz", "yaw", "pitch",
            "direction" };

    public static void main(String[] args) {
        String[] identifiers = { "3:distance_spawn",
                "3:coordx_spawn",
                "3:yaw_spawn",
                "3:pitch_spa:wn_asda",
                "3:pitch_spawn_asda",
                "3r:pitch_spawn",
                "30:2pitch_spawn",
                "coordx_spawn",
                "direction_spawn" };

        for (String string : identifiers) {
            System.out.println(test(string));
            System.out.println();
        }
    }

    public static String test(String identifier) {
        String parts[] = identifier.split("_");
        String targetName = parts[1];
        String prefix = getPrefix(parts[0].split(":"));
        int precision = getPrecision(parts[0].split(":"));

        System.out.println(identifier.toUpperCase());

        if (prefix == null) {
            return null;
        }

        switch (prefix) {
            case "distance":
                return print(targetName);
            case "coordx":
            case "coordy":
            case "coordz":
                char axis = prefix.charAt(5);
                return print(targetName, axis);
            case "yaw":
                return print(targetName);
            case "pitch":
                return print(targetName);
            case "direction":
                return print(targetName);
        }

        // System.out.println("targetName: " + targetName);
        // System.out.println("prefix: " + prefix);
        // System.out.println("precision: " + precision);
        return "targetName: " + targetName + "\nprefix: " + prefix + "\nprecision: " + precision;
    }

    private static String print(String targetName) {
        return "targetName: " + targetName;
    }

    private static String print(String targetName, char axis) {
        return "targetName: " + targetName + "\naxis: " + axis;
    }

    private static String getPrefix(String[] parts) {
        String prefix = parts.length == 2 ? parts[1] : parts[0];
        if (Arrays.asList(VALID_PREFIXES).contains(prefix)) {
            return prefix;
        }
        return null;
    }

    private static int getPrecision(String[] parts) {
        try {
            return parts.length == 2 ? Integer.parseInt(parts[0]) : 1;
        } catch (Exception e) {
            return 1;
        }
    }
}
