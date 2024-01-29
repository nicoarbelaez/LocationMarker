package lm.nicoarbelaez.api;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.Location;

import lm.nicoarbelaez.LocationMarker;
import lm.nicoarbelaez.api.enums.Direction;
import lm.nicoarbelaez.model.ConfigModel;

public class LocationMarkerAPI {

    private static LocationMarker plugin;

    public LocationMarkerAPI(LocationMarker plugin) {
        LocationMarkerAPI.plugin = plugin;
    }

    public static double getDistance(Player player, String targetName) throws Exception {
        Location[] locations = getLocations(player, targetName, true);
        double eyeHeight = 1.62f;
        locations[0].setY(locations[0].getY() - eyeHeight);
        return locations[0].distance(locations[1]);
    }

    public static double getCoord(Player player, String targetName, char axis) throws Exception {
        Location targetLocation = getLocations(player, targetName, false)[1];
        return getAxisValue(targetLocation, axis);
    }

    public static double getYaw(Player player, String targetName) throws Exception {
        Location[] locations = getLocations(player, targetName, false);
        double dx = locations[1].getX() - locations[0].getX();
        double dz = locations[1].getZ() - locations[0].getZ();

        double yaw = Math.atan2(dz, dx) - Math.PI / 2;
        yaw = Math.toDegrees(yaw);
        yaw = (yaw + 360) % 360;
        if (yaw > 180) {
            yaw -= 360;
        }

        return yaw;
    }

    public static double getPitch(Player player, String targetName) throws Exception {
        Location[] locations = getLocations(player, targetName, false);
        double dx = locations[1].getX() - locations[0].getX();
        double dy = locations[1].getY() - locations[0].getY();
        double dz = locations[1].getZ() - locations[0].getZ();

        double distance = Math.sqrt(dx * dx + dz * dz);
        double pitch = Math.atan2(dy, distance);
        pitch = -Math.toDegrees(pitch);

        return pitch;
    }

    /**
     * This method is used to get the locations of the player and the target.
     *
     * @param player     The player for whom the locations will be fetched.
     * @param targetName The name of the target for whom the locations will be
     *                   fetched.
     * @return An array of Location objects containing the player's location and the
     *         target's location, in that order.
     */
    private static Location[] getLocations(Player player, String targetName, boolean adjust) throws Exception {
        ConfigModel configModel = plugin.getFileManager().getConfigModel();

        Location playerLocation = player.getLocation();
        Location targetLocation = configModel.getTargetLocation(configModel.getTargets().get(targetName), adjust);
        double eyeHeight = 1.62f;
        playerLocation.setY(playerLocation.getY() + eyeHeight);

        if (playerLocation.getWorld() != targetLocation.getWorld()) {
            throw new Exception(configModel.getMessages().get("invalid-world"));
        }

        return new Location[] { playerLocation, targetLocation };
    }

    public static String getDirection(Player player, String targetName) throws Exception {
        Map<String, String> symbols = plugin.getFileManager().getConfigModel().getSymbols();
        double targetYaw = getYaw(player, targetName);
        double targetPitch = getPitch(player, targetName);
        double playerYaw = player.getLocation().getYaw();

        double yaw = targetYaw - playerYaw;

        yaw = (yaw + 360) % 360; // Normalizar a 0-360

        double distance = getDistance(player, targetName);

        if (distance < 1) {
            return symbols.get(Direction.ZERO.getKey());
        } else {
            Direction direction = Direction.fromYaw(yaw);
            Direction verticalDirection = Direction.fromPitch(targetPitch);

            if (verticalDirection != null) {
                return symbols.get(verticalDirection.getKey());
            } else {
                return symbols.get(direction.getKey());
            }
        }
    }

    private static double getAxisValue(Location location, char axis) {
        switch (axis) {
            case 'x':
                return location.getX();
            case 'y':
                return location.getY();
            case 'z':
                return location.getZ();
            default:
                return 0.0f;
        }
    }
}
