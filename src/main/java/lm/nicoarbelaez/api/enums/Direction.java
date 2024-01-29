package lm.nicoarbelaez.api.enums;

public enum Direction {
    UP("up"),
    DOWN("down"),
    NORTHEAST("northeast"),
    NORTHWEST("northwest"),
    SOUTHEAST("southeast"),
    SOUTHWEST("southwest"),
    RIGHT("right"),
    LEFT("left"),
    ASCEND("ascend"),
    DESCEND("descend"),
    ZERO("zero");

    private final String key;

    Direction(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public static Direction fromYaw(double yaw) {
        if (yaw <= 22.5 || yaw > 337.5) {
            return UP;
        } else if (yaw > 22.5 && yaw <= 67.5) {
            return NORTHEAST;
        } else if (yaw > 67.5 && yaw <= 112.5) {
            return RIGHT;
        } else if (yaw > 112.5 && yaw <= 157.5) {
            return SOUTHEAST;
        } else if (yaw > 157.5 && yaw <= 202.5) {
            return DOWN;
        } else if (yaw > 202.5 && yaw <= 247.5) {
            return SOUTHWEST;
        } else if (yaw > 247.5 && yaw <= 292.5) {
            return LEFT;
        } else if (yaw > 292.5 && yaw <= 337.5) {
            return NORTHWEST;
        } else {
            throw new IllegalArgumentException("Invalid yaw: " + yaw);
        }
    }

    public static Direction fromPitch(double pitch) {
        if (pitch <= -75) {
            return ASCEND;
        } else if (pitch >= 75) {
            return DESCEND;
        } else {
            return null;
        }
    }
}