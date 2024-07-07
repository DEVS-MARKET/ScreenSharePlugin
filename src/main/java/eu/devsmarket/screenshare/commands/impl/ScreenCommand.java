package eu.devsmarket.screenshare.commands.impl;

import eu.devsmarket.screenshare.ScreenPlugin;
import eu.devsmarket.screenshare.commands.Commands;
import eu.devsmarket.screenshare.logic.ScreenCreator;
import eu.devsmarket.screenshare.utils.MessageUtil;
import eu.devsmarket.screenshare.utils.TabCompleteObj;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class ScreenCommand implements Commands {
    private int bukkitTaskId = 0;

    @Override
    public String getName() {
        return "screen";
    }

    @Override
    public void runCommand(String[] args, CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 0) {
                switch (args[0].toLowerCase(Locale.ROOT)) {
                    case "turn" -> onTurnCommand(args, player);
                    case "reload" -> onReloadCommand(player);
                }
            }else{
                onHelpCommand(player);
            }
        }
    }

    @Override
    public void initializeCommand() {
        PluginCommand screenCommand = screenPlugin.getCommand(getName());
        Objects.requireNonNull(screenCommand).setExecutor(commandExecutor);
        screenCommand.setTabCompleter(commandExecutor);
    }


    @Override
    public ArrayList<TabCompleteObj> getTips() {
        ArrayList<TabCompleteObj> tipsList = new ArrayList<>();
        tipsList.add(new TabCompleteObj(0, getName(), new String[]{"turn", "reload"}));
        tipsList.add(new TabCompleteObj(1, "turn", new String[]{"on", "off"}));
        return tipsList;
    }

    private void onTurnCommand(String[] args, Player player){
        if(args[1] != null) {
            ScreenCreator screenCreator = new ScreenCreator();
            if (args[1].equalsIgnoreCase("on")) {
                enableScreen(screenCreator, player);
            } else {
                disableScreen(screenCreator, player);
            }
        }else{
            onHelpCommand(player);
        }
    }

    private void enableScreen(ScreenCreator screenCreator, Player player){
        MessageUtil.sendMessageToPlayer("ScreenSharing was turned &aon&7!", player);
        bukkitTaskId = screenCreator.renderPixelScreen(player.getLocation());
    }

    private void disableScreen(ScreenCreator screenCreator, Player player){
        if(bukkitTaskId != 0) {
            MessageUtil.sendMessageToPlayer("ScreenSharing was turned &coff&7!", player);
            Bukkit.getScheduler().cancelTask(bukkitTaskId);
            screenCreator.despawnAll();
        }else{
            MessageUtil.sendMessageToPlayer("ScreenSharing is not enabled!", player);
        }
    }

    private void onReloadCommand(Player player){
        screenPlugin.reloadConfig();
        ScreenPlugin.CONFIG = screenPlugin.getConfig();
        MessageUtil.sendMessageToPlayer("ScreenShare Plugin was reloaded &asuccessful&7!", player);
    }

    private void onHelpCommand(Player player){
        MessageUtil.sendMessageToPlayer("Available commands:", player);
        MessageUtil.sendMessageToPlayer("/screen turn on/off &8- &fTurn on/off the screensharing", player);
        MessageUtil.sendMessageToPlayer("/screen reload &8- &fReload the plugin", player);
    }
}
