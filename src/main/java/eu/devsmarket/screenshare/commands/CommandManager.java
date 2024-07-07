package eu.devsmarket.screenshare.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    private static CommandManager commandManager;
    private final ArrayList<Commands> commandsList = new ArrayList<>();

    public static CommandManager getInstance(){
        if(commandManager == null){
            commandManager = new CommandManager();
        }
        return commandManager;
    }

    public void registerCommand(Commands... commands){
        commandsList.addAll(Arrays.asList(commands));
        commandsList.forEach(Commands::initializeCommand);
    }

    public boolean handleCommand(String commandName, CommandSender sender, String[] args){
        for (Commands cmd : commandManager.getCommandsList()) {
            if (commandName.equalsIgnoreCase(cmd.getName())) {
                cmd.runCommand(args, sender);
            }
        }
        return true;
    }

    public List<String> handleTabComplete(String commandName, String[] args){
        List<String> completions = new ArrayList<>();
        for (Commands cmd : commandManager.getCommandsList()) {
            if (commandName.equalsIgnoreCase(cmd.getName()) && args.length > 0) {
                completions.clear();
                cmd.getTips().forEach(s -> {
                    if (args.length == 1 && s.getIndex() == 0) {
                        completions.addAll(Arrays.asList(s.getTips()));
                    } else if (args.length > 1 && (args[args.length - 2].equalsIgnoreCase(s.getMainWord()) && args.length - 1 == s.getIndex())){
                        completions.addAll(Arrays.asList(s.getTips()));
                    }
                });
            }
        }
        return completions;
    }

    public ArrayList<Commands> getCommandsList() {
        return commandsList;
    }
}
