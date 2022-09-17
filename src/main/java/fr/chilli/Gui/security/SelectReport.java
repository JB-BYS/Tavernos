package fr.chilli.Gui.security;

import fr.chilli.Gui.Utils.menu.PaginatedMenu;
import fr.chilli.Gui.Utils.menu.PlayerMenuUtility;
import fr.chilli.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class SelectReport extends PaginatedMenu {

    public SelectReport(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GREEN+"Quel joueur est en tord ?";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ArrayList<Player> players = new ArrayList<Player>(getServer().getOnlinePlayers());

        switch (e.getCurrentItem().getType()){
            case PLAYER_HEAD:
                PlayerMenuUtility playerMenuUtility = Main.getPlayerMenuUtility(p);
                playerMenuUtility.setPlayerToKill(Bukkit.getPlayer(UUID.fromString(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(), "uuid"), PersistentDataType.STRING))));

                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage(ChatColor.GREEN + "Quel est le soucis avec ce joueur ?");
                Main.getPlugin().event.put((Player) e.getWhoClicked(),true);
                Main.getPlugin().map.clear();
                while (Main.getPlugin().map.get(e.getWhoClicked()) == null){
                    //waiting
                }
                Main.getPlugin().event.put((Player) e.getWhoClicked(),false);
                if (!(playerMenuUtility.getPlayerToKill().getName() == p.getName())) {
                    p.performCommand("mail send chilli_pepper " + "Joueur en tord: " + playerMenuUtility.getPlayerToKill().getName() + ChatColor.RED + " Cause: " + Main.getPlugin().map.get(e.getWhoClicked()) + " Envoyé par: " + p.getName());
                    p.performCommand("mail send l0lilian0l " + "Joueur en tord: " + playerMenuUtility.getPlayerToKill().getName() + ChatColor.RED + " Cause: " + Main.getPlugin().map.get(e.getWhoClicked()) + " Envoyé par: " + p.getName());
                }
                else{
                    p.sendMessage(ChatColor.RED+"Essaye tu réellement de t'auto niquer ?");
                }
                break;
            case BARRIER:
                p.closeInventory();

        }
    }

    @Override
    public void setMenuItems() {

        addMenuBorder();

        //The thing you will be looping through to place items
        ArrayList<Player> players = new ArrayList<Player>(getServer().getOnlinePlayers());

        ///////////////////////////////////// Pagination loop template
        if(players != null && !players.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= players.size()) break;
                if (players.get(index) != null){
                    ///////////////////////////

                    //Create an item from our collection and place it into the inventory
                    ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD, 1);
                    ItemMeta playerMeta = playerItem.getItemMeta();
                    playerMeta.setDisplayName(ChatColor.RED + players.get(index).getDisplayName());

                    playerMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "uuid"), PersistentDataType.STRING, players.get(index).getUniqueId().toString());
                    playerItem.setItemMeta(playerMeta);

                    inventory.addItem(playerItem);

                    ////////////////////////
                }
            }
        }
        ////////////////////////




    }

}
