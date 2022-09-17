package fr.chilli.Gui;

import fr.chilli.Gui.Utils.menu.PaginatedMenu;
import fr.chilli.Gui.Utils.menu.PlayerMenuUtility;
import fr.chilli.Gui.balance.BalancePlayerMenu;
import fr.chilli.Gui.pay.PayPlayerMenu;
import fr.chilli.Gui.security.SecurityMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getServer;

public class AccountPlayerMenu extends PaginatedMenu {

    public AccountPlayerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GREEN+"Mon Compte TheTavern";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ArrayList<Player> players = new ArrayList<Player>(getServer().getOnlinePlayers());

        switch (e.getCurrentItem().getType())
        {
            case RED_WOOL:
                p.closeInventory();
                new SecurityMenu(playerMenuUtility).open();
                break;
            case BARRIER:
                p.closeInventory();
                break;
            case DIAMOND:
                p.closeInventory();
                new PayPlayerMenu(playerMenuUtility).open();
                break;
            case BOOK:
                p.closeInventory();
                new BalancePlayerMenu(playerMenuUtility).open();
                break;
        }
    }

    @Override
    public void setMenuItems() {

        addMenuBorder();
        ItemStack playerItem = new ItemStack(Material.DIAMOND, 1);
        ItemMeta playerMeta = playerItem.getItemMeta();
        playerMeta.setDisplayName(ChatColor.RED + "Envoyer de l'argent");
        playerItem.setItemMeta(playerMeta);
        ItemStack security = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta securitymeta = playerItem.getItemMeta();
        securitymeta.setDisplayName(ChatColor.RED + "Sécurité du compte");
        security.setItemMeta(securitymeta);
        ItemStack balance = new ItemStack(Material.BOOK, 1);
        ItemMeta balancemeta = balance.getItemMeta();
        balancemeta.setDisplayName(ChatColor.RED + "Argent");
        balance.setItemMeta(balancemeta);

        inventory.setItem(31,playerItem);
        inventory.setItem(30,security);
        inventory.setItem(29,balance);

    }


}
