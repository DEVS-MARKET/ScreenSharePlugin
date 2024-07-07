package eu.devsmarket.screenshare.commands;


import eu.devsmarket.screenshare.ScreenPlugin;
import eu.devsmarket.screenshare.utils.TabCompleteObj;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public interface Commands {
    ScreenPlugin screenPlugin = ScreenPlugin.getPlugin(ScreenPlugin.class);
    CommandRunner commandExecutor = new CommandRunner();
    String getName();
    void runCommand(String[] args, CommandSender sender);
    void initializeCommand();
    ArrayList<TabCompleteObj> getTips();
}
