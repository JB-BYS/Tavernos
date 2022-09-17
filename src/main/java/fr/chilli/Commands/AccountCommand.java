package fr.chilli.Commands;

import fr.chilli.Gui.AccountPlayerMenu;
import fr.chilli.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AccountCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            //create the menu and then open it for the player
            new AccountPlayerMenu(Main.getPlayerMenuUtility(p)).open();

        }

        return true;
    }

}
