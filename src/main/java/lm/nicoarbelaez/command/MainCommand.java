package lm.nicoarbelaez.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import lm.nicoarbelaez.LocationMarker;

public class MainCommand implements CommandExecutor, TabCompleter {

    private final LocationMarker plugin;

    public MainCommand(LocationMarker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("locationmaker")) {
            if (args.length == 0) {
                sender.sendMessage("¡Bienvenido a LocationMaker!");
                return true;
            } else if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage("Este es el comando de información de LocationMaker.");
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onTabComplete'");
    }

    public LocationMarker getPlugin() {
        return plugin;
    }
}
