package src.net.minecraft.cloth.commands;

import src.net.minecraft.core.ICommandListener;

public class help {
    //Help code -- ChessChicken
    public static void command(String username, ICommandListener icommandlistener, String command){
        String[] getArgs = command.split(" ");
        if(getArgs.length == 2)
        {
            if(getArgs[1].equalsIgnoreCase("1"))
            {
                icommandlistener.log("§bConsole commands (page 1)");
                icommandlistener.log("§a/help <id>  or  ? shows this message");
                icommandlistener.log("§a/kick <player> §bremoves a player from the server");
                icommandlistener.log("§a/ban <player> §bbans a player from the server");
                icommandlistener.log("§a/pardon <player> §bpardons a banned player");
                icommandlistener.log("§a/ban-ip <ip> §bbans an IP address");
                icommandlistener.log("§a/pardon-ip <ip> §bpardons a banned IP address");
                icommandlistener.log("§a/op <player> §bturns a player into an op");
                icommandlistener.log("§a/deop <player> §bremoves op status from a player");
                icommandlistener.log("§a/tp <player1> <player2> §bmoves player1 to player2");
                icommandlistener.log("§a/tpcord <player1><x><y><z> §bmoves player to the coords");
                return;
            }
            if(getArgs[1].equalsIgnoreCase("2"))
            {
                icommandlistener.log("§bConsole commands (page 2)");
                icommandlistener.log("§a/give <player><name string>[num] §bgives a player a resource");
                icommandlistener.log("§a/giveid <player><id>[num] §bgives a player a resource taking numeric ID");
                icommandlistener.log("§a/tell <player><message> §bsends a private message to a player");
                icommandlistener.log("§a/stop §bgracefully stops the server");
                icommandlistener.log("§a/save-all §bforces a server-wide level save");
                icommandlistener.log("§a/save-off §bdisables terrain saving (useful for backup scripts)");
                icommandlistener.log("§a/save-on §bre-enables terrain saving");
                icommandlistener.log("§a/list §blists all currently connected players");
                icommandlistener.log("§a/time set <day/night/number of ticks> §bchanges time");
                icommandlistener.log("§a/say <message> §bbroadcasts a message to all players");
                return;
            }

            icommandlistener.log("§bTo run the server without a gui, start it like this:");
            icommandlistener.log("§bjava -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
        }else
        {
            icommandlistener.log("§cError in command arguments, try §a/help 1");
        }
    }
}
