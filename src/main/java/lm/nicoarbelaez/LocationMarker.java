package lm.nicoarbelaez;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import lm.nicoarbelaez.api.ExpansionLM;
import lm.nicoarbelaez.api.LocationMarkerAPI;
import lm.nicoarbelaez.command.MainCommand;
import lm.nicoarbelaez.config.FileManager;
import lm.nicoarbelaez.util.OtherUtils;

public final class LocationMarker extends JavaPlugin {
    public static final String PREFIX = "[LocationMarker]";
    private final boolean isDebug = true;
    
    private static LocationMarkerAPI locationMakerAPI;
    private static FileManager fileManager;

    @Override
    public void onEnable() {
        printDebuggingPoster();

        System.out.print("LocationMarker has been enabled!");
        fileManager = new FileManager(this);
        fileManager.load();
        locationMakerAPI = new LocationMarkerAPI(this);

		if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
			new ExpansionLM(this).register();
		}
        
        registerCommands();

        printDebuggingPoster();
    }

    @Override
    public void onDisable() {
        printDebuggingPoster();

        // fileManager.save();
        System.out.print("LocationMarker has been disabled!");

        printDebuggingPoster();
    }

    private void registerCommands() {
        this.getCommand("locationmarker").setExecutor(new MainCommand(this));
        OtherUtils.printDebug(isDebug(), this, "MainCommand has been registered!");
    }

    private void printDebuggingPoster() {
        if (!isDebug()) {
            return;
        }
        String str = "&6====================" + "&c[debug on]&f " + PREFIX + "&6====================";
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', str));
    }

    public boolean isDebug() {
        return isDebug;
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public static LocationMarkerAPI getLocationMakerAPI() {
        return locationMakerAPI;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
