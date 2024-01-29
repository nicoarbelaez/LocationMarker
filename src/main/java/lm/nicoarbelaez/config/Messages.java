package lm.nicoarbelaez.config;

import lm.nicoarbelaez.LocationMarker;

public class Messages extends FileHandler {
    public Messages(LocationMarker plugin) {
        super("messages.yml", plugin);
    }

}
