package eu.devsmarket.screenshare.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandRunner implements CommandExecutor, TabCompleter {

    private final CommandManager commandManager = CommandManager.getInstance();

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        return commandManager.handleTabComplete(command.getName(), args);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        return commandManager.handleCommand(command.getName(), sender, args);
    }

}
