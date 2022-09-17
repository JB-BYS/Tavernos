package fr.chilli.Gui.pay;

import fr.chilli.Gui.Utils.menu.PaginatedMenu;
import fr.chilli.Gui.Utils.menu.PlayerMenuUtility;
import fr.chilli.Gui.pay.PayConfirmMenu;
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

public class PayPlayerMenu extends PaginatedMenu {

    public PayPlayerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GREEN+"Envoyer de l'argent";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ArrayList<Player> players = new ArrayList<Player>(getServer().getOnlinePlayers());

        if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {

            PlayerMenuUtility playerMenuUtility = Main.getPlayerMenuUtility(p);
            playerMenuUtility.setPlayerToKill(Bukkit.getPlayer(UUID.fromString(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(), "uuid"), PersistentDataType.STRING))));

            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage(ChatColor.GREEN + "Combien d'argent veux tu envoyer ?");
            Main.getPlugin().event.put((Player) e.getWhoClicked(),true);
            Main.getPlugin().map.clear();
            while (Main.getPlugin().map.get(e.getWhoClicked()) == null){
                //waiting
            }
            Main.getPlugin().event.put((Player) e.getWhoClicked(),false);
            checkInput(Main.getPlugin().map.get(e.getWhoClicked()),e);

        }else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {

            //close inventory
            p.closeInventory();

        }else if(e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)){
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")){
                if (page == 0){
                    p.sendMessage(ChatColor.GRAY + "You are already on the first page.");
                }else{
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")){
                if (!((index + 1) >= players.size())){
                    page = page + 1;
                    super.open();
                }else{
                    p.sendMessage(ChatColor.GRAY + "You are on the last page.");
                }
            }
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
    public void checkInput(String args, InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Main plugin = Main.getPlugin();
        try
        {
            Integer.parseInt(args);
            new PayConfirmMenu(playerMenuUtility).open();


        }
        catch (NumberFormatException t)
        {
            e.getWhoClicked().sendMessage("veuillez entrer un chiffre valide");
        }
    }
}