package eu.devsmarket.screenshare;

import eu.devsmarket.screenshare.commands.CommandManager;
import eu.devsmarket.screenshare.commands.Commands;
import eu.devsmarket.screenshare.commands.impl.ScreenCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ScreenPlugin extends JavaPlugin {
    public static FileConfiguration CONFIG;
    private final CommandManager commandManager = CommandManager.getInstance();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        CONFIG = getConfig();
        commandManager.registerCommand(
                new ScreenCommand()
        );
        getLogger().info("ScreenShare Plugin - by 0WhiteDev [devsmarket.eu] has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("ScreenShare Plugin - by 0WhiteDev [devsmarket.eu] has been disabled");
    }
}
