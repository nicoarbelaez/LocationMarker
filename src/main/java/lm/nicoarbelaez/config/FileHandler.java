package lm.nicoarbelaez.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import lm.nicoarbelaez.LocationMarker;

public class FileHandler {
    private CommentedConfigurationNode configNode;
    private final LocationMarker plugin;
    private final Path filePath;
    private final String fileName;
    private boolean firstTime;

    public FileHandler(String fileName, LocationMarker plugin) {
        this.plugin = plugin;
        this.fileName = fileName;

        this.configNode = null;
        File fileConfig = new File(plugin.getDataFolder(), fileName);
        this.filePath = fileConfig.toPath();
        this.firstTime = false;

        if (!fileConfig.exists()) {
            plugin.saveResource(fileName, false);
            firstTime = true;
        }
    }

    public void reloadConfig() {
        loadConfig();
    }

    public void saveConfig() {
        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .nodeStyle(NodeStyle.BLOCK)
                .path(filePath)
                .build();

        try {
            loader.save(configNode);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not be saved " + fileName + ": " + e.getMessage());
        }
    }

    public void loadConfig() {
        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .nodeStyle(NodeStyle.BLOCK)
                .path(filePath)
                .build();

        try {
            configNode = loader.load();
        } catch (IOException e) {
            plugin.getLogger().severe("Could not be loaded " + fileName + ": " + e.getMessage());
        }
    }

    public CommentedConfigurationNode getConfigNode() {
        return configNode;
    }

    public void setConfigNode(CommentedConfigurationNode configNode) {
        this.configNode = configNode;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public LocationMarker getPlugin() {
        return plugin;
    }
}
