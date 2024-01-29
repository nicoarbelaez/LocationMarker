package lm.nicoarbelaez.config;

import org.spongepowered.configurate.serialize.SerializationException;

import lm.nicoarbelaez.LocationMarker;
import lm.nicoarbelaez.model.ConfigModel;
import lm.nicoarbelaez.util.OtherUtils;

public class FileManager {
    private final LocationMarker plugin;
    private final ConfigModel configModel;
    private final Config config;

    public FileManager(LocationMarker plugin) {
        this.plugin = plugin;
        this.configModel = new ConfigModel(plugin);
        this.config = new Config(plugin, configModel);

        OtherUtils.printDebug(plugin.isDebug(), this, configModel.toString());
    }

    public boolean load() {
        config.load();
        return true;
    }

    public boolean save() {
        if (ConfigModel.isNullAttributeDetected()) {
            try {
                config.save();
            } catch (SerializationException e) {
                plugin.getLogger().warning("Error saving config file!");
            }
        }
        return true;
    }

    public ConfigModel getConfigModel() {
        return configModel;
    }
}
