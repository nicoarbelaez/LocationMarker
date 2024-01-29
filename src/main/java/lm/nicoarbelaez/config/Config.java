package lm.nicoarbelaez.config;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import lm.nicoarbelaez.LocationMarker;
import lm.nicoarbelaez.model.ConfigModel;
import lm.nicoarbelaez.util.OtherUtils;

public class Config extends FileHandler {
    private final ConfigModel config;

    public Config(LocationMarker plugin, ConfigModel config) {
        super("config.yml", plugin);
        this.config = config;
    }

    public void load() {
        OtherUtils.printDebug(getPlugin().isDebug(), this, "Loading config...");
        loadConfig();
        CommentedConfigurationNode node = getConfigNode();

        String version = node.node("version").getString();
        Map<String, String> symbols = new HashMap<>();
        Map<String, Map<String, String>> targets = new HashMap<>();

        ConfigurationNode symbolsNode = node.node("symbols");
        ConfigurationNode targetsNode = node.node("targets");
        ConfigurationNode messagesNode = node.node("messages");

        for (ConfigurationNode symbol : symbolsNode.childrenMap().values()) {
            symbols.put(symbol.key().toString(), symbol.getString());
        }

        for (ConfigurationNode target : targetsNode.childrenMap().values()) {
            Map<String, String> targetMap = new HashMap<>();
            for (ConfigurationNode targetChild : target.childrenMap().values()) {
                targetMap.put(targetChild.key().toString(), targetChild.getString());
            }
            targets.put(target.key().toString(), targetMap);
        }

        for (ConfigurationNode message : messagesNode.childrenMap().values()) {
            config.getMessages().put(message.key().toString(), message.getString());
        }

        config.setVersion(version);
        config.setSymbols(symbols);
        config.setTargets(targets);
        OtherUtils.printDebug(getPlugin().isDebug(), this, config.toString());
        OtherUtils.printDebug(getPlugin().isDebug(), this, "Config loaded!");
    }

    public void save() throws SerializationException {
        OtherUtils.printDebug(getPlugin().isDebug(), this, "Saving config...");
        CommentedConfigurationNode node = getConfigNode();

        node.node("version").set(config.getVersion());

        for (Map.Entry<String, String> entry : config.getSymbols().entrySet()) {
            node.node("symbols", entry.getKey()).set(entry.getValue());
        }

        for (Map.Entry<String, Map<String, String>> entry : config.getTargets().entrySet()) {
            for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
                node.node("targets", entry.getKey(), innerEntry.getKey()).set(innerEntry.getValue());
            }
        }

        for (Map.Entry<String, String> entry : config.getMessages().entrySet()) {
            node.node("messages", entry.getKey()).set(entry.getValue());
        }

        saveConfig();
        OtherUtils.printDebug(getPlugin().isDebug(), this, config.toString());
        OtherUtils.printDebug(getPlugin().isDebug(), this, "Config saved!");
    }

    public ConfigModel getConfig() {
        return config;
    }
}
