package lm.nicoarbelaez.api;

import java.util.Arrays;
import java.util.Locale;

import org.bukkit.entity.Player;

import lm.nicoarbelaez.LocationMarker;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class ExpansionLM extends PlaceholderExpansion {

    private final LocationMarker plugin;
    private static final String[] VALID_PREFIXES = { "distance", "coordx", "coordy", "coordz", "yaw", "pitch",
            "direction" };

    public ExpansionLM(LocationMarker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "nicoarbelaez";
    }

    @Override
    public String getIdentifier() {
        return "locationmarker";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (player == null) {
            return "";
        }

        String parts[] = identifier.split("_");
        String targetName = parts[1];
        String prefix = getPrefix(parts[0].split(":"));
        int precision = getPrecision(parts[0].split(":"));

        if (prefix == null) {
            return null;
        }

        try {
            switch (prefix) {
                case "distance":
                    return getDecimalWithPrecision(LocationMarkerAPI.getDistance(player, targetName), precision);
                case "coordx":
                case "coordy":
                case "coordz":
                    char axis = prefix.charAt(5);
                    return getDecimalWithPrecision(LocationMarkerAPI.getCoord(player, targetName, axis), precision);
                case "yaw":
                    return getDecimalWithPrecision(LocationMarkerAPI.getYaw(player, targetName), precision);
                case "pitch":
                    return getDecimalWithPrecision(LocationMarkerAPI.getPitch(player, targetName), precision);
                case "direction":
                    return LocationMarkerAPI.getDirection(player, targetName);
            }

        } catch (Exception e) {
            return e.getMessage();
        }

        return null;
    }

    private String getDecimalWithPrecision(double value, int precision) {
        return String.format(Locale.US, "%." + precision + "f", value);

    }

    private String getPrefix(String[] parts) {
        String prefix = parts.length == 2 ? parts[1] : parts[0];
        if (Arrays.asList(VALID_PREFIXES).contains(prefix)) {
            return prefix;
        }
        return null;
    }

    private int getPrecision(String[] parts) {
        try {
            return parts.length == 2 ? Integer.parseInt(parts[0]) : 2;
        } catch (Exception e) {
            return 1;
        }
    }
}
