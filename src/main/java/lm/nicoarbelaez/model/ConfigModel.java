package lm.nicoarbelaez.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import lm.nicoarbelaez.LocationMarker;
import lm.nicoarbelaez.api.enums.Direction;

public class ConfigModel {
    private final LocationMarker plugin;
    private static boolean nullAttributeDetected;
    private String version;
    private Map<String, String> symbols;
    private Map<String, Map<String, String>> targets;
    private Map<String, String> messages;

    public ConfigModel(LocationMarker plugin) {
        this.plugin = plugin;
        ConfigModel.nullAttributeDetected = false;
        this.version = "1.0.0";
        this.symbols = new HashMap<>();
        this.targets = new HashMap<>();
        setDefaultValues();
    }

    private void setDefaultValues() {
        // Symbols
        symbols.put(Direction.UP.getKey(), "⬆");
        symbols.put(Direction.DOWN.getKey(), "⬇");
        symbols.put(Direction.NORTHEAST.getKey(), "⬈");
        symbols.put(Direction.NORTHWEST.getKey(), "⬉");
        symbols.put(Direction.SOUTHEAST.getKey(), "⬊");
        symbols.put(Direction.SOUTHWEST.getKey(), "⬋");
        symbols.put(Direction.RIGHT.getKey(), "⮕");
        symbols.put(Direction.LEFT.getKey(), "⬅");
        symbols.put(Direction.ASCEND.getKey(), "⇈");
        symbols.put(Direction.DESCEND.getKey(), "⇊");
        symbols.put(Direction.ZERO.getKey(), "●");
        // Targets
        Map<String, String> spawn = new HashMap<>();
        spawn.put("world", "world");
        spawn.put("x", "0");
        spawn.put("y", "100");
        spawn.put("z", "0");

        Map<String, String> wasted = new HashMap<>();
        wasted.put("player", "Kolacho");

        targets.put("spawn", spawn);
        targets.put("wasted", wasted);
        // Messages
        messages = new HashMap<>();
        messages.put("no-permission", "&cYou don't have permission to use this command!");
        messages.put("invalid-target", "&cInvalid target!");
        messages.put("invalid-direction", "&cInvalid direction!");
        messages.put("invalid-axis", "&cInvalid axis!");
        messages.put("invalid-player", "&cInvalid player!");
        messages.put("invalid-world", "&cInvalid world!");
    }

    public Location getTargetLocation(Map<String, String> target, boolean adjust) {
        Location targetLocation = new Location(plugin.getServer().getWorlds().get(0), 0, 0, 0);

        if (target.containsKey("player")) {
            Player targetPlayer = plugin.getServer().getPlayer(target.get("player"));
            targetLocation = targetPlayer.getLocation();
        } else if (target.containsKey("world") && target.containsKey("x") && target.containsKey("y")
                && target.containsKey("z")) {
            String worldName = target.get("world");

            double x = Double.parseDouble(target.get("x"));
            double y = Double.parseDouble(target.get("y"));
            double z = Double.parseDouble(target.get("z"));
            if (adjust) {
                x = adjustForIntegerCoordinate(x);
                z = adjustForIntegerCoordinate(z);
            }

            targetLocation.setWorld(plugin.getServer().getWorld(worldName));
            targetLocation.setX(x);
            targetLocation.setY(y);
            targetLocation.setZ(z);
        }
        return targetLocation;
    }

    private static double adjustForIntegerCoordinate(double coord) {
        if (coord % 1 == 0) {
            coord += 0.5;
        }
        return coord;
    }

    private String toJson(Object obj, int indent) {
        StringBuilder sb = new StringBuilder();
        String indentation = String.join("", Collections.nCopies(indent, "\t"));
        if (obj == null) {
            sb.append("null");
        } else if (obj instanceof Map) {
            sb.append("{\n");
            Map<?, ?> map = (Map<?, ?>) obj;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                sb.append(indentation).append("\t\"").append(entry.getKey()).append("\": ");
                sb.append(toJson(entry.getValue(), indent + 1)).append(",\n");
            }
            sb.append(indentation).append("}");
        } else if (obj instanceof String) {
            sb.append("\"").append(obj).append("\"");
        } else {
            sb.append(obj.toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        Map<String, Object> map = new HashMap<>();
        map.put("nullAttributeDetected", nullAttributeDetected);
        map.put("version", version);
        map.put("symbols", symbols);
        map.put("targets", targets);
        map.put("messages", messages);
        return toJson(map, 0);
    }

    private <T> boolean isNull(T value) {
        if (value == null) {
            nullAttributeDetected = true;
            return true;
        }
        return false;
    }

    private <K, V> boolean isNull(Map<K, V> value) {
        if (value == null || value.isEmpty()) {
            nullAttributeDetected = true;
            return true;
        }
        return false;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        if (!isNull(version)) {
            this.version = version;
        }
    }

    public Map<String, String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, String> symbols) {
        if (!isNull(symbols)) {
            this.symbols = symbols;
        }
    }

    public Map<String, Map<String, String>> getTargets() {
        return targets;
    }

    public void setTargets(Map<String, Map<String, String>> targets) {
        if (Objects.nonNull(targets)) {
            this.targets = targets;
        }
    }

    public static boolean isNullAttributeDetected() {
        return nullAttributeDetected;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }
}